package PO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.codehaus.jettison.json.*;

public class BIToolTester {

 
  public static void main(String[] args) throws Exception{
    
    BITool bi = new BITool();
    
    JSONObject jsonSale = new JSONObject();
    JSONArray jsonArr = new JSONArray();
    Object test = new JSONObject();
    ResultSet test2 = null;
    
    //central
  // jsonArr = bi.centralCube("product.category", "store.store_state", "date_time.year");
    
   //jsonSale.put("data", jsonArr);
    
  //Roll up dimension  
  /*
    ArrayList<String> rollUp = new ArrayList<String>();
    rollUp.add("product.category");
    rollUp.add("store.store_state");
    rollUp.add("date_time.year");
    
    jsonArr = bi.rollUpByDimension(rollUp);
    
  */
    
    
    ArrayList<String> param1 = new ArrayList<String>();
    ArrayList<String> param2 = new ArrayList<String>();
    
    param1.add("product.category");
    param1.add("store.store_state");
    param1.add("date_time.year");
    
    param2.add("store.store_state = 'PA'");
        
        
    jsonArr = bi.slice(param1, param2);
    
   
   
   System.out.println(jsonArr.toString());
   
    
    /*
    for(int i = 0; i < 5; i++){
      
      test = jsonArr.get(i);
      
      
      
      //System.out.println("");
      
      //System.out.println(test.toString());
 
      
    }
    */
    /*
    JSONObject jsonSale = new JSONObject();
    
    
    String param1 = "Product";
    String param2 = "Store";
    String param3 = "Date_time";
    
    String param3 = "year";
    
    JSONArray dimobj = new JSONArray();
    dimobj.put("Product");
    dimobj.put("Store");
    dimobj.put("Date_time");
    jsonSale.put("dimension", dimobj);
    
    
    JSONArray attobj = new JSONArray();
    attobj.put("category");
    attobj.put("state");
    attobj.put(param3);
    jsonSale.put("attributes", attobj);
    
    JSONArray dataobjarray = new JSONArray();
    
    JSONObject dataobj = new JSONObject();
    JSONArray keyobj = new JSONArray();
    JSONArray valueobj = new JSONArray();
    

    
    keyobj.put("Drinks");
    keyobj.put("AZ");
    keyobj.put("1994");
    dataobj.put("key", keyobj);
    
    valueobj.put("11312");
    dataobj.put("value", valueobj);
    
    dataobjarray.put(dataobj);
    //dataobjarray.put(valueobj);
    
    
    //dataobj.put("key", keyobj);
    
    
    
    
    
    jsonSale.put("data", dataobjarray);
    */
    
    
    
  }
  
  
}
