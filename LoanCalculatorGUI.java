import java.util.*;
import javax.swing.*;

public class LoanCalculatorGUI extends JFrame
{
    


    public static void main(String[] args)
    {
        LoanCalculatorGUI frame = new LoanCalculatorGUI();
    }


    public LoanCalculatorGUI()
    {
        super("Calculator");

        this.add(new JTextField(20));

        this.setVisible(true);
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        

        //this.setLayout(new FlowLayout());

        
    }
}