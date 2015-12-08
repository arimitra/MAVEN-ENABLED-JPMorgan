/**
 * 
 */
package org.jpmorgan.stocks;

import java.util.Collection;

import org.jpmorgan.stocks.valueobject.Stock;

/**
 * @author Arindam
 *
 */
public class StocksCalculator {

	/**
	 * GBCE calculator for all the stock prices available
	 * from a specific exchange. Assumed stockfactor == 1.
	 * @param stocks
	 * @return
	 */
	public double calculateGBCE(Collection<Stock> stocks) {
		double stockFactor =1.0;
		for(Stock stock: stocks) {
			stockFactor*=stock.getPrice();
		}
		return Math.pow(stockFactor, 1.0/stocks.size());
	}

}
