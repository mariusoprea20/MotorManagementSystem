package Task8;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Task5.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import Task7.*;

public class LogInWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	protected JTextField txtFUserID;
	private JPasswordField pwFUserPW;
	private JLabel warningLabel;
	protected HRAccount accountHR;
	protected LogInLauncher launcher;

	
	public LogInWindow(LogInLauncher newLauncher) {
		launcher=newLauncher;
		accountHR= new HRAccount(launcher);
		
		/**declare the warning label inside the constructor to be able to modify based on actions**/
		warningLabel = new JLabel();
		warningLabel.setForeground(Color.RED);
		
		
		setTitle("LogIn");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 607, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel ButtonPanel = new JPanel();
		contentPane.add(ButtonPanel, BorderLayout.SOUTH);
		
		//Method to log in
		JButton btnLogInButton = new JButton("Log In");
		btnLogInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//get the user input to log in
				String username= txtFUserID.getText();
				String password= String.valueOf(pwFUserPW.getPassword());
		        //iterate through all the accounts to match  the user name and password
				for(UserAccount user:launcher.dbQuery.getUserAccountInfo()) {
					if((user.getUsername().equals(username)) && (user.getPassword().equals(password))) {
						
						
						// allow access based on job title
							if(user.getUserJob().equals("HR")) {
								//HRAccount hrs= new HRAccount(this);
								accountHR.statusLabel.setText(user.getUserFirstName()+", Connected");
								accountHR.setVisible(true);
								 dispose();
							}
							 if(user.getUserJob().equals("Tech")) {
								 //pass the connection of the database to a different package
								 TaskAllocationApp taApp=  new TaskAllocationApp(launcher);
								  taApp.setUserIDLog(user.getUserID());
								  
								  //pass the username to Task7.TaskAllocationComponent
								  taApp.setUserName(username);
								 dispose();
							 }
							if(user.getUserJob().equals("Customer Support")) {
								//pass the connection of the database to a different package
								JobProgressApp jpApp= new JobProgressApp(launcher);
								
								jpApp.setUserName(user.getUserFirstName()+" "+user.getUserLastName());
								
								 dispose();
							 }
						
						 }
					//if username or password is incorrect, display a warning label
					else if(!(user.getUsername().equals(username)) || !(user.getPassword().equals(password))) {
						warningLabel.setText("Wrong Username or Password!");
					}
					
				}
				//if the username or password is empty, display a warning label
				 if(username.isEmpty() || password.isEmpty()) {
					 warningLabel.setText("Please enter your log in details!");
					 
				}
			}
		});
		ButtonPanel.add(btnLogInButton);
		
		//Method to clear the input text
		JButton btnClearButton = new JButton("Clear ");
		btnClearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==btnClearButton) {
					txtFUserID.setText("");
					pwFUserPW.setText("");
				}
			}
		});
		ButtonPanel.add(btnClearButton);
		
		JPanel MainPanel = new JPanel();
		contentPane.add(MainPanel, BorderLayout.CENTER);
		MainPanel.setLayout(null);
		
		txtFUserID = new JTextField();
		txtFUserID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		txtFUserID.setBounds(128, 96, 301, 34);
		MainPanel.add(txtFUserID);
		txtFUserID.setColumns(10);
		
		pwFUserPW = new JPasswordField();
		pwFUserPW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		pwFUserPW.setBounds(128, 189, 301, 34);
		MainPanel.add(pwFUserPW);
		
		JLabel lblUserIDLabel = new JLabel("User ID");
		lblUserIDLabel.setFont(new Font("Rockwell", Font.BOLD, 15));
		lblUserIDLabel.setBounds(20, 87, 86, 52);
		MainPanel.add(lblUserIDLabel);
		
		JLabel lblPasswordLabel = new JLabel("Password");
		lblPasswordLabel.setFont(new Font("Rockwell", Font.BOLD, 15));
		lblPasswordLabel.setBounds(20, 182, 86, 48);
		MainPanel.add(lblPasswordLabel);
		
		JLabel lblLogInTopLabel = new JLabel("Login Vulture");
		lblLogInTopLabel.setForeground(new Color(139, 0, 0));
		lblLogInTopLabel.setFont(new Font("Stencil", Font.BOLD, 20));
		lblLogInTopLabel.setBounds(214, 0, 176, 78);
		MainPanel.add(lblLogInTopLabel);
		
		
		warningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		warningLabel.setBounds(161, 335, 229, 14);
		MainPanel.add(warningLabel);
		
	}
	

}

