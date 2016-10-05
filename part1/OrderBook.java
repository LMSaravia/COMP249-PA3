package part1;

import java.util.NoSuchElementException;

public class OrderBook {

	private class OrderNode {

		private OrderNode previous;
		private Order order;
		private OrderNode next;

		public OrderNode() {
			previous = null;
			order = null;
			next = null;
		}

		public OrderNode(OrderNode previous, Order order, OrderNode next) {
			this.previous = previous;
			this.order = order;
			this.next = next;
		}

	}	// class OrderNode

	
	public class OBIterator {
		
		private OrderNode position = null;
		
		
		public OBIterator() {
			
			position = head;
		}
		
		
		
//		public void restart() {
//			
//			position = head;
//		}
		
		
//		public Order next() {
//			
//			if (!hasNext())
//				throw new IllegalStateException();
//			
//			Order toReturn = position.order;
//			position = position.next;
//			return toReturn;
//		}
		
		
		public boolean hasNext() {
			
			return (position!= null);
		}



		public Order peek() {
			
			if(!hasNext())
				throw new IllegalStateException();
			
			return position.order;
		}
		
		
//		public void insertHere(Order newOrder) {
//			
//			if (position == null && head != null) { 
//				// Add to end by moving a temp pointer to the end of the list
//				OrderNode temp = head;
//				while (temp.next != null)
//					temp = temp.next;
//				
//				temp.next = new OrderNode(temp, newOrder, null);
//			}
//			else if (head == null || position.previous == null)	// at head of list
//				OrderBook.this.addToStart (newOrder);
//			
//			else {	// Insert before the current position
//				OrderNode temp = new OrderNode(position.previous, newOrder, position);
//				position.previous.next = temp;
//				position.previous = temp;
//			}
//		}
		
		
		public void deleteAndForward( ) {
			
			if (position == null)
				throw new IllegalStateException( );
			else if (position.previous == null && position.next == null) {
				// Deleting the only node in the list
				head = null;
				position = head;
			}
			else if (position.previous == null) { // Deleting first node
				head = head.next;
				position = head;
				position.previous = null;
			}
			else if (position.next == null) { // Deleting last node
				position.previous.next = null;
				position = null;
			}
			else {
				position.previous.next = position.next;
				position.next.previous = position.previous;
				position = position.next;
			}
		}
		
		public void deleteAndBack( ) {

			if (position == null)
				throw new IllegalStateException( );
			else if (position.previous == null) { // Deleting first node
				head = head.next;
				position = head;
			}
			else if (position.next == null) { // Deleting last node
				position.previous.next = null;
				position = position.previous;
			}
			else {
				position.previous.next = position.next;
				position.next.previous = position.previous;
				position = position.previous;
			}
		}


		public void goToBestOffer() {
			if (bestOffer != null)
				position = bestOffer;
			else
				throw new NoSuchElementException();
		}

		
		public void goToBestBid() {
			if (bestBid != null)
				position = bestBid;
			else
				throw new NoSuchElementException();			
		}


		public void setBestBid() {
			
			OrderBook.this.setBestBid(position);
			
		}

		
		public void setBestOffer() {
			
			OrderBook.this.setBestOffer(position);
			
		}


		public void matchOfferOrder(Order newOrder) {
			System.out.println("Matching new order:");
			System.out.println(newOrder);
			do {
				System.out.println("Match found:");
				newOrder.printFullDetails();
				peek().printFullDetails();
				// If newOrder has less volume sells everything and it's done.
				if(newOrder.getVolume() < peek().getVolume()) {
					peek().setVolume(
							peek().getVolume() - newOrder.getVolume());
					newOrder.setVolume(0);
				}
				// If newOrder has more or equal volume, update stock, delete bid and update best bid
				else {
					newOrder.setVolume(
							newOrder.getVolume() - peek().getVolume());
					deleteAndForward();
					setBestBid();
				}
				// while not at the end and volume remaining and buyers left
			} while (hasNext() && newOrder.getVolume() > 0 &&
					newOrder.getPrice() <= peek().getPrice());
			System.out.println("Done");
		}


		public void matchBidOrder(Order newOrder) {
			System.out.println("Matching new order:");
			System.out.println(newOrder);
			do {
				System.out.println("Match found:");
				newOrder.printFullDetails();
				peek().printFullDetails();
				// If newOrder has less volume, buy and it's done
				if(newOrder.getVolume() < peek().getVolume()) {
					peek().setVolume(
							peek().getVolume() - newOrder.getVolume());
					newOrder.setVolume(0);
				}
				// If newOrder asks for more or equal volume, update, delete offer and update best offer
				else {
					newOrder.setVolume(
							newOrder.getVolume() - peek().getVolume());
					deleteAndBack();
					setBestOffer();
				}
				// while not at head and volume left and good buyers left
			} while (hasNext() && newOrder.getVolume() > 0 &&
					newOrder.getPrice() >= peek().getPrice());
			System.out.println("Done");
		}
	
	} // OrderIterator


	private OrderNode head;
	private OrderNode bestBid;
	private OrderNode bestOffer;

	public OrderBook() {
		
		head = null;
	}

	
	/**
	 * Method to add a Node keeping OfferOrders at the beginning and BidOrders at the end while
	 * following a decreasing price order.
	 * @param o
	 */
	public void addOrder(Order newOrder) {
	
		OrderNode newNode = new OrderNode(null, newOrder, null);
		OrderNode current;
	
		if (head != null) {	// Case list is not empty	
	
			// If the new order is an offer 
			if (newOrder instanceof OfferOrder) {
				// If there is a bestOffer
				if (bestOffer != null) {
					//If the new offer order has a lower price
					if (newOrder.getPrice() < bestOffer.order.getPrice()) {
						//add the new order after the best offer and set as best offer
						current = bestOffer;
						addAfter(current, newNode);
						bestOffer = newNode;
					}
					else { 
						/* The new order price is equal or higher than best offer price.
						 * Traverse from head until an equal or lower price is found. 
						 */
						current = head;
						while (newOrder.getPrice() < current.order.getPrice() ) {
							current = current.next;
						}
						addBefore(current, newNode);
					}
				}
				else {
					// No offer orders on list. Add as head.
					current = head;
					addBefore(current, newNode);
					bestOffer = newNode;
				}		
			}	
			
			else {
				// It is a bid
				if (bestBid != null) {
					// There is a best bid. Start from best bid
					current = bestBid;
					// If the new order has a higher price
					if (newOrder.getPrice() > bestBid.order.getPrice()) {
						// add before the best bid and set as best bid.
						addBefore(current, newNode);
						bestBid = newNode;
					}
					else {
						/* The new order price is equal or lower than best bid price.
						 * Traverse from bestBid until a lower price is found
						 */
						while (current.next != null && newOrder.getPrice() <= current.order.getPrice()) {
							current = current.next;
						}
						// Current has lower price or is the last node
						
						if (newOrder.getPrice() > current.order.getPrice()) {
							addBefore(current, newNode);
						}
						else {
							addAfter(current, newNode);
						}							
					}
				} 
				else {
					// There's no best bid. 
					current = head;
					// Add as the last node in the list and set as best bid
					while (current.next != null) {
						current = current.next;
					}
					addAfter(current, newNode);
					bestBid = newNode;
				}
			}
		}
		else {	// Case list is empty
			head = newNode;
			if (newOrder instanceof OfferOrder)
				bestOffer = newNode;
			else
				bestBid = newNode;
		}	
	}	// addOrder()		


	/**
	 * @return the bestBid
	 */
	public OrderNode getBestBid() {
		return bestBid;
	}


	/**
	 * @return the bestOffer
	 */
	public OrderNode getBestOffer() {
		return bestOffer;
	}


	public boolean hasBestOffer() {
		if (bestOffer != null)
			return true;
		else
			return false;
	}


	public boolean hasBestBid() {
		if (bestBid != null)
			return true;
		else
			return false;
	}
		
		public OBIterator iterator() {
			
			return new OBIterator();
		}


	/**
	 * Prints out the best bid and offer
	 */
	public void outputBBO() {
	
		System.out.println("Best Bid & Offer:");
		
		if (bestOffer != null)
			System.out.println(bestOffer.order);
		else
			System.out.println("No best offer to show.");
		
		if (bestBid != null)
			System.out.println(bestBid.order + "\n");
		else
			System.out.println("No best bid to show.\n");
	}


	/**
	 * Prints out the OrderBook
	 */
	public void outputBook() {
		
		if (head != null)
			System.out.println(toString());
		else
			System.out.println("Empty book. Nothing to print.");
	}


	public void setBestBid(OrderNode bestBid) {
		
		if (bestBid == null || bestBid.order instanceof BidOrder)
			this.bestBid = bestBid;
		else
			throw new ClassCastException();
	}


	/**
	 * @param bestOffer the bestOffer to set
	 */
	public void setBestOffer(OrderNode bestOffer) {
		
		if (bestOffer == null || bestOffer.order instanceof OfferOrder)
			this.bestOffer = bestOffer;
		else
			throw new ClassCastException();
	}
	

	public String toString() {	// Prints the full book.

		String temp = "Order Book:\n";
		OrderNode position = head;

		while (position != null) {
			temp += position.order.toString() + "\n";
			position = position.next;
		}

		return temp;
	}

	
	private void addAfter(OrderNode position, OrderNode newNode) {
		
		if (position == null){
			throw new NullPointerException("Error. Cannot add after a null position");
		}
		else {
			newNode.previous = position;
			if (position.next != null) {
				newNode.next = position.next;
				position.next.previous = newNode;
			}
			position.next = newNode;
		}
	}


	private void addBefore(OrderNode position, OrderNode newNode) {
	
		if (position == null){
			throw new NullPointerException("Error. Cannot add before a null position");
		}
		else {
			if (position.previous != null) {
				System.out.println("Position is " + position.order);
				System.out.println("Position.previous is " + position.previous.order);
				newNode.previous = position.previous;
				position.previous.next = newNode;
			}
			else {
				head = newNode;
			}
			newNode.next = position;
			position.previous = newNode;
		}
		
		
	}

} // class OrderBook
