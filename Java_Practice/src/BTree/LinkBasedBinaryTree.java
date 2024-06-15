package BTree;
import java.util.*;

public class LinkBasedBinaryTree {
    public static class LinkedNode {
        private int data;
        private LinkedNode left;
        private LinkedNode right;

        public LinkedNode(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private LinkedNode root;

    public LinkBasedBinaryTree() {
        this.root = null;
    }

    //return the node to be searched
    public List<LinkedNode> search(int data){
        LinkedNode cur=root;
        List<LinkedNode> result=new ArrayList<>();
        while(cur!=null){
            if(data==cur.data){
                result.add(cur);
                cur=cur.right; //go right
            }
            else if(data<cur.data)cur=cur.left;
            else cur=cur.right; //data>cur.data
        }
        return !result.isEmpty() ? result : null ;
    }

    //return the result[0]=parent, result[1]=node with data of the node to be searched
//    public LinkedNode[] search_parent(int data){
//        LinkedNode cur=root;
//        LinkedNode prev=null;
//        LinkedNode[] result=new LinkedNode[2];
//        while(cur!=null){
//            if(data==cur.data){ //found
//                result[0]=prev;
//                result[1]=cur;
//                return result;
//            }
//            //not found, keep searching
//            prev=cur;
//            if(data<cur.data)cur=cur.left;
//            if(data>cur.data)cur=cur.right;
//        }
//        return null;
//    }

    //return the result[0]=parent, result[1]=node with data of the node to be searched
//    public List<LinkedNode> search_parent(int data){
//        LinkedNode cur=root;
//        LinkedNode prev=null;
//        List<LinkedNode> result=new ArrayList<>();
//        while(cur!=null) {
//            if (data == cur.data) { //found
//                result.add(prev);
//                result.add(cur);
//                prev = cur;
//                cur = cur.right; //go right
//            } else {//not found
//                prev = cur;
//                if (data < cur.data) cur = cur.left;
//                else cur = cur.right;
//            }
//        }
//        return !result.isEmpty()?result:null;
//    }

    //return the result[0]=parent, result[1]=node with data of the node to be searched
    public LinkedNode[] search_parent(int data){
        LinkedNode cur=root;
        LinkedNode prev=null;
        LinkedNode[] result=new LinkedNode[2];
        while(cur!=null) {
            if (data == cur.data) { //found
                result[0]=prev;
                result[1]=cur;
                return result;
            } else {//not found
                prev = cur;
                if (data < cur.data) cur = cur.left;
                else cur = cur.right;
            }
        }
        return null;
    }

    //delete all data with the same value
    public void delete(int data){
        while(true){
            //empty tree
            if (root == null) return;
            //find the node to be deleted
            LinkedNode[] parent_current = search_parent(data);
            if (parent_current == null) return; //not found
            //found
            LinkedNode parent = parent_current[0];
            LinkedNode cur = parent_current[1];
            //case 1: current has no child
            if (cur.left == null && cur.right == null) {
                if (parent == null) { //cur is root
                    root = null;
                } else {  //cur is leaf
                    if (parent.left == cur) parent.left = null;
                    if (parent.right == cur) parent.right = null;
                }
            }
            //case 2: current has one child
            else if ((cur.left == null && cur.right != null) || (cur.left != null && cur.right == null)) {
                if (parent == null) { //cur is root
                    if (cur.left != null) root = cur.left;
                    if (cur.right != null) root = cur.right;
                } else { //cur is not root
                    if (parent.left == cur) {
                        parent.left = cur.left != null ? cur.left : cur.right;
                    } else { //parent.right==cur
                        parent.right = cur.left != null ? cur.left : cur.right;
                    }
                }
            }
            //case 3: current has two children
            else if (cur.left != null && cur.right != null) {
                LinkedNode[] parent_and_min = get_parent_min(cur, cur.right); //locate the minimum node in the right subtree
                delete(parent_and_min[0], parent_and_min[1]); //delete the minimum node
                cur.data = parent_and_min[1].data; //replace the current node with the minimum node
            }
        }
    }

    public void delete(LinkedNode parent, LinkedNode cur){
        LinkedNode child;
        if(cur.left!=null)child=cur.left;
        else if(cur.right!=null) child=cur.right;
        else child=null;

        if(parent.left==cur)parent.left=child;
        else parent.right=child;
    }



    public void insert(int data){
        if(root==null) root=new LinkedNode(data);
        else{
            LinkedNode cur=root;
            while(true){
                if(data<cur.data){  // if data is less than or equal to current node's data, go left or put it left
                    if(cur.left==null){
                        cur.left=new LinkedNode(data);
                        return;
                    }else{
                        cur=cur.left;
                    }
                }
                if(data>=cur.data){ //if data is greater than current node's data, go right or put it right
                    if(cur.right==null){
                        cur.right=new LinkedNode(data);
                        return;
                    }else{
                        cur=cur.right;
                    }
                }
            }
        }
    }

    public LinkedNode[] get_parent_min(LinkedNode parent_start,LinkedNode start){
        LinkedNode cur=start;
        LinkedNode prev=parent_start;
        while(cur.left!=null){
            prev=cur;
            cur=cur.left;
        }
        LinkedNode[] result={prev,cur};
        return result;
    }

    public void preOrder_traverse(LinkedNode node){
        if(node==null)return;
        System.out.print(node.data+" ");  //cur
        preOrder_traverse(node.left);
        preOrder_traverse(node.right);
    }

    public void preOrder_traverse(){
        preOrder_traverse(root);
        System.out.println();
    }

    public void inOrder_traverse(LinkedNode node){
        if(node==null)return;
        inOrder_traverse(node.left);
        System.out.print(node.data+" ");  //cur
        inOrder_traverse(node.right);
    }

    public void inOrder_traverse(){
        inOrder_traverse(root);
        System.out.println();
    }

    public void postOrder_traverse(LinkedNode node){
        if(node==null)return;
        postOrder_traverse(node.left);
        postOrder_traverse(node.right);
        System.out.print(node.data+" ");  //cur
    }

    public void postOrder_traverse(){
        postOrder_traverse(root);
        System.out.println();
    }


    public void print_levelwise(){
        if(root==null)return;
        Queue<LinkedNode> q=new LinkedList<>(); //dequeue a node and enqueue its subnodes
        q.add(root);
        int level=1;
        while(!q.isEmpty()){ //loop through all levels
            int size_of_level=q.size();  //size of current level
            System.out.print("level "+level+": ");
            for(int i=0;i<size_of_level;i++){  //deal with a level
                LinkedNode cur=q.poll();  //dequeue
                System.out.print(cur.data+" ");
                if(cur.left!=null)q.add(cur.left); //enqueue left child
                if(cur.right!=null)q.add(cur.right); //enqueue right child
            }
            level++;
            System.out.println();
        }
    }

    public int height(LinkedNode node){
        if(node==null)return 0;
        int left_height=height(node.left);
        int right_height=height(node.right);
        return Math.max(left_height,right_height)+1;
    }

    public static void main(String[] args) {
        LinkBasedBinaryTree tree=new LinkBasedBinaryTree();
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);
        tree.insert(2);
        tree.insert(4);
        tree.insert(6);
        tree.insert(8);
        tree.insert(1);
        tree.insert(9);
        tree.insert(8);
        tree.insert(7);
        System.out.println("Level-wise traversal: ");
        tree.print_levelwise();
        System.out.println();
//        System.out.println("Preorder traversal: ");
//        tree.preOrder_traverse();
//        System.out.println("Inorder traversal: ");
//        tree.inOrder_traverse();
//        System.out.println("Postorder traversal: ");
//        tree.postOrder_traverse();
//        List<LinkedNode> pairs=tree.search_parent(8);  //good
//        int i=1;
//        if(pairs!=null){
//            for(LinkedNode node:pairs){
//                if(i%2==1)System.out.println("parent: "+node.data);
//                else System.out.println("current: "+node.data);
//                i++;
//            }
//        }
        tree.delete(8);
//        tree.delete(2);
        tree.print_levelwise();

//        tree.delete(5);
//        tree.print_levelwise();
//        System.out.println("Inorder traversal is: ");
//        tree.inOrder_traverse();
    }
}
