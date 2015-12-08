package org.jpmorgan.stocks;

import org.jpmorgan.stocks.valueobject.Stock;

public class PreferredStockCalculator extends StockCalculator {

	 @Override
	public float calculateYield(Stock stock) {
			return stock.getFixedDividend() * stock.getParValue() / stock.getPrice();
	}
}
	
