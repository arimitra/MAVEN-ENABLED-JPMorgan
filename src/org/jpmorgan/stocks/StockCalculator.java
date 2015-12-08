/**
 * 
 */
package org.jpmorgan.stocks;

import org.jpmorgan.stocks.valueobject.Stock;

/**
 * @author Arindam
 *
 */
public abstract class StockCalculator {
	
	public abstract float calculateYield(Stock stock);
	
	public float calculatePERatio(Stock stock) {
		
		if(0 == stock.getLastDividend()) return -1;
		
		return stock.getPrice() / stock.getLastDividend(); 
	}
}
