import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/*****************************************************************************************************
* Represents an instance of a main menu to display on the JFrame.
*****************************************************************************************************/
public class Background_Panel extends JPanel 
{
	/* Pointer to the main java window to access its get() methods for other panels */
	protected JFrame outerFrame;

	/* Panels displaying different options to the user */
	private JPanel currentScreen;


	/*********************************************************************************
	* Main constructor used when creating a main menu.
	*********************************************************************************/
	public Background_Panel(JFrame frame) 
	{
		//setLayout(new GridLayout(2, 4));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// establish a reference to the frame to change the displayed panel
		this.outerFrame = frame;

		add(currentScreen = new Welcome_Screen(this));

	}

	/*********************************************************************************
	* Switches the current main panel to the passed in one.
	*********************************************************************************/
	protected void changeCurrentPanel(JPanel panel)
	{
		//removeAll();
		remove(currentScreen);

		add(currentScreen = panel);

		revalidate();
		repaint();

	} // end changeCurrentPanel()



} // end Welcome_Screen class