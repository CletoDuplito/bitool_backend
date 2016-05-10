package cs157B_Project;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Sales {

	 private String product;
	 private String store;
	 private int dateTime;
	 private int salesTotal;
	 
	public int getDateTime() {
		return dateTime;
	}
	
	public String getProduct(){
		return product;
	}
	
	public int getSalesTotal() {
		return salesTotal;
	}
	
	public String getStore() {
		return store;
	}
	
	public void setDateTime(int aDate) {
		dateTime = aDate;
	}
	
	public void setProduct(String aProduct) {
		product = aProduct;
	}
	
	public void setSalesTotal(int sales) {
		salesTotal = sales;
	}
	
	public void setStore(String aStore) {
		store = aStore;
	}
	 



}
