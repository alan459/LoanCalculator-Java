package loanCalc;

/**
 * NOTES: UPDATE TO ACCOUNT FOR MONTHS, WILL PROBABLY NEED TO CHANGE THE NUMERATOR/DENOMINATOR COMPUTATIONS TO USING A PERIOD VARIABLE INSTEAD OF YEARS
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
	private double interestMultiplier;	// (1 + interest / 100)
	private int paymentsPerCompounding; 
	//private int periods;
	private double amountRemaining;		// used when computing the total paid
	
	// computations based on input data
	private double total, paymentAmount;
	
	private final int MONTHS_PER_YEAR = 12;
	
	/** Default constructor.
	 */
	Loan() {
		
		loanAmount = 0;
		interestRate = 0;
		loanTermYears = 0;
		loanTermMonths = 0;
		interestMultiplier = 1;
	}
	
	/**
	 * Returns the payment amount that needs to be paid periodically to pay off the loan in the given term.
	 * 
	 * @return The payment amount.
	 */
	public double getPaymentAmount() {
		return paymentAmount / paymentsPerCompounding;
	}
	
	/**
	 * Returns the total amount that will be paid to pay off the loan in the given term.
	 * 
	 * @return The total that will be paid over the life of the loan.
	 */
	public double getTotalPaid() {
		return total;
	}
	
	/**
	 * Sets the amount of the loan to the passed in value.
	 * 
	 * @param amount The amount of the loan.
	 */
	public void setAmount(double amount) {
		this.loanAmount = amount;
	}
	
	/**
	 * Sets the term of the loan to the passed in years and months.
	 * 
	 * @param years The years of the loan term.
	 * @param months The months of the loan term.
	 */
	public void setLoanTerm(int years, int months) {
		
		this.loanTermYears = years;
		this.loanTermMonths = months;
		
		// periods = # of months of loan term
		//this.periods = loanTermYears * MONTHS_PER_YEAR + loanTermMonths;
	}
	
	/**
	 * Sets the frequency of the payments that will be made throughout a year to payoff the loan to
	 * the passed in string.
	 * 
	 * @param freq Frequency of the payments that will be made throughout a year to payoff the loan.
	 */
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

	/**
	 * Sets the frequency of the compounding done on the loan in a given year to the passed in string.
	 * 
	 * @param freq Frequency of the compounding done on the loan in a given year.
	 */
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
		
		double denominator = 1;
		for(int period = 1; period < loanTermYears; period++)
		{
			denominator += Math.pow(interestMultiplier, period);
		}
		
		return denominator;
	}

}
