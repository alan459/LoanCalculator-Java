import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/*****************************************************************************************************
* Represents an instance of a main menu to display on the JFrame.
*****************************************************************************************************/
public class Welcome_Screen extends JPanel 
{
	/* Pointer to the main java window to access its get() methods for other panels */
	//protected Loan_Calculator_Frame outer_frame;

	/* Panels displaying different options to the user */
	private Background_Panel background;


	/*********************************************************************************
	* Main constructor used when creating a main menu.
	*********************************************************************************/
	public Welcome_Screen(Background_Panel outerPanel) 
	{
		//setLayout(new GridLayout(2, 4));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// establish a reference to the frame to change the displayed panel
		this.background = outerPanel;

		add(new Centered_Text_Panel("Welcome to Loan Calculator"));

		add(new Continue_Button());

		//add(insert_delete_panel = new Update_Panel());

	}



	private class Continue_Button extends JButton 
	{
		public Continue_Button()
		{
			super("Continue ->");

			addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					background.changeCurrentPanel(new Loan_Screen(background));
				}
			});
		}
	}




} // end Welcome_Screen class