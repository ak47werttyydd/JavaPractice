import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
import java.util.Collections;

public class Sort {
        public void bubbleSort(int[] arr){
            for(int j=0;j<arr.length;j++)  //each loop, the local largest element will be moved to the end
            {
                boolean sorted=true; //flag: if the array is already sorted, break the loop
                for (int i = 0; i < arr.length-1-j; i++) //compare the adjacent elements from the beginning to the sorted part
                {
                    if (arr[i] > arr[i + 1])
                    {
                        int tmp = arr[i];
                        arr[i] = arr[i + 1];
                        arr[i + 1] = tmp;
                        sorted=false; //not sorted
                    }
                }
                if(sorted) break;  //sorted, break the outer loop
            }
        }
        public void insertionSort(int[] arr){
            //arr[>=i] is unsorted
            //arr[<i] is sorted
            for(int i=1;i<arr.length;i++){ //arr[0] is sorted, i starts from 1
                int tmp=arr[i];
                int j=i-1; //j is the last element of the sorted part
                while(j>=0 && arr[j]>tmp){
                    arr[j+1]=arr[j];
                    j--;
                }
                arr[j+1]=tmp;
            }
        }

        public void selectionSort(int[] arr){
            for(int i=0;i<arr.length-1;i++){
            //after finish i=arr.length-2, the last element is already sorted
                int index4min=i;
                boolean all_sorted=true; //flag: if the array is already sorted, break the loop
                for(int j=i+1;j<arr.length;j++){
                    if(arr[j]<arr[index4min]) index4min=j; //find the index of the minimum element
                    if(arr[j]<arr[j-1]) all_sorted=false; //not sorted
                }
                if(all_sorted) { //sorted, break the outer loop
                    System.out.println("Already sorted! Exit when i= "+i);
                    break;
                }
                else { //not sorted, swap the minimum element with the first element of the unsorted part
                    if (index4min != i) {
                        int tmp = arr[i];
                        arr[i] = arr[index4min];
                        arr[index4min] = tmp;
                    }
                }
            }
        }

        public void shellSort(int[] arr){
            //determining gaps
            //sorting the subarrays with the same gap.
            //gaps is chosen from large to 1.
            //gap=1 is the same as the insertion sort
            ArrayList<Integer> gaps=new ArrayList<>();
            for(int i=0 ;; i++ ){
                int even=(int)(9*Math.pow(4,i)-9*Math.pow(2,i)+1); //even is less than odd for the same i
                int odd=(int)(Math.pow(2,i+2)*(Math.pow(2,i+2)-3)+1);
                if(even<arr.length) {
                    gaps.add(even);
                    if(odd<arr.length) gaps.add(odd);
                }
                else break;
            }

            //reverting the order of gaps
            //reverted gaps is chosen from large to small
            for(int i=0;i<gaps.size()/2;i++){
                int tmp=gaps.get(i);
                gaps.set(i,gaps.get(gaps.size()-1-i));
                gaps.set(gaps.size()-1-i,tmp);
            }
            System.out.println("reverted gap is "+gaps.toString());

            //sorting
            for(int gap:gaps){
                for(int i=gap;i<arr.length;i++){
                    int tmp=arr[i];
                    int j=i-gap;
                    while(j>=0 && arr[j]>tmp){
                        arr[j+gap]=arr[j];
                        j-=gap;
                    }
                    arr[j+gap]=tmp;
                }
            }
        }

        public void mergeSort(int[] arr,int begin,int end){
            //begin is the index of the first element of the subarray
            //end is the index of the last element of the subarray

            //terminal condition
            if(begin>=end) return;

            //divide
            mergeSort(arr,begin,(begin+end)/2);
            mergeSort(arr,(begin+end)/2+1,end);

            //merge
            __merge(arr,begin,end);
            return;
        }

        public void __merge(int[] arr, int begin, int end){
            int[] tmp=new int[end-begin+1];
            int i=begin;
            int j=(begin+end)/2+1;
            int k=0;
            while( i<=(begin+end)/2 && j<=end ){  //until one of the two subarrays is empty
                if( arr[i] < arr[j] ) tmp[k++]=arr[i++];
                else tmp[k++]=arr[j++];
            }
            while(i<=(begin+end)/2) tmp[k++]=arr[i++];
            while(j<=end) tmp[k++]=arr[j++];
            for(k=0;k<tmp.length;k++) arr[begin+k]=tmp[k];
        }

        public void quickSort(Integer[] arr,int begin,int end){
            //termination condition
            if(begin>=end) return;

            //partition
            int pivot=arr[end];
            int i=begin; //partition point, where the current pivot will be placed, arr[begin to i-1] < pivot, arr[i to end] >= pivot
            //j is scanning cursor
            int tmp; //temporary variable
            for(int j=begin;j<end;j++){
                if(arr[j]<pivot){
                    swap(arr,i,j);
                    i++;
                }
            }
            swap(arr,i,end);

            //recursion
            //arr[begin to i-1] < arr[i] < arr[i+1 to end], i.e. arr[i] is in the right position
            quickSort(arr,begin,i-1);
            quickSort(arr,i+1,end);
        }

        public static final <T> void swap (T[] a, int i, int j) {
            T t = a[i];
            a[i] = a[j];
            a[j] = t;
        }

        //quickSort for ArrayList
        public void quickSort(ArrayList<Integer> arr,int begin,int end){
            //termination condition
            if(begin>=end) return;

            //partition
            int pivot=arr.get(end);
            int i=begin; //partition point, where the current pivot will be placed, arr[begin to i-1] < pivot, arr[i to end] >= pivot
            //j is scanning cursor
            int tmp; //temporary variable
            for(int j=begin;j<end;j++){
                if(arr.get(j)<pivot){
                    Collections.swap(arr,i,j);
                    i++;
                }
            }
            Collections.swap(arr,i,end);

            //recursion
            //arr[begin to i-1] < arr[i] < arr[i+1 to end], i.e. arr[i] is in the right position
            quickSort(arr,begin,i-1);
            quickSort(arr,i+1,end);
        }

        public void bucketSort(int[] arr){
            //determine the range of the array
            int max=arr[0];
            int min=arr[0];
            for(int i=1;i<arr.length;i++){
                if(arr[i]>max) max=arr[i];
                if(arr[i]<min) min=arr[i];
            }
            int range=max-min+1;

            //create buckets
            //buckets' index is from 0 to range/10
            int bucket_num=range/10+1;  //e.g. min=0 max=52, range=53, range/10=5, need 6 buckets, so bucket_number=range/10+1
            ArrayList<ArrayList<Integer>> buckets=new ArrayList<>(); //outer list is the buckets, inner list is the elements in the bucket
            for(int i=0;i<bucket_num;i++){
                buckets.add(new ArrayList<>());
            }

            //put elements into buckets
            for(int i=0;i<arr.length;i++){
                int index=(arr[i]-min)/10;
                buckets.get(index).add(arr[i]);
            }

            //sort each bucket
            for(int i=0; i < bucket_num; i++ ){
                int bucket_size_i=buckets.get(i).size();
                quickSort(buckets.get(i),0,bucket_size_i-1);
            }

            //merge the sorted buckets
            int k=0;
            for(int i=0;i<bucket_num;i++){
                for(int j=0;j<buckets.get(i).size();j++){
                    arr[k++]=buckets.get(i).get(j);
                }
            }
        }

        public static void countingSort(int[] arr){
            //determine the range of the array
            int max=arr[0];
            int min=arr[0];
            for(int i=1;i<arr.length;i++){
                if(arr[i]>max) max=arr[i];
                if(arr[i]<min) min=arr[i];
            }
            int range=max-min+1;

            //create a counting array, and count the number of each element
            int[] counting=new int[range];  //default elements are 0
            for(int i=0;i<arr.length;i++){
                counting[arr[i]-min]++;
            }

            //counting[i] is the number of elements less than or equal to i+min
            for(int i=1;i<counting.length;i++){
                counting[i]=counting[i]+counting[i-1];
            }

            //temp array to store the sorted array
            int[] r=new int[arr.length];
            for(int i=arr.length-1;i>=0;i--){  //from the end to the beginning, to keep the stability, i.e. data is placed behind in the original array, it will be placed behind in the sorted array
                r[counting[arr[i]-min]-1]=arr[i];
                counting[arr[i]-min]--;
            }

            //reconstruct the original array
            for(int i=0;i<arr.length;i++){
                arr[i]=r[i];
            }
        }

        public static void main(String[] args){
            Sort sort=new Sort();
//            int[] arr={3,5,6,3,2,4,9};
//            System.out.println("Original array:"+ Arrays.toString(arr));
//            sort.bubbleSort(arr);
//            System.out.println("Sorted array by BubbleSort is:"+ Arrays.toString(arr));
//
//            int[] arr2={3,5,6,3,2,4,9};
//            System.out.println("Original array:"+ Arrays.toString(arr2));
//            sort.insertionSort(arr2);
//            System.out.println("Sorted array by InsertionSort is:"+ Arrays.toString(arr2));
//
//            int[] arr3={3,3,3,3,4,4,9};
//            System.out.println("Original array:"+ Arrays.toString(arr3));
//            sort.selectionSort(arr3);
//            System.out.println("Sorted array by InsertionSort is:"+ Arrays.toString(arr3));

//            int[] arr4={3,5,6,3,2,4,9};
//            System.out.println("Original array:"+ Arrays.toString(arr4));
//            sort.shellSort(arr4);
//            System.out.println("Sorted array by ShellSort is:"+ Arrays.toString(arr4));

//            int[] arr5={3,5,6,3,2,4,9};
//            System.out.println("Original array:"+ Arrays.toString(arr5));
//            sort.mergeSort(arr5,0,arr5.length-1);
//            System.out.println("Sorted array by MergeSort is:"+ Arrays.toString(arr5));

//            Integer[] arr6={3,5,6,3,2,4,9};
//            System.out.println("Original array:"+ Arrays.toString(arr6));
//            sort.quickSort(arr6,0,arr6.length-1);
//            System.out.println("Sorted array by QuickSort is:"+ Arrays.toString(arr6));

//            //test bucketSort
//            int[] arr7={31,54,62,39,27,4,99};
//            System.out.println("Original array: "+ Arrays.toString(arr7));
//            sort.bucketSort(arr7);
//            System.out.println("Sorted array by BucketSort is: "+ Arrays.toString(arr7));

            //test countingSort
            int[] arr8={0,0,0,9,7,2,9,5,4,3,8,6};
            System.out.println("Original array: "+ Arrays.toString(arr8));
            countingSort(arr8);
            System.out.println("Sorted array by CountingSort is: "+ Arrays.toString(arr8));
        }
}
