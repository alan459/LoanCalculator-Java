import java.util.*;
import javax.swing.*;


/****************************************************************************************************
* An interactive interface to interact with a hospital database.
****************************************************************************************************/
public class Loan_Calculator_Frame extends JFrame
{
    public static final int WIDTH = 500;
    public static final int HEIGHT = 400;

    private JPanel currentScreen, managerPanel;


    /******************************************************************************************
    * Main method to start the program.
    ******************************************************************************************/
    public static void main(String[] args)
    {
        Loan_Calculator_Frame frame = new Loan_Calculator_Frame();
    }


    /******************************************************************************************
    * Main constructor to initialize the frame.
    ******************************************************************************************/
    public Loan_Calculator_Frame()
    {
        super("Calculator");

        //add(new JTextField(20));
        createMainWindow();

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);    // center the window
        

        //setLayout(new FlowLayout());

        setVisible(true);
    }


    /******************************************************************************************
    * Creates a jpanel with the frame displaying the main menu options and sets it as the 
    * main screen.
    ******************************************************************************************/
    public void createMainWindow()
    {
        add(currentScreen = new Background_Panel(this));
    }
}