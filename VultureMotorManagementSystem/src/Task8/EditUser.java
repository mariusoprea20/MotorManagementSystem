package Task8;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class EditUser extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldUsername;
	private JTextField textFieldNewPassword;
	private JTextField textFieldConfirmPassword;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JComboBox comboBoxUserJob;
	private HRAccount hrAccount;
    private LogInLauncher launcher;
    private String oldPassword;
    protected int userID;

	public EditUser(HRAccount hrAccount, LogInLauncher launcher) {
		this.hrAccount=hrAccount;
        this.launcher= launcher;
		
		setTitle("Edit User");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		{
			JLabel lblJobTitleLabel = new JLabel("Job Title");
			contentPanel.add(lblJobTitleLabel);
		}
		{
			comboBoxUserJob = new JComboBox();
			comboBoxUserJob.setModel(new DefaultComboBoxModel(new String[] {"Tech", "HR", "Customer Support"}));
			contentPanel.add(comboBoxUserJob);
		}
		{
			JLabel lblNewLabeFirstName = new JLabel("First Name");
			contentPanel.add(lblNewLabeFirstName);
		}
		{
			textFieldFirstName = new JTextField();
			contentPanel.add(textFieldFirstName);
			textFieldFirstName.setColumns(10);
		}
		{
			JLabel lblNewLabelLastName = new JLabel("Last Name");
			contentPanel.add(lblNewLabelLastName);
		}
		{
			textFieldLastName = new JTextField();
			contentPanel.add(textFieldLastName);
			textFieldLastName.setColumns(10);
		}
		{
			JLabel lblNewLabelUserName = new JLabel("Username");
			contentPanel.add(lblNewLabelUserName);
		}
		{
			textFieldUsername = new JTextField();
			contentPanel.add(textFieldUsername);
			textFieldUsername.setColumns(10);
		}
		{
			JLabel lblNewLabelNewPassword = new JLabel("Set New  Password");
			contentPanel.add(lblNewLabelNewPassword);
		}
		{
			textFieldNewPassword = new JTextField();
			contentPanel.add(textFieldNewPassword);
			textFieldNewPassword.setColumns(10);
		}
		{
			JLabel lblNewLabelConfirmPassword = new JLabel("Confirm New Password*");
			contentPanel.add(lblNewLabelConfirmPassword);
		}
		{
			textFieldConfirmPassword = new JTextField();
			contentPanel.add(textFieldConfirmPassword);
			textFieldConfirmPassword.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				//Method to edit the user account info
				JButton okButton = new JButton("Edit");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//retrieve the data passed from HRAccount
					  String username= textFieldUsername.getText();
					  String newPassword= textFieldNewPassword.getText();
					  String confirmPW= textFieldConfirmPassword.getText();
					  String jobTitle= (String) comboBoxUserJob.getItemAt(comboBoxUserJob.getSelectedIndex());
					  String firstName= textFieldFirstName.getText();
				      String lastName= textFieldLastName.getText();
				      
				      //If the fields are empty when submitting, display a message dialog
				      if( username.isEmpty() || jobTitle.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
				    	  JOptionPane.showMessageDialog(null, "Please complete all the fields!", "WARNING", JOptionPane.PLAIN_MESSAGE);
				    	  
				      } else {
				    	     // if the password fields are empty, keep the old password
				    	      if(newPassword.equals("") && confirmPW.equals("")) {
				    	    	  //update user in the database
						    	  launcher.editUser(userID, username, oldPassword, firstName, lastName, jobTitle);
						    	  //refresh the JTable in the HRAccount class
						    	  hrAccount.displayAllUsers(launcher.dbQuery.getUserAccountInfo());
						    	  JOptionPane.showMessageDialog(null, "User "+userID+" edited!","Edited", JOptionPane.PLAIN_MESSAGE );
							      setModal(false);
							      dispose();
				    	      }
				    	     //make sure both password fields are not empty if new passwords is entered
				    	      else if(!(newPassword.isEmpty()) && confirmPW.equals("")){
						    	  JOptionPane.showMessageDialog(null, "Please confirm the new password!", "Confirm Password", JOptionPane.WARNING_MESSAGE);
						          
						     // if the two passwords do not match, display warning message
						      } else if(!(newPassword.equals(confirmPW))){
						    	 JOptionPane.showMessageDialog(null,"Passwords do not match. Check the passwords again!", "Password not matching!", JOptionPane.WARNING_MESSAGE);
						    	 
						     //else edit user
						      } else {
						    	  //update user in the database
						    	  launcher.editUser(userID, username, newPassword, firstName, lastName, jobTitle);
						    	  //refresh the JTable in the HRAccount class
						    	  hrAccount.displayAllUsers(launcher.dbQuery.getUserAccountInfo());  
						    	  JOptionPane.showMessageDialog(null, "User "+userID+" edited!","Edited", JOptionPane.PLAIN_MESSAGE );
							      setModal(false);
							      dispose();
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
	
	/*** method to display the existing data in the text fields ***/
	public void displayExistingUserData() {
		//Set the (EditUser) textFields to the existing data from DB
		for(UserAccount user:launcher.dbQuery.getUserAccountInfo()) {
			if(user.getUserID()==userID) {
			textFieldUsername.setText(user.getUsername());
			textFieldFirstName.setText(user.getUserFirstName());
			textFieldLastName.setText(user.getUserLastName());
			comboBoxUserJob.setSelectedItem(user.getUserJob());
			this.oldPassword=user.getPassword();
			
			}
		}
	 }

}
