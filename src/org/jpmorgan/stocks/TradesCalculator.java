/**
 * 
 */
package org.jpmorgan.stocks;

import java.util.List;

import org.jpmorgan.stocks.valueobject.Trade;

/**
 * @author Arindam
 *
 */

public class TradesCalculator {

/**
 * Calculates the stock price based on stock traded over a period of time
 * Eg: over last 15 minutes.
 * @param ticket
 * @param limit
 * @param trades
 * @return
 */
	public float calculateStockPrice(long limit, List<Trade> trades) {
		long numerator = 0;
		long denominator = 0;
		for(Trade trade : trades) {
			if(trade.getTimestamp() >= limit ) {
				numerator += (trade.getPrice() * trade.getQuantity());
				denominator += trade.getQuantity();
			}
			else {
				//we assume trades are sorted by timestamp
				break;
			}
		}
		
		if(0 == denominator){
			return -1.0f;
		}else{
			return numerator / denominator;	
		}
	}

}