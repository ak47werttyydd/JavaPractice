package Recursion;
import java.util.*;
public class Steps {
    private Hashtable<Integer, Long> step2ways= new Hashtable<>(); //store the calculated values
    private int depth; //maxdepth of the recursion
    private final int MAX_DEPTH=100;
    public Steps(){
        step2ways.put(1, Long.valueOf(1));   //n=1, 1 way
        step2ways.put(2, Long.valueOf(2));   //n=2, 2 ways
        depth=0;
    }
    public long countWays(int n){
        ++depth;
        if (depth > MAX_DEPTH) {
            System.out.println("The depth of the recursion is too deep. Current depth is: "+depth);
            System.exit(0); //exit the program
        }
        //terminal condition
        if(n<=0) return 0;
        else if(n==1) return 1;
        else if(n==2) return 2;
        //recursive condition
        else{
            if(step2ways.containsKey(n)) return step2ways.get(n);  // if n is already calculated, return the value
            else{
                long tmp_countWays_n;
                if(step2ways.containsKey(n-1) && step2ways.containsKey(n-2)){  // if n-1 and n-2 are already calculated, calculate n
                    tmp_countWays_n=step2ways.get(n-1)+step2ways.get(n-2);
                    step2ways.put(n, tmp_countWays_n);
                    return tmp_countWays_n;
                }
                else if(step2ways.containsKey(n-1)){ // if n-1 is already calculated, calculate n
                    long tmp_countWays_n_minus2=countWays(n-2);
                    step2ways.put(n-2, tmp_countWays_n_minus2);
                    tmp_countWays_n=step2ways.get(n-1)+tmp_countWays_n_minus2;
                    step2ways.put(n, tmp_countWays_n);
                    return tmp_countWays_n;
                }
                else if(step2ways.containsKey(n-2)){ // if n-2 is already calculated, calculate n
                    long tmp_countWays_n_minus1=countWays(n-1);
                    step2ways.put(n-1, tmp_countWays_n_minus1);
                    tmp_countWays_n=tmp_countWays_n_minus1+step2ways.get(n-2);
                    step2ways.put(n, tmp_countWays_n);
                    return tmp_countWays_n;
                }
                // if n-1 and n-2 are not calculated, calculate n
                long tmp_countWays_n_minus1=countWays(n-1);
                long tmp_countWays_n_minus2=countWays(n-2);
                step2ways.put(n-1, tmp_countWays_n_minus1);
                step2ways.put(n-2, tmp_countWays_n_minus2);
                tmp_countWays_n=tmp_countWays_n_minus1+tmp_countWays_n_minus2;
                step2ways.put(n, tmp_countWays_n);
                return tmp_countWays_n;
            }
        }
    }
    public static void main(String[] args){
        Steps s=new Steps();
        int n=100;
        System.out.printf("How many ways to implement steps %d (with 1 or 2 steps in every action)?:  %d \n",n,s.countWays(n));
    }
}

