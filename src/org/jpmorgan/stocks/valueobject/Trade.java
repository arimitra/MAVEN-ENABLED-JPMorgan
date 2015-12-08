/**
 * 
 */
package org.jpmorgan.stocks.valueobject;

/**
 * @author Arindam
 *
 */
public class Trade {
	private String ticket;
	private Operation operation;
	private long timestamp;
	private int quantity;
	private float price;
	
	public enum Operation { BUY, SELL}

	/**
	 * @return the ticket
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * @param ticket the ticket to set
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the qty
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param qty the qty to set
	 */
	public void setQuantity(int qty) {
		this.quantity = qty;
	}

	/**
	 * @return the operation
	 */
	public Operation getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	};
}
