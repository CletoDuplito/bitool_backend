package hw3;

import javax.persistence.*;

@Embeddable
public class PizzaSize {
	
  public enum PizzaSizes {
	  SMALL, MEDIUM, LARGE;
  }
  
  @Column(name = "Pizza_Size")
  private PizzaSizes sizeName;
  
  @Column(name = "Pizza_Price")
  private double pizzaCost;
 
  
  /**
   * Returns the price of the pizza size.
   * @return the price
   */
  public double getPrice() {
	  return pizzaCost;
  }

  /**
   * Returns the size of the pizza.
   * @return the String of pizza size
   */
  public String getPizzaSize() {
	  if (sizeName.toString().equalsIgnoreCase("Small")) return "Small";
	  else if (sizeName.toString().equalsIgnoreCase("Medium")) return "Medium";
	  else return "Large";
  }
  
  /**
   * Define the cost for each pizza size.
   * @param size The pizza size that determines the price of the pizza
   */
  public void setPrice(PizzaSizes size) {
	  switch (size) {
	  	case SMALL:
	  		pizzaCost = 3;
	  		break;
	  	case MEDIUM:
	  		pizzaCost = 5;
	  		break;
	  	case LARGE:
	  		pizzaCost = 7;
	  		break;
	  	default:
	  		System.out.println("Sorry, please enter a valid Pizza size");
	  		break;
	  }
  }
 
  /**
   * Sets the Pizza Size.
   * @param size the Pizza size to set
   */
  public void setPizzaSize(PizzaSizes size) {
	  sizeName = size;
  }
  
  /**
   * Returns the string of the Pizza size.
   * @return the String of the Pizza size
   */
  public String toString(){
	  return "Pizza Size: " + this.getPizzaSize();
  }
  
}
