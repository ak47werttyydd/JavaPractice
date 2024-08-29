package StringMatching;

public class MatrixMatching {
    String[][] main_matrix;
    String[][] ptrn_matrix;
    int[] main_size;
    int[] ptrn_size;
    public MatrixMatching(String[][] main, String[][] pattern){
        main_matrix = main;
        ptrn_matrix = pattern;
        main_size = new int[]{main_matrix.length,main_matrix[0].length}; //(row size, column size)
        ptrn_size = new int[]{ptrn_matrix.length,ptrn_matrix[0].length}; //(row size, column size)
    }

    public int hashStr(String str){
        int hash=0;
        for(int i=0;i<str.length();i++){
            hash=hash*26+(int)(str.charAt(i)-'a');
        }
        return hash;
    }

    //matrix should be the same size with the pattern matrix
    public int hash_matrix_yesCollide(String[][] matrix) {
        int hash = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                hash += hashStr(matrix[i][j]);
            }
        }
        return hash;
    }

    public String[][] slice_main_matrix(int row_start,int col_start){
        String[][] slice = new String[ptrn_size[0]][ptrn_size[1]];
        for(int i=0;i<ptrn_size[0];i++){
            for(int j=0;j<ptrn_size[1];j++){
                slice[i][j] = main_matrix[row_start+i][col_start+j];
            }
        }
        return slice;
    }

    public void rabinkarp(){
        int ptrnHash = hash_matrix_yesCollide(ptrn_matrix);
        int mainHash = hash_matrix_yesCollide(main_matrix);
        boolean found = false;
        for(int i=0;i<=main_size[0]-ptrn_size[0];i++){
            for(int j=0;j<=main_size[1]-ptrn_size[1];j++){
                if(mainHash==ptrnHash){
                    boolean match = true;
                    for(int q=0;q<ptrn_size[0];q++){
                        for(int p=0;p<ptrn_size[1];p++){
                            if(main_matrix[i+q][j+p]!=ptrn_matrix[q][p]){
                                match = false;
                            }
                        }
                    }
                    if (match){
                        System.out.println("Pattern found at index: ("+i+","+j+")");
                        found = true;
                    }
                }
                if(j+ptrn_size[1]<main_size[1]){
                    mainHash = hash_matrix_yesCollide(slice_main_matrix(i,j+1));
                }
            }
            if(i+ptrn_size[0]<main_size[0]){
                mainHash = hash_matrix_yesCollide(slice_main_matrix(i+1,0));
            }
        }
        if(!found){
            System.out.println("Pattern not found");
        }
    }

    public static void main(String[] args) {
        String[][] main = new String[][]{{"I love China","how","about","you"},{"eeee","ffff","gxxx","haaa"},{"what","a","great","holiday"},{"who","is","olympic","pianist"}};
        String[][] pattern = new String[][]{{"great","holiday"},{"olympic","pianist"}};
        MatrixMatching mm = new MatrixMatching(main,pattern);
        mm.rabinkarp();
    }
}
