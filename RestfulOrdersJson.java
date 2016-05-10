package PO;
import edu.cs157b.util.*;

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


@Path("/bi")
public class RestfulOrdersJson {


    Hw3DAO dao = new Hw3DAO();

    
    /**
     * VERSION 5.10.2016 @ 430pm
     * Get input from URL query string and parse data to send. 
     * 
     *  
     * 
     * @return
     */
    @Path("/parse1")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response inputURLquery(@QueryParam("action") String action, 
                                   @QueryParam("dimension") List<String> dimension){
       
      
      String output = "Prameter1: " + action + "\nParameter2: " + dimension.toString();

      /** still need to use parse method to parse data into strings **/
      
      return Response.status(200).entity(output).build();
    
      
    }
   
    /**
     * VERSION 5.10.2016 @ 430pm
     * Getting data and parsing it into JSON format
     * 
     * 
     * @return
     * @throws JSONException
     */
    
    @Path("/parser")
    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)   
    public String restultsAsJSON(
                                  ) throws JSONException//@DefaultValue("Undefined")                         
                                  //@QueryParam("action") String action)
                                  //@QueryParam("dimension") List<String> element)
                                  {
       
      List<JSONTester> test = new ArrayList<JSONTester>();
      
      String output = "";
      
      
      /** action to roll_up, drill_down, central cube **/ 
   
      /*
      switch(action){
      case "drill_down" :
        
          //output = "This is th drill_down";
         /*
          Data data = new Data();
          JSONTester js = new JSONTester(data);
          test.add(js);
         */
          
         //Statements
         //break; //optional

      
        //output = "Invalid Action";
     //} 
      
      
      
      
      /** JSON formated Template*/
     
      JSONObject jsonSale = new JSONObject();
      
      /*
      String param1 = "Product";
      String param2 = "Store";
      String param3 = "Date_time";
      */
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
      
      
      
      
      
      
      
      return jsonSale.toString();
      
     //Response response = Response.ok(test).header("CustomHeader", "CustomValue").build();
    //  return response;
      //return test;
      
    }
    
    /**
     * Parse input from Web
     * @param element
     * @return
     */
    public String[] parser(String element){
      String[] data = element.split("\\.");
      
      
      return data;
    }

    
    
    
    
    
    
    
    
    
    
    
    /***BELOW is just for refererences *********/
    
    
    @Path("/parse3")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response parseURLquery3(@DefaultValue("Undefined")
                                 // @QueryParam("action") String action,
                                 //@QueryParam("dimension") String dimension,
                                 // @QueryParam("action") String action,
                                 // @QueryParam("action") String action,
                                //  @QueryParam("test") String dimenstion)
                                  @QueryParam("parse") List<String> element)
                                  {
      
      
      
      //String output = "Action: " + action + "\nDimension: " + dimension.toString();
      String output = "parse: " + element.toString();

      
      return Response.status(200).entity(output).build();
    
    }
    
    
    
    
    
    
    
    
    /**
     * Insert Customer into table
     * @param first
     * @param last
     * @param user
     * @return
     */
    @POST
    @Path("customers/insert/{first}/{last}/{user}")
    public Response insertCustomer(@PathParam("first") String first, 
                             @PathParam("last") String last,
                             @PathParam("user") String user){
      
      String output = "POST:Jersey say : " + first + " " + last + " " + user;
      
      Calendar calendar = Calendar.getInstance();
      java.util.Date now = calendar.getTime();
      java.sql.Timestamp deliveryTime = new java.sql.Timestamp(now.getTime());
      
      
      Customer c = new Customer();
      c.setFirstName(first);
      c.setLastName(last);
      c.setUserName(user);
      c.setRegistrationDate(calendar);
      dao.create(c);
      
      return Response.status(200).entity(output).build();
      
    }
    
    /**
     * Add a new customer
     * @param first
     * @param last
     * @param user
     * @return
     */
    @POST
    @Path("customers/{name}")
    public Response addNewCustomer(@PathParam("name")String newCustomer ){
      
      
      Calendar calendar = Calendar.getInstance();
      java.util.Date now = calendar.getTime();
      java.sql.Timestamp deliveryTime = new java.sql.Timestamp(now.getTime());
      
      
      Customer c = new Customer();
      c.setFirstName(newCustomer);
      c.setLastName("NewLastName");
      c.setUserName("NewUser");
      c.setRegistrationDate(calendar);
      dao.create(c);
      
      String output = outputResponse(c, "POST");
      
      return Response.status(200).entity(output).build();
      
    }
    
    

    
    @Path("/customers2")
    @GET
    //@Produces(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAllCust(){

      List<Customer> customer = dao.findAll();
      System.out.println("***after dao method");
  
      
      String output = " ";
      
      for(Customer x: customer){
       output += x.printCustomer(x, "GET");
      }
      
      
      return Response.status(200).entity(output).build();
    
    }
  
    /**
     * PRACTICE ON USING JSON
     * @return
     */
    @Path("/customers3")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List searchAllCustJson(){

      List<Customer> customer = dao.findAll();
      //System.out.println("***after dao method");
  
      
      String output = " ";
      
      for(Customer x: customer){
       output += x.printCustomer(x, "GET");
      }
       Customer c = customer.get(0);
      
      //return Response.status(200).entity(output).build();
      return customer;
    }
  
    
    
    
    
    @Path("customers/{id}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response searchCustByID(@PathParam("id") int custID){
      
      
      Customer c = new Customer();
      c = dao.findCustPrimaryKey(custID);
      String output = outputResponse(c, "GET");

      return Response.status(200).entity(output).build();
    }
    
    
    
    
    @DELETE
    @Path("customers/{id}")
    @Produces({ MediaType.TEXT_PLAIN})
    public Response deleteCustomer(@PathParam("id") int custID){
      
      Customer c = new Customer();
      c = dao.findCustPrimaryKey(custID);
      
      
      String output = outputResponse(c, "DELETE");
      dao.deleteCust(c);
    
      return Response.status(200).entity(output).build();
  
    }
    
    @PUT
    @Path("customers/{id}/{first}")
    @Consumes({ MediaType.TEXT_PLAIN})
    @Produces({ MediaType.TEXT_PLAIN})
    public Response updateUser(@PathParam("id") int custID, 
                               @PathParam("first") String first)
                               {
      
      Customer c = dao.findCustPrimaryKey(custID);
      String output = outputResponse(c, "GET");
      
      c.setFirstName(first);

      
      dao.updateCustomer(c);
      
      output += outputResponse(c, "PUT"); 
      
      
      
      return Response.status(200).entity(output).build();
      
    }
    
    /**
     * create ORDER table
     * @param first
     * @param last
     * @param user
     * @return
     */
    
    @POST
    @Path("orders/insert/{id}/{price}/{paymethod}")
    public Response postCust(@PathParam("id") int id, 
                             @PathParam("price") double price,
                             @PathParam("paymethod") String paymethod){
      
      PlaceOrder order = new PlaceOrder();
      //Customer c = dao.findCustPrimaryKey(id);
      order.setOrderID(id);
      order.setPrice(price);
      order.setPayment(paymethod);
      //order.setCustomer(c);
      dao.createOrder(order);
      
      String output = outputResponseOrder(order, "POST");
      
      //String output = "Test";
      return Response.status(200).entity(output).build();
     
    }
    
    /**
     * DELETE ORDERS
     * @param orderID
     * @return
     */
    @DELETE
    @Path("orders/{id}")
    @Produces({ MediaType.TEXT_PLAIN})
    public Response deleteOrder(@PathParam("id") int orderID){
      
      PlaceOrder order = new PlaceOrder();
      order = dao.findOrderPrimaryKey(orderID);
      
      
      String output = outputResponseOrder(order, "DELETE");
      dao.deleteOrder(order);
    
      return Response.status(200).entity(output).build();
  
    }
    
    /**
     * FIND ALL ORDERS
     * @return
     */
    @Path("orders")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    //@Produces(MediaType.TEXT_PLAIN)
    public Response searchAllOrders(){

      List<PlaceOrder> orders = dao.findAllOrder();
      
      String output = " ";
      
      for(PlaceOrder x: orders){
       output += x.printOrder(x, "GET");
      }
      
      return Response.status(200).entity(output).build();
    
    }
    
    
    
    /**
     * SEARCH ORDER by id
     * @param orderID
     * @return
     */
    @Path("orders/{id}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response searchOrderByID(@PathParam("id") int orderID){
      
      
      PlaceOrder order = new PlaceOrder();
      order = dao.findOrderPrimaryKey(orderID);
      String output = outputResponseOrder(order, "GET");

      return Response.status(200).entity(output).build();
    }
    
    /**
     * ADD NEW ORDER
     * @param price
     * @return
     */
    @POST
    @Path("orders/{price}")
    public Response addNewOrder(@PathParam("price")double price ){
      
      
      PlaceOrder order = new PlaceOrder();
      order.setPrice(price);
      order.setPayment("CASH");
      
      dao.createOrder(order);
      
      String output = outputResponseOrder(order, "POST");
      
      return Response.status(200).entity(output).build();
      
    }
    
    
    @PUT
    @Path("orders/{id}/{price}")
    @Consumes({ MediaType.TEXT_PLAIN})
    @Produces({ MediaType.TEXT_PLAIN})
    public Response updateOrder(@PathParam("id") int orderID, 
                               @PathParam("price") int price)
                               {
      
      PlaceOrder order = dao.findOrderPrimaryKey(orderID);
      String output = outputResponseOrder(order, "GET");
      
      order.setPrice(price);

      
      dao.updateOrder(order);
      
      output += outputResponseOrder(order, "PUT"); 
      
      
      
      return Response.status(200).entity(output).build();
      
    }
    
    
    
    
    /**
     * Response for ORDERS
     * @param c
     * @param method
     * @return
     */
    public String outputResponseOrder(PlaceOrder order, String method){
      
      int id = order.getOrderID();
      double price = order.getPrice();
      String paymethod = order.getPayment();
      //Customer c = order.getCustomer();
      //int custID = c.getCustID();
      String output = method + ":Jersey say : " + 
                               "Order Id) " + id + 
                               "\nPrice: "+ price + 
                               "\nPay Method: " + paymethod + 
                               "\n";
                              // "\nCustomer ID: " + custID;
    
      return output;
    }


    
    
    /**
     * Response  for CUSTOMERS
     * @param c
     * @param method
     * @return
     */
    public String outputResponse(Customer c, String method){
      
      int custID = c.getCustID();
      String first = c.getFirstName();
      String last = c.getLastName();
      String user = c.getUserName();  
      
      String output = method + ":Jersey say : " + "Id) " + custID + 
          "\nFirst Name: "+ first + 
          "\nLast Name: " + last + 
          "\nUser Name: \n" + user;

      return output;
    }

    
    
    

}//End Restful








