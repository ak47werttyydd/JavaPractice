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
            if(tail_data.val>val){
                System.out.println("push_back failed! Ascending order. The value is smaller than the last node");
                return;
            }

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

    public void pop_back(){
        if(head==null)return;  //no node
        else if(length==1){  //only one node
            head=null;
            tail_data=null;
            for(int i=0;i<max_level;i++){
                tail_index[i]=null;
            }
            length=0;
        }
        else{  //more than one node
            //remove linked nodes in index layers
            LinkedNode cur=tail_data;
            for(int i=0;i<=max_level-1;i++){ //i+1 is the layer no.
                if(tail_index[i].down==cur) {  //index nodes linked to the tail of the data layer
                    cur=tail_index[i]; //record the tail node of the index layer to be removed

                    //update tail node of the index layer
                    //find the head of the index layer
                    LinkedNode layer_head=head;
                    int j=i+1; //layer No.
                    while(j<max_level) {
                        layer_head=layer_head.down;
                        j++;
                    }

                    //find the new tail node of the index layer
                    while(layer_head.next!=tail_index[i]) {
                        layer_head = layer_head.next;
                    }

                    tail_index[i]=layer_head; //update tail node of the index layer
                    tail_index[i].next=null;
                }
            }
            //remove the last node in the data layer
            LinkedNode data_layer_head=head;
            while(data_layer_head.down!=null){
                data_layer_head=data_layer_head.down;
            }
            while(data_layer_head.next!=tail_data){
                data_layer_head=data_layer_head.next;
            }
            data_layer_head.next=null;
            tail_data=data_layer_head;
            length--;
        }
    }

    public void pop_back(int k){
        if(k>length){
            System.out.println("pop_back failed! k is larger than the length of the SkipList");
            return;
        }
        for(int i=0;i<k;i++){
            pop_back();
        }
    }

    //return the last node with value equal to target. e.g. sequence:1 3 3 3 5, target=3, return the last node with value equal to 3
    //Or return the last node with value smaller than target. e.g. sequence: 1 3 3 3 5, target=4, return the last node with value equal to 3
    public LinkedNode searchValue_lastEqualOrSmaller(int target){
        if(length==0) return null;  //no node

        if(tail_data.val<=target) return tail_data;
        else{  //target<tail_data.val
            if(length==1) return null;  //only one node in data layer
            //more than one node in data layer
            LinkedNode cur=head;
            while(cur.down!=null){
                while(cur.next!=null && cur.next.val<=target){
                    cur=cur.next;
                }
                cur=cur.down;
            }
            //cur is in data layer
            while(cur.next!=null && cur.next.val<=target){
                cur=cur.next;
            }
            int Address=System.identityHashCode(cur);
            System.out.println("The address of the last node with value equal to "+target+" is: "+Address);
            return cur;
        }
    }

    //return the first node with value equal to target. e.g. sequence:1 3 3 3 5, target=3, return the first node with value equal to 3
    //Or return the first node with value larger than target. e.g. sequence: 1 3 3 3 5, target=2, return the first node with value equal to 3
    public LinkedNode searchValue_firstEqualOrLarger(int target){   //return the index of the last node with value equal to target
        if(length==0) return null;  //no node
        if(tail_data.val<target) return null;
        else{  //target<=tail_data.val
            if(length==1) return null;  //only one node in data layer
            //more than one node in data layer
            LinkedNode cur=head;
            while(cur.down!=null){
                while(cur.next!=null && cur.next.val<target){  //the index nodes of the target
                    cur=cur.next;
                }
                cur=cur.down;
            }
            //cur is in data layer
            while(cur.next!=null && cur.val<target){  //the first node with value equal to target in data layer
                cur=cur.next;
            }
            int Address=System.identityHashCode(cur);
            System.out.println("The address of the first node with value equal to "+target+" is: "+Address);
            return cur;
        }
    }

    public NodeTrack track_lastEqualOrSmaller(int target){
        if(length==0) return null;  //no node
        else{
            LinkedNode cur=head;
            while(cur.down!=null){
                cur=cur.down;
            } //cur is the smallest onde in the data layer
            if(cur.val>target)return null; //the smallest node in the data layer is larger than target, return null;
            else{ //target >= the smallest node
                NodeTrack nt = new NodeTrack();
                nt.track=new LinkedNode[max_level];  //track[i] is the immediate predecessor of the node， which is at level i+1
                LinkedNode cur2=head;
                int i=max_level-1;  //index of layer, from top to bottom
                while(cur2.down!=null){
                    while(cur2.next!=null && cur2.next.val<=target){
                        cur2=cur2.next;
                    }
                    nt.track[i]=cur2;
                    cur2=cur2.down;
                    i--;
                }//cur is in data layer
                while(cur2.next!=null && cur2.next.val<=target){
                    cur2=cur2.next;
                }
                nt.node=cur2;
                return nt;
            }
        }
    }

    public void insert_back(int val) {
        if (length == 0) {  // no node
            push_back(val);
        } else if (tail_data.val <= val) {  // val is the largest
            push_back(val);
        } else if (val < head.val) {  //val is smaller than the smallest node in the data layer
            //update index level of the previous head node in the data layer
            int new_level = random_level();

            //update new head of the SkipList
            LinkedNode new_head = new LinkedNode(val);
            LinkedNode cur_headNode = new_head;  //it points to the new head node in the skiplist
            LinkedNode pre_headNode = head; //points to the previous head node in the skiplist
            //update head
            head=new_head;
            //link the upmost index layer
            boolean delete; //whether to delete the immediately higher pre_headNode
            if(new_level==max_level){
                cur_headNode.next=pre_headNode;
                delete=false;
            }
            else{
                cur_headNode.next=pre_headNode.next;
                delete=true;
            }
            for (int i = 1; i <= max_level; i++) {
                //create a new head node in (max_level-i)th index layer
                cur_headNode.down = new LinkedNode(val);
                cur_headNode = cur_headNode.down;

                //find the previous head node in the (max_level-i)th index layer
                LinkedNode tmp = pre_headNode;  //record the immediately higher pre_headNode
                pre_headNode=pre_headNode.down;

                //clear up the immediately higher pre_headNode
                if(delete){
                    tmp.val=0;
                    tmp.down=null;
                    tmp.next=null;
                }

                //link the (max_level-i)th index layer
                if( (max_level-i) <= new_level ){
                    cur_headNode.next = pre_headNode;
                    delete=false;
                }
                else{
                    cur_headNode.next=pre_headNode.next;
                    delete=true;
                }
            }
            //After loops, cur_headNode is the new head node in the data layer, pre_head_node is the previous head node in the data layer
            length++;
        } else{ //val is in the middle
            NodeTrack nt=track_lastEqualOrSmaller(val);
//            LinkedNode cur=nt.node;
//            //insert a node after cur
//            LinkedNode tmp=cur.next;
//            cur.next=new LinkedNode(val);
//            cur.next.next=tmp;
//            //update index layers for the new node
            //insert nodes in index layers and data layer
            int new_level = random_level();
            LinkedNode higherNewNode=null;
            for(int i=new_level-1; i>=0; i--){
                //link the new node in the i+1th index layer
                LinkedNode tmp=nt.track[i].next;
                nt.track[i].next=new LinkedNode(val);
                nt.track[i].next.next=tmp;
                //link higher new node in the i+2th index layer to the new node in the i+1th index layer
                if(higherNewNode!=null) higherNewNode.down=nt.track[i].next;
                //update higherNewNode
                higherNewNode=nt.track[i].next;
                //down to the immediate below layer
            } //higherNewNode is the new node in the 1th index layer
            //link the new node in the data layer
            LinkedNode tmp=nt.node.next;
            nt.node.next=new LinkedNode(val);
            nt.node.next.next=tmp;
            if(higherNewNode!=null) higherNewNode.down=nt.node.next;
            length++;
        }
    }

    public int random_level(){
        int level=0;
        double p=0.5D;
        while(Math.random()<p && level<max_level){  //Math.random() generate greater than or equal to 0.0 and less than 1.0.
            level++;
            p/=2;
        }
        return level;
    }


    public void printAddress_lastNode(){
        if(length==0) return;  //no node
        int Address=System.identityHashCode(tail_data);
        System.out.println("The address of the last node is: "+Address);
    }

    public void print_nodes(){
        LinkedNode layer_head = head;
        int level= max_level;
        //print index layer
        while(layer_head.down!=null) { //iterate through each index layer
            LinkedNode cur=layer_head;
            String spaces;
            System.out.print("Index Level "+level+": ");
            while(cur!=null){
                if(cur.next!=null && cur.next.val<10) spaces =" ".repeat((int)Math.pow(2,level+1)-1);
                else spaces =" ".repeat(3*((int)Math.pow(2,level)-1)+1);
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
        System.out.println();
    }

    public static void main(String[] args){
        SkipList s1 = new SkipList(3);
//        int i=0;
//        while(i<100){
//            System.out.println("new_level: "+s1.random_level());
//        }

        s1.push_back(1);
        s1.push_back(2);
        s1.push_back(3);
        s1.push_back(4);
        s1.push_back(5);
        s1.push_back(6);
        s1.push_back(7);
        s1.push_back(8);
        s1.push_back(9);
        s1.printAddress_lastNode();
        s1.push_back(9);
        s1.push_back(9);
        s1.push_back(9);
        s1.push_back(9);
        s1.printAddress_lastNode();
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
        s1.print_nodes();
        System.out.println("insert a new node with value 0");
        s1.insert_back(0);
        s1.print_nodes();

        System.out.println("insert a new node with value 9");
        s1.insert_back(9);
        s1.print_nodes();

        System.out.println("insert a new node with value 21");
        s1.insert_back(21);
        s1.print_nodes();

        System.out.println("insert a new node with value 17");
        s1.insert_back(17);
        s1.print_nodes();
    }
}
