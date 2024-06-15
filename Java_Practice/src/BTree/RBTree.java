package BTree;
import java.util.LinkedList;
import java.util.Queue;

public class RBTree {
    public static class Node{
        private int data;
        private Node left;
        private Node right;
        private Node parent;
        private boolean isRed;

        public Node(int data){
            this.data=data;
            this.left=null;
            this.right=null;
            this.parent=null;
            this.isRed=true;
        }

        public Node(int data, boolean isRed){
            this.data=data;
            this.left=null;
            this.right=null;
            this.parent=null;
            this.isRed=isRed;
        }

        public String bool2str(){
            return isRed?"red":"black";
        }
    }


    private Node NIL=new Node(999,false);
    //NIL node are leaves with invalid data 999
    // color is black.
    //NIL are actually shared leaves, so we can use the same NIL node as all leaves. So it doesn't have parent
    private Node root; //root node of the tree

    public RBTree(){
        this.root=null;
    }

    public void leftRotate(Node x){ //x was the parent of y, y was the right child of x
        Node y=x.right;
        //turn y's left subtree into x's right subtree
        x.right=y.left;
//        y.left.parent= y.left!=NIL?x:null;  //*
        y.left.parent=x;
        //link x's parent to y
        y.parent=x.parent;  //x must not be NIL, cuz y was x's right child
        if(x.parent==null) root=y;
        else if(x==x.parent.left) x.parent.left=y;
        else x.parent.right=y;
        //put x on y's left
        y.left=x;
        x.parent=y;
    }

    public void rightRotate(Node x){ //x was the parent of y, y was the left child of x
        Node y=x.left;
        //turn y's right subtree into x's left subtree
        x.left=y.right;
//        y.right.parent= y.right!=NIL?x:null; //*
        y.right.parent=x;
        //link x's parent to y
        y.parent=x.parent;  //x must not be NIL, cuz y was x's right child
        if(x.parent==null) root=y;
        else if(x==x.parent.left) x.parent.left=y;
        else x.parent.right=y;
        //put x on y's right
        y.right=x;
        x.parent=y;
    }

    public void insert(int data){
        //create the new node
        Node z=new Node(data); //default color is red
        //Case 0: tree is empty
        if(root==null){
            root=z;
            z.isRed=false; //turn it to black
            z.parent=null;
            z.left=NIL;
            z.right=NIL;
            return;
        }
        // tree is not empty, insert it as a leaf
        Node cur=root;
        while(true){
            if(data<cur.data){
                if(cur.left==NIL){
                    cur.left=z;
                    z.parent=cur;
                    z.left=NIL;
                    z.right=NIL;
                    //z is red
                    break; //insertion done
                }else{ //cur.left!=NIL, go leftwards
                    cur=cur.left;
                }
            }else{ //data>=cur.data
                if(cur.right==NIL){
                    cur.right=z;
                    z.parent=cur;
                    z.left=NIL;
                    z.right=NIL;
                    //z is red
                    break; //insertion done
                }else{ //cur.right!=NIL, go rightwards
                    cur=cur.right;
                }
            }
        }

        //fix the tree
        //z is the focused node, it originates from the inserted node. After iteration, it will be the root or its father is black
        while(z!=root && z.parent.isRed){ //termination condition: z reaches root && z.parent is black
            if(z.parent.parent.left==z.parent){
                Node z_uncle=z.parent.parent.right;
                if(z_uncle.isRed){ //case 1: z's uncle is red
                    z.parent.isRed=false;
                    z_uncle.isRed=false;
//                    z.parent.parent.isRed=true;  //if z's grandparent is root, it should have been black
                    z.parent.parent.isRed=z.parent.parent==root?false:true; //if z's grandparent is root, it should have been black
                    z=z.parent.parent;  //move focused node to its grandparent
                }else{ //z's uncle is black
                    if(z.parent.right==z){ //case 2: z is the right child of its parent (triangle shape)
                        z=z.parent; //move focused node to its parent
                        leftRotate(z);
                    }else{ // case 3: z is the left child of its parent (line shape)
                        z.parent.isRed=false;
                        z.parent.parent.isRed=true;
                        rightRotate(z.parent.parent);
                        //finish fixing
                    }
                }
            }else{ //z.parent is the right child of z's grandparent
                Node z_uncle=z.parent.parent.left;
                if(z_uncle.isRed){ //case 1: z's uncle is red
                    z.parent.isRed=false;
                    z_uncle.isRed=false;
                    z.parent.parent.isRed=true;
//                    z.parent.parent.isRed=z.parent.parent==root?false:true;
                    z=z.parent.parent;  //move z to its grandparent
                    continue;
                }else{ //z's uncle is black
                    if(z.parent.left==z){ //case 2: z's uncle is black, z is the left child of its parent (triangle shape)
                        z=z.parent; //move z to its parent
                        rightRotate(z);
                    }else{ //case 3: z's uncle is black, z is the right child of its parent (line shape)
                        z.parent.isRed=false;
                        z.parent.parent.isRed=true;
                        leftRotate(z.parent.parent);
                        //finish fixing
                    }
                }
            }
        }
    }

    //link u's parent to u's only child v
    public void transplant(Node u, Node v){
        if(u.parent==null) root=v;
        else if(u==u.parent.left) u.parent.left=v;
        else u.parent.right=v;
//        if(v!=NIL)v.parent=u.parent; //*
        v.parent=u.parent;
    }

    public Node searchData(int data){  //if not found, return null. If found, return the node
        Node cur=root;
        while(cur!=null && cur!=NIL){
            if(data==cur.data)return cur;
            if(data<cur.data)cur=cur.left;
            else cur=cur.right;
        }
        return null;
    }

    public Node searchRightMin(Node cur){
        cur=cur.right;
        while(cur.left!=NIL){
            cur=cur.left;
        }
        return cur;
    }

    public void delete(int data){
        Node z=searchData(data);
        boolean y_original_color=z.isRed;
        if(z==null)return; //not found
        Node x;  //z's only child
        //Case 1: z's left child is NIL, z is black(need fix) or red(no need to fix), z's right child is red + both children are NIL
        if(z.left==NIL){  //z's right is x
            x=z.right;
            transplant(z,z.right);
            x.isRed=false;   //turn x to be black
            return;
        //Case 2: z's right child is NIL, z is black, z's left child is red
        }else if(z.right==NIL){ //z's left is x
            x=z.left;
            transplant(z,z.left);
            x.isRed=false;  //turn x to be black
            return;
        }//Case 3: Neither child is NIL
        else{
            Node y=searchRightMin(z);
            y_original_color=y.isRed;
//            if(y.isRed==false){  //maybe not necessary
            x=y.right;
            transplant(y,x);
            //link y and z's right, z's right to be y's right child
            y.right=z.right;
            if(z.right!=NIL)z.right.parent=y;
            //link y and z's parent1
            transplant(z,y);
            //link y and z's left, z's left to be y's left child (i.e. delete z)
            y.left=z.left;
            z.left.parent=y;
            y.isRed=z.isRed;
//            }
        }

        //fix from node x
        if(!y_original_color){  //y_original_color represents the color of z(deleted node), if red, no need to fix
            while(x!=root && x.isRed==false) {
                Node w; //sibling of x
                if (x.parent.left == x) w = x.parent.right;
                else w = x.parent.left; //not shown on instruction
                //Case 1: x's sibling w is red
                if (w.isRed) {
                    w.isRed = false;
                    leftRotate(x.parent);
                    x.parent.isRed = true;
                    w = x.parent.right;    //new w must be black
                }
                //Case 2 and Case 1 can be sequential
                //Case 2: x's sibling w is black, both of w's children are black
                if (w.left.isRed == false && w.right.isRed == false) {
                    w.isRed = true;
                    x = x.parent;
                }else{
                    //Case 3: x's sibling w is black,  w's right child is black
                    if(w.right.isRed==false){
                        w.left.isRed=false;
                        w.isRed=true;
                        rightRotate(w);
                        w=x.parent.right;
                    }
                    //finish Case 3 will lead to Case 4
                    //Case 4: x's sibling w is black, w's right child is red
                    if(x==NIL){
                        x=root;
                        break;
                    }
                    w.isRed=x.parent.isRed;
                    x.parent.isRed=false;
                    w.right.isRed=false;
                    leftRotate(x.parent);  //todo: if x is NIL
                    x=root; //terminate the loop
                }
            }
            x.isRed=false;  //turn root to be black
        }
    }

    public void print_levelwise(){
        if(root==null)return;
        Queue<Node> q=new LinkedList<>(); //queue for level order traversal
        q.add(root);
        int level=1;
        while(!q.isEmpty()){ //loop through all levels
            int size_of_level=q.size();  //size of current level
            System.out.print("level "+level+": ");
            for(int i=0;i<size_of_level;i++){  //deal with a level
                Node cur=q.poll();  //dequeue
                System.out.print("("+cur.data+","+cur.bool2str()+") ");
                if(cur.left!=null)q.add(cur.left); //enqueue left child
                if(cur.right!=null)q.add(cur.right); //enqueue right child
            }
            level++;
            System.out.println();
        }
    }




    public static void main(String[] args){
        RBTree tree=new RBTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(15);
        tree.insert(25);
        tree.insert(5);
        tree.insert(35);
        tree.print_levelwise();
        tree.delete(20);
        tree.delete(35);
        tree.print_levelwise();
        tree.delete(25);
        tree.print_levelwise();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.print_levelwise();
    }
}
