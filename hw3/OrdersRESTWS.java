package hw3;

import java.util.Calendar;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.Timestamp;

@Path("/orders")
public class OrdersRESTWS {

	private Hw3DAO dao = new Hw3DAO();
	private final int TOPPING_MAX = 3;
	
	/**
	 * A good URI example for this is:
	 * http://localhost:8080/FirstRestfulService/api/orders/param?size=SMALL&topping=Pepperoni&topping=Sausage&topping=Bacon
	 *
	 */
	@Path("/{param}")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response createOrder(@QueryParam("size") PizzaSize.PizzaSizes pizzaSize, @QueryParam("topping") List<String> toppings)
	{
		PizzaOrder o = new PizzaOrder();
		PizzaSize size = new PizzaSize();
		size.setPrice(pizzaSize);
		double pizzaPrice = size.getPrice();
		o.setPizzaSize(pizzaSize);
		o.setOrderPrice(pizzaPrice);
		double toppingPrice = 0;
		for (int i = 0; i < toppings.size(); i++) {
			Topping oneTopping = new Topping();
			oneTopping.setName(toppings.get(i));
			oneTopping.setPrice(toppings.get(i));
			o.addTopping(oneTopping);
			toppingPrice += oneTopping.getPrice();
		}
		o.setToppingPrice(toppingPrice);
		double totalPrice = o.getOrderPrice() + o.getToppingsPrice();
		o.setOrderPrice(totalPrice);
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp deliveryTime = new java.sql.Timestamp(now.getTime());
		o.setDeliveryTime(deliveryTime);
		PizzaOrder created = dao.createOrder(o);
		String output = "Creating pizza order: " + created.toString();
		return Response.status(200).entity(output).build();
	} //createOrder
	
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteOrder(@PathParam("id") int orderId)
	{
		PizzaOrder order = dao.getOrder(orderId);
		String output = "Deleting pizza order: " + orderId;
		dao.deleteOrder(order);
		return Response.status(200).entity(output).build();
	} //deleteOrder
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getAllOrders()
	{ 
		String output = "Retrieving all orders: \n";
		List<PizzaOrder> orders = dao.getAllOrders();
		for (PizzaOrder o : orders) {
			output += "Pizza Order: " + o.toString() + "\n";
		}
		return Response.status(200).entity(output).build();
	} //getAllOrders
	
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getOrder(@PathParam("id") int orderId)
	{
		PizzaOrder order = dao.getOrder(orderId);
		String output = "Retrieving order: " + order.toString();
		return Response.status(200).entity(output).build();
	} //getOrder

	
	@PUT
	@Path("/{id}/{new_price}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateOrderPrice(@PathParam("id") int orderId, @PathParam("new_price") double newPrice)
	{
		PizzaOrder order = dao.getOrder(orderId);
		order.setOrderPrice(newPrice);
		String output = "Updating Pizza Order's Price: " + orderId;
		dao.updateOrderPrice(order);
		return Response.status(200).entity(output).build();
	} //updateOrderPrice
	
	
}
