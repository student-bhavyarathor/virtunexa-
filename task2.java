

import java.util.Arrays;
import java.util.Scanner;

public class task2 {
    public static boolean areAnagrams(String str1, String str2 ){
        str1 = str1.replaceAll("\\s","").toLowerCase();
        str2= str2.replaceAll("\\s","").toLowerCase();
        if (str1.length() != str2.length()){
            return false ;
        }
     char[]charArray1= str1.toCharArray();
     char[]charArray2= str2.toCharArray();
     Arrays.sort(charArray1);
     Arrays.sort(charArray2);
     return Arrays.equals(charArray1, charArray2);


 }
public static void main(String[] args) {
     Scanner scanner = new Scanner (System.in);

     System.out.println("Enter the first string:");
     String first = scanner.nextLine();
     
     System.out.println("Enter the second string :");
     String second = scanner.nextLine();

     if (areAnagrams(first ,second )){
        System.out.println("The strings anagrams .");

     }else {
        System.out.println("The strings are note anagrams .");
     }
     scanner.close();

}

    
}
