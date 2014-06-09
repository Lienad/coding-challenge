import java.util.ArrayList;

public class Portfolio {
	
	private ArrayList<Investment> investmentList;
	private double unAllocatedFunds;
	private double totalAmountOfFundsAllocated;
	private double totalPercentageOfFundsAllocated;
	
	public Portfolio(){
		investmentList = new ArrayList<Investment>();
		unAllocatedFunds = 0;
		totalAmountOfFundsAllocated = 0;
	}
	
	public Portfolio(ArrayList<Investment> investmentList, double unusedAllocations, double totalAllocations){
		this.investmentList = new ArrayList<Investment>();
		for (int i = 0; i < investmentList.size(); i++){
			this.investmentList.add(new Investment(investmentList.get(i)));
		}
		this.unAllocatedFunds = unusedAllocations;
		this.totalAmountOfFundsAllocated = totalAllocations;
	}
	
	/**
	 * add invstment to user's portfolio
	 * @param i
	 */
	public void addInvestment(Investment i){
		investmentList.add(i);
		totalAmountOfFundsAllocated += i.getAllocationAmount();
		totalPercentageOfFundsAllocated += i.getActualAllocation();
		calculateUnAllocatedFunds();
	}
	
	private void calculateUnAllocatedFunds(){
		double totalFundsAvailable = totalAmountOfFundsAllocated / (totalPercentageOfFundsAllocated/100);
		// TODO: throw exception if calculated total funds available does not match previous investments
		unAllocatedFunds = totalFundsAvailable - totalAmountOfFundsAllocated;
	}
	
	/**
	 * unallocates all funds
	 * then finds optimal allocations
	 * @return actions required to obtain optimal allocation of funds
	 */
	public String rebalanceAllocations(){
		Portfolio p = new Portfolio(investmentList, unAllocatedFunds, totalAmountOfFundsAllocated);
		p.setOptimalBalance();
		String rebalance = this.rebalanceToMatch(p);
		return rebalance;
	}
	
	/**
	 * gets mapping that orders investments by shareprice descending
	 * allocate funds one at a time in that order
	 */
	private void setOptimalBalance(){
		resetAllocations();
		
		// get mapping that orders investments based on share price
		int[] mapping = getMappingOfInvestmentsBasedOnSharePrice();
		
		for (int i = 0; i < mapping.length; i++){
			Investment investment = investmentList.get(mapping[i]);
			double targetAllocationAmount = totalAmountOfFundsAllocated * (investment.getTargetAllocation()/100f);
			int numSharesToBuy = (int) Math.round(targetAllocationAmount/investment.getSharePrice());
			if ((numSharesToBuy * investment.getSharePrice()) < unAllocatedFunds){
				investment.setSharesOwned(numSharesToBuy);
				investment.setActualAllocation(((numSharesToBuy * investment.getSharePrice())/totalAmountOfFundsAllocated)*100);
				unAllocatedFunds -= numSharesToBuy * investment.getSharePrice();
			}else{
				numSharesToBuy = (int) Math.floor(unAllocatedFunds/investment.getSharePrice());
				investment.setSharesOwned(numSharesToBuy);
				investment.setActualAllocation(((numSharesToBuy * investment.getSharePrice())/totalAmountOfFundsAllocated)*100);
				unAllocatedFunds -= numSharesToBuy * investment.getSharePrice();
			}
		}	
	}
	
	/**
	 * return a mapping that will map the current order of investments to ordered by shareprice descending
	 * 
	 * rather then finding this mapping, it might be better to sort the investments as they come in
	 * when the portfolio is initially created
	 * @return
	 */
	private int[] getMappingOfInvestmentsBasedOnSharePrice(){
		int[] mapping = new int[investmentList.size()];
		
		for (int i = 0; i < investmentList.size(); i++){
			int ithLargestShareIndex = 0;
			double ithLargestSharePrice = 0f;
			double maxIthSharePrice = 0f; // the ith largest cannot exceed the (i-1) largest
			if (i > 0){
				maxIthSharePrice = investmentList.get(mapping[i - 1]).getSharePrice();
			}
			for (int j = 0; j < investmentList.size(); j++){
				double jthSharePrice = investmentList.get(j).getSharePrice();
				if (jthSharePrice > ithLargestSharePrice){
					if (i > 0){
						if (jthSharePrice > maxIthSharePrice){
							continue;
						} else if (jthSharePrice == maxIthSharePrice){ // there could be multiple with the same share price
							boolean indexAlreadyUsed = false;
							for (int k = 0; k < i; k++){
								if (mapping[k] == j){
									indexAlreadyUsed = true;
									break;
								}
							}
							if (indexAlreadyUsed){
								continue;
							}else{
								ithLargestSharePrice = jthSharePrice;
								ithLargestShareIndex = j;
							}
						} else {
							ithLargestSharePrice = jthSharePrice;
							ithLargestShareIndex = j;
						}
					} else { // find the max value
						ithLargestSharePrice = jthSharePrice;
						ithLargestShareIndex = j;
					}
				}
			}
			mapping[i] = ithLargestShareIndex;
		}
		
		return mapping;
	}
	
	private void resetAllocations(){
		for (int i = 0; i < investmentList.size(); i++){
			unAllocatedFunds += investmentList.get(i).resetAllocatedAmount();
		}
	}
	
	/**
	 * @param p
	 * @return procedure to match allocations of p
	 */
	private String rebalanceToMatch(Portfolio p){
		StringBuilder rebalanceBuilder = new StringBuilder();
		
		for (int i = 0; i < investmentList.size(); i++){
			Investment thisInvestment = this.investmentList.get(i);
			Investment investmentToMatch = p.investmentList.get(i);
			
			int shareDifference = investmentToMatch.getSharesOwned() - thisInvestment.getSharesOwned();
			
			if (shareDifference > 1){
				rebalanceBuilder.append("buy " + shareDifference + " shares of " + thisInvestment.getTicker() + ", ");
			}else if (shareDifference == 1){
				rebalanceBuilder.append("buy 1 share of " + thisInvestment.getTicker() + ", ");
			} else if (shareDifference == 0){
				// do nothing
			} else if (shareDifference == -1){
				rebalanceBuilder.append("sell 1 share of " + thisInvestment.getTicker() + ", ");
			} else if (shareDifference < -1){
				rebalanceBuilder.append("sell " + Math.abs(shareDifference) + " shares of " + thisInvestment.getTicker() + ", ");
			}
			
		}
		
		// remove ending comma and space
		if (rebalanceBuilder.length() > 1){
			rebalanceBuilder.delete(rebalanceBuilder.length() - 2, rebalanceBuilder.length());
		}
		
		this.matchInvestments(p);
		
		return rebalanceBuilder.toString();
	}
	
	/**
	 * 
	 * @param p
	 */
	private void matchInvestments(Portfolio p){
		this.investmentList = new ArrayList<Investment>(p.investmentList);
		this.unAllocatedFunds = p.unAllocatedFunds;
		this.totalAmountOfFundsAllocated = p.totalAmountOfFundsAllocated;
	}
}
