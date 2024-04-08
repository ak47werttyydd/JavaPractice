public class CircularQueue {
    private int[] elements;
    private int head;
    private int end;
    private int size;

    public CircularQueue(int capacity){
        elements=new int[capacity];
        head=0;
        end=0;
        size=0;
    }

    public void enqueue(int data){
        if((end+1)%elements.length==head){
            System.out.println("Queue is full");
        }
        else{
            elements[end]=data;
            end=(end+1)%elements.length;
            size++;
            System.out.printf("Enqueue data: %d, The size is %d\n",data,size);
        }
    }

    public int dequeue() throws EmptyQueueException{
        if(head==end){
            System.out.println("Queue is empty");
            throw new EmptyQueueException("Queue is empty");
        }
        else{
            int data=elements[head];
            head=(head+1)%elements.length;
            size--;
            System.out.println("Dequeue data: "+data);
            return data;
        }
    }

    public class EmptyQueueException extends Exception {
        public EmptyQueueException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static void main(String[] args){
        CircularQueue queue1=new CircularQueue(5);
        queue1.enqueue(1);
        queue1.enqueue(2);
        queue1.enqueue(3);
        queue1.enqueue(4);
        queue1.enqueue(5);
        while(true){
            try{
                queue1.dequeue();
            }
            catch(EmptyQueueException e1){
                System.out.println("Dequeue fail! Empty queue.");
                break;
            }
        }


    }
}


