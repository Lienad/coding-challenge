
public class Investment {
	
	private String ticker;
	private double targetAllocation;
	private double actualAllocation;
	private int sharesOwned;
	private double sharePrice;
	
	public Investment(){
		ticker = "";
		setTargetAllocation(0f);
		setActualAllocation(0f);
		setSharesOwned(0);
		setSharePrice(0f);
	}
	
	public Investment(String ticker, double targetAllocation,
			double actualAllocation, int sharesOwned, double sharePrice) {
		this.ticker = ticker;
		this.targetAllocation = targetAllocation;
		this.actualAllocation = actualAllocation;
		this.sharesOwned = sharesOwned;
		this.sharePrice = sharePrice;
	}

	public Investment(Investment investment) {
		this.ticker = investment.ticker;
		this.targetAllocation = investment.targetAllocation;
		this.actualAllocation = investment.actualAllocation;
		this.sharesOwned = investment.sharesOwned;
		this.sharePrice = investment.sharePrice;
	}

	public void setTicker(String ticker){
		this.ticker = ticker;
	}
	
	public String getTicker(){
		return ticker;
	}

	public double getTargetAllocation() {
		return targetAllocation;
	}

	public void setTargetAllocation(double targetAllocation) {
		this.targetAllocation = targetAllocation;
	}

	public double getActualAllocation() {
		return actualAllocation;
	}

	public void setActualAllocation(double actualAllocation) {
		this.actualAllocation = actualAllocation;
	}

	public int getSharesOwned() {
		return sharesOwned;
	}

	public void setSharesOwned(int sharesOwned) {
		this.sharesOwned = sharesOwned;
	}

	public double getSharePrice() {
		return sharePrice;
	}

	public void setSharePrice(double sharePrice) {
		this.sharePrice = sharePrice;
	}
	
	/**
	 * unallocate money from investment
	 * @return money that went toward this investment
	 */
	public double resetAllocatedAmount(){
		double totalAllocatedAmount = sharesOwned * sharePrice;
		sharesOwned = 0;
		actualAllocation = 0f;
		return totalAllocatedAmount;
	}
	
	public double getAllocationAmount(){
		return sharesOwned * sharePrice;
	}

}
