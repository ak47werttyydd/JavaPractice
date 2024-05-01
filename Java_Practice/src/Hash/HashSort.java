package Hash;
import java.util.*;
/*Sorting URLs by frequency*/
public class HashSort {
    private String[] URL_arr;
    private HashMap<String, Integer> URL_map;

    public HashSort(String[] URL_arr) {
        this.URL_arr = URL_arr;
        this.URL_map = new HashMap<String, Integer>();
    }

    public String[] sort() {
        for (String url : URL_arr) {
            Integer count = URL_map.get(url); //if url is not in the map, count is null
            URL_map.put(url, count == null ? 1 : count + 1);
        }

        //print the frequency of each URL
        for(Map.Entry<String,Integer> entry :URL_map.entrySet()){
            System.out.println(entry.getKey()+" occurs "+entry.getValue()+" times");
        }

        //Converting HashMap to Map.Entry array
        Map.Entry<String, Integer>[] entries = new Map.Entry[URL_map.size()];
        URL_map.entrySet().toArray(entries);
        //Or Map.Entry<String,Integer>[] entries= URL_map.entrySet().toArray(new Map.Entry[0]);
        //new Map.Entry[0] is an empty array of Map.Entry. This is used as the argument to toArray(). The toArray() method will then return a new array of Map.Entry that contains all the entries in the URL_map HashMap. If the URL_map is empty, the returned array will also be empty.

        //Sorting the Map.Entry array
        mergeSort_descending(entries, 0, entries.length - 1);

        //Extracting URLs from sorted entries
        String[] sorted_URLs = new String[entries.length];
        for (int i = 0; i < entries.length; i++) sorted_URLs[i] = entries[i].getKey();

        return sorted_URLs;
    }

    public void mergeSort_descending(Map.Entry<String, Integer>[] arr, int start, int end) {
        if (start >= end) return;
        int mid = start + (end - start) / 2;
        mergeSort_descending(arr, start, mid);
        mergeSort_descending(arr, mid + 1, end);
        __merge_descending(arr, start, end);
    }

    public void __merge_descending(Map.Entry<String, Integer>[] arr, int start, int end) {
        int mid = start + (end - start) / 2;
        int i = start; // scan left part
        int j = mid + 1; //scan right part
        Map.Entry<String, Integer>[] temp = new Map.Entry[end - start + 1]; //tmp array stores sorted elements
        int k = 0; //index of temp
        //Compare elements in two parts, the bigger value precedes, until one of the parts has been traversed
        while (i <= mid && j <= end) temp[k++] = arr[i].getValue() > arr[j].getValue() ? arr[i++] : arr[j++];
        //Copy the remaining elements to tmp array
        while (i <= mid) temp[k++] = arr[i++];
        while (j <= end) temp[k++] = arr[j++];
        //Copy the sorted elements in tmp array to the original array
        for (k = 0; k < temp.length; k++) arr[start + k] = temp[k];
    }

    public static void main(String[] args) {
        String[] URL_arr = {"www.google.com", "www.facebook.com", "www.google.com", "www.facebook.com", "www.facebook.com", "www.facebook.com", "www.google.com", "www.facebook.com", "www.google.com", "www.facebook.com"};
        HashSort hs = new HashSort(URL_arr);
        String[] sorted_URLs = hs.sort();
        System.out.print("The sorted URLs are: ");
        for (String url : sorted_URLs) System.out.print(url+" ");
        System.out.println();
    }
}

