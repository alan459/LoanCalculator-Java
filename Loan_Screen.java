import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/*****************************************************************************************************
* Represents an instance of a main menu to display on the JFrame.
*****************************************************************************************************/
public class Loan_Screen extends JPanel 
{
	/* Pointer to the main java panel to access other panels */
	private Background_Panel background;

	private double loanAmount, interest;
	private int years;

	private JTextField loantf, yearstf, inttf;

	private Output_Panel totalPaid, yearlyPayments, monthlyPayments;

	private Loan loan;


	/*********************************************************************************
	* Main constructor used when creating a main menu.
	*********************************************************************************/
	public Loan_Screen(Background_Panel outerPanel) 
	{
		setLayout(new GridLayout(7, 2));
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.background = outerPanel;									// reference to outer panel
		
		add(new Centered_Text_Panel("Loan Amount:	 $"));		

		add(loantf = new JTextField(10));

		add(new Centered_Text_Panel("Duration of Loan in Years:	 "));		

		add(yearstf = new JTextField(10));

		add(new Centered_Text_Panel("Interest rate of Loan:	 "));		

		add(inttf = new JTextField(10));

		add(new JLabel());
		add(new Submit_Button());

		add(new Centered_Text_Panel("Total Payments: "));
		add(totalPaid = new Output_Panel(""));

		add(new Centered_Text_Panel("Yearly Payments: "));
		add(yearlyPayments = new Output_Panel(""));

		add(new Centered_Text_Panel("Monthly Payments: "));
		add(monthlyPayments = new Output_Panel(""));
	}



	private class Submit_Button extends JButton 
	{
		public Submit_Button()
		{
			super("Submit");

			addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					try 
					{
						fetchInput();
						outputResults();
					}
					catch (Exception ex)
					{
						System.err.println(ex);
					}
					
				}
			});
		}
	}


	public void fetchInput() throws Exception 
	{
		loanAmount = Double.parseDouble(loantf.getText());
		years = Integer.parseInt(yearstf.getText());
		interest = Double.parseDouble(inttf.getText());

		loan = new Loan(loanAmount, interest, years);
	}

	public void outputResults()
	{
		String total = Double.toString(loan.getTotalPaid());
		double payments = loan.getPaymentAmount();

		totalPaid.setText(total);
		yearlyPayments.setText(Double.toString(payments));
		monthlyPayments.setText(Double.toString(payments / 12));

		//displayOutputPanel(loan.getPayment(), loan.total)
	}

	private class Output_Panel extends JPanel
	{
		private JLabel currentText;

		public Output_Panel(String text)
		{
			setLayout(new GridBagLayout()); 

			add(currentText = new JLabel(text));
		}

		public void setText(String newText)	{	currentText.setText(newText);	}
	}


	/*
	private class Input_Field extends JTextField
	{
		public Input_Field(String place)
		{
			super(10);

			addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					fetchInput();
					outputResults();
				}
			});
		}
	}*/


	




} // end Loan_Screen class