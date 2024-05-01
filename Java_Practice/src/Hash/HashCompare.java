package Hash;
import java.util.HashSet;
import java.util.ArrayList;

/*find common strings in two string arrays*/
public class HashCompare {
    private String[] str1;
    private String[] str2;
    private ArrayList<String> common;
    public HashCompare(String[] str1, String[] str2) {
        this.str1 = str1;
        this.str2 = str2;
        this.common = new ArrayList<String>();
    }
    public void compare(){
        //Store the strings in str1 in a HashMap, with the string as the key and the value as 0
        HashSet<String> str_set = new HashSet<String>();
        for(String str: str1) str_set.add(str);


        //Scan str2, if the string(key) is in str_value, set the value to 1
        for(String str: str2) {
            if(str_set.contains(str)) {
                common.add(str);
            }
        }
    }
    public ArrayList<String> getCommon(){
        return common;
    }
    public static void main(String[] args) {
        String[] str1 = {"Adrian", "boiling", "clion", "daisy", "e"};
        String[] str2 = {"boiling", "d", "f", "daisy"};
        HashCompare hc = new HashCompare(str1, str2);
        hc.compare();
        for(String str: hc.getCommon()) System.out.println(str);
    }
}
