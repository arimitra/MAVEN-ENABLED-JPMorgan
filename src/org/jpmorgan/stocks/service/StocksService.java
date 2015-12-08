/**
 * 
 */
package org.jpmorgan.stocks.service;

/**
 * @author Arindam
 *
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jpmorgan.stocks.StockCalculator;
import org.jpmorgan.stocks.StockCalculatorFactory;
import org.jpmorgan.stocks.StocksCalculator;
import org.jpmorgan.stocks.TradesCalculator;
import org.jpmorgan.stocks.valueobject.Stock;
import org.jpmorgan.stocks.valueobject.Trade;

public class StocksService {

	Map<String, Stock> data;  //map symbol -> Stock
	
	Map<String, List<Trade>> trades = new HashMap<String, List<Trade>>();
	
	private volatile static  StocksService _instance;
	/**
	 * Initialise the the StocksService for performing 
	 * different stock/trade based operations	
	 * @param data
	 */
	
	private StocksService() {
		
	}
	/**
	 * Double-checked locking of StocksService
	 * implemented to ensure thread-safety
	 * @return
	 */
	public static StocksService getInstance(){
		if(_instance==null){
			synchronized(StocksService.class){
				if(_instance==null){
					_instance = new StocksService();
				}
			}
		}
		
		return _instance;
	}
	
	public void setStockData(Map<String, Stock> data){
		this.data = data;
	}

/**
 * Calculate the yield for specific stock
 * Common Stock : (last div)/(market price)
 * Preferred Stock : (fixed div)*(Par Value)/(market price)
 * @param symbol
 * @return
 */

	public float calculateDividentYield(String symbol) {
		Stock stock = data.get(symbol);
		StockCalculator calculator = StockCalculatorFactory.create(stock.getType());
		return calculator.calculateYield(stock);
	}


/**
 * Calculate the PE ratio of a given stock
 * PE Ratio : (market price)/dividend (use last dividend)
 * @param symbol
 * @return
 */
	public double calculatePERatio(String symbol) {
		Stock stock = data.get(symbol);
		StockCalculator calculator = StockCalculatorFactory.create(stock.getType());
		return calculator.calculatePERatio(stock);
	}

/**
 * In memory recording of a trade based on the ticket
 * of the trade.
 * @param trade
 * @return
 */

	public int recordTrade(Trade trade) {
		String tradeTicket = trade.getTicket();
		List<Trade> stockTrades = getTradesForTicket(tradeTicket);
		
		if(null==stockTrades) {
			stockTrades = new ArrayList<Trade>();
			trades.put(tradeTicket, stockTrades);
		}
		stockTrades.add(trade);

		return 0;
	}

/**
 * Get all the trades as a list using a ticket
 * @param ticket
 * @return
 */
	public List<Trade> getTradesForTicket(String ticket) {
		return trades.get(ticket);
	}

/**
 * Calculate the stock price based on trades that happened 
 * within a limited period of time. Eg: 15 minutes
 * @param ticket
 * @param limit
 * @return
 */

	public Stock calculateStockPrice(String ticket, long limit) {
		List<Trade> trades = this.trades.get(ticket);
		Stock stock = data.get(ticket);
		
		stock.setPrice(new TradesCalculator().calculateStockPrice(limit,trades));
		data.put(ticket, stock);
		
		return stock;
	}

/**
 * Uses utility class to calculate the GBCE for the stocks
 * @return
 */

	public double calculateGBCE() {
		Collection<Stock> stocks = data.values();
		
		return new StocksCalculator().calculateGBCE(stocks);
		
	}
}

