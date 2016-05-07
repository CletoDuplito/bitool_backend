package hw3;

import javax.persistence.Embeddable;

@Embeddable
public class PaymentMethod {
	
	public enum PaymentType {
		CASH, VISA, MASTER
	}
	
}
