package Task5;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class TerminatedMotors extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JobProgressApp app;
	private MotorPanel motorPanel;
	private JButton suspendedMotorsBtn;
	private JButton fixedMotorsBtn;


	public TerminatedMotors(JobProgressApp app) {
		this.app=app; // pass the main class
		Color color= new Color(240,240,240);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1211, 726);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.setName("Terminated Motors");
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel southPanel = new JPanel();
		panel.add(southPanel, BorderLayout.SOUTH);
		
		/*** selected open the motor ***/
		JButton openMotorbtn = new JButton("Open Motor");
		openMotorbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int selectedRow=table.getSelectedRow();// select row
				if(selectedRow>=0) { // make sure the selection is bigger or equal to raw 0
				int motor_ID= (int)table.getValueAt(selectedRow, 0); //get the motor id of the raw
				motorPanel=new MotorPanel(app,motor_ID); // pass the details into the motorPanel constructor
				motorPanel.setVisible(true);// visibility on
			  }
			}
		});
		southPanel.add(openMotorbtn);
		
		/*** close the window ***/
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		southPanel.add(closeBtn);
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Motor ID", "Motor Name", "Status", "Manufacturer", "Arriving Date", "Deadline", "Inspection ID"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(57);
		scrollPane.setViewportView(table);
		
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.GRAY);
		contentPane.add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
		
		//display all the fixed motors if button pressed
		fixedMotorsBtn = new JButton("Fixed Motors");
		fixedMotorsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//change the colors of the buttons to let the user know the current state of the frame
				suspendedMotorsBtn.setBackground(UIManager.getColor("activeCaption"));
				fixedMotorsBtn.setBackground(SystemColor.info);
				displayFixedMotors(app.database.listOfMotors());
			}
		});
		northPanel.add(fixedMotorsBtn);
		
		//display all suspended motors if button pressed
		suspendedMotorsBtn = new JButton("Suspended Motors");
		suspendedMotorsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//change the colors of the buttons to let the user know the current state of the frame
				fixedMotorsBtn.setBackground(UIManager.getColor("activeCaption"));
				suspendedMotorsBtn.setBackground(SystemColor.info);
				displaySuspendedJobs(app.database.listOfMotors());
			}
		});
		northPanel.add(suspendedMotorsBtn);
		
		//display data in JTable
		displayFixedMotors(app.database.listOfMotors());
	}
	
	//method to fixed motors
	public void displayFixedMotors(ArrayList<Motor> motorList) {
		DefaultTableModel tableModel=(DefaultTableModel)table.getModel();
		tableModel.setRowCount(0);
		for(Motor m: motorList){
			if(m.getMotorStatus().equals("Completed")) {
			tableModel.addRow(new Object[] {m.getMotorID(), m.getMotorName(), m.getMotorStatus(), m.getMotorManufacturer(), m.getArrivalDate(), m.getDeadline(), m.getClient()});
		   }
		}
	}
	
	//method to display suspended jobs
	public void displaySuspendedJobs(ArrayList<Motor> motorList) {
		DefaultTableModel tableModel=(DefaultTableModel)table.getModel();
		tableModel.setRowCount(0);
		for(Motor m: motorList){
			if(m.getDelay().equals("Suspended") && m.getMotorStatus().equals("Suspended")) {
			tableModel.addRow(new Object[] {m.getMotorID(), m.getMotorName(), m.getMotorStatus(), m.getMotorManufacturer(), m.getArrivalDate(), m.getDeadline(), m.getClient()});
		   }
		}
	}
}
