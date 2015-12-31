package loanCalc;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.ButtonGroup;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JComboBox;

public class LoanCalc {

	private JFrame frame;
	
	// input data from user
	private JTextField loanAmountInput, interestRate, loanYears;

	// computed output data 
	private JTextField textFieldPeriodicPayments, textTotalPayments;

	// user selection of frequency of payments and compounding for loan
	private String[] paymentFrequencies = {"Monthly", "Yearly"};
	private String[] compoundingFrequencies = {"Yearly"};
	
	// combo boxes to display frequency of payments and compounding for loan
	private JComboBox comboBoxPayFreq, comboBoxCompFreq;
	
	private Loan loan1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoanCalc window = new LoanCalc();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoanCalc() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Loan Calc");
		frame.getContentPane().setBackground(Color.WHITE);
		
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(610, 400);
		frame.getContentPane().setLayout(null);
		
		JLabel lblLoanAmount = new JLabel("Loan Amount");
		lblLoanAmount.setBounds(59, 41, 101, 16);
		frame.getContentPane().add(lblLoanAmount);
		
		JLabel lblInterestRate = new JLabel("Interest Rate (% APR)");
		lblInterestRate.setBounds(59, 119, 140, 26);
		lblInterestRate.setBackground(Color.YELLOW);
		frame.getContentPane().add(lblInterestRate);
		
		loanAmountInput = new JTextField();
		loanAmountInput.setBounds(201, 31, 130, 26);
		frame.getContentPane().add(loanAmountInput);
		loanAmountInput.setColumns(10);
		
		interestRate = new JTextField();
		interestRate.setBounds(201, 119, 130, 26);
		frame.getContentPane().add(interestRate);
		interestRate.setColumns(10);
		
		JLabel lblLoanTerm = new JLabel("Loan Term");
		lblLoanTerm.setBounds(59, 76, 66, 27);
		frame.getContentPane().add(lblLoanTerm);
		
		JLabel lblPaymentFrequency = new JLabel("Payments Made");
		lblPaymentFrequency.setBounds(59, 163, 140, 25);
		frame.getContentPane().add(lblPaymentFrequency);
		
		textFieldPeriodicPayments = new JTextField();
		textFieldPeriodicPayments.setBounds(180, 319, 106, 26);
		textFieldPeriodicPayments.setEditable(false);
		textFieldPeriodicPayments.setColumns(8);
		frame.getContentPane().add(textFieldPeriodicPayments);
		
		loanYears = new JTextField();
		loanYears.setBounds(201, 76, 67, 26);
		loanYears.setColumns(10);
		frame.getContentPane().add(loanYears);
		
		JLabel lblPeriodicPayments = new JLabel("Periodic Payments");
		lblPeriodicPayments.setBounds(59, 319, 114, 27);
		frame.getContentPane().add(lblPeriodicPayments);
		
		JLabel lblTotalPayments = new JLabel("Total Paid");
		lblTotalPayments.setBounds(333, 319, 71, 27);
		frame.getContentPane().add(lblTotalPayments);
		
		textTotalPayments = new JTextField();
		textTotalPayments.setBounds(412, 319, 106, 26);
		textTotalPayments.setEditable(false);
		textTotalPayments.setColumns(8);
		frame.getContentPane().add(textTotalPayments);
		
		JButton btnCompute = new JButton("Compute\nTotals");
		btnCompute.setBounds(450, 15, 122, 227);
		btnCompute.setBackground(Color.PINK);
		btnCompute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				loan1 = new Loan();
				
				// try to get user input data, if success compute and display
				// if failed, the method will display the appropriate error message
				if(getData()) {
					loan1.compute();
					
					String paymentAmount = Double.toString( loan1.getPaymentAmount() ) ;
					paymentAmount = getUp2Decimals(paymentAmount);
					textFieldPeriodicPayments.setText( paymentAmount ); 
					
					String total = Double.toString( loan1.getTotalPaid() ) ; 
					total = getUp2Decimals(total);
					textTotalPayments.setText( total ); 
					
				}
			}
		});
		frame.getContentPane().add(btnCompute);
		
		JLabel lblYears = new JLabel("Years");
		lblYears.setBounds(276, 81, 43, 16);
		frame.getContentPane().add(lblYears);
		
		comboBoxPayFreq = new JComboBox(paymentFrequencies);
		comboBoxPayFreq.setBounds(211, 163, 101, 27);
		comboBoxPayFreq.setSelectedItem("Monthly");
		
		
		frame.getContentPane().add(comboBoxPayFreq);
		
		JLabel lblCompoundingFrequency = new JLabel("Compounding ");
		lblCompoundingFrequency.setBounds(59, 203, 140, 25);
		frame.getContentPane().add(lblCompoundingFrequency);
		
		comboBoxCompFreq = new JComboBox(compoundingFrequencies);
		comboBoxCompFreq.setBounds(211, 203, 101, 27);
		frame.getContentPane().add(comboBoxCompFreq);
		frame.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{loanAmountInput, interestRate, textFieldPeriodicPayments, loanYears, textTotalPayments, btnCompute}));
		
	} // end initialize() method
	
	/**
	 * Gets the data needed to compute the loan, return true if successful and false
	 * if unsuccessful.
	 * 
	 * An error window is displayed to the user in the case that a problem is 
	 * encountered with the input.
	 * 
	 * @return True if all input data was formatted correctly and false if not.
	 */
	private boolean getData() {
		
		try {
			String amount = loanAmountInput.getText();
			loan1.setAmount( Double.parseDouble( amount ) );
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error: Loan amount field has non numeric character", "Invalid Entry", JOptionPane.ERROR_MESSAGE);		
			return false;
		}
		
		try {
			String years = loanYears.getText();
			//String months = loanMonths.getText();
			
			loan1.setLoanTerm( Integer.parseInt( years ), 0 );
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error: Field for years or months for loan term has non-numeric character", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		try {
			String interest = interestRate.getText();
			loan1.setInterestRate( Double.parseDouble( interest ) );
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error: Interest rate field has non numeric character", "Invalid Entry", JOptionPane.ERROR_MESSAGE);		
			return false;
		}
		
		try {
			String payFreq = (String) comboBoxPayFreq.getSelectedItem();
			loan1.setPaymentFrequency( payFreq );
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error: Payment Frequency Selection", "Invalid Entry", JOptionPane.ERROR_MESSAGE);		
			return false;
		}
		
		try {
			String compFreq = (String) comboBoxCompFreq.getSelectedItem();
			loan1.setCompoundingFrequency( compFreq );
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error: Payment Frequency Selection", "Invalid Entry", JOptionPane.ERROR_MESSAGE);		
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Modifies a string such that if there is a decimal point, no more than 2 digits beyond 
	 * the decimal point are retained.
	 * 
	 * @param value String of the double to be modified.
	 * @return	The modified string with no more than 2 digits beyond the decimal point.
	 */
	private String getUp2Decimals(String value) {
		
		// keep track of whether a decimal is encountered
		boolean decimal = false;
		
		// find index of decimal point in string if there is one
		int i;
		for(i = 0; i < value.length(); i++)
		{
			if(value.charAt(i) == '.')
			{
				decimal = true;
				break;
			}
		}
		
				
		// if a decimal was encountered, return up to 2 digits beyond decimal poin,
		// otherwise just return the passed in string
		if(decimal && (i + 3) <= value.length())
			return value.substring(0, i + 3);
		
		// case where only 1 digit beyond decimal
		else if(decimal && (i + 2) <= value.length())
			return value.substring(0, i + 2);
		
		// case of no decimal
		else
			return value;
	} // end getUp2Decimals() method
} // end class Loan Calc
