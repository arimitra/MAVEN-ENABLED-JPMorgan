/**
 * 
 */
package org.jpmorgan.stocks;

import org.jpmorgan.stocks.valueobject.Stock;

/**
 * @author Arindam
 *
 */
public class CommonStockCalculator extends StockCalculator {

	 @Override
	public float calculateYield(Stock stock) {
		return stock.getLastDividend() / stock.getPrice();
	}
	 
}

