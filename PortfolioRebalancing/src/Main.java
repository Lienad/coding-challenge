import java.util.Scanner;
public class Main {
	
	public static void main (String[] args){
		Scanner scanner = new Scanner(System.in);
		
		int numInvestments = 0;
		
		try{
			numInvestments = Integer.parseInt(scanner.next());
		}catch (Exception e){}
		
		Portfolio portfolio = new Portfolio();
		
		for (int i = 0; i < numInvestments; i ++){
			try{
				String ticker = scanner.next();
				float targetAllocation = Float.parseFloat(scanner.next());
				float actualAllocation = Float.parseFloat(scanner.next());
				int sharesOwned = Integer.parseInt(scanner.next());
				float sharePrice = Float.parseFloat(scanner.next());
				
				Investment investment = new Investment(ticker, targetAllocation, actualAllocation, sharesOwned, sharePrice);
				portfolio.addInvestment(investment);
			} catch (Exception e){
				// invalid input
				break;
			}
			
		}
		
		System.out.println(portfolio.rebalanceAllocations());
		
		scanner.close();
	}
}