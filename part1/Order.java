package part1;

public abstract class Order implements Anonymous {
	
	private String id;
	private double price;
	private int volume;		// number of shares
	
	
	public Order() {
		
		id = "";
		price = 0.00;
		volume = 0;
	}
	
	public Order(String id, double price, int volume) {
		
		this();
		setId(id);
		setPrice(price);
		setVolume(volume);	
	}
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		
		return id;
	}
	
	
	/**
	 * @return the price
	 */
	public double getPrice() {
		
		return price;
	}
	
	
	/**
	 * @return the volume
	 */
	public int getVolume() {
		
		return volume;
	}
	
	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		
		if (id != null)
			this.id = id;
	}
	
	
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		
		if (price > 0)
			this.price = price;
	}
	
	
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(int volume) {
		
		if (volume >= 0)
			this.volume = volume;	
	}
	
	
	public String toString() {
		
		String orderType = getClass().getSimpleName(); 
		return (orderType.substring(0, 3) + ": " + price + " " + volume);
	}
	
	
	/**
	 * printFullDetails() will print out the name, along with the other details.
	 */	
	public void printFullDetails() {
		
		System.out.println(toString() + " " + getId()); 
		
	}
	
	
	public boolean equals(Object o) {
		
		if (o == null)
			return false;
		else if (getClass() != o.getClass())
			return false;
		else {
			Order otherObject = (Order) o;
			return (id.equals(otherObject.id) && price == otherObject.price &&
					volume == otherObject.volume);
		}
	}
	
	public int comparesTo(Order otherOrder) throws NullPointerException {	// compares Order.price only
		if (otherOrder == null)
			throw new NullPointerException();
		else if(this.price < otherOrder.price)
			return -1;
		else if(this.price > otherOrder.price)
			return 1;
		else
			return 0;
	}
	
}	// class Order
