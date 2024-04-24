package Search;

public class GetRoot {
    public float positiveRoot(float data){
        float left = 0;
        float right = data;
        float mid = 0;
        while(right-left>0.00001){
            mid = left + (right-left)/2;
            if(mid*mid<data) left=mid;
            else right=mid;
        }
        return mid;
    }

    public float negativeRoot(float data){
        return -positiveRoot(data);
    }

    public static void main(String[] args) {
        GetRoot g1 = new GetRoot();
        float data = 2;
        System.out.println("The positive root of " + data + " is " + g1.positiveRoot(data));
        System.out.println("The negative root of " + data + " is " + g1.negativeRoot(data));
    }
}
