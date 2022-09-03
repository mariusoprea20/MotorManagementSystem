package Task5;

import java.awt.BorderLayout;



import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Task8.LogInWindow;
import java.awt.SystemColor;


public class MotorListGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private AddMotor newMotor;
	protected TerminatedMotors terminatedMotors;
    protected JobProgressApp jpApp;
    protected MotorPanel motorPanel;
    protected InspectionGUI inspection;
    private JTextField txtSearch_1;

	/**
	 * Create the frame.
	 */
	public MotorListGUI(JobProgressApp app) {
        
		this.jpApp=app;
		setTitle("Motor Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1248, 617);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(SystemColor.inactiveCaption);
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Motor ID", "Motor Name", "Status", "Manufacturer", "Arriving Date", "Deadline", "Client Name"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		scrollPane.setViewportView(table);
		
		JPanel sidePanel = new JPanel();
		sidePanel.setBackground(SystemColor.inactiveCaption);
		contentPane.add(sidePanel, BorderLayout.WEST);
		sidePanel.setLayout(new GridLayout(15, 1, 0, 10));
		
/************************************************************************************************************************/
		//method to find the motor in a list 
		txtSearch_1 = new JTextField();
		txtSearch_1.setForeground(Color.LIGHT_GRAY);
		txtSearch_1.setText("Search...");
		
		// change colour and text when mouse clicked inside the text box
		txtSearch_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtSearch_1.setForeground(Color.BLACK);
				txtSearch_1.setText("");
				txtSearch_1.removeMouseListener(this);
			}

		});

		//searches for the motor name or arriving date in the store
		txtSearch_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
				tableModel.setRowCount(0);
				
				for(Motor m:jpApp.database.listOfMotors()) {
					String txtSearch= txtSearch_1.getText();
					if(m.getArrivalDate().contains(txtSearch)) {
						if(!(m.getDelay().equals("Suspended")) && !(m.getMotorStatus().equals("Suspended"))) { // display no suspended motors
					    	if((!(m.getMotorStatus().equals("Completed"))) && (!(m.getMotorStatus().equals("Inspection")))) // display no completed motors
						tableModel.addRow(new Object[] {m.getMotorID(), m.getMotorName(), m.getMotorStatus(), m.getMotorManufacturer(), m.getArrivalDate(), m.getDeadline(), 0});//m.getInspection_id()
					  }					}
					else if( m.getMotorName().contains(txtSearch)){
						if(!(m.getDelay().equals("Suspended")) && !(m.getMotorStatus().equals("Suspended"))) { // display no suspended motors
							if((!(m.getMotorStatus().equals("Completed"))) && (!(m.getMotorStatus().equals("Inspection")))) // display no completed motors
						tableModel.addRow(new Object[] {m.getMotorID(), m.getMotorName(), m.getMotorStatus(), m.getMotorManufacturer(), m.getArrivalDate(), m.getDeadline(), 0});//m.getInspection_id()
					  }
					}
					else if(txtSearch.equals("")) {
						if(!(m.getDelay().equals("Suspended")) && !(m.getMotorStatus().equals("Suspended"))) { // display no suspended motors
							if((!(m.getMotorStatus().equals("Completed"))) && (!(m.getMotorStatus().equals("Inspection")))) // display no completed motors
						tableModel.addRow(new Object[] {m.getMotorID(), m.getMotorName(), m.getMotorStatus(), m.getMotorManufacturer(), m.getArrivalDate(), m.getDeadline(), 0});//m.getInspection_id()
					  }
					}
				}	
			}
		});
		sidePanel.add(txtSearch_1);
		txtSearch_1.setColumns(10);
		
/*************************************************************************************************************************************************************************************************************************/
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setEnabled(false);
		sidePanel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setEnabled(false);
		sidePanel.add(lblNewLabel_6);
		
		//add new motor
		JButton addMotorBtn = new JButton("Add Motor");
		addMotorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newMotor= new AddMotor(jpApp);
				newMotor.setVisible(true);
			}
		});
		sidePanel.add(addMotorBtn);
		
		/*** To open a motor, pass the ID from the selected row to the MotorPanel Class ***/
		JButton openMotorBtn = new JButton("Open Motor");
		openMotorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow=table.getSelectedRow();// select row
				if(selectedRow>=0) { // make sure the selection is bigger or equal to raw 0
				int motor_ID= (int)table.getValueAt(selectedRow, 0); //get the motor id of the raw
				motorPanel=new MotorPanel(jpApp,motor_ID); // pass the details into the motorPanel constructor
				motorPanel.setVisible(true);// visibility on
				}
			}
		});
		sidePanel.add(openMotorBtn);
		
		//suspend a task
		JButton suspendMotorBtn = new JButton("Suspend");
		suspendMotorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow= table.getSelectedRow();
				if(selectedRow>=0) {
				int motorID= (int)table.getValueAt(selectedRow, 0);
					for(Motor m:jpApp.database.listOfMotors()) {
						if(m.getMotorID()==motorID) {
                            jpApp.database.suspendMotor(motorID);
							displayMotors(jpApp.database.listOfMotors());
							app.displayTerminatedMotors(); // update the JTable from TerminatedMotors class
						}
					}
				}
			}
		});
		sidePanel.add(suspendMotorBtn);
		
		JButton inspectionMotorBtn = new JButton("Inspection");
		inspectionMotorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inspection= new InspectionGUI(jpApp);
				inspection.setVisible(true);
			}
		});
		sidePanel.add(inspectionMotorBtn);
		
		JButton fixedMotorsBtn = new JButton("Closed Motors");
		fixedMotorsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				terminatedMotors= new TerminatedMotors(jpApp);
				terminatedMotors.setVisible(true);
			}
		}); 
		sidePanel.add(fixedMotorsBtn);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setEnabled(false);
		sidePanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setEnabled(false);
		sidePanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setEnabled(false);
		sidePanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setEnabled(false);
		sidePanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setEnabled(false);
		sidePanel.add(lblNewLabel_4);
		
		JButton deleteMotorBtn = new JButton("Delete Motor");
		deleteMotorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow=table.getSelectedRow();
				if(selectedRow>=0) {
					int motorID=(int)table.getValueAt(selectedRow,0);
					//confirm yes or no deletion of a motor
                    int result= JOptionPane.showConfirmDialog(null,"Do you want to delete motor id "+motorID+"?","Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(result == JOptionPane.YES_OPTION) {
					jpApp.database.deleteMotor(motorID);
                    }
					jpApp.displayAllMotors();
					
				}
				
			}
		});
		sidePanel.add(deleteMotorBtn);
		
		/***method to log out***/
		JButton logOutBtn = new JButton("Log Out");
		logOutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			   dispose();// close the window
			   LogInWindow logOut= new LogInWindow(jpApp.launcher); // pas the launcher from Task8 to open the log in window
			   logOut.setVisible(true);
			   
			}
		});
		sidePanel.add(logOutBtn);
		
	}
	
	//method to display a list of existing motors on the screen
	public void displayMotors(ArrayList<Motor> motorList) {
		DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
		tableModel.setRowCount(0);
		for(Motor m: motorList){
			if( !(m.getMotorStatus().equals("Suspended"))) { // display no suspended motors
		    	if((!(m.getMotorStatus().equals("Completed"))) && (!(m.getMotorStatus().equals("Inspection")))) // display no completed or inspection status motors
			tableModel.addRow(new Object[] {m.getMotorID(), m.getMotorName(), m.getMotorStatus(), m.getMotorManufacturer(), m.getArrivalDate(), m.getDeadline(), m.getClient()});
		  }
	   }
	}
}
