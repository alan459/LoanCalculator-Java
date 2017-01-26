import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Centered_Text_Panel extends JPanel
{
	public Centered_Text_Panel(String text)
	{
		setLayout(new GridBagLayout()); 

		add(new JLabel(text));
	}
}