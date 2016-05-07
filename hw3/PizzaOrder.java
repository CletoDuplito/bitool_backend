package hw3;

import javax.persistence.*;

import org.hibernate.type.TimestampType;

import hw3.Topping;

import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name="PIZZAORDER_TABLE")
public class PizzaOrder {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="Order_Id")
	private int orderId;
	
	@Column(name="Total_Price")
	private double orderPrice;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "Pizza_Size")
	private PizzaSize.PizzaSizes size;
	
	/*@Enumerated(EnumType.STRING)
	@Column(name = "Payment_Method")
	private PaymentMethod.PaymentType payment;*/
	
/*	@ManyToOne (fetch = FetchType.LAZY) //maybeFetchType.EAGER
	@JoinColumn(name = "Customer_Name")
	private Customer customer;*/
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "joinOrderandTopping_table",
	joinColumns = {@JoinColumn(name="Order_Id")},
	inverseJoinColumns = {@JoinColumn(name = "Topping_Id")})
	
	
	@Column(name = "Toppings")
	private List<Topping> toppings;
	
	
	private double toppingPrice;

	
	@Column(name="Delivery_Time")
	private Timestamp time;
	
	
	/**
	 * Constructor for Pizza Order.
	 */
	public PizzaOrder() {
		toppings = new ArrayList<Topping>();
		toppingPrice = 0;
	}
	
	/**
	 * Gets the order id.
	 * @return the order id
	 */
	public int getOrderId() {
		return orderId;
	}
	
	/**
	 * Sets the order id.
	 * @param id the order id
	 */
	public void setOrderId(int id) {
		orderId = id;
	}
	
	/**
	 * Returns the price of the pizza order.
	 * @return the price of the pizza order
	 */
	public double getOrderPrice() {
		return orderPrice;
	}
	
	/**
	 * Sets the price for the pizza order.
	 */
	public void setOrderPrice(double price) {
		orderPrice = price;
	}
	
	
	/**
	 * Returns the delivery time for the pizza order.
	 * @return the delivery time
	 */
	@Temporal(TemporalType.TIMESTAMP)
	public Timestamp getDeliveryTime() {
		return time;
	}
	
	
	/**
	 * Sets the time it takes to deliver pizza.
	 * @param deliveryTime the delivery time
	 */
	public void setDeliveryTime(Timestamp deliveryTime) {
		time = deliveryTime;
	}
	
	
	/**
	 * Returns the list of toppings added to pizza.
	 * @return the list of toppings
	 */
	public List<Topping> getTopping() {
		return toppings;
	}
	
	
	/**
	 * Adds the toppings for the pizza order.
	 * @param topping toppings that will be added
	 */
	public void setTopping(List<Topping> someToppings) {
		toppings = someToppings ;
	}
	
	/**
	 * Returns the total price from toppings used for pizza order.
	 * @return the total price from toppings used.
	 */
	public double getToppingsPrice() {
		return toppingPrice;
	}
	
	
	/**
	 * Adds the toppings for the pizza order.
	 * @param topping toppings that will be added
	 */
	public void addTopping(Topping topping) {
			toppings.add(topping);
	}
	
	/**
	 * Sets the total price for all toppings used on pizza.
	 * @param topPrice the price to set the Topping Price.
	 */
	public void setToppingPrice(double topPrice) {
		toppingPrice = topPrice;
	}
	
	
	/**
	 * Returns the pizza size.
	 * @return the pizza size
	 */
	public PizzaSize.PizzaSizes getPizzaSize() {
		return size;
	}
	
	
	/**
	 * Sets the pizza size.
	 * @param size the pizza size
	 */
	public void setPizzaSize(PizzaSize.PizzaSizes size) {
		this.size = size;
	}
	
	
	/**
	 * Returns a string of the Pizza order in details of id, size, and price
	 *@return the String of the pizza order
	 */
	public String toString() { 
		return "ID: " + orderId + "\nSize: " + size.toString() + "\nPrice: $"+ orderPrice +"\n";
	}
}
