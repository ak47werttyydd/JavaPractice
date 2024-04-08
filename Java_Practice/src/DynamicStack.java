import static java.lang.Math.floor;

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class DynamicStack {
    private String[] elements;
    private int head;  //points to the hat of the head element
    private int size;

    public DynamicStack(int size){
        this.size=size;
        elements=new String[size];
        head=0;
    }

    public boolean push(String element){
        if( head < 0 ){
            return false;
        }
        else if( head >= size ){
            size=size*2;
            String new_elements[]=new String[size];
            int i=0;
            for(String x : elements){
                new_elements[i++]=x;
            }
            elements=new_elements;
        }

        elements[head]=element;
        head++;
        return true;
    }

    public String pop(){
        if(head > size || head <= 0 ){
            System.out.println("Pop Fail!");
            return null;
        }
        else{
            String tmpString=elements[--head];
            elements[head]=null;
            //if using half stack, prune it to half size
            if( head == floor(size/2)-1 ){
                size= (int) floor(size/2);
                String new_elements[]=new String[size];
                for(int i=0;i<size;i++){
                    new_elements[i]=elements[i];
                }
                elements=new_elements;
            }
            return tmpString;
        }
    }


    public static void main(String[] args) {
        DynamicStack stack1=new DynamicStack(1);
        stack1.push("you");
        stack1.push("love");
        stack1.push("I");
        System.out.println("Before pop, The size is: "+ stack1.size);
        System.out.printf("The Sentence is: %s %s %s",stack1.pop(),stack1.pop(),stack1.pop());
        System.out.println("After pop, The size is: "+ stack1.size);
    }
}