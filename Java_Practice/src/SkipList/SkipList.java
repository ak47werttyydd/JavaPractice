package SkipList;
import java.lang.Math;

public class SkipList {
    private LinkedNode head; //top left node
    private LinkedNode tail_data; //bottom right node
    private LinkedNode[] tail_index; //tail node of each index layer, tail_index[0] is the tail node of the first index layer
    private int max_level; //level for index layer, excluding data layer
    private int length; //data layers' length


    public SkipList(int max_level){
        this.head = null;
        this.tail_data = null;
        this.max_level = max_level;
        this.tail_index = new LinkedNode[max_level];
    }

    public void push_back(int val){
        if(head == null){  //no node
            head = new LinkedNode(val);
            tail_index[max_level-1]=head; //tail of the top index layer
            LinkedNode cur = head;
            for(int i = 1; i<= max_level; i++){
                cur.down = new LinkedNode(val);
                cur= cur.down;
                if(i!=max_level)  tail_index[max_level-1-i]=cur; //when i==max_level, tail_data records the tail of the data layer
            }
            length++;  //length==1
            tail_data =cur;  //the first node in data layer
        }
        else{  //nodes existing
            //add a new node to the right of the last node
            tail_data.next = new LinkedNode(val);
            tail_data = tail_data.next;  //move tail to the new node
            length++;

            //add nodes to index layers
            LinkedNode cur = tail_data;  //the node to be indexed down
            for(int i=1;i<=max_level;i++){
                if( (length-1)%Math.pow(2,i)==0 ){
                    tail_index[i-1].next=new LinkedNode(val);  //add a tail node in the ith index layer
                    tail_index[i-1]=tail_index[i-1].next;  //move tail to the new node
                    tail_index[i-1].down=cur;  //link tail nodes across layers
                    cur=tail_index[i-1];  //move cur to the tail node of the ith index layer
                }
            }
        }
    }

    public void print_nodes(){
        LinkedNode layer_head = head;
        int level= max_level;
        //print index layer
        while(layer_head.down!=null) { //iterate through each index layer
            LinkedNode cur=layer_head;
            String spaces = " ".repeat((int)Math.pow(2,level+1)-1);
            System.out.print("Index Level "+level+": ");
            while(cur!=null){
                System.out.print(cur.val+spaces);
                cur=cur.next;
            }
            System.out.println();
            level--;
            layer_head=layer_head.down;
        }

        //print data layer
        LinkedNode cur=layer_head;
        System.out.print("Data Layer:    ");
        while(cur!=null){
            System.out.print(cur.val+" ");
            cur=cur.next;
        }
    }

    public static void main(String[] args){
        SkipList s1 = new SkipList(3);
        s1.push_back(1);
        s1.push_back(2);
        s1.push_back(3);
        s1.push_back(4);
        s1.push_back(5);
        s1.push_back(6);
        s1.push_back(7);
        s1.push_back(8);
        s1.push_back(9);
        s1.push_back(10);
        s1.push_back(11);
        s1.push_back(12);
        s1.push_back(13);
        s1.push_back(14);
        s1.push_back(15);
        s1.push_back(16);
        s1.push_back(17);
        s1.push_back(18);
        s1.push_back(19);
        s1.push_back(20);
        s1.push_back(21);
        s1.push_back(22);
        s1.push_back(23);
        s1.push_back(24);
        s1.push_back(25);
        s1.push_back(26);
        s1.push_back(27);
        s1.push_back(28);
        s1.push_back(29);
        s1.push_back(30);
        s1.push_back(31);
        s1.push_back(32);
        s1.push_back(33);
        s1.push_back(34);
        s1.push_back(35);
        s1.push_back(36);
        s1.push_back(37);
        s1.push_back(38);
        s1.push_back(39);
        s1.push_back(40);
        s1.push_back(41);
        s1.push_back(42);
        s1.push_back(43);
        s1.push_back(44);
        s1.push_back(45);
        s1.push_back(46);
        s1.push_back(47);
        s1.push_back(48);
        s1.push_back(49);
        s1.push_back(50);
        s1.push_back(51);
        s1.push_back(52);
        s1.push_back(53);
        s1.push_back(54);
        s1.push_back(55);
        s1.push_back(56);
        s1.push_back(57);
        s1.push_back(58);
        s1.push_back(59);
        s1.push_back(60);
        s1.push_back(61);
        s1.print_nodes();
    }
}
