package org.jpmorgan.stocks.test;

/**
 * @author Arindam
 *
 */
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jpmorgan.stocks.service.StocksService;
import org.jpmorgan.stocks.valueobject.Stock;
import org.jpmorgan.stocks.valueobject.Trade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class StocksServiceTest {



	
	StocksService service;

	
	//test parameters
	String ticket;
	float yield;
	float peRatio;
	Trade[] trades;
	float stockPrice ;

	//constants
	static float NO_DATA_VALUE = -1f;
	static final double PRECISION = 0.02;
	static long NOW = System.currentTimeMillis();
	static float TEA_PRICE = 2.3f; 
	static float POP_PRICE = 31.2f; 
	static float ALE_PRICE = 50f; 
	static float GIN_PRICE = 25f; 
	static float JOE_PRICE = 60f; 
	static float TEA_DIVIDEND = 0f; 
	static float POP_DIVIDEND = 8f; 
	static float ALE_DIVIDEND = 23f; 
	static float GIN_DIVIDEND = 8f; 
	static float JOE_DIVIDEND = 13f; 
	static int   GIN_PAR = 100;
	static float GIN_FIX_DIV = 0.02f;

	
	
	public StocksServiceTest(String ticket, 
							Trade[] trades, 
							float stockPrice, 
							float yield, 
							float peRatio) {
		this.ticket = ticket;
		this.yield = yield;
		this.peRatio = peRatio;
		this.trades = trades;
		this.stockPrice = stockPrice;
	}



	@Before
	public void setup() {
		
		Map<String, Stock> data = new HashMap<String,Stock>();
		data.put("TEA",new Stock("TEA", Stock.StockType.COMMON,    TEA_PRICE,  TEA_DIVIDEND, NO_DATA_VALUE, 100));
		data.put("POP",new Stock("POP", Stock.StockType.COMMON,    POP_PRICE,  POP_DIVIDEND, NO_DATA_VALUE, 100));
		data.put("ALE",new Stock("ALE", Stock.StockType.COMMON,    ALE_PRICE,  ALE_DIVIDEND, NO_DATA_VALUE, 60));
		data.put("GIN",new Stock("GIN", Stock.StockType.PREFERRED, GIN_PRICE,  GIN_DIVIDEND, GIN_FIX_DIV,  GIN_PAR));
		data.put("JOE",new Stock("JOE", Stock.StockType.COMMON,    JOE_PRICE,  JOE_DIVIDEND, NO_DATA_VALUE, 250));

		service = StocksService.getInstance();
		service.setStockData(data);
	}


	//Next are the parameterized TEST CASES to be executed, accordingly to the data setup above.
	
	@Parameters
	public static Collection<Object[]> generateData()
	{ 
		return Arrays.asList(new Object[][] {
			
			// test case 1
			{ "TEA",  				// ticket

									// the trades performed. Ordered by timestamp.
				new Trade[]{tradeBuilder("TEA", NOW, 			   1, 20f ),  
							tradeBuilder("TEA", NOW - 5*60*1000,   2, 22f ), 	//trade 5 minutes ago
							tradeBuilder("TEA", NOW - 10*60*1000,  4, 24f ),  	//trade 10 minutes ago
							tradeBuilder("TEA", NOW - 15*60*1000,  6, 26f ), 	//trade 15 minutes ago
							tradeBuilder("TEA", NOW - 20*60*1000, 10, 18f ) },	//trade 20 minutes ago

				24f,  				// the expected stock price of the past 15 minute trades.
				
				TEA_DIVIDEND/TEA_PRICE, 	// the expected dividend yield 

				-1f 				// the expected P/E Ratio (because dividend is 0) 
				
			}, 

			// test case 2
			{ "POP", null, 0, POP_DIVIDEND/POP_PRICE, POP_PRICE/POP_DIVIDEND},
			
			// test case 3
			{ "ALE", null, 0, ALE_DIVIDEND/ALE_PRICE, ALE_PRICE/ALE_DIVIDEND},
			
			// test case 4
			{ "GIN", null, 0, GIN_FIX_DIV * GIN_PAR / GIN_PRICE, GIN_PRICE/GIN_DIVIDEND},
			
			// test case 5
			{ "JOE", null, 0, JOE_DIVIDEND/JOE_PRICE, JOE_PRICE/JOE_DIVIDEND},

		});
	}



	@Test
	public void calculate_dividentYield_for_ticket() {
		
		assertEquals(yield, service.calculateDividentYield(ticket), PRECISION);

	}

	@Test
	public void calculate_peRatio_for_ticket() {

		assertEquals(peRatio, service.calculatePERatio(ticket), PRECISION);

	}


	@Test
	public void one_ticket_trade_is_recorded() {

		//GIVEN...
		Trade trade = tradeBuilder(ticket, NOW);

		//WHEN..
		int result = service.recordTrade(trade);
		//THEN..
		assertEquals(0, result);
		//assertEquals(1, service.getTradesForTicket(ticket).size());
	}

	@Test
	public void two_ticket_trades_are_recorded() {

		//GIVEN...
		Trade trade = tradeBuilder(ticket, NOW);
		Trade trade2 = tradeBuilder(ticket, NOW);
		Trade otherTicket_trade = tradeBuilder("other", NOW);

		//WHEN.. 
		int result = service.recordTrade(trade);
		assertEquals(0, result);
		result = service.recordTrade(trade2);
		assertEquals(0, result);
		result = service.recordTrade(otherTicket_trade);
		assertEquals(0, result);
		//THEN..
		//assertEquals(2, service.getTradesForTicket(ticket).size());
	}

	@Test
	public void calculate_stock_price() {

		//GIVEN.. the trades (if any) are recorded
		if(null==trades) return;
		
		for(int i = 0; i<trades.length; i++ ) {
			service.recordTrade(trades[i]);
		}

		//WHEN.. 
		Stock stock= service.calculateStockPrice(ticket, NOW - 15*60*1000);  //now - 15 minutes
		
		//THEN..
		assertEquals(this.stockPrice, stock.getPrice(), PRECISION); 
	}


	@Test
	public void calculate_gbce() {
	
		//GIVEN ..
		double expected_gbce = Math.pow((TEA_PRICE * POP_PRICE * ALE_PRICE  * GIN_PRICE * JOE_PRICE), 1/5f); 
 		
		//WHEN..
		double gdbce = service.calculateGBCE();
		
		//THEN..
		assertEquals(expected_gbce, gdbce, PRECISION);
	}
	
	
	
	// 
	private Trade tradeBuilder(String ticket, long timestamp) {
		Trade trade = tradeBuilder(ticket, timestamp, 10, 30f);
		return trade;
	}

	static private Trade tradeBuilder(String ticket, long timestamp, int qty, float price) {
		Trade trade = new Trade();
		trade.setTicket(ticket);
		trade.setTimestamp(timestamp);
		trade.setQuantity(qty);
		trade.setOperation(Trade.Operation.BUY);
		trade.setPrice(price);
		return trade;
	}

}
