package Task8;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class HRAccount extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable employeeTable;
	protected JLabel statusLabel;
	protected LogInLauncher launcher;
	protected TechList techMenu;
	private AddUser newUser;
	private EditUser userEdit;

	public HRAccount(LogInLauncher newLogIn) {
		launcher= newLogIn;
		userEdit= new EditUser(this, launcher);
		
		setBackground(Color.BLACK);
		//database= new LogInDatabase(launcher.database);
		statusLabel = new JLabel();
		statusLabel.setForeground(new Color(50, 205, 50));
		setTitle("HR Services");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 820, 691);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setBackground(Color.BLACK);
		setJMenuBar(menuBar);
		
		//Method to display a tech menu 
		JButton btnTechMenu = new JButton("Tech Menu");
		btnTechMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//call the TechList Class
			    techMenu= new TechList(launcher);
				techMenu.setVisible(true);
			}
		});
		btnTechMenu.setBackground(UIManager.getColor("inactiveCaption"));
		btnTechMenu.setForeground(Color.BLACK);
		menuBar.add(btnTechMenu);
		
		JButton btnTaskLog = new JButton("Task Log");
		btnTaskLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TaskLogGUI log= new TaskLogGUI(launcher);
				log.setVisible(true);
			}
		});
		menuBar.add(btnTaskLog);
		
		JPanel fillinPanelJMenuBar = new JPanel();
		fillinPanelJMenuBar.setBackground(Color.BLACK);
		menuBar.add(fillinPanelJMenuBar);
		
		JButton LogOutButton = new JButton("Log Out");
		LogOutButton.setToolTipText("");
		LogOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				//log out the user
				LogInWindow logOut = new LogInWindow(launcher);
				logOut.setVisible(true);
			}
		});
		
		menuBar.add(LogOutButton);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.BLACK);
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		
		//Method for adding new user accounts
		JButton btnAddButton = new JButton("Add");
		btnAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//add new user by calling the method addUser()
				addUser();
			}
		});
		bottomPanel.add(btnAddButton);
		
		// Method for editing existing user accounts 
		JButton btnEditButton = new JButton("Edit");
		btnEditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int selectedRow= employeeTable.getSelectedRow();
				 if(selectedRow>=0) {
					 int selectedUserID=(int)employeeTable.getValueAt(selectedRow, 0);
					userEdit.userID=selectedUserID;
					userEdit.displayExistingUserData();
					userEdit.setVisible(true);
				 }
			}
		});
		bottomPanel.add(btnEditButton);
		
		//Method to delete a user account from DB
		JButton btnDeleteButton = new JButton("Delete");
		btnDeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow= employeeTable.getSelectedRow();
				if(selectedRow>=0) 	{
				 int selectedUserID= (int)employeeTable.getValueAt(selectedRow,0);
				 //for every user account
					for(UserAccount user:launcher.dbQuery.getUserAccountInfo()) {
						if(user.getUserID()==selectedUserID) {
							//re-confirm the decision with the user by displaying a message dialog
							int result = JOptionPane.showConfirmDialog(null,"Do you want to Delete '"+user.getUsername()+"'?", "CONFIRMATION", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					            if(result == JOptionPane.YES_OPTION){
					            	launcher.removeUser(selectedUserID);// pass the id to to delete the user
					            	displayAllUsers(launcher.dbQuery.getUserAccountInfo());//refresh the table
					            }
						}
					}
				}
//				displayAllUsers(launcher.dbQuery.getUserAccountInfo());
			}
		});
		bottomPanel.add(btnDeleteButton);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		employeeTable = new JTable();
		employeeTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"User ID", "Job Title", "First Name", "LastName", "Username"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		employeeTable.getColumnModel().getColumn(1).setPreferredWidth(97);
		employeeTable.getColumnModel().getColumn(2).setPreferredWidth(143);
		employeeTable.getColumnModel().getColumn(3).setPreferredWidth(153);
		employeeTable.getColumnModel().getColumn(4).setPreferredWidth(157);
		scrollPane.setViewportView(employeeTable);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setForeground(Color.RED);
		scrollPane.setColumnHeaderView(btnNewButton);
		
		statusLabel.setFont(new Font("Arial Black", Font.PLAIN, 11));
		statusLabel.setBackground(Color.WHITE);
		statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(statusLabel, BorderLayout.NORTH);
		//display the user lists
		this.displayAllUsers(launcher.dbQuery.getUserAccountInfo());
	}
	
	//Method to display all users
	void displayAllUsers(ArrayList<UserAccount> userList) {
		DefaultTableModel tableModel= (DefaultTableModel)employeeTable.getModel();
		tableModel.setRowCount(0);
		
		for(UserAccount user:userList) {
			tableModel.addRow(new Object[] {user.getUserID(), user.getUserJob(), user.getUserFirstName(), user.getUserLastName(), user.getUsername()});
		}
	}
	//method will be invoked to add a new user
	public void addUser() {
		newUser= new AddUser(this, launcher);
		newUser.setVisible(true);
	}

}
