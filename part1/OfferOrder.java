package part1;

public class OfferOrder extends Order implements Anonymous {	

	public OfferOrder() {
		super();
	}
	
	public OfferOrder(String id, double price, int volume) {
		super(id, price, volume);
	}
}
