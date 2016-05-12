package PO;

import java.util.List;
import java.util.ArrayList;

import static java.util.Arrays.asList;

public class TesterSplit {

  public static void main(String[] args){
    
    String splitData= "date_time.year.1994+1995";
    
    
    String[] parser = splitData.split("\\.");
    //String[] parser = splitData.split("\\+");
    
    String first = parser[0];
    String second = parser[1];
    String third = parser[2];
    //System.out.println(first + "\n" + second + "\n" + third);
/*    
    for(String x: elements){
      String[] parserList = x.split("\\.");
      for(String y: parserList){
        System.out.println(y);
      }

    
    
    }
*/    
    if((first.contains("+"))){
      System.out.println("theres's a plus");
    }
    
    String[] test = splitData.split("\\.");
    System.out.println(test[0] + "." + test[1]);
    System.out.println(third);
    
    String[] values = third.split("\\+");
    String combine = values[0];
    int counter = values.length;

    for(int i = 1; i < counter; i++){
      combine = combine + " OR " + values[i];
      
    }
    
    
    
    
    
    System.out.println(combine);
    
    System.out.println(parser.toString());
    
    
  }
  
  
}
