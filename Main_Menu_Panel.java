import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/*****************************************************************************************************
* Represents an instance of a main menu to display on the JFrame.
*****************************************************************************************************/
public class Main_Menu_Panel extends JPanel 
{
	/* Pointer to the main java window to access its get() methods for other panels */
	protected Hospital_Frame hospital_frame;

	/* Panels displaying different options to the user */
	private JPanel access_panel, insert_delete_panel;


	/*********************************************************************************
	* Main constructor used when creating a main menu.
	*********************************************************************************/
	public Main_Menu_Panel(Hospital_Frame frame) 
	{
		setLayout(new GridLayout(2, 4));

		// establish a reference to the frame to change the displayed panel
		this.hospital_frame = frame;

		add(new Centered_Text_Panel("Access Records for: "));

		add(access_panel = new Records_Panel());

		add(new Centered_Text_Panel("Insert/Delete Records for: "));

		add(insert_delete_panel = new Update_Panel());

	}



	/*********************************************************************************
	* Upper half of the main menu, displaying the buttons for accessing and modifying 
	* the relations: patient, nurse, doctor.
	*********************************************************************************/
	private class Records_Panel extends JPanel
	{

		/*********************************************************************************
		* Main constructor used when creating Records_Panel.
		*********************************************************************************/
		public Records_Panel() 
		{
			setLayout(new GridBagLayout());

			add(new Patient_Button());
			add(new Doctor_Button());
			add(new Nurse_Button());
		}



		/******************************************************************************
		* Patient button for Records_Panel class.
		******************************************************************************/
		private class Patient_Button extends JButton
		{
			/******************************************************************
			* Main constructor for Patient_Button
			******************************************************************/
			public Patient_Button()
			{
				// name the button
				super("Patient");

				addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						hospital_frame.changeScreen( new Patient_Menu_Panel(hospital_frame) );		
					}
				});
			}

		} // end class Patient_Button




		/******************************************************************************
		* Doctor button for Records_Panel class.
		******************************************************************************/
		private class Doctor_Button extends JButton
		{
			/******************************************************************
			* Main constructor for Doctor_Button
			******************************************************************/
			public Doctor_Button()
			{
				// name the button
				super("Doctor");

				addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						hospital_frame.changeScreen( new Doctor_Menu_Panel(hospital_frame) );		
					}
				});
			}
		} // end class Doctor_Button


		/******************************************************************************
		* Nurse button for Records_Panel class.
		******************************************************************************/
		private class Nurse_Button extends JButton
		{
			/******************************************************************
			* Main constructor for Nurse_Button
			******************************************************************/
			public Nurse_Button()
			{
				// name the button
				super("Nurse");

				addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						hospital_frame.changeScreen( new Nurse_Menu_Panel(hospital_frame) );		
					}
				});
			}
		} // end class Nurse_Button

	} // end Records class



	/*********************************************************************************
	* Bottom half of the main menu, displaying a dropdown menu for inserting and deleting
	* in the relations "Patient", "Nurse", "Doctor", "Medication", "Prescription", 
	* "Treatment", "Procedure".
	*********************************************************************************/
	private class Update_Panel extends JPanel
	{
		/* Drop down menu for selecting which relation to insert into */
		private JComboBox relation_selection_combo_box;

		/* Options for the user to select from in the drop down menu */
		private String[] relation_selection_options = {"Patient", "Nurse", "Doctor", "Record", "Prescription", "Treatment", "Procedure"};


		/*********************************************************************************
		* Main constructor used when creating a Update_Panel.
		*********************************************************************************/
		public Update_Panel() 
		{
			setLayout(new GridBagLayout());

			relation_selection_combo_box = new JComboBox(relation_selection_options);

			add(relation_selection_combo_box);

			add(new Submit_Button());
		}


		/******************************************************************************
		* Submit button for Update_Panel class.
		******************************************************************************/
		private class Submit_Button extends JButton
		{
			/******************************************************************
			* Main constructor for Submit_Button
			******************************************************************/
			public Submit_Button()
			{
				// name the button
				super("Submit");

				addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						fetchDropDownMenuSelection();
					}
				});
			}
		}

		/*********************************************************************************
		* Get the selected relation from the drop down menu and change the screen to the 
		* panel for inserting into that selection
		*********************************************************************************/
		private void fetchDropDownMenuSelection()
		{
			String selection = relation_selection_combo_box.getSelectedItem().toString();

			// Switch the screen to the selected option
			switch(selection)
			{
				case "Patient": 
					hospital_frame.changeScreen(new Insert_Patient_Menu_Panel(hospital_frame));
					break;

				case "Nurse": 
					hospital_frame.changeScreen(new Insert_Nurse_Menu_Panel(hospital_frame));
					break;

				case "Doctor": 
					hospital_frame.changeScreen(new Insert_Doctor_Menu_Panel(hospital_frame));
					break;

				case "Prescription": 
					hospital_frame.changeScreen(new Prescription_Menu_Panel(hospital_frame));
					break;

				case "Treatment": 
					hospital_frame.changeScreen(new Treatment_Menu_Panel(hospital_frame));
					break;

				case "Record": 
					hospital_frame.changeScreen(new Insert_Record_Menu_Panel(hospital_frame));
					break;

				case "Procedure":
					hospital_frame.changeScreen(new Procedure_Menu_Panel(hospital_frame));
					break;

				default:
					hospital_frame.changeScreen(new Insert_Doctor_Menu_Panel(hospital_frame));
					break;

			}
		}
		

	} // end update panel class



} // end main menu class