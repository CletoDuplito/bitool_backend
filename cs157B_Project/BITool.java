package cs157B_Project;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Business Intelligence tool that grabs the data from the database
 * @author duplito
 */
public class BITool {

   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/grocery"; //change database name after localhost/

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "cs157b";

   public String parseParam(String param){
	    String[] text = null;
	    String st = "";
	    text = param.split("\\.");
	    
	    st = text[1];
	    
	    System.out.println(st);
	    return st;
	  }
   
   
   public JSONArray convertRsToJSON(String product, String store, String dateTime, ResultSet rs) throws Exception {
	   ArrayList<String> attr = new ArrayList<>();
	   JSONArray jsonArray = new JSONArray();
       attr.add(parseParam(product));
       attr.add(parseParam(store));
       attr.add(parseParam(dateTime));
       
       ToJSON rsToJSON = new ToJSON();
       jsonArray = rsToJSON.toJSONArray(rs, attr);
       return jsonArray;
   }
   
   /**
    * Returns the objects containing the data table for central cube.
    * @param product the Product's dimension
    * @param store the Store's dimension
    * @param dateTime the Date_time's dimension
    * @return The JSONArray containing the rows of data
    * @throws Exception 
    */
	public JSONArray centralCube(String product, String store, String dateTime) throws Exception
	{
   		Connection conn = null;
   		PreparedStatement statement = null;
   		ResultSet rs = null;
   	    JSONArray jsonArray = new JSONArray();
   		try {
		    //Open a connection
		    System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
   			
   			//Execute a query that returns the central cube
   			System.out.println("Creating statement...");
		   	 String sql;
		   	sql = "SELECT " + product + ", " + store + ", " + dateTime + ", " + "sum(sales.dollar_sales) AS sales_total " +
			   		"FROM Product, Store, Date_time, Sales " +
			   		"WHERE Sales.product_key = Product.product_key AND " +
		    		"Sales.store_key = Store.store_key AND Sales.time_key = Date_time.time_key " +
			   		"GROUP BY " + product +", " + store +", " + dateTime;
		   	 statement = conn.prepareStatement(sql);
		     /*statement.setString(1,  product);
		     statement.setString(2, store);
		     statement.setString(3, dateTime);*/
		     rs = statement.executeQuery(sql);
		     
		    /* //Extract data from result set
		     while(rs.next()){
		    	int total_rows = rs.getMetaData().getColumnCount();
		    	JSONObject obj = new JSONObject();
		    	for (int i = 0; i < total_rows; i++) {
		    		obj.put(rs.getMetaData().getColumnLabel(i+ 1).toLowerCase(),
		    				rs.getObject(i + 1));
		    		jsonArray.put(obj);
		    	}
		    	}*/
		     /*ArrayList<String> attr = new ArrayList<>();
	         attr.add(parseParam(product));
	         attr.add(parseParam(store));
	         attr.add(parseParam(dateTime));
	         
	         ToJSON rsToJSON = new ToJSON();

	         jsonArray = rsToJSON.toJSONArray(rs, attr);*/
	         jsonArray = convertRsToJSON(product, store, dateTime, rs);
		     
   		} //end try
	   	 finally{
			  if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
			  if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
			  System.out.println("Closing....");
			  if (conn != null) try { conn.close(); } catch (SQLException ignore) {} 
		   } //end finally 
   		return jsonArray; 
	} //central cube
   
   	
   	/**
   	 * Returns the objects containing the data table for rolling up by concept hierarchy
   	 * @param product the Product's Dimension
   	 * @param store the Store's Dimension
   	 * @param dateTime the Date_time dimension
   	 * @return The JSONArray containing the rows of data
   	 * @throws Exception 
   	 */
	public JSONArray rollUpByHierarchy(String product, String store, String dateTime) throws Exception
	{
   		Connection conn = null;
   		Statement statement = null;
   		ResultSet rs = null;
   		JSONArray jsonArray = new JSONArray();
   		try {
   			//Open a connection
		    System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
   			
   			statement = conn.createStatement();
   			//Execute a query that rolls up the central cube by rolling up the hierarchy
   			System.out.println("Creating statement...");
		   	String sql;
		    sql = "SELECT " + product + ", " + store + ", " + dateTime + ", " + "sum(sales.dollar_sales) AS sales_total " +
			   		"FROM Product, Store, Date_time, Sales " +
			   		"WHERE Sales.product_key = Product.product_key AND " +
		    		"Sales.store_key = Store.store_key AND Sales.time_key = Date_time.time_key " +
			   		"GROUP BY " + product + ", " + store + ", " + dateTime;
		    rs = statement.executeQuery(sql);
		
		     //Extract data from result set
		  /*   while(rs.next()){
		    	 int total_rows = rs.getMetaData().getColumnCount();
			    	JSONObject obj = new JSONObject();
			    	for (int i = 0; i < total_rows; i++) {
			    		obj.put(rs.getMetaData().getColumnLabel(i+ 1).toLowerCase(),
			    				rs.getObject(i + 1));
			    		jsonArray.put(obj);
			    	}
		     }*/
		     
		     jsonArray = convertRsToJSON(product, store, dateTime, rs);
		     
   		} //end try
	   	 finally{
			  if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
			  if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
			  System.out.println("Closing....");
			  if (conn != null) try { conn.close(); } catch (SQLException ignore) {} 
		   } //end finally 
   		return jsonArray; 
	} //roll up by hierarchy
   	
   	
	/**
	 * Returns the objects containing the data table for removing a dimension
	 * @param dimensions the number of dimensions to be passed to create a broader central cube
	 * @return The JSONArray containing the rows of data
	 * @throws SQLException
	 * @throws JSONException
	 */
	public JSONArray rollUpByDimension(ArrayList<String> dimensions) throws SQLException, JSONException
	{
   		Connection conn = null;
   		Statement statement = null;
   		ResultSet rs = null;
   		JSONArray jsonArray = new JSONArray();
   		try {
   			//Open a connection
		    System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
		    
   			statement = conn.createStatement();
   			//Execute a query that rolls up by reducing a dimension
   			System.out.println("Creating statement...");
		   	 String sql;
		   	 //Extracts all dimension-attributes names and formats them into Select statement
		   	 String select_statement = "";
		   	 for (int i = 0; i < dimensions.size(); i++) {
		   		 if (i == 0) select_statement = "" + dimensions.get(i);
		   		 else {
		   			select_statement = select_statement + ", " + dimensions.get(i);
		   		 }
		   	 }
		   	sql = "SELECT " + select_statement + ", " + "sum(sales.dollar_sales) AS sales_total " +
		    		"FROM Product, Store, Date_time, Sales " +
		    		"WHERE Sales.product_key = Product.product_key AND " +
		    		"Sales.store_key = Store.store_key AND Sales.time_key = Date_time.time_key " +
		    		"GROUP BY " + select_statement;
		     rs = statement.executeQuery(sql);
		
		     //Extract data from result set
		     while(rs.next()){
		    	 int total_rows = rs.getMetaData().getColumnCount();
			    	JSONObject obj = new JSONObject();
			    	for (int i = 0; i < total_rows; i++) {
			    		obj.put(rs.getMetaData().getColumnLabel(i+ 1).toLowerCase(),
			    				rs.getObject(i + 1));
			    		jsonArray.put(obj);
			    	}
		     }
   		} //end try
	   	 finally{
			  if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
			  if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
			  System.out.println("Closing....");
			  if (conn != null) try { conn.close(); } catch (SQLException ignore) {} 
		   } //end finally 
   		return jsonArray; 
	} //roll up by dimension
   	

   /**
    * Returns the object containing data table for drilling down by concept hierarchy
    * @param product the Product's Dimension
    * @param store the Store's Dimension
    * @param dateTime the Date_time's Dimension
    * @return The JSONArray containing the rows of data
    * @throws Exception 
    */
	public JSONArray drillDownByHierarchy(String product, String store, String dateTime) throws Exception
	{
   		Connection conn = null;
   		Statement statement = null;
   		ResultSet rs = null;
   		JSONArray jsonArray = new JSONArray();
   		try {
   			//Open a connection
		    System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
		    
   			statement = conn.createStatement();
   			//Execute a query that drills down by climbing down the hierarchy
   			System.out.println("Creating statement...");
		   	 String sql;
		     sql = "SELECT " + product + ", " + store + ", " + dateTime + ", " + "sum(sales.dollar_sales) AS sales_total " +
			   		"FROM Product, Store, Date_time, Sales " +
			   		"WHERE Sales.product_key = Product.product_key AND " +
		    		"Sales.store_key = Store.store_key AND Sales.time_key = Date_time.time_key " +
			   		"GROUP BY " + product + ", " + store + ", " + dateTime;
		     rs = statement.executeQuery(sql);
		
		     //Extract data from result set
		    /* while(rs.next()){
		    	 int total_rows = rs.getMetaData().getColumnCount();
			    	JSONObject obj = new JSONObject();
			    	for (int i = 0; i < total_rows; i++) {
			    		obj.put(rs.getMetaData().getColumnLabel(i+ 1).toLowerCase(),
			    				rs.getObject(i + 1));
			    		jsonArray.put(obj);
			    	}
		     }*/
		     jsonArray = convertRsToJSON(product, store, dateTime, rs);
   		} //end try
	   	 finally{
			  if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
			  if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
			  System.out.println("Closing....");
			  if (conn != null) try { conn.close(); } catch (SQLException ignore) {} 
		   } //end finally 
   		return jsonArray; 
	} //drill down by hierarchy
   	
	
	/**
	 * Returns the objects containing the data table for adding a dimension
	 * @param dimensions the number of dimensions used to represent a specific cube
	 * @return The JSONArray containing the rows of data
	 * @throws SQLException
	 * @throws JSONException
	 */
	public JSONArray drillDownAddDimension(ArrayList<String> dimensions) throws SQLException, JSONException
	{
   		Connection conn = null;
   		Statement statement = null;
   		ResultSet rs = null;
   		JSONArray jsonArray = new JSONArray();
   		try {
   			//Open a connection
		    System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
   			
   			statement = conn.createStatement();
   			//Execute a query that drills down by adding a dimension
		      System.out.println("Creating statement...");
		   	 String sql;
		   	 String select_statement = "";
		   	 for (int i = 0; i < dimensions.size(); i++) {
		   		 if (i == 0) select_statement = "" + dimensions.get(i);
		   		 else {
		   			select_statement = select_statement + ", " + dimensions.get(i);
		   		 }
		   	 }
		   	sql = "SELECT " + select_statement + ", " + "sum(sales.dollar_sales) AS sales_total " +
		    		"FROM Product, Store, Date_time, Sales " +
		    		"WHERE Sales.product_key = Product.product_key AND " +
		    		"Sales.store_key = Store.store_key AND Sales.time_key = Date_time.time_key " +
		    		"GROUP BY " + select_statement;
		     rs = statement.executeQuery(sql);
		
		     //Extract data from result set
		     while(rs.next()){
		    	 
		    	 int total_rows = rs.getMetaData().getColumnCount();
			    	JSONObject obj = new JSONObject();
			    	for (int i = 0; i < total_rows; i++) {
			    		obj.put(rs.getMetaData().getColumnLabel(i+ 1).toLowerCase(),
			    				rs.getObject(i + 1));
			    		jsonArray.put(obj);
			    	}
		     }
   		} //end try
	   	 finally{
			  if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
			  if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
			  System.out.println("Closing....");
			  if (conn != null) try { conn.close(); } catch (SQLException ignore) {} 
		   } //end finally 
   		return jsonArray; 
	} //drill down by adding dimension
   	

	/**
	 * Returns the objects containing the table data for slicing a cube
	 * @param dimensions the parameters used in select and group by statements to slice the cube
	 * @param parameters the parameters used in the where clause to slice the cube
	 * @return The JSONArray containing the rows of data
	 * @throws SQLException
	 * @throws JSONException
	 */
	public JSONArray slice(ArrayList<String> dimensions, ArrayList<String> parameters) throws SQLException, JSONException
	{
   		Connection conn = null;
   		Statement statement = null;
   		ResultSet rs = null;
   		JSONArray jsonArray = new JSONArray();
   		try {
   			//Open a connection
		    System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
   			
   			statement = conn.createStatement();
   			//Execute a query that slices the cube
   			System.out.println("Creating statement...");
		   	String sql;
		   	String select_statement = "";
		   	 for (int i = 0; i < dimensions.size(); i++) {
		   		 if (i == 0) select_statement = "" + dimensions.get(i);
		   		 else {
		   			select_statement = select_statement + ", " + dimensions.get(i);
		   		 }
		   	 }
		   	 String where_clause = "";
		   	 for (int i = 0; i < parameters.size(); i++) {
		   		 if (i == 0) where_clause = "" + parameters.get(i);
		   		 else {
		   			 where_clause = where_clause + ", " + parameters.get(i);
		   		 }
		   	 }
		     sql = "SELECT " + select_statement + ", sum(sales.dollar_sales) AS sales_total " +
		    		"FROM Product, Store, Date_time, Sales " +
		    		"WHERE Sales.product_key = Product.product_key AND " +
		    		"Sales.store_key = Store.store_key AND Sales.time_key = Date_time.time_key AND " +
		    		where_clause +
		    		" GROUP BY " + select_statement;
		     rs = statement.executeQuery(sql);
		
		     //Extract data from result set
		     while(rs.next()){
		    	 
		    	 int total_rows = rs.getMetaData().getColumnCount();
			    	JSONObject obj = new JSONObject();
			    	for (int i = 0; i < total_rows; i++) {
			    		obj.put(rs.getMetaData().getColumnLabel(i+ 1).toLowerCase(),
			    				rs.getObject(i + 1));
			    		jsonArray.put(obj);
			    	}
		     }
   		} //end try
	   	 finally{
			  if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
			  if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
			  System.out.println("Closing....");
			  if (conn != null) try { conn.close(); } catch (SQLException ignore) {} 
		   } //end finally 
   		return jsonArray; 
	} //slice
 	
 	
 	
	
	/**
	 * Returns the object containing the table data representing a dice of the central cube
	 * @param dimensions the parameters used in select and group by statements to dice the cube
	 * @param parameters the parameters used in the where clause to dice the cube
	 * @return The JSONArray containing the rows of data
	 * @throws SQLException
	 * @throws JSONException
	 */
	public JSONArray dice(ArrayList<String> dimensions, ArrayList<String> parameters) throws SQLException, JSONException
	{
   		Connection conn = null;
   		Statement statement = null;
   		ResultSet rs = null;
   		JSONArray jsonArray = new JSONArray();
   		try {
   			//Open a connection
		    System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
   			
   			statement = conn.createStatement();
   			//Execute a query that dices the cube
   			System.out.println("Creating statement...");
		   	String sql;
		   	String select_statement = "";
		   	 for (int i = 0; i < dimensions.size(); i++) {
		   		 if (i == 0) select_statement = "" + dimensions.get(i);
		   		 else {
		   			select_statement = select_statement + ", " + dimensions.get(i);
		   		 }
		   	 }
		   	 String where_clause = "";
		   	 for (int i = 0; i < parameters.size(); i++) {
		   		 if (i == 0) where_clause = "" + parameters.get(i);
		   		 else {
		   			 where_clause = where_clause + ", " + parameters.get(i);
		   		 }
		   	 }
		     sql = "SELECT " + select_statement + ", sum(sales.dollar_sales) AS sales_total " +
		    		"FROM Product, Store, Date_time, Sales " +
		    		"WHERE Sales.product_key = Product.product_key AND " +
		    		"Sales.store_key = Store.store_key AND Sales.time_key = Date_time.time_key AND " +
		    		where_clause +
		    		" GROUP BY " + select_statement;
		   	 
		    /* sql = "SELECT Product.category, Store.store_state, Date_time.year, sum(sales.dollar_sales) AS sales_total " +
		    		"FROM Product, Store, Date_time, Sales " +
		    		"WHERE Sales.product_key = Product.product_key AND " +
		    		"Sales.store_key = Store.store_key AND Sales.time_key = Date_time.time_key AND " +
		    		"(Product.category = Food OR p.category = Drinks) AND " +
		    		"(s.store_state = CA OR s.store_state = NY) AND t.year = 1994 " +
		    		"GROUP BY Product.category, Store.store_state, Date_time.year";*/
		     rs = statement.executeQuery(sql);
		
		     //Extract data from result set
		     while(rs.next()){
		    	 
		    	 int total_rows = rs.getMetaData().getColumnCount();
			    	JSONObject obj = new JSONObject();
			    	for (int i = 0; i < total_rows; i++) {
			    		obj.put(rs.getMetaData().getColumnLabel(i+ 1).toLowerCase(),
			    				rs.getObject(i + 1));
			    		jsonArray.put(obj);
			    	}
		     }
   		} //end try
	   	 finally{
			  if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
			  if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
			  System.out.println("Closing....");
			  if (conn != null) try { conn.close(); } catch (SQLException ignore) {} 
		   } //end finally 
   		return jsonArray; 
	} //dice
  
   	
   	
	
  /* public static void main(String[] args) {
	   Connection conn = null;
	   Statement stmt = null;
	   try{
		   	  //Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");
	
		      //Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	
		      //Execute a query that creates the central cube
		      System.out.println("Creating statement...");


		      stmt.close();
		      conn.close();
	   }
	   catch(SQLException se) {
		   //Handles errors for JDBC
		   se.printStackTrace();
	   }
	   catch (Exception e) {
		   //Handles errors for Class.forName
		   e.printStackTrace();
	   } finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
	   }//end try
	   
	   
	   
	   System.out.println("Closing");
   }//end main
*/   
   
   
} // end class
