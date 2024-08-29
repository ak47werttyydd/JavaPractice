package StringMatching;

public class StringMatching {
    private String mainStr=new String(); //find the pattern in the mainStr
    private String ptrnStr=new String(); //pattern string
    private int[] lkupTable_rabinKarp; //storing 26^0 to 26^(ptrnStr.length()-1) for calculating the hash value for rabinKarp
    private int[]hashTable_boyerMoore=new int[256]; //storing the last index of each character in the pattern string for boyerMoore

    private StringMatching(String main, String pattern){
        if(main.length()<pattern.length()){
            pattern=pattern.substring(0,main.length());
        }
        mainStr = main;
        ptrnStr = pattern;

        //rabinKarp
        lkupTable_rabinKarp = new int[ptrnStr.length()];
        for(int i=0;i<ptrnStr.length();i++){
            lkupTable_rabinKarp[i]=(int)Math.pow(26,i);
        } //from 26^0 to 26^(ptrnStr.length()-1)

        //boyerMoore
        for(int i=0;i<256;i++){
            hashTable_boyerMoore[i]=-1;
        }
        for(int i=0;i<ptrnStr.length();i++){
            hashTable_boyerMoore[(int)ptrnStr.charAt(i)]=i; //store the last index of each character in the pattern string to the hashTable indexed by the character's ASCII
        }
    }

    public void bruteForce(){
        boolean found=false;
        for(int i=0;i<=mainStr.length()-ptrnStr.length();i++){
            String main_subStr=mainStr.substring(i,i+ptrnStr.length());
            if(main_subStr.equals(ptrnStr)){
                found=true;
                System.out.println("Pattern found at index: "+i);
            }
        }
        if(!found){
            System.out.println("Pattern not found");
        }
    }

    public void rabinKarp() {
        int ptrnHash = hash_lowercase_noCollide(ptrnStr);
        int mainHash = hash_lowercase_noCollide(mainStr.substring(0, ptrnStr.length()));
        boolean found = false;
        for (int i = 0; i <= mainStr.length() - ptrnStr.length(); i++) {
            if (mainHash == ptrnHash) {
                System.out.println("Pattern found at index: " + i);
                found = true;
            }
            if (i + ptrnStr.length() < mainStr.length()) { //update the mainHash if the substring is not the last one
                mainHash = 26 * (mainHash - (int) (mainStr.charAt(i) - 'a') * lkupTable_rabinKarp[ptrnStr.length() - 1]) + (int) (mainStr.charAt(i + ptrnStr.length()) - 'a');
            }
        }
        if (!found) {
            System.out.println("Pattern not found");
        }
    }

    public void boyerMoore(){
        boolean found=false;
        for(int i=0;i<=mainStr.length()-ptrnStr.length();){  //scan the mainStr
            int move=1;
            int move_badChar=0;
            int si=-1; //the index of the bad character in the pattern string
            int xi=-1; //the last index of the bad character in the pattern string
            int move_goodSuffix=0;
            String goodSuffix=null;
            for(int j=ptrnStr.length()-1;j>=0;j--) {//scan the ptrnStr from the end to the start to find bad character and good suffix, get the move_badChar
                if (mainStr.charAt(i + j) != ptrnStr.charAt(j)) { //bad character
                    si = j;
                    char badChar = mainStr.charAt(i+j);
                    goodSuffix = ptrnStr.substring(si + 1); //the good suffix of the pattern string
                    xi = hashTable_boyerMoore[(int) badChar]; //find the last index of the bad character in the pattern string. If the bad character is not in the pattern string, xi=-1
                    move_badChar = si - xi; //move the pattern string to the right by si-xi
                    break;
                }
            }
            //if the pattern string is found (i.e. no bad char)
            if(si==-1){
                System.out.println("Pattern found at index: "+i);
                found=true;
                move=ptrnStr.length();
            }
            //if the pattern string not found
            else{
                //calculate move_goodSuffix by scanning the pattern string from the start
                boolean found_goodSuffix=false;
                for(int k=si; k>=0; k--) {
                    //find a matching string with goodsuffix in pattern string, move pattern string to match
                    if (ptrnStr.substring(k, k + goodSuffix.length()).equals(goodSuffix)) {
                        found_goodSuffix = true;
                        move_goodSuffix = si + 1 - k;
                        break;
                    }
                }
                //doesn't find a matching string with goodsuffix in pattern string, find prefix of the pattern string that is also substring of the good suffix
                if(!found_goodSuffix){
                    for(int k=goodSuffix.length()-1;k>=1;k--){
                        if(ptrnStr.substring(0,k)==goodSuffix.substring(goodSuffix.length()-k,goodSuffix.length())){
                            move_goodSuffix=ptrnStr.length()-k;
                        }
                    }
                }
                //
                move = Math.max(move_goodSuffix,move_badChar);
            }
            i+=move;
        }
    }

    //str is a string of lowercase letters, the hash doesn't collide.
    public int hash_lowercase_noCollide(String str){
        int hash=0;
        for(int i=0;i<str.length();i++){
            hash=hash*26+(int)(str.charAt(i)-'a');
        }
        return hash;
    }

    public static void main(String[] args) {
        String mainStr = "AABAACAADAABAAABAA";
        String ptrnStr = "AABA";
        StringMatching sm = new StringMatching(mainStr,ptrnStr);
        long startTime= System.nanoTime();
        sm.bruteForce();
        long endTime= System.nanoTime();
        System.out.println("------------The time for bruteForce is "+(endTime-startTime)/1000+" microseconds------------------");
        startTime= System.nanoTime();
        sm.rabinKarp();
        endTime= System.nanoTime();
        System.out.println("------------The time for rabinKarp is "+(endTime-startTime)/1000+" microseconds------------------");
        startTime= System.nanoTime();
        sm.boyerMoore();
        endTime= System.nanoTime();
        System.out.println("------------The time for boyerMoore is "+(endTime-startTime)/1000+" microseconds------------------");
    }
}
