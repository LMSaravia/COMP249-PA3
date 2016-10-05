package part1;

import java.util.Scanner;


public class Driver {

	public static void main(String[] args) {

		OrderBook book = new OrderBook();

		Order o1 = new OfferOrder("Wanda", 155.0, 300);
		execute(o1, book);
		book.outputBook();
		book.outputBBO();
	
		Order o2 = new OfferOrder("Roxana", 152.5, 120);
		execute(o2, book);
		book.outputBook();
		book.outputBBO();		
		
		Order o3 = new OfferOrder("Amparo", 152.0, 100);
		execute(o3, book);
		book.outputBook();
		book.outputBBO();
		
		Order o4 = new BidOrder("Feng", 148, 75);
		execute(o4, book);
		book.outputBook();
		book.outputBBO();
		
		Order o5 = new BidOrder("Bob", 147, 200);
		execute(o5, book);
		book.outputBook();
		book.outputBBO();		
		
		Order o6 = new BidOrder("Ishana", 146.60, 100);
		execute(o6, book);
		book.outputBook();
		book.outputBBO();
		
		Order o7 = new BidOrder("Zaira", 146.50, 50);
		execute(o7, book);
		book.outputBook();
		book.outputBBO();

		Order o8= new OfferOrder("Alice", 146.60, 500);
		execute(o8, book);	
		book.outputBook();
		book.outputBBO();
		
	} // main()

	
	private static void execute(Order newOrder, OrderBook book) {
		if (book != null) {
			OrderBook.OBIterator ity = book.new OBIterator();
			// If there's nothing to compare
			if (!ity.hasNext()) {
				book.addOrder(newOrder);
				System.out.println("Nothing to match. New order added to order book.");
			}
			else {
				// There's something to compare
				// If new order is an offer order				
				if (newOrder instanceof OfferOrder) {
					// If no BidOrders add
					if (!book.hasBestBid()) {
						System.out.println("No bid to match. New offer added to order book.");
						book.addOrder(newOrder);
					}
					else{
						// There's a best bid
						// If no best offer, compare w/ best bid
						if(!book.hasBestOffer()) {
							ity.goToBestBid();
							// If higher price add. It's a new bestOffer
							if (newOrder.getPrice() > ity.peek().getPrice()) {
								book.addOrder(newOrder);
								System.out.println("No best offer to match. Price higher than best bid.\n"
										+ "New offer added to order book.");
							}
							else {
								// Is lower than or equal to bestBid. Match!
								System.out.println("No best offer to match. Price lower than or "
										+ "equal to best bid.");
								ity.matchOfferOrder(newOrder);
								// No more buyers
								// If new order has shares left to sell, add.
								if (newOrder.getVolume() > 0)
									book.addOrder(newOrder);
							}
						}
						else {
							// There's a best offer
							// Compare w/ best offer and if higher or equal, add
							ity.goToBestOffer();
							if(newOrder.getPrice() >= ity.peek().getPrice()) {
								book.addOrder(newOrder);
								System.out.println("Price higher than best offer. New offer added to order book.");
							}
							else{
								// Is lower than best offer.
								// Compare w/ best bid and if higher add and is the best offer
								ity.goToBestBid();
								if (newOrder.getPrice() > ity.peek().getPrice()){
									book.addOrder(newOrder);
									System.out.println("Price lower than best offer but higher than best bid.\n"
											+ "New offer added to order book.");
								}
								else {
									// Is less than or equal to bestBid. Match!
									System.out.println("Price lower than or equal to best bid.");
									ity.matchOfferOrder(newOrder);
									// No more buyers
									// If new order has shares left to sell, add.
									if (newOrder.getVolume() > 0)
										book.addOrder(newOrder);
								}
							}
						}			
					}			
				}
				else {
					// New order is a bid order
					// If no OfferOrders add
					if(!book.hasBestOffer()) {
						book.addOrder(newOrder);
						System.out.println("No offer to match. New bid added to order book.");
					}
					else {
						// There's a bestOffer.
						// If no bestBid, compare w/ bestOffer and if lower add
						ity.goToBestOffer();
						if (!book.hasBestBid()) {
							if (newOrder.getPrice() < ity.peek().getPrice()) {
								book.addOrder(newOrder);
							System.out.println("No best bid to match. Price higher than best offer.\n"
									+ "New bid added to order book.");
							}
							else {
								// No best bid y and higher than or equal to bestOffer. Match!
								System.out.println("No best bid to match. Price higher than or"
										+ " equal to best offer.");
								ity.matchBidOrder(newOrder);
								// No more good sellers
								// If new order has remaining volume, add..
								if (newOrder.getVolume() > 0)
									book.addOrder(newOrder);
							}
						}
						else {
							// There's bestBid and bestOffer.
							// If lower than or equal to bestBid add
							ity.goToBestBid();
							if(newOrder.getPrice() <= ity.peek().getPrice()) {
								book.addOrder(newOrder);
								System.out.println("Price lower than or equal to best bid.\n"
										+ "New bid added to order book.");
							}
							else {
								// Is higher than bestBid
								// If lower than bestOffer add
								ity.goToBestOffer();
								if(newOrder.getPrice() < ity.peek().getPrice()) {
									book.addOrder(newOrder);
									System.out.println("Price higher than best bid but lower than best offer.\n"
											+ "New bid added to order book.");
								}									
								else{
									// Is higher than or equal to bestOffer. Match!
									System.out.println("Price higher than or equal to best offer.");
									ity.matchBidOrder(newOrder);
									// No more good sellers
									// If new order has remaining volume, add..
									if (newOrder.getVolume() > 0)
										book.addOrder(newOrder);							
								}
							}
						}
					}
				}
			}
		}
		else { // Book is null
			System.err.println("Error. Book does not exist");
		}
	}
}
