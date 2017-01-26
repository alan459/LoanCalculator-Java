import java.util.*;
import javax.swing.*;


/****************************************************************************************************
* The main JFrame class to hold the GUI objects displayed to the user for interacting with the backend
* Loan.java class to perform all computations on input and feedback output.
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
    * Creates a jpanel with the frame displaying the background panel which will itself hold
    * whatever the current screen is.
    ******************************************************************************************/
    public void createMainWindow()
    {
        add(currentScreen = new Background_Panel(this));
    }
}