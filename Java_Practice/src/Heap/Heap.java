package Heap;
import java.util.ArrayList;
import java.util.Collections;

public class Heap {
    private ArrayList<Integer> heap= new ArrayList<>();
    private int size; // size of the heap
    //ctor, build the heap
    public Heap(int... values){
        heap.add(0);  // 0th index is not used, so the first element is at index 1
        for (int value: values){
            heap.add(value);
        }
        size= heap.size()-1;
        //build the heap from the bottom up
        //size represents the last element, size/2 is the parent of the last element(i.e. the last element on the second last level)
        for(int i=size/2; i>0; i--){
            heapify_down(i);
        }

    }
    //find the largest element among the parent and its children, and swap the parent with the largest element
    //starts from index node and recursively goes down to the leaf node
    public void heapify_down(int index){
        int left=2*index;
        int right=2*index+1;
        int largest=index; //initiate the current index is the largest
        if(left<=size && heap.get(left)>heap.get(largest)){
            largest=left;
        }
        if(right<=size && heap.get(right)>heap.get(largest)){
            largest=right;
        }
        if(largest!=index){
            Collections.swap(heap, index, largest);
            heapify_down(largest); //recursively heapify the subtree
        }
    }

    //range of the subarray is from 1 to arr_size
    public void partial_heapify_down(int index, int arr_size){
        int left=2*index;
        int right=2*index+1;
        int largest=index; //initiate the current index is the largest
        if(left<=arr_size && heap.get(left)>heap.get(largest)){
            largest=left;
        }
        if(right<=arr_size && heap.get(right)>heap.get(largest)){
            largest=right;
        }
        if(largest!=index){
            Collections.swap(heap, index, largest);
            partial_heapify_down(largest,arr_size); //recursively heapify the subtree
        }
    }

    public void heapify_up(int index){
        int parent= index/2;
        if(parent>0 && heap.get(index)>heap.get(parent)){
            Collections.swap(heap, index, parent);
            heapify_up(parent);
        }
    }

    public void insert(int value){
        heap.add(value);  //add the new value to the end of the arr
        size++;
        heapify_up(size);
    }

    public void delete_top(){
        Collections.swap(heap, 1, size);
        heap.remove(size);  //remove the last element (previous top element)
        size--;
        heapify_down(1);
    }

    //sort the arr in ascending order
    //arr left is unsorted, arr right is sorted
    public void sort_arr_ascend(){
        int n=size;  //size of unsorted array
        while(n>1){
            //each time, swap the top element with the end of the unsorted array
            Collections.swap(heap, 1, n);
            n--;
            partial_heapify_down(1,n);
            print_levelwise();
        }
    }

    public void print_levelwise(){
        int level=1;
        int i=1;
        while(i<=size){
            System.out.print("Level "+level+": ");
            for(; i<Math.pow(2,level); i++){
                if(i>size){
                    break;
                }
                System.out.print(heap.get(i)+" ");
            }
            System.out.println();
            level++;
        }
        System.out.println("------------------------------");
    }

    public void print_array(){
        for(int i=1; i<=size; i++){
            System.out.print(heap.get(i)+" ");
        }
        System.out.println("------------------------------");
    }

    public static void main(String[] args){
        Heap heap= new Heap(1,2,3,4,5,6,7,8,9,10);
        heap.print_levelwise();
        heap.insert(11);
        heap.print_levelwise();
        heap.delete_top();
        heap.print_levelwise();
        heap.sort_arr_ascend();
        heap.print_array();
    }

}
