package hw3;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "topping")
public class Topping {
	
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	@Column(name="Topping_Id")
	private int toppingId;

	@Column(name = "Topping_Name")
	private String toppingName;
	
	//@Column(name= "Topping_Price")
	private double price;
	

    @ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "joinOrderandTopping_table",
	joinColumns = {@JoinColumn(name="Topping_Id")},
	inverseJoinColumns = {@JoinColumn(name = "Order_Id")})
	private List<PizzaOrder> pOrders;
    
    //private List<String> toppings;
    
    /**
     * Constructor for Topping.
     */
    public Topping() {
    	pOrders = new ArrayList<PizzaOrder>();
    	//toppings = new ArrayList<String>();
    	price = 0;
    }
    
    /**
     * Gets the Topping id.
     * @return the Topping id
     */
    public int getToppingId() {
    	return toppingId;
    }
    
	/**
	 * Returns the name of the topping
	 * @return the name of the topping
	 */
	public String getName() {
		return toppingName;
	}
	
	/**
	 * Sets the name for the topping
	 * @param name the name for the topping
	 */
	public void setName(String name) {
		toppingName = name;
	}
	
	
	/**
	 * Returns the price for the pizza topping.
	 * @return the price of the pizza topping
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * Sets the price for pizza toppings.
	 * @param the price of the pizza topping to be set
	 */
	public void setPrice(String topping) {
		if (topping.equalsIgnoreCase("Onions")) price += 1;
		else if (topping.equalsIgnoreCase("Pepperoni")) price += 2;
		else if (topping.equalsIgnoreCase("Sausage")) price += 2;
		else if (topping.equalsIgnoreCase("Black_Olives")) price += 2;
		else if (topping.equalsIgnoreCase("Green_Peppers")) price += 2;
		else if (topping.equalsIgnoreCase("Spinach")) price += 2;
		else if (topping.equalsIgnoreCase("Pineapple")) price += 3;
		else if (topping.equalsIgnoreCase("Mushrooms")) price += 3;
		else if (topping.equalsIgnoreCase("Extra Cheese")) price += 3;
		else if (topping.equalsIgnoreCase("Bacon")) price += 5;
		else price += 0;
	}
	
    /**
     * Gets the list of pizza orders.
     * @return the list of pizza orders
     */
    public List<PizzaOrder> getOrders(){
    	return pOrders;
    }
	
	 /**
     * Sets the pizza orders.
     * @param order the pizza order
     */
    public void setOrders(PizzaOrder order) {
    	pOrders.add(order);
    }
	
	
}
