import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PortfolioThreeInvestmentsTest {
	
	private static Investment googInvestment = new Investment("goog", 60f, 50.96f, 52, 98f);
	private static Investment aaplInvestment = new Investment("aapl", 30f, 29.92f, 136, 22f);
	private static Investment tslaInvestment = new Investment("tsla", 10f, 19.12f, 239, 8f);
	static String rebalanceTslaFirst = "sell 114 shares of tsla, buy 9 shares of goog";
	static String rebalanceGoogFirst = "buy 9 shares of goog, sell 114 shares of tsla";
	
	@Test
	public void testSharepriceAscending() {
		Portfolio p = new Portfolio();
		p.addInvestment(googInvestment);
		p.addInvestment(aaplInvestment);
		p.addInvestment(tslaInvestment);
		String rebalance = p.rebalanceAllocations();
		assertTrue(rebalance.equals(rebalanceGoogFirst));
	}
	
	@Test
	public void testSharepriceDescending(){
		Portfolio p = new Portfolio();
		p.addInvestment(tslaInvestment);
		p.addInvestment(aaplInvestment);
		p.addInvestment(googInvestment);
		String rebalance = p.rebalanceAllocations();
		assertTrue(rebalance.equals(rebalanceTslaFirst));
	}

}
