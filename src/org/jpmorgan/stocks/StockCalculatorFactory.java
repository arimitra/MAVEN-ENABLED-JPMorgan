/**
 * 
 */
package org.jpmorgan.stocks;

import org.jpmorgan.stocks.valueobject.Stock;

/**
 * @author Arindam
 *
 */
public class StockCalculatorFactory {
	
	public static StockCalculator create(Stock.StockType type) {
		if(Stock.StockType.COMMON== type) return new CommonStockCalculator();
		if(Stock.StockType.PREFERRED == type) return new PreferredStockCalculator();
		return null;
	}
}
