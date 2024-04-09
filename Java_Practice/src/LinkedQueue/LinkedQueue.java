package LinkedQueue;

public class LinkedQueue {
    private _LinkedNode head;
    private _LinkedNode tail;
    private int size;

    public LinkedQueue(){
        head=null;
        tail=null;
        size=0;
    }

    public void enqueue(int data){
        if(size==0){
            head=new _LinkedNode(data);
            tail=head;
            size++;
        }
        else{
            _LinkedNode newNode = new _LinkedNode(data);
            tail.setNextNode(newNode);
            tail = newNode;
            size++;
        }
    }

    public int dequeue(){
        if(head==null){   //empty queue
            throw new NullPointerException("empty queue");
        }
        else if(head==tail) {  //one node
            int data = head.getData();
            head = null;
            tail = null;
            size--;
            return data;
        }
        else{  // more than one node
            int data = head.getData();
            head=head.getNextNode();
            size--;
            return data;
        }
    }

    public static void main(String[] args) {
        LinkedQueue queue1=new LinkedQueue();
        queue1.enqueue(1);
        queue1.enqueue(2);
        queue1.enqueue(3);
        System.out.println("Before dequeue, The size is: "+ queue1.size);
        System.out.printf("The Sentence is: %d %d %d",queue1.dequeue(),queue1.dequeue(),queue1.dequeue());
        System.out.println("After dequeue, The size is: "+ queue1.size);
    }

}
