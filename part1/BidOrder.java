package part1;

public class BidOrder extends Order implements Anonymous {

	public BidOrder () {
		super();
	}
	
	
	public BidOrder (String id, double price, int volume) {
		super(id, price, volume);
	}
}
