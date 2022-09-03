package Task5;

import java.awt.BorderLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.toedter.calendar.JDateChooser;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class AddTaskGUI extends JFrame {

	private JPanel contentPane;
	private JTextField taskTypeFld;
	private JComboBox statusComboBox;
	private JDateChooser endDateChooser;
	private JDateChooser deadlineDateChooser;
	private JDateChooser startDateChooser;
	private JTextArea descTextArea;
	private JTextArea notesTextArea;
	private JobProgressApp app;
	private int motorID;
	
	
    
	
	
	public AddTaskGUI(JobProgressApp app, int motorID, TaskGUI taskTable) {
		this.app=app;
		this.motorID=motorID;
		
		setTitle("Add Task");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1157, 731);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel southPanel = new JPanel();
		southPanel.setBackground(SystemColor.activeCaption);
		contentPane.add(southPanel, BorderLayout.SOUTH);
		
		JButton addBtn = new JButton("Add");
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String taskType= taskTypeFld.getText();
				String taskStatus= statusComboBox.getSelectedItem().toString();
				String startDate= ((JTextField)startDateChooser.getDateEditor().getUiComponent()).getText();
				String endDate= ((JTextField)endDateChooser.getDateEditor().getUiComponent()).getText();
				String deadline=((JTextField)deadlineDateChooser.getDateEditor().getUiComponent()).getText();
				String desc= descTextArea.getText();
				String notes= notesTextArea.getText();
				
				
				if(taskType.equals("") || desc.equals("")) {
					JOptionPane.showMessageDialog(null,"Please complete the fields!", "Warning", JOptionPane.WARNING_MESSAGE);
				} else {
				
				try {
					if(startDate.equals("")&&endDate.equals("")) {
						startDate="";
						endDate="";
					}
					else if(startDate.equals("")) {
						startDate="";
						new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
					}
					else if(endDate.equals("")) {
						endDate="";
						new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
					}
					else {
						new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
						new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
					}

					new SimpleDateFormat("dd/MM/yyyy").parse(deadline);
					LocalDate now= LocalDate.now();
					LocalDate tskDeadline=LocalDate.parse(deadline, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					long days= ChronoUnit.DAYS.between(now, tskDeadline);
					//add new task and refresh the JTable in TaskGUI class
					app.database.addNewTask(taskType, taskStatus, startDate, endDate, deadline, desc, notes, motorID,(int)days );
					taskTable.displayTableData(app.database.getAllTasks());
					
					//close the page after the task is added in the JTable
					dispose();
				}catch(ParseException p) {
					JOptionPane.showMessageDialog(null, "Please enter only dates were required!", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			 }
			}
		});
		southPanel.add(addBtn);
		
		//close window
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		southPanel.add(closeBtn);
		
		JPanel mainPanel = new JPanel();
		contentPane.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(SystemColor.activeCaption);
		mainPanel.add(topPanel, BorderLayout.NORTH);
		
		JLabel startDatelbl = new JLabel("Start Date");
		
		startDateChooser = new JDateChooser();
		startDateChooser.setDateFormatString("dd/MM/yyyy");
		
		JLabel endDatelbl = new JLabel("End Date");
		
		JLabel deadlinelbl = new JLabel("Deadline* ");
		
	    endDateChooser = new JDateChooser();
		endDateChooser.setDateFormatString("dd/MM/yyyy");
		
		deadlineDateChooser = new JDateChooser();
		deadlineDateChooser.setDateFormatString("dd/MM/yyyy");
		GroupLayout gl_topPanel = new GroupLayout(topPanel);
		gl_topPanel.setHorizontalGroup(
			gl_topPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topPanel.createSequentialGroup()
					.addGap(132)
					.addComponent(startDatelbl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(startDateChooser, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
					.addGap(47)
					.addComponent(endDatelbl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(endDateChooser, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
					.addGap(55)
					.addComponent(deadlinelbl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(deadlineDateChooser, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
					.addGap(219))
		);
		gl_topPanel.setVerticalGroup(
			gl_topPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topPanel.createSequentialGroup()
					.addGroup(gl_topPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_topPanel.createSequentialGroup()
							.addGap(5)
							.addGroup(gl_topPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(endDatelbl)
								.addComponent(startDatelbl)
								.addGroup(Alignment.TRAILING, gl_topPanel.createSequentialGroup()
									.addGroup(gl_topPanel.createParallelGroup(Alignment.TRAILING)
										.addComponent(startDateChooser, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
										.addComponent(endDateChooser, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addGap(11)))
							.addGap(3))
						.addGroup(gl_topPanel.createSequentialGroup()
							.addGap(8)
							.addGroup(gl_topPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(deadlineDateChooser, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(deadlinelbl))
							.addGap(11)))
					.addGap(0))
		);
		topPanel.setLayout(gl_topPanel);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(SystemColor.inactiveCaption);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		JLabel descriptionLbl = new JLabel("Description");
		
		JLabel taskTypeLbl = new JLabel("Task Type");
		
		taskTypeFld = new JTextField();
		taskTypeFld.setColumns(10);
		
		JLabel statusLbl = new JLabel("Task Status");
		
		//status combo box
	    statusComboBox = new JComboBox();
	    statusComboBox.setModel(new DefaultComboBoxModel(new String[] {"New", "In Progress", "Normal Priority", "High Priority", "Completed"}));
		
		JLabel notesLbl = new JLabel("Notes");
		
		JScrollPane scrollPaneDesc = new JScrollPane();
		
		JScrollPane scrollPaneNotes = new JScrollPane();
		
		GroupLayout gl_centerPanel = new GroupLayout(centerPanel);
		gl_centerPanel.setHorizontalGroup(
			gl_centerPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_centerPanel.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_centerPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_centerPanel.createSequentialGroup()
							.addGroup(gl_centerPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(taskTypeLbl, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
								.addComponent(statusLbl))
							.addGap(18))
						.addGroup(gl_centerPanel.createSequentialGroup()
							.addComponent(descriptionLbl, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)))
					.addGroup(gl_centerPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_centerPanel.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(taskTypeFld, Alignment.LEADING)
							.addComponent(statusComboBox, Alignment.LEADING, 0, 534, Short.MAX_VALUE))
						.addComponent(scrollPaneDesc, GroupLayout.DEFAULT_SIZE, 1013, Short.MAX_VALUE))
					.addContainerGap())
				.addGroup(Alignment.LEADING, gl_centerPanel.createSequentialGroup()
					.addGap(255)
					.addComponent(scrollPaneNotes, GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
					.addGap(173))
				.addGroup(gl_centerPanel.createSequentialGroup()
					.addGap(594)
					.addComponent(notesLbl, GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
					.addGap(342))
		);
		gl_centerPanel.setVerticalGroup(
			gl_centerPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_centerPanel.createSequentialGroup()
					.addGap(53)
					.addGroup(gl_centerPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(taskTypeLbl)
						.addComponent(taskTypeFld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(38)
					.addGroup(gl_centerPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(statusLbl)
						.addComponent(statusComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(51)
					.addComponent(notesLbl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPaneNotes, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
					.addGap(56)
					.addGroup(gl_centerPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(descriptionLbl)
						.addComponent(scrollPaneDesc, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)))
		);
		
		//notes text area
		notesTextArea = new JTextArea();
		scrollPaneNotes.setViewportView(notesTextArea);
		
		//desc text area
		descTextArea = new JTextArea();
		scrollPaneDesc.setViewportView(descTextArea);
		centerPanel.setLayout(gl_centerPanel);
	}
}
