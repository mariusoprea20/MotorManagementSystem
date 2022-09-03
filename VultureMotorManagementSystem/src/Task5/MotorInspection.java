package Task5;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MotorInspection extends JFrame {

	private JPanel contentPane;
	private JTable table;
    private int motorID;
    private JobProgressApp app;
    private MotorPanel motorPanel;
    private JTextArea textArea;
    private JButton addBtn;
    
	public MotorInspection(int motorID, JobProgressApp app) {
		
		this.app=app;
		this.motorID=motorID;
		
		setTitle("Inspection");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1209, 653);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel bottomPanel = new JPanel();
		
		JPanel centerPanel = new JPanel();
		
		JPanel topPanel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(bottomPanel, GroupLayout.DEFAULT_SIZE, 1115, Short.MAX_VALUE)
				.addComponent(centerPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1115, Short.MAX_VALUE)
				.addComponent(topPanel, GroupLayout.DEFAULT_SIZE, 1115, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(centerPanel, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(bottomPanel, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
		);
		
		//if  button clicked, set the motor status to Completed
		JButton completedBtn = new JButton("Completed");
		completedBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int result= JOptionPane.showConfirmDialog(null,"Are you sure you want to mark it as 'Completed' motor?", "Confirm", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_OPTION) {
				//if  button clicked, set the motor status to Completed
				 app.database.updateMotorStatus(motorID, "Completed");
				 app.refreshMotorInspectionList();// refresh the JTable in the InspectionGUI
				 app.refreshCompletedMotorsList(); // update the JTable in terminatedMotor class
				 dispose();
				}
			}
		});
		
		//open motor panel if mouse clicked
		JButton openMotorBtn = new JButton("Open Motor");
		openMotorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				motorPanel=new MotorPanel(app, motorID);
				motorPanel.setVisible(true);
			}
		});
		GroupLayout gl_topPanel = new GroupLayout(topPanel);
		gl_topPanel.setHorizontalGroup(
			gl_topPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_topPanel.createSequentialGroup()
					.addComponent(completedBtn, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 989, Short.MAX_VALUE)
					.addComponent(openMotorBtn))
		);
		gl_topPanel.setVerticalGroup(
			gl_topPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topPanel.createSequentialGroup()
					.addGroup(gl_topPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(openMotorBtn)
						.addComponent(completedBtn))
					.addGap(4))
		);
		topPanel.setLayout(gl_topPanel);
		bottomPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel bottomInsidePanel = new JPanel();
		bottomPanel.add(bottomInsidePanel, BorderLayout.NORTH);
		
		//clear the text area
		JButton clearBtn = new JButton("Clear");
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");//clear text 
				textArea.setEditable(true);//enable editing
				addBtn.setEnabled(true);//enable adding new inspection
			}
		});
		bottomInsidePanel.add(clearBtn);
		
		/*** add new inspection ***/
		addBtn = new JButton("Add");
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newTxt= textArea.getText();//get the text
				LocalDate nowDate=LocalDate.now();//get the current date
				String date= nowDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // format the current date
				if(!(newTxt.equals(""))) {
					app.database.addInspection(motorID, app.getUserName(), newTxt,date, "");//add the inspection in the database
					textArea.setText("");//clear the message
					displayData(app.database.inspectionList()); // display all the data on the screen
				}else {
					JOptionPane.showMessageDialog(null, "Please enter your inspection result!", "IMPORTANT",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		bottomInsidePanel.add(addBtn);
		
	    textArea = new JTextArea();
		bottomPanel.add(textArea, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));
		
		//scroll pane text area
		JScrollPane spTextArea= new JScrollPane(textArea);
		spTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		spTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		bottomPanel.add(spTextArea);
		
		JScrollPane scrollPane = new JScrollPane();
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		//if the row is selected, display text in the textArea
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow= table.getSelectedRow();
				if(selectedRow>=0) {
					int inspectionID=(int)table.getValueAt(selectedRow, 1);
					for(Inspection ip:app.database.inspectionList()) {
						if(ip.getInspection_id()==inspectionID) {
							textArea.setText(ip.getInspection_result());
							textArea.setEditable(false);//don't allow any editing
							addBtn.setEnabled(false); // don't allow adding existing comment
						}	
					}
				}
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Date/Time", "Inspection ID", "Inspector ID", "Result"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(642);
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
		
		displayData(app.database.inspectionList()); // display all the data on the screen
	}
	
	//method created to display all data
		public void displayData(ArrayList<Inspection> inspList) {
			
			DefaultTableModel tableModel=(DefaultTableModel)table.getModel();
			tableModel.setRowCount(0);
			
			for(Inspection is:inspList) {
				if(motorID==is.getMotorID()) {
					tableModel.addRow(new Object[] {is.getInspection_date(), is.getInspection_id(), is.getInspector(), is.getInspection_result()});
				}
			}
		}
}
