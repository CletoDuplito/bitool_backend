package hw3;

import java.util.ArrayList;
import java.util.List;

import hw3.PaymentMethod.PaymentType;
import hw3.PizzaSize.PizzaSizes;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * A class to define the DAO methods for CRUD Operations.
 * @author duplito
 */
public class Hw3DAO {
	
	private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	/**
	 * Create a Customer
	 * @param The customer to insert
	 * @return the newly created Customer
	 */
	public Customer createUser(Customer customer)
	{   
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(customer);
	    session.getTransaction().commit();
	    session.close();
	    return customer;
	}
	
	
	/**
	 * Creates a pizza order.
	 * @param the pizza order to insert into the database
	 * @return the pizza order
	 */
	public PizzaOrder createOrder(PizzaOrder order)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
	    session.save(order);
	    session.getTransaction().commit();
	    session.close();
	    return order;
	}
	
	
	/**
	 * Deletes a customer with id.
	 * @param c The customer to delete.
	 */
	public void deleteCustomer(Customer c)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
	    session.delete(c);
	    session.getTransaction().commit();
	    session.close();
	}
	
	
	/**
	 * To delete a pizza order with id.
	 * @param order The pizza order to be deleted
	 */
	public void deleteOrder(PizzaOrder order)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();	
		
	    session.delete(order);
	    session.getTransaction().commit();
	    session.close();
	}
	
	
	/**
	 * To retrieve all customers.
	 * @return the list of all customers.
	 */
	public List<Customer> getAllCustomers()
	{   
		List<Customer> customers = new ArrayList<Customer>();
		
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	    String queryString = "FROM Customer ORDER BY Cust_Id";
	    System.out.println("** before session.createQuery");
	    Query query = session.createQuery(queryString);
	    customers = (List<Customer>) query.list();
		session.getTransaction().commit();
		session.close();
		return customers;
	}
	
	
	/**
	 * Retrieves customer by id.
	 * @param customerId the customer id used to retrieve the customer
	 * @return the specific customer
	 */
	public Customer getCustomer(int customerId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String queryString = "from Customer where Cust_Id= :CustomerId";
		Query query = session.createQuery(queryString);
		query.setInteger("CustomerId", customerId);
		Object queryResult = query.uniqueResult();
		Customer cust = (Customer) queryResult;
		session.getTransaction().commit();
		session.close();
		return cust;
	}
	
	
	/**
	 * To Retrieve all orders.
	 * @return the list of all pizza orders
	 */
	public List<PizzaOrder> getAllOrders()
	{   
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	    String queryString = "from PizzaOrder ORDER BY Order_Id";
	    Query query = session.createQuery(queryString);
		List<PizzaOrder> orders = new ArrayList<PizzaOrder>();
	    orders = (List<PizzaOrder>) query.list();
		session.getTransaction().commit();
		session.close();
		return orders;
	}
	
	
	/**
	 * Finds one specific pizza order.
	 * @param order_Id the order id to be used to look up order
	 * @return the pizza order
	 */
	public PizzaOrder getOrder(int order_Id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String queryString = "from PizzaOrder where Order_Id = :OrderId";
		Query query = session.createQuery(queryString);
		query.setInteger("OrderId", order_Id);
		Object queryResult = query.uniqueResult();
		PizzaOrder order = (PizzaOrder) queryResult;
		session.getTransaction().commit();
		session.close();
		return order;
	}

	
	/**
	 * Updates or replaces the name of the customer identified by id with a new name.
	 * @param c The customer to update or replace name
	 * @return True if customer's name was updated, false otherwise.
	 */
	public boolean updateCustomerName(Customer c)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(c);
	    session.getTransaction().commit();
	    session.close();
	    return true;
	}
	
	
	/**
	 * To change or update an order.
	 * @param order The pizza order to update or replace price
	 * @return True if pizza order's price was updated, false otherwise.
	 */
	public boolean updateOrderPrice(PizzaOrder order)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
	    session.update(order);
	    session.getTransaction().commit();
	    session.close();
	    return true;
	}	
	
}
