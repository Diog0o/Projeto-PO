package core;

public class GFG { 
	  
    // Main driver method 
    public static void main(String args[]) 
    { 
        // Custom input string 
    	String str = "BASIC|969004|cli003|ON"; 
        String[] arrOfStr = str.split("[|]",0); 
  
        for (String a : arrOfStr) 
            System.out.println(a); 
    } 
}
