package cs157B_Project;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Sales {

	 private List<String> product = new ArrayList<String>();
	 private List<String> store = new ArrayList<String>();
	 private List<Integer> dateTime = new ArrayList<Integer>();
	 private int salesTotal;
	 
	public List<Integer> getDateTime() {
		return dateTime;
	}
	
	public List<String> getProduct(){
		return product;
	}
	
	public int getSalesTotal() {
		return salesTotal;
	}
	
	public List<String> getStore() {
		return store;
	}
	
	public void setDateTime(int aDate) {
		dateTime.add(aDate);
	}
	
	public void setProduct(String aProduct) {
		product.add(aProduct);
	}
	
	public void setSalesTotal(int sales) {
		salesTotal = sales;
	}
	
	public void setStore(String aStore) {
		store.add(aStore);
	}
	 



}
