package Search;

public class Search {
    public int[] binarySearch_NonRecursion(int arr[], int target){
        int length = arr.length;
        int begin=0;
        int end=length-1;
        while(begin<=end){
            int mid = begin + (( end - begin ) >> 1) ; //(begin+end)/2 may cause overflow
            if(arr[mid]==target){  //find the target
                //half-open interval [lowerbound,upperbound) is the index range of target
                int lowerbound=mid;
                while(lowerbound>=0 && arr[lowerbound]==target) lowerbound--;
                lowerbound++;
                int upperbound=mid;
                while(upperbound<length && arr[upperbound]==target) upperbound++;
                return new int[]{lowerbound,upperbound};
            }
            else if(arr[mid]<target) begin=mid+1;
            else end=mid-1;
        }
        return new int[]{-1,-1};
    }

    public int[] binarySearch_Recursion(int arr[], int target, int begin, int end){
        if(begin>end) return new int[]{-1,-1};
        int mid = begin + (( end - begin ) >> 1) ; //(begin+end)/2 may cause overflow
        if(arr[mid]==target){
            //half-open interval [lowerbound,upperbound) is the index range of target
            int lowerbound=mid;
            while(lowerbound>=0 && arr[lowerbound]==target) lowerbound--;
            lowerbound++;
            int upperbound=mid;
            while(upperbound<arr.length && arr[upperbound]==target) upperbound++;
            return new int[]{lowerbound,upperbound};
        }
        else if(arr[mid]<target) return binarySearch_Recursion(arr,target,mid+1,end);
        else return binarySearch_Recursion(arr,target,begin,mid-1);
    }

    public int searchFirstTarget(int arr[], int target){
        int[] range = binarySearch_NonRecursion(arr,target);
        if(range[0] == -1) return -1;
        else{
            return range[0];
        }
    }

    public int searchLastTarget(int arr[], int target){
        int[] range = binarySearch_NonRecursion(arr,target);
        if(range[0] == -1 && range[1] == -1) return -1;
        else{
            return range[1]-1;
        }
    }

    public int searchFirstLargeOrEqual(int arr[], int target){
        int[] range = binarySearch_NonRecursion(arr,target);
        if(range[0] == -1) return -1;
        else{
            if(range[1]<arr.length)  return range[1];
            else return -1;
        }
    }

    public int searchLastSmallOrEqual(int arr[], int target){
        int[] range = binarySearch_NonRecursion(arr,target);
        if(range[0] == -1) return -1;
        else return range[0]-1;
    }



    public static void main(String[] args) {
        //test binarySearch_NonRecursion
//        Search.Search s1 = new Search.Search();
//        int[] arr = new int[]{1,2,3,4,4,4,5,6,7,8,9};
//        int target = 4;
//        int[] result = s1.binarySearch_NonRecursion(arr, target);
//        System.out.println("The target is in the range of [" + result[0] + "," + result[1]+"]");

        //test binarySearch_Recursion
//        Search s2 = new Search();
//        int[] arr = new int[]{1,2,3,4,4,4,5,6,7,8,9};
//        int target = 4;
//        int[] result = s2.binarySearch_Recursion(arr, target, 0, arr.length-1);
//        System.out.println("The target is in the range of [" + result[0] + "," + result[1]+"]");

        //test searchFirstTarget
        Search s3 = new Search();
        int[] arr = new int[]{1,2,3,4,4,4,5,6,7,8,9};
//        int target = 4;
//        int result = s3.searchFirstTarget(arr, target);
//        System.out.println("The first target is at index " + result);
//
//        target=1;
//        result = s3.searchFirstTarget(arr, target);
//        System.out.println("The first target is at index " + result);

//        int target=9;
//        int result = s3.searchFirstTarget(arr, target);
//        System.out.println("The first target is at index " + result);


        //test searchLastTarget
        int target4=4;
        int target9=9;
        int result4 = s3.searchLastTarget(arr, target4);
        int result9 = s3.searchLastTarget(arr, target9);
        System.out.println("The last target4 is at index " + result4);
        System.out.println("The last target9 is at index " + result9);

        //test searchFirstLargeOrEqual
        result4=s3.searchFirstLargeOrEqual(arr,target4);
        result9=s3.searchFirstLargeOrEqual(arr,target9);
        System.out.println("The first element equal or larger 4 is at index " + result4);
        System.out.println("The first element equal or larger 9 is at index " + result9);

        //test searchLastSmallOrEqual
        int target1=1;
        int result1=s3.searchLastSmallOrEqual(arr,target1);
        result4=s3.searchLastSmallOrEqual(arr,target4);
        int target10=10;
        int result10=s3.searchLastSmallOrEqual(arr,target10);
        System.out.println("The last element equal or smaller 1 is at index " + result1);
        System.out.println("The last element equal or smaller 4 is at index " + result4);
        System.out.println("The last element equal or smaller 10 is at index " + result10);


    }
}

