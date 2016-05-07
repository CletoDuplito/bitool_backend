package hw3;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/customers")
public class CustomersRESTWS {
	
	private Hw3DAO dao = new Hw3DAO();
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getAllCustomers()
	{
		String output = "Retrieving all customers: \n";
		List<Customer> customers = dao.getAllCustomers();
		for (Customer c : customers) {
			output += "Customer: " + c.toString() + "\n";
		}
		return Response.status(200).entity(output).build();
	} //getAllCustomers
	
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getCustomer(@PathParam("id") int customerId)
	{
		Customer c = dao.getCustomer(customerId);
		String output = "Customer ID: " + customerId + " - " + c.getName();
		return Response.status(200).entity(output).build();
	} //getCustomer
	
	
	@Path("/{param}")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response addNewCustomer(@PathParam("param") String name)
	{
		String output = "Creating customer: " + name;
		Customer c = new Customer();
		c.setName(name);
		dao.createUser(c);
		return Response.status(200).entity(output).build();
	} //addNewCustomer
	
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteCustomer(@PathParam("id") int customerId)
	{
		Customer c = dao.getCustomer(customerId);
		String output = "Removing customer: " + c.getName();
		dao.deleteCustomer(c);
		return Response.status(200).entity(output).build();
	} //deleteCustomer
	
	
	@PUT
	@Path("/{id}/{name}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateCustomerName(@PathParam("id") int customerId, @PathParam("name") String newName)
	{
		Customer c = dao.getCustomer(customerId);
		String output = "Updating customer: " + c.getName();
		c.setName(newName);
		dao.updateCustomerName(c);
		return Response.status(200).entity(output).build();
	} //updateCustomerName
	
	
}
