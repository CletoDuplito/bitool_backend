package hw3;

import java.util.*;
import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="CUSTOMER_TABLE")
public class Customer {
	  
	  @Id
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  @Column(name="Cust_Id")
	  private int userId; 
	  
	  @Column(name="Customer_Name",unique = true)
	  private String userName;
	  
	 /* @Column(name="Pass_Word")
	  private String password;*/
	  
	  //private Address address;
	  
	  /*@OneToMany(mappedBy="customer", targetEntity = PizzaOrder.class,
	  fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	  private List<PizzaOrder> orders;*/
	  
/*	  
    /**
     * The order to be added to the Customer's list of orders.
     * @param order the pizza order to be added
     *//*
    public void addOrders(PizzaOrder order) {
    	orders.add(order);
    }*/
	   
	/**
	 * Generates an id for customer
	 * @return the customer id.
	 */
	public int getId() {
		return userId;
	}
	
    
    /**
	 * Returns the name for customer
	 * @return the name
	 */
	public String getName() {
		return userName;
	}
	
    /**
     * Returns the list of pizza orders made by this customer.
     * @return the list of orders
     *
    public List<PizzaOrder> getOrders() {
    	return orders;
    }*/
	
	/**
	 * Sets the customer id.
	 * @param id the customer id
	 */
	public void setId(int id) {
		userId = id;
	}
	
	/**
	 * Sets the name for customer
	 * @param userName the name to be set
	 */
	public void setName(String userName) {
		this.userName = userName;
	}
    
	/**
	 * Returns a string of the Customer's name
	 *@return the String of the customer's name
	 */
	public String toString() { 
		return "ID: " + userId + " - Name: " + userName;
	}
	
}
