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


public class BITool {

   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/EMP";

   //  Database credentials
   static final String USER = "username";
   static final String PASS = "password";
/* //Register JDBC driver
   Class.forName("com.mysql.jdbc.Driver");*/

   
   	@PUT
	@Path("/{id}/{new_price}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Sales> centralCube(@PathParam("id") int orderId, @PathParam("new_price") double newPrice) throws SQLException
	{
   		Connection conn = null;
   		Statement statement = null;
   		ResultSet rs = null;
   		List<Sales> sales = new ArrayList<Sales>();
   	    
   		try {
		    //Open a connection
		    System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
   			
   			statement = conn.createStatement();
   			//Execute a query that returns the central cube
   			System.out.println("Creating statement...");
		   	 String sql;
		     sql = "SELECT p.category, s.store_state, t.year, sum(f.dollar_sales) AS sales_total" +
		   		"FROM Product p, Store s, Date_time t, Sales f" +
		   		"WHERE f.product_key = p.product_key AND" +
		   		"f.store_key = s.store_key AND f.time_key = t.time_key" +
		   		"GROUP BY p.category, s.store_state, t.year";
		     rs = statement.executeQuery(sql);
		
		     //Extract data from result set
		     while(rs.next()){
		        //Retrieve by column name
		    	Sales sale = new Sales();
		    	sale.setProduct(rs.getString("p.category"));
		        sale.setStore(rs.getString("s.store_state"));
		        sale.setDateTime(rs.getInt("t.year"));
		        sale.setSalesTotal(rs.getInt("sales_total"));
		        sales.add(sale);
		
		        //Display values
		        /*System.out.print("Category: " + category);
		        System.out.print(", Store_state: " + storeState);
		        System.out.print(", Year: " + year);
		        System.out.print(", Sales_total: " + salesTotal);*/
		     }
   		} //end try
	   	 finally{
			  if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
			  if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
			  System.out.println("Closing....");
			  if (conn != null) try { conn.close(); } catch (SQLException ignore) {} 
		   } //end finally 
   		return sales; 
	} //central cube
   
   	
   	
 	@PUT
	@Path("/{id}/{new_price}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Sales> rollUp(@PathParam("id") int orderId, @PathParam("new_price") double newPrice) throws SQLException
	{
   		Connection conn = null;
   		Statement statement = null;
   		ResultSet rs = null;
   		List<Sales> sales = new ArrayList<Sales>();
   	    
   		try {
   			//Open a connection
		    System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
   			
   			statement = conn.createStatement();
   			//Execute a query that rolls up the central cube by rolling up the hierarchy
   			System.out.println("Creating statement...");
		   	String sql;
		    sql = "SELECT p.department, s.store_state, t.year, sum(f.dollar_sales) AS sales_total" +
		    		"FROM Product p, Store s, Date_time t, Sales f" +
		    		"WHERE f.product_key = p.product_key AND" +
		    		"f.store_key = s.store_key AND f.time_key = t.time_key" +
		    		"GROUP BY p.department, s.store_state, t.year";
		    rs = statement.executeQuery(sql);
		
		     //Extract data from result set
		     while(rs.next()){
		        //Retrieve by column name
		    	Sales sale = new Sales();
		    	sale.setProduct(rs.getString("p.department"));
		        sale.setStore(rs.getString("s.store_state"));
		        sale.setDateTime(rs.getInt("t.year"));
		        sale.setSalesTotal(rs.getInt("sales_total"));
		        sales.add(sale);
		
		        //Display values
		        /* System.out.print("Department: " + department);
		         System.out.print(", Store_state: " + storeState);
		         System.out.print(", Year: " + year);
		         System.out.print(", Sales_total: " + salesTotal);*/
		     }
   		} //end try
	   	 finally{
			  if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
			  if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
			  System.out.println("Closing....");
			  if (conn != null) try { conn.close(); } catch (SQLException ignore) {} 
		   } //end finally 
   		return sales; 
	} //rollup by hierarchy
   	
   	
 	
 	@PUT
	@Path("/{id}/{new_price}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Sales> rollUpByDimension(@PathParam("id") int orderId, @PathParam("new_price") double newPrice) throws SQLException
	{
   		Connection conn = null;
   		Statement statement = null;
   		ResultSet rs = null;
   		List<Sales> sales = new ArrayList<Sales>();
   	    
   		try {
   			//Open a connection
		    System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
		    
   			statement = conn.createStatement();
   			//Execute a query that rolls up by reducing a dimension
   			System.out.println("Creating statement...");
		   	 String sql;
		     sql = "SELECT s.store_state, t.year, sum(f.dollar_sales) AS sales_total" +
		    		"FROM Product p, Store s, Date_time t, Sales f" +
		    		"WHERE f.product_key = p.product_key AND" +
		    		"f.store_key = s.store_key AND f.time_key = t.time_key" +
		    		"GROUP BY s.store_state, t.year";
		     rs = statement.executeQuery(sql);
		
		     //Extract data from result set
		     while(rs.next()){
		        //Retrieve by column name
		    	Sales sale = new Sales();
		        sale.setStore(rs.getString("s.store_state"));
		        sale.setDateTime(rs.getInt("t.year"));
		        sale.setSalesTotal(rs.getInt("sales_total"));
		        sales.add(sale);
		
		        //Display values
		        /*System.out.print("Store_state: " + storeState);
		         System.out.print(", Year: " + year);
		         System.out.print(", Sales_total: " + salesTotal);*/
		     }
   		} //end try
	   	 finally{
			  if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
			  if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
			  System.out.println("Closing....");
			  if (conn != null) try { conn.close(); } catch (SQLException ignore) {} 
		   } //end finally 
   		return sales; 
	} //roll up by dimension
   	

   	
 	@PUT
	@Path("/{id}/{new_price}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Sales> drillDown(@PathParam("id") int orderId, @PathParam("new_price") double newPrice) throws SQLException
	{
   		Connection conn = null;
   		Statement statement = null;
   		ResultSet rs = null;
   		List<Sales> sales = new ArrayList<Sales>();
   	    
   		try {
   			//Open a connection
		    System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
		    
   			statement = conn.createStatement();
   			//Execute a query that drills down by climbing down the hierarchy
   			System.out.println("Creating statement...");
		   	 String sql;
		     sql = "SELECT p.brand, s.city, t.year, sum(f.dollar_sales) AS sales_total" +
		    		"FROM Product p, Store s, Date_time t, Sales f" +
		    		"WHERE f.product_key = p.product_key AND" +
		    		"f.store_key = s.store_key AND f.time_key = t.time_key" +
		    		"GROUP BY p.brand, s.city, t.year";
		     rs = statement.executeQuery(sql);
		
		     //Extract data from result set
		     while(rs.next()){
		        //Retrieve by column name
		    	Sales sale = new Sales();
		    	sale.setProduct(rs.getString("p.brand"));
		        sale.setStore(rs.getString("s.city"));
		        sale.setDateTime(rs.getInt("t.year"));
		        sale.setSalesTotal(rs.getInt("sales_total"));
		        sales.add(sale);
		
		        
		        //Display values
		        /*System.out.print("Brand: " + brand);
		         System.out.print(", City: " + storeCity);
		         System.out.print(", Year: " + year);
		         System.out.print(", Sales_total: " + salesTotal);*/
		     }
   		} //end try
	   	 finally{
			  if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
			  if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
			  System.out.println("Closing....");
			  if (conn != null) try { conn.close(); } catch (SQLException ignore) {} 
		   } //end finally 
   		return sales; 
	} //drill down
   	
	
 	@PUT
	@Path("/{id}/{new_price}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Sales> drillDownAddDimension(@PathParam("id") int orderId, @PathParam("new_price") double newPrice) throws SQLException
	{
   		Connection conn = null;
   		Statement statement = null;
   		ResultSet rs = null;
   		List<Sales> sales = new ArrayList<Sales>();
   	    
   		try {
   			//Open a connection
		    System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
   			
   			statement = conn.createStatement();
   			//Execute a query that drills down by adding a dimension
		      System.out.println("Creating statement...");
		   	 String sql;
		   	 sql = "SELECT p.package_size, p.category, s.city,  s.store_state, t.year, sum(f.dollar_sales) AS sales_total" +
		    		"FROM Product p, Store s, Date_time t, Sales f" +
		    		"WHERE f.product_key = p.product_key AND" +
		    		"f.store_key = s.store_key AND f.time_key = t.time_key" +
		    		"GROUP BY p.package_size, p.category, s.city, s.store_state, t.year";
		     rs = statement.executeQuery(sql);
		
		     //Extract data from result set
		     while(rs.next()){
		        //Retrieve by column name
		    	Sales sale = new Sales();
		    	sale.setProduct(rs.getString("p.package_size"));
		    	sale.setProduct(rs.getString("p.category"));  //check how to deal with two product strings
		        sale.setStore(rs.getString("s.city"));      //check how to deal with two store strings
		        sale.setStore(rs.getString("s.store_state"));
		        sale.setDateTime(rs.getInt("t.year"));
		        sale.setSalesTotal(rs.getInt("sales_total"));
		        sales.add(sale);
	
	
		         //Display values
		         /*System.out.print("Package_size: " + packageSize);
		         System.out.print(", Category: " + category);
		         System.out.print(", City: " + storeCity);
		         System.out.print(", Store_state: " + storeState);
		         System.out.print(", Year: " + year);
		         System.out.print(", Sales_total: " + salesTotal);*/
		     }
   		} //end try
	   	 finally{
			  if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
			  if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
			  System.out.println("Closing....");
			  if (conn != null) try { conn.close(); } catch (SQLException ignore) {} 
		   } //end finally 
   		return sales; 
	} //drill down by adding dimension
   	
 	

 	@PUT
	@Path("/{id}/{new_price}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Sales> slice(@PathParam("id") int orderId, @PathParam("new_price") double newPrice) throws SQLException
	{
   		Connection conn = null;
   		Statement statement = null;
   		ResultSet rs = null;
   		List<Sales> sales = new ArrayList<Sales>();
   	    
   		try {
   			//Open a connection
		    System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
   			
   			statement = conn.createStatement();
   			//Execute a query that slices the cube
   			System.out.println("Creating statement...");
		   	 String sql;
		     sql = "SELECT s.city, s.store_state, s.store_zip, sum(f.dollar_sales) AS sales_total" +
		    		"FROM Product p, Store s, Date_time t, Sales f" +
		    		"WHERE f.product_key = p.product_key AND" +
		    		"f.store_key = s.store_key AND f.time_key = t.time_key AND" +
		    		"s.store_state = PA" +
		    		"GROUP BY s.city, s.store_state, s.store_zip";
		     rs = statement.executeQuery(sql);
		
		     //Extract data from result set
		     while(rs.next()){
		        //Retrieve by column name
		    	Sales sale = new Sales();
		    	sale.setStore(rs.getString("s.city"));
		        sale.setStore(rs.getString("s.store_state"));
		        sale.setStore(rs.getString("s.store_zip"));
		        sale.setSalesTotal(rs.getInt("sales_total"));
		        sales.add(sale);
	
		         //Display values
		         /*System.out.print("City: " + storeCity);
		         System.out.print(", Store_state: " + storeState);
		         System.out.print(", store_zip: " + zipCode);
		         System.out.print(", Sales_total: " + salesTotal);*/
		     }
   		} //end try
	   	 finally{
			  if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
			  if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
			  System.out.println("Closing....");
			  if (conn != null) try { conn.close(); } catch (SQLException ignore) {} 
		   } //end finally 
   		return sales; 
	} //slice
 	
 	
 	
 	@PUT
	@Path("/{id}/{new_price}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Sales> dice(@PathParam("id") int orderId, @PathParam("new_price") double newPrice) throws SQLException
	{
   		Connection conn = null;
   		Statement statement = null;
   		ResultSet rs = null;
   		List<Sales> sales = new ArrayList<Sales>();
   	    
   		try {
   			//Open a connection
		    System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
   			
   			statement = conn.createStatement();
   			//Execute a query that dices the cube
   			System.out.println("Creating statement...");
		   	 String sql;
		     sql = "SELECT p.category, s.store_state, t.year, sum(f.dollar_sales) AS sales_total" +
		    		"FROM Product p, Store s, Date_time t, Sales f" +
		    		"WHERE f.product_key = p.product_key AND" +
		    		"f.store_key = s.store_key AND f.time_key = t.time_key AND" +
		    		"(p.category = Food OR p.category = Drinks) AND" +
		    		"(s.store_state = CA OR s.store_state = NY) AND t.year = 1994" +
		    		"GROUP BY p.category, s.store_state, t.year";
		     rs = statement.executeQuery(sql);
		
		     //Extract data from result set
		     while(rs.next()){
		        //Retrieve by column name
		    	Sales sale = new Sales();
		    	sale.setProduct(rs.getString("p.category"));
		        sale.setStore(rs.getString("s.store_state"));
		        sale.setDateTime(rs.getInt("t.year"));
		        sale.setSalesTotal(rs.getInt("sales_total"));
		        sales.add(sale);
	
	
		         //Display values
		         /*System.out.print("City: " + category);
		         System.out.print(", Store_state: " + storeState);
		         System.out.print(", Year: " + year);
		         System.out.print(", Sales_total: " + salesTotal);*/
		     }
   		} //end try
	   	 finally{
			  if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
			  if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
			  System.out.println("Closing....");
			  if (conn != null) try { conn.close(); } catch (SQLException ignore) {} 
		   } //end finally 
   		return sales; 
	} //dice
  
   	
   	
   	
   public static void main(String[] args) {
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
} // end class
