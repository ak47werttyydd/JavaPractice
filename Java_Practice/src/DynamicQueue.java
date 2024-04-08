import static java.lang.Math.floor;

public class DynamicQueue {
    private String[] elements;
    private int size; //size of the array String[] elements;
    private int head; //points to the earliest element in the queue
    private int end; //points to the blank position immediately after the last element

    public DynamicQueue(int size){
        this.size=size;
        this.elements=new String[this.size];
        this.head=0;
        this.end=0;
    }

    public String dequeue(){
        if(head==end) {
            System.out.println("Empty queue");
            return null;
        }
        else{  //no empty
            String tmp=elements[head++];
            elements[head-1]=null;
            if( (end-head) == ( floor(size/2)-1 ) ){  //actual space occupied is less than half of the size
                //half_compress and move
                half_compress_and_move();
            }
            return tmp;
        }
    }

    public boolean enqueue(String new_element){
        if( end == size){
            if( head == 0 ) {  //full-sized queue
                if(double_expand()){ //double
                    //insert
                    elements[end++]=new_element;
                    return true;
                }
                else{
                    return false;
                }
            }
            else{ //has spare space
                if(move()){//move
                    //insert
                    elements[end++]=new_element;
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        else{
            elements[end++]=new_element;
            return true;
        }
    }

    public boolean double_expand(){
        if(head==0 && end==size){
            size*=2;
            String[] new_elements=new String[size];
            for(int i=0; i<elements.length;i++){
                new_elements[i]=elements[i];
                elements[i]=null;
            }
            elements=new_elements;
            return true;
        }
        else {
            System.out.println("No need to expand");
            return false;
        }
    }

    public boolean move(){
        if(head==0) {
            System.out.println("No need to move");
            return false;
        }
        else{ //head != 0
            String[] new_elements=new String[size];
            for(int i=head; i<end;i++){
                new_elements[i - head] = elements[i];
                elements[i] = null;
            }
            elements=new_elements;
            end-=head;
            head=0;
            return true;
        }
    }

    public boolean half_compress_and_move(){
        size= (int) (floor(size/2)-1);
        String[] new_elements=new String[size];
        for(int i=head; i<end;i++){
            new_elements[i-head]=elements[i];
            elements[i]=null;
        }
        elements=new_elements;
        end-=head;
        head=0;
        return true;
    }

    public static void main(String[] args) {
        DynamicQueue queue1=new DynamicQueue(1);
        queue1.enqueue("love");
        queue1.enqueue("I");
        queue1.enqueue("you");
        System.out.println("Before dequeue, The size is: "+ queue1.size);
        System.out.printf("The Sentence is: %s %s %s\n",queue1.dequeue(),queue1.dequeue(),queue1.dequeue());
        System.out.println("After dequeue, The size is: "+ queue1.size);
    }
}
