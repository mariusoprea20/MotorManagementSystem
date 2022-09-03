package Task8;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.awt.event.ActionEvent;

public class AddTech extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField TechIDField;
	private JTextField FirstNameField;
	private JTextField LastNameField;
	private JTextField TechAgeField;
	private JTextField TechSkillsField;
	private JTextField HealthStatusField;
	// LogInLauncher is passing its reference through classes constructor and needed to use the DB functions
	private LogInLauncher launcher; 
	//It is being passed in the constructor & used to refresh the JTable when a new user is added
	private TechList techList;



	public AddTech(LogInLauncher newLauncher, TechList newTechList) {
		launcher=newLauncher;
		techList= newTechList;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 843, 429);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnAddTech = new JButton("Add");
		btnAddTech.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Get the text entered in the text fields
				String newTechID=TechIDField.getText();
				String newAge=TechAgeField.getText();
				String firstName=FirstNameField.getText();
				String lastName=LastNameField.getText();
				String techSkills=TechSkillsField.getText();
				String healthStatus=HealthStatusField.getText();
				
				   /**
				    * Use HashSet to store the tech id of all technicians. 
				    * HashSets will be used to make sure the Admin does not enter duplicates
				    * HashSet O(1): for add() and contains()
				    */
				HashSet<Integer>techIDList=new HashSet<>();
			    for(Technician t:launcher.dbQuery.getAllTech()) {
			    	techIDList.add(t.getTech_id());
			    }

				//if the fields are empty, display a message dialog
			      if(newTechID.isEmpty()|| newAge.isEmpty() ||firstName.isEmpty()|| lastName.isEmpty() || techSkills.isEmpty() || healthStatus.isEmpty()) {
			    	  JOptionPane.showMessageDialog(null, "Please complete all the fields!", "WARNING", JOptionPane.PLAIN_MESSAGE);
			      } else {
						try {
							/**
							 * If the user does not enter only digits for the ID and age,
							 * throw a message dialog
							 */
							int techID=Integer.parseInt(newTechID);
							int age=Integer.parseInt(newAge);
							
						  //if the ID is already taken, display a warning
					    	  if((techIDList.contains(techID))) {
						    	  JOptionPane.showMessageDialog(null, "User ID already taken!", "WARNING", JOptionPane.PLAIN_MESSAGE);
					    	  }else {
							      //add a new tech in the database by calling launcher.dbQuery.addTech from LogInDatabase Class 
									launcher.dbQuery.addTech(techID, firstName, lastName, age, techSkills, healthStatus);
									
								 //refresh the JTable from TechList Class
									techList.displayTableData(launcher.dbQuery.getAllTech());
									dispose();
					    	  }
						}catch(NumberFormatException ex){
							 JOptionPane.showMessageDialog(null, "Tech ID & Tech Age must contain only numbers!", "WARNING", JOptionPane.PLAIN_MESSAGE);
						}
			      }
			}
			      
		});
		panel.add(btnAddTech);
		
		JButton btnCancelWindow = new JButton("Cancel");
		btnCancelWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(btnCancelWindow);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblTechID = new JLabel("Tech ID");
		panel_1.add(lblTechID);
		
		TechIDField = new JTextField();
		panel_1.add(TechIDField);
		TechIDField.setColumns(10);
		
		JLabel lblTechFirstName = new JLabel("First Name");
		panel_1.add(lblTechFirstName);
		
		FirstNameField = new JTextField();
		FirstNameField.setColumns(10);
		panel_1.add(FirstNameField);
		
		JLabel lblLastName = new JLabel("Last Name");
		panel_1.add(lblLastName);
		
		LastNameField = new JTextField();
		LastNameField.setColumns(10);
		panel_1.add(LastNameField);
		
		JLabel lblTechAge = new JLabel("Tech Age");
		panel_1.add(lblTechAge);
		
		TechAgeField = new JTextField();
		TechAgeField.setColumns(10);
		panel_1.add(TechAgeField);
		
		JLabel lblSkills = new JLabel("Tech Skills");
		panel_1.add(lblSkills);
		
		TechSkillsField = new JTextField();
		TechSkillsField.setColumns(10);
		panel_1.add(TechSkillsField);
		
		JLabel lblHealthStatus = new JLabel("Health Status");
		panel_1.add(lblHealthStatus);
		
		HealthStatusField = new JTextField();
		HealthStatusField.setColumns(10);
		panel_1.add(HealthStatusField);
		
		
	}
}
