package Task8;

import java.awt.BorderLayout;



import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class AddUser extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldUsername;
	private JTextField textFieldPassword;
	private JTextField textFieldUserID;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private HRAccount hrAccount;
	private LogInLauncher launcher; 
	


	public AddUser(HRAccount hrAccount, LogInLauncher launcher) {
		this.hrAccount=hrAccount;
		this.launcher=launcher;
		
		setTitle("Add New User");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		{
			JLabel lblUserID = new JLabel("User ID*");
			contentPanel.add(lblUserID);
		}
		{
			textFieldUserID = new JTextField();
			contentPanel.add(textFieldUserID);
			textFieldUserID.setColumns(10);
		}
		{
			JLabel lblFirstNameLabel = new JLabel("First Name*");
			contentPanel.add(lblFirstNameLabel);
		}
		{
			textFieldFirstName = new JTextField();
			contentPanel.add(textFieldFirstName);
			textFieldFirstName.setColumns(10);
		}
		{
			JLabel lblLastName = new JLabel("Last Name*");
			contentPanel.add(lblLastName);
		}
		{
			textFieldLastName = new JTextField();
			contentPanel.add(textFieldLastName);
			textFieldLastName.setColumns(10);
		}
		{
			JLabel lblNewLabelUserName = new JLabel("Username*");
			contentPanel.add(lblNewLabelUserName);
		}
		{
			textFieldUsername = new JTextField();
			contentPanel.add(textFieldUsername);
			textFieldUsername.setColumns(10);
		}
		{
			JLabel lblNewLabelPassword = new JLabel("Password*");
			contentPanel.add(lblNewLabelPassword);
		}
		{
			textFieldPassword = new JTextField();
			contentPanel.add(textFieldPassword);
			textFieldPassword.setColumns(10);
		}
		{
			JLabel lblNewLabelJobTitle = new JLabel("JobTitle*");
			contentPanel.add(lblNewLabelJobTitle);
		}
		
		JComboBox comboBoxUserJob = new JComboBox();
		comboBoxUserJob.setModel(new DefaultComboBoxModel(new String[] {"Tech", "HR", "Customer Support"}));
		contentPanel.add(comboBoxUserJob);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Add");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
			          //Get all the user inputs
					  String username= textFieldUsername.getText();
					  String password= textFieldPassword.getText();
					  
					  /**ComboBox is used to fetch the job titles from the DB 
					     This is suitable for preventing human errors such as inserting the wrong job title*/
					  String jobTitle= (String) comboBoxUserJob.getItemAt(comboBoxUserJob.getSelectedIndex());
					  String userID= textFieldUserID.getText();
					  String firstName= textFieldFirstName.getText();
				      String lastName= textFieldLastName.getText();
				      
				   /**
				    * Use HashSet to store the user id and user name of all users. 
				    * HashSets will be used to make sure the Admin does not enter duplicates
				    * HashSet O(1): for add() and contains()
				    */
				      HashSet<Integer> userIDList= new HashSet<>();
				      HashSet<String> usernameList= new HashSet<>();
				      
				      for(UserAccount t:launcher.dbQuery.getUserAccountInfo()) {
				    	  userIDList.add(t.getUserID());
				    	  usernameList.add(t.getUsername());
				      }
	
				      //if a filed is empty, display a message dialog
				      if(userID.isEmpty()|| username.isEmpty() ||password.isEmpty()|| jobTitle.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
				    	  JOptionPane.showMessageDialog(null, "Please complete all the fields!", "WARNING", JOptionPane.PLAIN_MESSAGE);
				    	  
				      } else {
							try {
								//if the user does not insert only numbers for the ID, throw a message dialog!
						    	 int convertedID=Integer.parseInt(userID);
						    	 
						    	 //If user id is already taken, throw a message dialog
						    	  if(userIDList.contains(convertedID)) {
							         JOptionPane.showMessageDialog(null, "User ID already taken!", "WARNING", JOptionPane.PLAIN_MESSAGE);					    	   
						    	  }
						    	  //if the user name is already taken, throw a message dialog
						    	  else if(usernameList.contains(username)) {
						    		  JOptionPane.showMessageDialog(null,"Username already taken!", "WARNING", JOptionPane.PLAIN_MESSAGE);
						    	  }
						    	  //else record everything
						    	  else {	 			
						    		  //add new user
								      launcher.addNewUser(convertedID, username, password, firstName, lastName, jobTitle);
								      //refresh the JTable
								      hrAccount.displayAllUsers(launcher.dbQuery.getUserAccountInfo());
							    	  setModal(false);
							    	  dispose();
						    	  }
							}catch(NumberFormatException number) {
						    	  JOptionPane.showMessageDialog(null, "User ID must contain only numbers!", "WARNING", JOptionPane.PLAIN_MESSAGE);
							}
				         }
				      }
				});
				
				buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setModal(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
