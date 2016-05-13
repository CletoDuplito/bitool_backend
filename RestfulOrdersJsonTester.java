package bitool;

import java.util.ArrayList;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

public class RestfulOrdersJsonTester {

  public static void main(String[] args) throws Exception{
    
    RestfulOrdersJson rest = new RestfulOrdersJson();
    
    ArrayList<String> param1 = new ArrayList<String>();
    ArrayList<String> param2 = new ArrayList<String>();
    
    param1.add("product.category");
    param1.add("store.store_state");
    param1.add("date_time.year");
    
   // param2.add("store.store_state = 'PA'");
    
    //JSONArray sliceResults = new JSONArray();
    //Response sliceResults;
    String sliceResults;
    sliceResults = rest.inputURLquery("slice", "product.category,store.store_state.PA");
    
    Response.status(200).entity(sliceResults.toString()).build();
    
    System.out.println(sliceResults.toString());
    
    
    
  }
  
  
}
