package Task5;

import java.awt.BorderLayout;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;


public class InspectionGUI extends JFrame {

	private JPanel contentPane;
    private JobProgressApp app;
    private JTable table;
    private JTextField textField;

	public InspectionGUI(JobProgressApp app) {
		
		this.app=app;
		setTitle("Inspection Panel");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 837, 414);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.WHITE);
		contentPane.add(southPanel, BorderLayout.SOUTH);
		
		/*** method to open the inspection ***/
		JButton inspectBtn = new JButton("Inspect");
		inspectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow= table.getSelectedRow();
				if(selectedRow>=0) {
					int selectedMotor= (int)table.getValueAt(selectedRow, 0);
					MotorInspection inspMotor= new MotorInspection(selectedMotor, app);
					inspMotor.setVisible(true);
				}
			}
		});
		southPanel.add(inspectBtn);
		
		/*** close the window ***/
		JButton Close = new JButton("Close");
		Close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		southPanel.add(Close);
		
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel searchLbl = new JLabel("Search Inspection");
		northPanel.add(searchLbl);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
				tableModel.setRowCount(0);
				
					for(Motor m:app.database.listOfMotors()) {
							String motor= Integer.toString(m.getMotorID());
							String searchTxt=textField.getText();
					 if(motor.contains(searchTxt)) {
							if(m.getMotorStatus().equals("Inspection")) 
							    tableModel.addRow(new Object[]{ m.getMotorID(), m.getMotor_fault(),  m.getMotorStatus(), m.getDeadline()});   
				   }
				}
			}
		});
		northPanel.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Motor ID", "Motor Fault", "Status", "Deadline"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(67);
		table.getColumnModel().getColumn(1).setPreferredWidth(63);

		scrollPane.setViewportView(table);
		
		//display the database data
	  displayInspectionList();
	}
	
	//display inspection list
	public void displayInspectionList() {
		DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
		tableModel.setRowCount(0);
		
		//look through the motors and associate them with the inspection
		for(Motor m:app.database.listOfMotors()) {
			if(m.getMotorStatus().equals("Inspection")) {
				    tableModel.addRow(new Object[]{ m.getMotorID(), m.getMotor_fault(),  m.getMotorStatus(), m.getDeadline()});   
			}
		}
	}
	
}
