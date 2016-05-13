package bitool;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;


@Path("/bi")
public class RestfulOrdersJson {
  
    static BITool bi = new BITool();

    ArrayList<String> URLquery = new ArrayList<>();
    
    /**
     * VERSION 5.10.2016 @ 430pm
     * Get input from URL query string and parse data to send. 
     * 
     *  
     * 
     * @return
     * @throws Exception 
     */
    @Path("/parser")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
  public String inputURLquery(@QueryParam("action") String action, 
  //public Response inputURLquery(@QueryParam("action") String action, 
  //public JSONArray inputURLquery(@QueryParam("action") String action, 
                               
      @QueryParam("dimension") String dimension) throws Exception{
       
      
      ArrayList<String> input = new ArrayList<>();
      Object result = null;
      //JSONArray jsonobj = new JSONArray();
      JSONObject data = new JSONObject();
      JSONObject dimobj = new JSONObject();
      JSONArray dimarr = new JSONArray();
      JSONArray attarr = new JSONArray();
      JSONObject attobj = new JSONObject();
      
      String param1 = "", param2 = "", param3 = "";
      String attparam1 = "", attparam2 = "", attparam3 = "";
      
      input = this.parseData(dimension);

  

      String output = "";
     
      switch(action){
        case "central_cube" :
          
          param1 = input.get(0);
          param2 = input.get(1);
          param3 = input.get(2);
          attparam1 = input.get(0);
          attparam2 = input.get(1);  
          attparam3 = input.get(2);
          
          //output = input.toString();
          
          
          result = bi.centralCube(param1, param2, param3);
          //System.out.println(result.toString());
         // data.put("data", result);
          
          
          param1 = parseParamFirst(param1);
          param2 = parseParamFirst(param2);
          param3 = parseParamFirst(param3);
          
          dimarr.put(param1);
          dimarr.put(param2);
          dimarr.put(param3);
          
          
          
          
          
          
          attparam1 = parseParamLast(attparam1);
          attparam2 = parseParamLast(attparam2);
          attparam3 = parseParamLast(attparam3);
          
          attarr.put(attparam1);
          attarr.put(attparam2);
          attarr.put(attparam3);
     
          dimobj.put("dimension", dimarr);
          dimobj.put("attributes", attarr);
          dimobj.put("data", result);

          
          
          
          break;
        
        case "roll_up_dimension" :
          
          param1 = input.get(0);
          param2 = input.get(1);
          param3 = input.get(2);
          
          attparam1 = input.get(0);
          attparam2 = input.get(1);
          attparam3 = input.get(2);
          
          ArrayList<String> rollUp = new ArrayList<String>();
          rollUp.add(param1);
          rollUp.add(param2);
          rollUp.add(param3);
          
          output = input.toString();
          
          
          result = bi.rollUpByDimension(rollUp);
          
          
          param1 = parseParamFirst(param1);
          param2 = parseParamFirst(param2);
          param3 = parseParamFirst(param3);
          
          dimarr.put(param1);
          dimarr.put(param2);
          dimarr.put(param3);

          
          attparam1 = parseParamLast(attparam1);
          attparam2 = parseParamLast(attparam2);
          attparam3 = parseParamLast(attparam3);
          
          attarr.put(attparam1);
          attarr.put(attparam2);
          attarr.put(attparam3);
     
          dimobj.put("dimension", dimarr);
          dimobj.put("attributes", attarr);
          dimobj.put("data", result);
          //CALL Anacleto ROLL UP
          //.(input)
          
          break;  
          
          
        case "roll_up_hierarchy" :

          param1 = input.get(0);
          param2 = input.get(1);
          param3 = input.get(2);
          attparam1 = input.get(0);
          attparam2 = input.get(1);
          attparam3 = input.get(2);
          
          result = bi.rollUpByHierarchy(param1, param2, param3);
          

          
          
          param1 = parseParamFirst(param1);
          param2 = parseParamFirst(param2);
          param3 = parseParamFirst(param3);
          
          dimarr.put(param1);
          dimarr.put(param2);
          dimarr.put(param3);
          
          
          attparam1 = parseParamLast(attparam1);
          attparam2 = parseParamLast(attparam2);
          attparam3 = parseParamLast(attparam3);
          
          attarr.put(attparam1);
          attarr.put(attparam2);
          attarr.put(attparam3);
     
          dimobj.put("dimension", dimarr);
          dimobj.put("attributes", attarr);
          dimobj.put("data", result);

          //CALL Anacleto ROLL UP
     
          
          break;
        
          
          
        case "drill_down_dimension" :
          
          param1 = input.get(0);
          param2 = input.get(1);
          param3 = input.get(2);
          attparam1 = input.get(0);
          attparam2 = input.get(1);
          attparam3 = input.get(2);
          
          ArrayList<String> drillDown = new ArrayList<String>();
          drillDown.add(param1);
          drillDown.add(param2);
          drillDown.add(param3);
          
          output = input.toString();
          
          
          result = bi.drillDownAddDimension(drillDown);
          
          param1 = parseParamFirst(param1);
          param2 = parseParamFirst(param2);
          param3 = parseParamFirst(param3);
          
          dimarr.put(param1);
          dimarr.put(param2);
          dimarr.put(param3);
          
          
          attparam1 = parseParamLast(attparam1);
          attparam2 = parseParamLast(attparam2);
          attparam3 = parseParamLast(attparam3);
          
          attarr.put(attparam1);
          attarr.put(attparam2);
          attarr.put(attparam3);
     
          dimobj.put("dimension", dimarr);
          dimobj.put("attributes", attarr);
          dimobj.put("data", result);
          
        case "drill_down_hierarchy" :
          
          param1 = input.get(0);
          param2 = input.get(1);
          param3 = input.get(2);
          attparam1 = input.get(0);
          attparam2 = input.get(1);
          attparam3 = input.get(2);
         
          
          result = bi.drillDownByHierarchy(param1, param2, param3);
          
          
          param1 = parseParamFirst(param1);
          param2 = parseParamFirst(param2);
          param3 = parseParamFirst(param3);
          
          dimarr.put(param1);
          dimarr.put(param2);
          dimarr.put(param3);
          
          
          attparam1 = parseParamLast(attparam1);
          attparam2 = parseParamLast(attparam2);
          attparam3 = parseParamLast(attparam3);
          
          attarr.put(attparam1);
          attarr.put(attparam2);
          attarr.put(attparam3);
     
          dimobj.put("dimension", dimarr);
          dimobj.put("attributes", attarr);
          dimobj.put("data", result);


          //CALL Anacleto ROLL UP
          //.(input)
          
          
          //CALL Anacleto DRILL DOWN
          break;
        
        case "slice" :
          System.out.println("This is the dimenstion: " + input);
          ArrayList<String> dim = new ArrayList<String>();
          ArrayList<String> val = new ArrayList<String>();
          ArrayList<String> temp = new ArrayList<String>();
          
          int lenOfinput = input.size();
          
          
        
          param1 = input.get(0);
         // param2 = input.get(1);
        //  param3 = input.get(2);
          
          attparam1 = input.get(0);
       //   attparam2 = input.get(1);
       //   attparam3 = input.get(2);
          
          //if(param1.contains("+")){
//          for(int i = 0; i < lenOfinput){
//            
//          }
          
          temp = sliceParse(dimension);
          dim.add(temp.get(0));
          val.add(temp.get(1));
             
              System.out.println(temp.toString());
          //}else{
              //dim.add(param1);
          //}
          /*
	          if(param1.contains("+")){
	              temp = sliceParse(param2);
	              dim.add(temp.get(0));
	              val.add(temp.get(1));
	          }else{
	              dim.add(param2);
	          }
	          if(param1.contains("+")){
	              temp = sliceParse(param3);
	              dim.add(temp.get(0));
	              val.add(temp.get(1));
	          }else{
	              dim.add(param3);
	          }
          */
          
          result = bi.slice(dim, val);
          
          param1 = parseParamFirst(param1);
          //param2 = parseParamFirst(param2);
          //param3 = parseParamFirst(param3);
          
          dimarr.put(param1);
          //dimarr.put(param2);
          //dimarr.put(param3);
          
          
          attparam1 = parseParamLast(attparam1);
          attarr.put(attparam1);
          //attparam2 = parseParamLast(attparam2);
          //attparam3 = parseParamLast(attparam3);
          
          dimarr.put(attparam1);
          //attarr.put(attparam2);
          //attarr.put(attparam3);
     
          dimobj.put("dimension", dimarr);
          dimobj.put("attributes", attarr);
          dimobj.put("data", result);

          
          
          //CALL Anacleto SLICE
          break;
        
        case "dice" :
    	  System.out.println("This is the dice dimenstion: " + input);
          ArrayList<String> dimDice = new ArrayList<String>();
          ArrayList<String> attrDice = new ArrayList<String>();
          ArrayList<String> cardinalDice = new ArrayList<String>();
          
          
          //getting dimensions
          for(int i = 0; i < input.size(); i++) {
        	  String param = input.get(i);
        	  String[] parsedParam = param.split("\\.");
        	  dimDice.add(parsedParam[0]);		//store dim key
        	  attrDice.add(parsedParam[1]);		//store attr
        	  cardinalDice.add(parsedParam[2]);
        	  
        	  System.out.println("This is the dice dimenstion: " + dimDice);
        	  System.out.println("This is the dice attribute: " + attrDice);
        	  System.out.println("This is the dice cardinal: " + cardinalDice);
          }
          
          break;
          
      }   
       
      input.clear();
      
        //output = "Invalid Action";
     
      
      //String output = "Parameter1: " + action + "\nParameter2: " + dimension.toString();

      /** still need to use parse method to parse data into strings **/
      
      
      //return Response.status(200).entity(dimobj.toString()).build();
      System.out.println("Done");
      //return Response.status(200).entity(dimobj.toString()).build();
      return dimobj.toString();
      //return jsonobj;
    }
    
   
    /**
     * VERSION 5.10.2016 @ 430pm
     * Getting data and parsing it into JSON format
     * 
     * 
     * @return
     * @throws JSONException
     */ 
    public ArrayList<String> sliceParse(String input){
      System.out.println("SliceParser" + input);
      ArrayList<String> sliceResult = new ArrayList<>();
      int countOfPlus = 0;
      String combine = "";
      String[] splitDim = input.split("\\.");
      String dim = splitDim[0] +"." + splitDim[1];
      
      sliceResult.add(dim);

      String clause = splitDim[2];
      int num = 0;
      
      String[] plus = clause.split("\\+");
      num =  plus.length;
      
/*      while(num == 1){
        countOfPlus++;
        System.out.println("i am counting" + countOfPlus);
        
      }*/
      
      //System.out.println(num);
      System.out.println(plus[0]);
      
      
      if(num == 1){
         combine = "'"+ plus[0] +"'";
         System.out.println(combine);
      }else{
      
        //String[] values = clause.split("\\+");
        //countOfPlus =  plus.length;
      combine = "'"+ plus[0] +"'";
      
      
        for(int i = 1; i < num; i++){
          combine = combine + " OR " + "'" + plus[i] +"'";
          
        }
      }
      combine = dim + " = " + " " + combine;
      
      sliceResult.add(combine);
      System.out.println("this ist slice: " + sliceResult);
      return sliceResult;
    }
    
    
    /**
     * 
     * @param param
     * @return
     */
    public String parseParamFirst(String param){
      String[] text = null;
      String st = "";
    
      text = param.split("\\.");
      //System.out.println(text[0]);
      
      st = text[0];
      
      //System.out.println(st);
      return st;
    }
    
    /**
     * 
     * @param param
     * @return
     */
    public String parseParamLast(String param){
      String[] text = null;
      String st = "";
      text = param.split("\\.");
     // System.out.println(text[0] + " " + text[1]);
      
      st = text[1];
     // System.out.println(text[1]);
      
      //System.out.println(st);
      return st;
    }
 
    
    /**
     * Parse URL query into an arraylist
     * @param element
     * @return
     */
    public ArrayList<String> parseData(String dimension){
      String[] dataToParse = dimension.split(",");
      ArrayList<String> dataParsed = new ArrayList<>();
      int len = dataToParse.length;
      
      for(int i = 0; i < len; i++){
        
        String x = dataToParse[i];
        dataParsed.add(x);
        
      }
      
      return dataParsed;
    }

    
    

}//End Restful








