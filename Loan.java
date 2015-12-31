package loanCalc;

/**
 * NOTES: WILL PROBABLY NEED TO CHANGE THE NUMERATOR/DENOMINATOR COMPUTATIONS TO USING A PERIOD VARIABLE INSTEAD OF YEARS
 * CURRENTLY ONLY USES YEARS FOR COMPUTATIONS, NEED TO ACCOUNT FOR MONTHS LATER.
 */


/**
 * Holds input data for a loan and performs computations to determine payment amount needed 
 * to payoff that loan in a given loan term with a given interest rate and the total that 
 * will be paid over the life of the loan.
 * 
 * @author Alan 
 *
 */
public class Loan {
	
	// input data for loan, interest rate is assumed to be out of 100
	private double loanAmount, interestRate;
	private int loanTermYears, loanTermMonths;
	private String payFreq, compFreq;
	private int paymentFrequency, compoundingFrequency;
	
	// used for computations
	private double interestMultiplier;
	private int paymentsPerCompounding;
	private int periods;
	private double amountRemaining;
	
	
	// computations based on input data
	private double total, paymentAmount;
	
	private final int MONTHS_PER_YEAR = 12;
	
	Loan() {
		
		loanAmount = 0;
		interestRate = 0;
		loanTermYears = 0;
		loanTermMonths = 0;
		interestMultiplier = 1;
	}
	
	public double getPaymentAmount() {
		return paymentAmount / paymentsPerCompounding;
	}
	
	public double getTotalPaid() {
		return total;
	}
	
	public void setAmount(double amount) {
		this.loanAmount = amount;
	}
	
	public void setLoanTerm(int years, int months) {
		
		this.loanTermYears = years;
		this.loanTermMonths = months;
		
		// periods = # of months of loan term
		this.periods = loanTermYears * MONTHS_PER_YEAR + loanTermMonths;
	}
	
	
	public void setPaymentFrequency(String freq) {
		this.payFreq = freq;
		
		switch(freq) {
		
		case("Yearly"):
			paymentsPerCompounding = 1;
			break;
		case("Monthly"):
			paymentsPerCompounding = MONTHS_PER_YEAR;
			break;
		}
		
	}
	
	public void setCompoundingFrequency(String freq) {
		this.compFreq = freq;
		
		switch(freq) {
		
			case("Yearly"):
				compoundingFrequency = 1;
				break;
			case("Monthly"):
				compoundingFrequency = MONTHS_PER_YEAR;
				break;
		}	
		
	}
	
	public void setInterestRate(double interest) {
		this.interestRate = interest;
		
		// adjust interest rate to account for frequency of compounding per year
		if(compFreq != null)
		{
			switch(compFreq) {
			
			case("Yearly"):
				interestRate /= 1;
				break;
			case("Monthly"):
				interestRate /= MONTHS_PER_YEAR;
				break;		
			}
		}
		this.interestMultiplier = (interestRate / 100) + 1;
	}
	
	
	/**
	 * Computes the payment amount needed payoff the loan and the total amount that will be paid over the life of the loan.
	 * 
	 * ASSUMES: PAYMENT FREQUENCY = COMPOUNDING FREQUENCY.
	 * 
	 * Equation used to compute payment amount:
	 * Payments = (Principal * (1 + interest rate) ^ n ) / Sum( (1 + interest rate) ^ j  [for j = 0 to j = n - 1] ).
	 */
	public void compute() {
		
		this.paymentAmount = ( getNumerator() / getDenominator() );
		
		amountRemaining = loanAmount;
		
		// compute total to be paid for the years of the loan during the loan term
		for(int i = 1; i <= loanTermYears; i++)
		{
			addInterest();
			makePayment();			
		}
		
		// account case that too much or too little is paid off
		// by end of loan term - too much paid will give negative
		// amount remaining, too little paid will give a positive
		// amount remaining
		if(amountRemaining != 0)
		{
			total += amountRemaining;
		}
	}
	
	private void addInterest() {
		
		amountRemaining *= interestMultiplier;
	}
	
	private void makePayment() {
		amountRemaining -= paymentAmount;
		total += paymentAmount;
	}
	
	
	/**
	 * Helper method to compute the numerator in the equation used to compute payment amount.
	 * 
	 * Numerator = Principal * (1 + interest rate) ^ n 
	 * 
	 * @return The value for the numerator in the equation used by the compute() method.
	 */
	private double getNumerator() {
				
		// value of (1 + interest rate) ^ n
		double multiplier = Math.pow(interestMultiplier, loanTermYears);
		
		// returns the value of principal * (1 + interest rate) ^ n
		return (loanAmount * multiplier);
	}
	
	/**
	 * Helper method to compute the denominator in the equation used to compute payment amount.
	 * 
	 * Denominator = Sum( (1 + interest rate) ^ j  [for j = 0 to j = n - 1] )
	 * 
	 * @return The value for the denominator in the equation used by the compute() method.
	 */
	private double getDenominator() {
		
		// value of (1 + interest rate)
		//double periodicIncrement = (interestRate / 100) + 1;
		
		double denominator = 1;
		for(int period = 1; period < loanTermYears; period++)
		{
			denominator += Math.pow(interestMultiplier, period);
		}
		
		return denominator;
	}

}
