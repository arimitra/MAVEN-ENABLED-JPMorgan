/**
 * 
 */
package org.jpmorgan.stocks.valueobject;

/**
 * @author Arindam
 *
 */

public class Stock {
	String symbol;
	private StockType type;
	private float price;
	private float lastDividend;
	private float fixedDividend;
	private float parValue;
	
	
	public Stock(String symbol, StockType type, float price, float lastDividend, float fixedDividend, float parValue) {
			this.symbol = symbol;
			this.setType(type);
			this.setPrice(price);
			this.setLastDividend(lastDividend);
			this.setFixedDividend(fixedDividend);
			this.setParValue(parValue);
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
	}

	/**
	 * @return the lastDividend
	 */
	public float getLastDividend() {
		return lastDividend;
	}

	/**
	 * @param lastDividend the lastDividend to set
	 */
	public void setLastDividend(float lastDividend) {
		this.lastDividend = lastDividend;
	}

	/**
	 * @return the type
	 */
	public StockType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(StockType type) {
		this.type = type;
	}

	/**
	 * @return the parValue
	 */
	public float getParValue() {
		return parValue;
	}

	/**
	 * @param parValue the parValue to set
	 */
	public void setParValue(float parValue) {
		this.parValue = parValue;
	}

	/**
	 * @return the fixedDividend
	 */
	public float getFixedDividend() {
		return fixedDividend;
	}

	/**
	 * @param fixedDividend the fixedDividend to set
	 */
	public void setFixedDividend(float fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	public enum StockType { COMMON, PREFERRED };
}
