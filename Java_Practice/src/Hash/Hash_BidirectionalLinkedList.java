package Hash;

public class Hash_BidirectionalLinkedList {
    class LinkedNode {
        private String key; //key for hash table
        private String value;
        private LinkedNode next; //next node in the directional linked list
        private LinkedNode prev; //previous node in the directional linked list
        private LinkedNode hnext; //next node in the chain of nodes with the same hash value
        private LinkedNode hprev; //previous node in the chain of nodes with the same hash value

        public LinkedNode(String key, String value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
            this.hnext = null;
            this.hprev = null;
        }

        public LinkedNode(){
            this.key = null;
            this.value = null;
            this.next = null;
            this.prev = null;
            this.hnext = null;
            this.hprev = null;
        }

        public int hashCode(){ //coding string to int
            if(key==null) return 0;
            else{
                int hash = 0;
                for(int i=0; i<key.length(); i++){
                    hash = (hash<<5) + key.charAt(i);
                }
                return hash;
            }
        }
    }

    private LinkedNode[] buckets; //hashtable buckets. Each bucket is the sentinel node of the chain of nodes with the same hash value
    private LinkedNode[] expansion_buckets; //new hashtable buckets when expanding. if it is not null, expansion is in progress
    private int index_oldHashTable; //index of the bucket in the old hashtable that is being moved to the new hashtable
    private int capacity; //bucket array size
    private int filled_buckets; //number of buckets with at least one node
    private int size; //number of nodes.
    private double loadFactor; //taken buckets over capacity
    private final double MAX_LOAD_FACTOR = 0.75;
    LinkedNode sentinel; //sentinel node for the head of the linked list;

    public int hash(LinkedNode node, int length){ //length is the size of the bucket array
        int hashcode= node.hashCode();
        return (hashcode ^ (hashcode>>>16)) & (length-1);
    }

    public Hash_BidirectionalLinkedList(int capacity){
        this.capacity = capacity;
        this.filled_buckets = 0;
        this.size = 0;
        this.loadFactor = 0;
        this.buckets = new LinkedNode[capacity]; //all elements in the array are null
        for(int i=0; i<capacity; i++) {
            this.buckets[i] =new LinkedNode(); //initialize all buckets as sentinel nodes
            this.buckets[i].hnext = this.buckets[i];
            this.buckets[i].hprev = this.buckets[i];
        }
        this.expansion_buckets = null;
        this.index_oldHashTable = 0;
        this.sentinel = new LinkedNode();
        this.sentinel.next = this.sentinel;
        this.sentinel.prev = this.sentinel;
    }

    //push a new node to the end of the bidirectional linkedlist and the hashtable
    public void push_back(String key_in,String value_in){
        LinkedNode new_node= new LinkedNode(key_in, value_in);
        //add new node to the end of the linked list
        new_node.next = sentinel;
        new_node.prev = sentinel.prev;
        sentinel.prev.next = new_node;
        sentinel.prev = new_node;
        //add new node to the hash table
        if(loadFactor>=MAX_LOAD_FACTOR) __expand_double();
        if(expansion_buckets!=null){  //expansion is in progress
            __hashtable_insertNew_moveOld(new_node);
        }else{ //no expansion
            __hashtable_insert(new_node);
        }
        size++;
    }

    //insert nodes to buckets
    public void __hashtable_insert(LinkedNode node){
        int i=hash(node,capacity); //i is index of the bucket
        if(buckets[i].hnext==buckets[i]){ //blank bucket
            filled_buckets++;
            loadFactor = (double)filled_buckets/capacity;
        }
        node.hnext=buckets[i];
        node.hprev=buckets[i].hprev;
        buckets[i].hprev.hnext=node;
        buckets[i].hprev=node;
    }

    public void __expand_double(){
        capacity*=2;
        expansion_buckets = new LinkedNode[capacity];
        for(int i=0; i<capacity; i++){
            expansion_buckets[i] = new LinkedNode();
            expansion_buckets[i].hnext = expansion_buckets[i];
            expansion_buckets[i].hprev = expansion_buckets[i];
        }
        filled_buckets = 0;
        loadFactor = 0;
    }

    //insert a new node to the new hashtable, and move a node from the old hashtable to the new hashtable
    public void __hashtable_insertNew_moveOld(LinkedNode node){
        //insert new node
        int i=hash(node,capacity); //i is index of node to be inserted in the expansion_buckets
        if(expansion_buckets[i]==expansion_buckets[i].hnext){ //blank bucket
            filled_buckets++;
            loadFactor = (double)filled_buckets/capacity;
        }
        node.hnext=expansion_buckets[i];
        node.hprev=expansion_buckets[i].hprev;
        expansion_buckets[i].hprev.hnext=node;
        expansion_buckets[i].hprev=node;

        //move a node from the old hashtable to the new hashtable
        //find the non-empty bucket in the old hashtable
        while(index_oldHashTable<(capacity>>>1) && buckets[index_oldHashTable].hnext==buckets[index_oldHashTable]) index_oldHashTable++;
        if(index_oldHashTable>=(capacity>>>1)){ //all buckets in the old hashtable are empty, expansion is done.
            buckets = expansion_buckets;
            expansion_buckets = null;
            index_oldHashTable = 0;
        }else{//move the first node in the non-empty bucket to the new hashtable
            LinkedNode move_node = buckets[index_oldHashTable].hnext;
            buckets[index_oldHashTable].hnext = move_node.hnext;
            move_node.hnext.hprev = buckets[index_oldHashTable];
            i=hash(move_node,capacity); //i is index of the bucket in the expansion_buckets
            if(expansion_buckets[i]==expansion_buckets[i].hnext){ //blank bucket
                filled_buckets++;
                loadFactor = (double)filled_buckets/capacity;
            }
            move_node.hnext=expansion_buckets[i];
            move_node.hprev=expansion_buckets[i].hprev;
            expansion_buckets[i].hprev.hnext=move_node;
            expansion_buckets[i].hprev=move_node;
        }
    }

    //print all nodes in the view of bidirectional linked list
    public void print_Viewlinkedlist(){
        System.out.println("--------------------linked list-----------------------");
        LinkedNode current = sentinel.next;
        while(current!=sentinel){
            System.out.print("("+current.key+","+current.value+") ");
            current = current.next;
        }
        System.out.println();
    }

    //print all nodes in the view of hashtable
    public void print_Viewhashtable(){
        System.out.println("--------------------hashtable-----------------------");
        if(expansion_buckets!=null){
            System.out.println("new hashtable: ");
            for(int i=0; i<capacity; i++){
                System.out.print("bucket["+i+"]: ");
                LinkedNode current = expansion_buckets[i].hnext;
                while(current!=expansion_buckets[i]){
                    System.out.print("("+current.key+","+current.value+")");
                    current = current.hnext;
                }
                System.out.println();
            }
            System.out.println("old hashtable: ");
            for(int i=0; i<capacity>>>1; i++){
                System.out.print("bucket["+i+"]: ");
                LinkedNode current = buckets[i].hnext;
                while(current!=buckets[i]){
                    System.out.print("("+current.key+","+current.value+")");
                    current = current.hnext;
                }
                System.out.println();
            }
        }else{
            System.out.println("hashtable: ");
            for(int i=0; i<capacity; i++){
                System.out.print("bucket["+i+"]: ");
                LinkedNode current = buckets[i].hnext;
                while(current!=buckets[i]){
                    System.out.print("("+current.key+","+current.value+")");
                    current = current.hnext;
                }
                System.out.println();
            }
        }
    }

    public void getLoadFactor(){
        System.out.println("load factor: "+loadFactor);
    }

    public static void main(String[] args) {
        Hash_BidirectionalLinkedList hbl= new Hash_BidirectionalLinkedList(4);
        hbl.push_back("a", "1");
        hbl.push_back("b", "2");
        hbl.print_Viewhashtable();
        hbl.print_Viewlinkedlist();
        hbl.push_back("c", "3");
        hbl.push_back("d", "4");
        hbl.print_Viewhashtable();
        hbl.print_Viewlinkedlist();
        hbl.push_back("c", "5");
        hbl.push_back("d", "6");
        hbl.print_Viewhashtable();
        hbl.print_Viewlinkedlist();
        hbl.push_back("c", "7");
        hbl.push_back("d", "8");
        hbl.print_Viewhashtable();
        hbl.print_Viewlinkedlist();
    }




}
