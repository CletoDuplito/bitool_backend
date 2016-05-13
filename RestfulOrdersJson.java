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
          if (input.size() == 3) {
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
          }
//          else if (input.size() == 2) {
//        	  param1 = input.get(0);
//              param2 = input.get(1);
//              attparam1 = input.get(0);
//              attparam2 = input.get(1);  
//              
//              //output = input.toString();
//              
//              
//              result = bi.centralCube(param1, param2);
//              //System.out.println(result.toString());
//             // data.put("data", result);
//              
//              
//              param1 = parseParamFirst(param1);
//              param2 = parseParamFirst(param2);
//              
//              dimarr.put(param1);
//              dimarr.put(param2);
//
//              
//              attparam1 = parseParamLast(attparam1);
//              attparam2 = parseParamLast(attparam2);
//              
//              attarr.put(attparam1);
//              attarr.put(attparam2);
//          }
//          else {
//	          param1 = input.get(0);
//	          attparam1 = input.get(0);
//	          
//	          //output = input.toString();
//	          
//	          
//	          result = bi.centralCube(param1);
//	          //System.out.println(result.toString());
//	         // data.put("data", result);
//	          
//	          
//	          param1 = parseParamFirst(param1);
//	          
//	          dimarr.put(param1);
//	
//	          
//	          attparam1 = parseParamLast(attparam1);
//	          
//	          attarr.put(attparam1);
//          }
     
          dimobj.put("dimension", dimarr);
          dimobj.put("attributes", attarr);
          dimobj.put("data", result);

          
          
          
          break;
        
        case "roll_up_dimension" :
          if (input.size() == 2) { //when choosing two dimensions only
        	  param1 = input.get(0);
              param2 = input.get(1);
              
              attparam1 = input.get(0);
              attparam2 = input.get(1);
              
              ArrayList<String> rollUp = new ArrayList<String>();
              rollUp.add(param1);
              rollUp.add(param2);
              
              output = input.toString();
              
              result = bi.rollUpByDimension(rollUp);
              
              param1 = parseParamFirst(param1);
              param2 = parseParamFirst(param2);
              
              dimarr.put(param1);
              dimarr.put(param2);

              
              attparam1 = parseParamLast(attparam1);
              attparam2 = parseParamLast(attparam2);
              
              attarr.put(attparam1);
              attarr.put(attparam2);
          }
          else if (input.size() == 1) { // when choosing one dimension only
        	  param1 = input.get(0);
              
              attparam1 = input.get(0);
              
              ArrayList<String> rollUp = new ArrayList<String>();
              rollUp.add(param1);
              
              output = input.toString();
              
              result = bi.rollUpByDimension(rollUp);
              
              param1 = parseParamFirst(param1);
              
              dimarr.put(param1);
              
              attparam1 = parseParamLast(attparam1);
              
              attarr.put(attparam1);
          }
          else {
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
          }
     
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
          
        	if (input.size() == 2) { //when choosing two dimensions only
          	  param1 = input.get(0);
                param2 = input.get(1);
                
                attparam1 = input.get(0);
                attparam2 = input.get(1);
                
                ArrayList<String> drillDown = new ArrayList<String>();
                drillDown.add(param1);
                drillDown.add(param2);
                
                output = input.toString();
                
                result = bi.drillDownAddDimension(drillDown);
                
                param1 = parseParamFirst(param1);
                param2 = parseParamFirst(param2);
                
                dimarr.put(param1);
                dimarr.put(param2);

                
                attparam1 = parseParamLast(attparam1);
                attparam2 = parseParamLast(attparam2);
                
                attarr.put(attparam1);
                attarr.put(attparam2);
            }
            else if (input.size() == 1) { // when choosing one dimension only
          	  param1 = input.get(0);
                
                attparam1 = input.get(0);
                
                ArrayList<String> drillDown = new ArrayList<String>();
                drillDown.add(param1);
                
                output = input.toString();
                
                result = bi.drillDownAddDimension(drillDown);
                
                param1 = parseParamFirst(param1);
                
                dimarr.put(param1);
                
                attparam1 = parseParamLast(attparam1);
                
                attarr.put(attparam1);
            }
            else {
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
            }
          
          
     
          dimobj.put("dimension", dimarr);
          dimobj.put("attributes", attarr);
          dimobj.put("data", result);
          break;

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
          
          
          //CALL Anacleto DRILL DOWN
          break;
     
          
          
        case "slice" :
            System.out.println("This is the dimension: " + input);
            ArrayList<String> dim = new ArrayList<String>();
            ArrayList<String> val = new ArrayList<String>();
            ArrayList<String> temp = new ArrayList<String>();
            ArrayList<String> param = new ArrayList<String>();
            ArrayList<String> attparam = new ArrayList<String>();
            
            int lenOfinput = input.size();
            System.out.println("This is the input: " + input);
          
            //param1 = input.get(0);
           // param2 = input.get(1);
          //  param3 = input.get(2);
            
            //attparam1 = input.get(0);
         //   attparam2 = input.get(1);
         //   attparam3 = input.get(2);
            
            //if(param1.contains("+")){
            for(int i = 0; i < lenOfinput; i++){
              String[] x = new String[0];
              x = input.get(i).split("\\.");
              int num = x.length;
              //x = new String[0];
             
              if(num < 3){
                dim.add(input.get(i));
                param.add(input.get(i));
                System.out.println("im in num < 3, in slice method: " + num);
                
              }else{
                System.out.println("Im in num > 3, in slice method: " + num);
                temp = sliceParse(input.get(i));
                dim.add(temp.get(0));
                val.add(temp.get(1));
                attparam.add(dim.get(0));            
                //System.out.println(" HELP " + dim.get(0));
              //param.add(input.get(i));
              //attparam.add(input.get(i));
              }

              
            }
               
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
            
            int pl = param.size();
            int al = attparam.size();
            
            for(int i = 0; i < pl; i++){
              String x = param.get(i);
              
              param.set(i, parseParamFirst(x));
              
              
            }
           
            for(int i = 0; i < al; i++){
              String x = attparam.get(i);
              
              attparam.set(i, parseParamLast(x));
              
              
            }
            
            
            
            param1 = parseParamFirst(param1);
            //param2 = parseParamFirst(param2);
            //param3 = parseParamFirst(param3);
            for(int i = 0; i < pl; i++){
              dimarr.put(param.get(i));
              
              
            }
            
            for(int i = 0; i < al; i++){
             attarr.put(attparam.get(i));
            
              
            }           
            
           // attparam1 = parseParamLast(attparam1);
           // attarr.put(attparam1);
            //attparam2 = parseParamLast(attparam2);
            //attparam3 = parseParamLast(attparam3);
            
            //dimarr.put(attparam);
         //   attarr.put(attparam2);
          //  attarr.put(attparam3);
       
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
          
          ArrayList<String> dimAttrPairDice = new ArrayList<String>();
          ArrayList<String> dimAttrCardinalDice = new ArrayList<String>();
          
          //getting dimensions
          for(int i = 0; i < input.size(); i++) {
        	  String paramDim = input.get(i);
        	  String[] parsedParam = paramDim.split("\\.");
        	  dimDice.add(parsedParam[0]);	 	 //store dim key
        	  attrDice.add(parsedParam[1]);		 //store attr
        	  cardinalDice.add(parsedParam[2]);  //store cardinal
        	  
        	  dimAttrPairDice.add(parsedParam[0] + "." + parsedParam[1]);		//create dim attr pair
        	  
        	  if(parsedParam[2].split("\\+").length > 1) { 						//handling more than 1 cardinal value case
        		  
        		  String[] cardinalParams = parsedParam[2].split("\\+");		//split the cardinals
        		  ArrayList<String> tempCardinals = new ArrayList<String>();
        		  
        		  for(int x = 0; x < cardinalParams.length; x++) {
        			  tempCardinals.add(parsedParam[0] + "." + parsedParam[1] + " = '" + cardinalParams[x] + "'");	//add the cardinal to its parent attribute
        		  }
        		  
        		  dimAttrCardinalDice.add("(" + String.join(" OR ", tempCardinals) + ")");							//join the cardinals into a formatted string
        		  
        		  System.out.println("This is the dice cardinal has more than 1 value: ");
        		  
        	  } else {
        		  dimAttrCardinalDice.add("(" + parsedParam[0] + "." + parsedParam[1] + " = '" + parsedParam[2] + "')") ; //add the cardinal into a formatted string
        	  }
        	  
        	  

          }
//    	  System.out.println("This is the dice dimenstion: " + dimDice);
//    	  System.out.println("This is the dice attribute: " + attrDice);
//    	  System.out.println("This is the dice cardinal: " + cardinalDice);
          System.out.println("This is the dice dimAttrPairDice: " + dimAttrPairDice);
    	  System.out.println("This is the dice dimAttrCardinalDice: " + dimAttrCardinalDice );
          
          result = bi.dice(dimAttrPairDice, dimAttrCardinalDice);
          
          dimobj.put("dimension", dimDice);
          dimobj.put("attributes", attrDice);
          dimobj.put("data", result);
    	  System.out.println("Result is: " + result.toString() );
          
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
        System.out.println("num: " + num);
  /*      while(num == 1){
          countOfPlus++;
          System.out.println("i am counting" + countOfPlus);
          
        }*/
        
        //System.out.println(num);
        System.out.println(plus[0]);
        
        
        if(num == 1){
           combine = "'"+ plus[0] +"'";
           System.out.println("combine: " + combine);
        }else{
        
          //String[] values = clause.split("\\+");
          //countOfPlus =  plus.length;
        combine = "'"+ plus[0] +"'";
        
        
          for(int i = 1; i < num; i++){
            combine = combine + " OR " + dim + " = '" + plus[i] +"'";
            
          }
          System.out.println("combine result: " + combine);
        }
        combine = '(' + dim + " = " + " " + combine + ')';
        
        sliceResult.add(combine);
        System.out.println("this is slice: " + sliceResult);
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








