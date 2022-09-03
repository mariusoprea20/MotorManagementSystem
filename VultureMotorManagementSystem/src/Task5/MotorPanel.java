package Task5;

import java.awt.BorderLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.awt.SystemColor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class MotorPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField motorNameFld;
	private JTextField manufacturerFld;
	private JTextField clientFld;
	private JTextField motorFaultFld;
	private JTextField replacementPartsFld;
	private JDateChooser arrivingDateChooser;
	private JDateChooser startDateChooser;
	private JDateChooser deadlineDateChooser;
	private JDateChooser endDateChooser;
	private JList<String> replacementPartsList;
	private JComboBox comboBoxStatus;
	private JComboBox delayComboBox;
	private MotorNotes motorNotes;
	protected DefaultListModel<String> listModel;
	protected JTextArea descTextArea;
    protected JobProgressApp app;
	protected int motorID; //used to retrieve the slected motor id from MotorListGUI 
	

	/**
	 * Create the frame.
	 */
	public MotorPanel(JobProgressApp app, int motor_ID) {
		//display the existing data from database
		this.app=app;
		motorID=motor_ID;
		setTitle("Motor Panel");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1332, 673);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel sidePanel = new JPanel();
		sidePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		sidePanel.setBackground(SystemColor.activeCaption);
		sidePanel.setForeground(SystemColor.inactiveCaption);
		contentPane.add(sidePanel, BorderLayout.WEST);
		sidePanel.setLayout(new GridLayout(15, 1, 0, 10));
		
		/*** inspect the motor ***/
		JButton inspectionBtn = new JButton("Inspect");
		inspectionBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//get all the text from the text fields
				String motorName=motorNameFld.getText();
				String manufacturer=manufacturerFld.getText();
				String client=clientFld.getText();
                String motorFault=motorFaultFld.getText();
                String description=descTextArea.getText();
                String arrivingDate=((JTextField)arrivingDateChooser.getDateEditor().getUiComponent()).getText();
                String deadline=((JTextField)deadlineDateChooser.getDateEditor().getUiComponent()).getText();
                String startDate=((JTextField)startDateChooser.getDateEditor().getUiComponent()).getText();
                String endDate=((JTextField)endDateChooser.getDateEditor().getUiComponent()).getText();
                
                //make sure all text input fields are not empty
                if(motorName.equals("") || manufacturer.equals("") || client.equals("") || motorFault.equals("") 
                   || description.equals("") || arrivingDate.equals("") || deadline.equals("") || startDate.equals("") 
                   || endDate.equals("")) {
                	JOptionPane.showMessageDialog(null, "Unable of inspection, all fields require completion!", "Warning", JOptionPane.CANCEL_OPTION);
                }
                else {
                	
                	//make sure there are only dates entered
                	try {
	                	new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
	                	new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
	                	new SimpleDateFormat("dd/MM/yyyy").parse(arrivingDate);
	                	new SimpleDateFormat("dd/MM/yyyy").parse(deadline);
	                	
	                	//update the motor before inspection to make sure everything is filled
						app.database.updateMotorBeforeInspection(motor_ID, motorName, manufacturer, client, motorFault, description, arrivingDate, deadline, startDate, endDate);

	                	
						JOptionPane.showMessageDialog(null,"Inspection pending!","Confirmation",JOptionPane.PLAIN_MESSAGE); //confirm update
						comboBoxStatus.setSelectedItem("Inspection");
						/*** store the current time in the database to show the time of when the motor was sent to inspection***/
						LocalDate nowDate= LocalDate.now();
						String inspStartDate= nowDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
						//add new inspection the moment the inspector clicks on Inspection button
						app.database.addInspection(motor_ID, app.getUserName(), "Inspection in progress..", inspStartDate, "");
						/******************************************/
						app.database.updateMotorStatus(motor_ID, "Inspection");//update the motor status once the inspection button is clicked
						app.displayAllMotors();// update the info on the MotorListGUI page
						app.displayTerminatedMotors();//update the info on the TerminatedMotors
						app.refreshMotorInspectionList();// update the JTable in InspectionGUI class
						
	                }catch(ParseException ex) {
		                JOptionPane.showMessageDialog(null, "Please enter only dates!", "Warning", JOptionPane.WARNING_MESSAGE);
	                }
                }
			}
		});
		sidePanel.add(inspectionBtn);
		
		// open the task list of the motor
		JButton tasksBtn = new JButton("Tasks");
		tasksBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TaskGUI taskList= new TaskGUI(app, motorID);
				taskList.setVisible(true);
			}
		});
		sidePanel.add(tasksBtn);
		
		//open motor notes
		JButton notesBtn = new JButton("Notes");
		notesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				motorNotes= new MotorNotes(app, motorID);
				motorNotes.setVisible(true);
			}
		});
		sidePanel.add(notesBtn);
		
		//edit motor description
		JButton editDescBtn = new JButton("Edit Desc");
		editDescBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MotorDesc motorDesc= new MotorDesc(app,motorID,descTextArea);
				motorDesc.setVisible(true);
			}
		});
		sidePanel.add(editDescBtn);
		
		JLabel lbl1 = new JLabel("");
		lbl1.setEnabled(false);
		lbl1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
		lbl1.setBackground(Color.BLACK);
		lbl1.setForeground(SystemColor.info);
		sidePanel.add(lbl1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setEnabled(false);
		sidePanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setEnabled(false);
		sidePanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setEnabled(false);
		sidePanel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setEnabled(false);
		sidePanel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setEnabled(false);
		sidePanel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setEnabled(false);
		sidePanel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.setEnabled(false);
		sidePanel.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("");
		lblNewLabel_9.setEnabled(false);
		sidePanel.add(lblNewLabel_9);
		
		/*** the update button used to update all the info about the motor in the database ***/
		JButton updateBtn = new JButton("Update");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String motorName=motorNameFld.getText();
				String manufacturer=manufacturerFld.getText();
				String client=clientFld.getText();
                String motorFault=motorFaultFld.getText();
                String motorStatus=comboBoxStatus.getSelectedItem().toString();
                String motorDelay=delayComboBox.getSelectedItem().toString();
                
                /***iterate over the replacement parts list and make them a single string that can be updated in the database***/
                String existingReplacementPartsList="";
                for(int i=0;i<replacementPartsList.getModel().getSize();i++) {
                     existingReplacementPartsList+=replacementPartsList.getModel().getElementAt(i);     	
                }
                app.database.replacementPartsUpdate(motorID, existingReplacementPartsList.trim());
                /***replacement parts updated***/
                
                String description=descTextArea.getText();
                String arrivingDate=((JTextField)arrivingDateChooser.getDateEditor().getUiComponent()).getText();
                String deadline=((JTextField)deadlineDateChooser.getDateEditor().getUiComponent()).getText();
                String startDate=((JTextField)startDateChooser.getDateEditor().getUiComponent()).getText();
                String endDate=((JTextField)endDateChooser.getDateEditor().getUiComponent()).getText();
                
                
                //make sure all text input fields are not empty
                if(motorName.equals("") || manufacturer.equals("") || client.equals("") || motorFault.equals("") || description.equals("")) {
                	JOptionPane.showMessageDialog(null, "Unable of update, all fields require completion!", "Warning", JOptionPane.CANCEL_OPTION);
                }
                else {
                //make sure that the user enters only dates
	                try {
	                	// if the DATE fields are empty, make them "" string as they can get updated later
	                	if(endDate.equals("") && startDate.equals("")) {
	                		startDate="";
	                		endDate="";
	                	}else if(startDate.equals("")) {
	                		startDate="";
	                		new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
	                		
	                	}else if(endDate.equals("")) {
	                		endDate="";
	                		new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
	                		
	                	}else {
		                	new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
		                	new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
	                	}
	                	new SimpleDateFormat("dd/MM/yyyy").parse(arrivingDate);
	                	new SimpleDateFormat("dd/MM/yyyy").parse(deadline);
	                	
						app.database.motorUpdate(motor_ID, motorName, manufacturer, client, motorFault, motorStatus, motorDelay, description, arrivingDate, deadline, startDate, endDate);
						JOptionPane.showMessageDialog(null,"Motor updated!","Confirmation",JOptionPane.PLAIN_MESSAGE); //confirm update
						app.displayAllMotors();// update the info on the MotorListGUI page
						app.displayTerminatedMotors();//update the info on the TerminatedMotors
						app.refreshMotorInspectionList(); // update the info in the InspectionGUI
	                	
	                }catch(ParseException ex) {
		                JOptionPane.showMessageDialog(null, "Please enter only dates!", "Warning", JOptionPane.WARNING_MESSAGE);
	                }	
				}
			}
		});
		sidePanel.add(updateBtn);
		
		//dispose the window when clicking on close button
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		sidePanel.add(closeBtn);
		
		JPanel mainPanel = new JPanel();
		contentPane.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel southPanel = new JPanel();
		southPanel.setBackground(SystemColor.inactiveCaption);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
		
		JLabel descriptionLbl = new JLabel("Description");
		descriptionLbl.setVerticalAlignment(SwingConstants.TOP);
		descriptionLbl.setPreferredSize(new Dimension(100,150));
		descriptionLbl.setHorizontalAlignment(SwingConstants.CENTER);
		southPanel.add(descriptionLbl);
		
		descTextArea = new JTextArea();
		descTextArea.setEditable(false);
		descTextArea.setRows(5);
		southPanel.add(descTextArea);
		
		JScrollPane scrollPane= new JScrollPane(descTextArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		southPanel.add(scrollPane);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(SystemColor.inactiveCaption);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		JLabel motorNamelbl = new JLabel("Motor Name");
		motorNamelbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		motorNameFld = new JTextField();
		motorNameFld.setColumns(10);
		
		JLabel manufacturerlbl = new JLabel("Manufacturer");
		manufacturerlbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel clientlbl = new JLabel("Client");
		clientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel motorFaultlbl = new JLabel("Motor Fault");
		motorFaultlbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel delaylbl = new JLabel("Delay");
		delaylbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel statuslbl = new JLabel("Motor Status");
		statuslbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		manufacturerFld = new JTextField();
		manufacturerFld.setColumns(10);
		
		clientFld = new JTextField();
		clientFld.setColumns(10);
		
		motorFaultFld = new JTextField();
		motorFaultFld.setColumns(10);
		
		comboBoxStatus = new JComboBox();
		comboBoxStatus.setModel(new DefaultComboBoxModel(new String[] {"New", "In Progress", "Ready", "Inspection", "Completed", "Further Work", "Suspended"}));
		
		JLabel replacementPartslbl = new JLabel("Replacement Parts");
		replacementPartslbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		listModel= new DefaultListModel();
	    replacementPartsList = new JList(listModel);
	
		replacementPartsFld = new JTextField();
		replacementPartsFld.setColumns(10);
		
		
		/***button to update the replacement parts list***/
		JButton addPartsListBtn = new JButton("Add Item");
		addPartsListBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(replacementPartsFld.getText().isEmpty()) {
					//display message if the field is empty and the user tries to add a new element
					JOptionPane.showMessageDialog(null,"Empty field. Could not add new element!", "Message", JOptionPane.ERROR_MESSAGE);
				}else {
					String newItem="+"+replacementPartsFld.getText();
					listModel.addElement(newItem); // add the new element in the jlist
				    replacementPartsFld.setText(null);//set the adding replacement parts field to null
				}
			}
		});
		
		//delete replacement items from the jlist
		JButton deleteRepPartsBtn = new JButton("Delete");
		deleteRepPartsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedItem=replacementPartsList.getSelectedIndex();
				if(selectedItem>=0) {
					int result= JOptionPane.showConfirmDialog(null,"Delete this item?","Warning Message", JOptionPane.YES_NO_OPTION);
					if(result==JOptionPane.YES_OPTION) {
				       listModel.remove(selectedItem);
					}
				}
			}
		});
		
		delayComboBox = new JComboBox();
		delayComboBox.setModel(new DefaultComboBoxModel(new String[] {"No Delay", "Delayed", "Suspended" }));
		
		JScrollPane repPartsScrollPane = new JScrollPane();
		repPartsScrollPane.setViewportView(replacementPartsList);
		
		JLabel displayMotorID = new JLabel("Motor ID: "+motorID);
		displayMotorID.setForeground(new Color(34, 139, 34));
		displayMotorID.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
		displayMotorID.setBackground(Color.BLACK);
		
		GroupLayout gl_centerPanel = new GroupLayout(centerPanel);
		gl_centerPanel.setHorizontalGroup(
			gl_centerPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_centerPanel.createSequentialGroup()
					.addGap(0)
					.addGroup(gl_centerPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_centerPanel.createSequentialGroup()
							.addGap(128)
							.addGroup(gl_centerPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(motorNamelbl, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
								.addComponent(clientlbl, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
								.addComponent(delaylbl, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE))
							.addGap(10)
							.addGroup(gl_centerPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(clientFld, GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
								.addComponent(delayComboBox, 0, 313, Short.MAX_VALUE)
								.addComponent(motorNameFld, GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE))
							.addGap(10)
							.addGroup(gl_centerPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(statuslbl, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
								.addComponent(manufacturerlbl, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
								.addComponent(motorFaultlbl, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE))
							.addGap(10)
							.addGroup(gl_centerPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(motorFaultFld, GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
								.addComponent(comboBoxStatus, 0, 324, Short.MAX_VALUE)
								.addComponent(manufacturerFld, GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)))
						.addGroup(gl_centerPanel.createSequentialGroup()
							.addGap(568)
							.addComponent(addPartsListBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(deleteRepPartsBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(331))
						.addGroup(gl_centerPanel.createSequentialGroup()
							.addGroup(gl_centerPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(repPartsScrollPane, GroupLayout.PREFERRED_SIZE, 549, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_centerPanel.createSequentialGroup()
									.addGap(493)
									.addComponent(replacementPartslbl, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(replacementPartsFld, GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)))
							.addGap(157)))
					.addGap(146))
				.addGroup(Alignment.TRAILING, gl_centerPanel.createSequentialGroup()
					.addContainerGap(1102, Short.MAX_VALUE)
					.addComponent(displayMotorID, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_centerPanel.setVerticalGroup(
			gl_centerPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_centerPanel.createSequentialGroup()
					.addComponent(displayMotorID, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addGroup(gl_centerPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(motorNamelbl, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_centerPanel.createSequentialGroup()
							.addGap(43)
							.addComponent(clientlbl, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_centerPanel.createSequentialGroup()
							.addGap(85)
							.addComponent(delaylbl, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_centerPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(motorNameFld, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(clientFld, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(delayComboBox, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_centerPanel.createSequentialGroup()
							.addGap(85)
							.addComponent(statuslbl, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
						.addComponent(manufacturerlbl, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_centerPanel.createSequentialGroup()
							.addGap(43)
							.addComponent(motorFaultlbl, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_centerPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(manufacturerFld, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(motorFaultFld, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(comboBoxStatus, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_centerPanel.createSequentialGroup()
							.addGap(156)
							.addGroup(gl_centerPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(replacementPartsFld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(replacementPartslbl))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(repPartsScrollPane, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_centerPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(addPartsListBtn)
						.addComponent(deleteRepPartsBtn))
					.addGap(9))
		);
		centerPanel.setLayout(gl_centerPanel);
		
		
		JPanel northPanel = new JPanel();
		northPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		northPanel.setBackground(SystemColor.activeCaption);
		mainPanel.add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new GridLayout(0, 4, 20, 5));
		
		JLabel arrivingDatelbl = new JLabel("Arriving Date");
		arrivingDatelbl.setHorizontalAlignment(SwingConstants.CENTER);
		northPanel.add(arrivingDatelbl);
		
	    arrivingDateChooser = new JDateChooser();
		arrivingDateChooser.setDateFormatString("dd/MM/yyyy");
		northPanel.add(arrivingDateChooser);
		
		JLabel startDatelbl = new JLabel("Start Date");
		startDatelbl.setHorizontalAlignment(SwingConstants.CENTER);
		northPanel.add(startDatelbl);
		
		startDateChooser = new JDateChooser();
		startDateChooser.setDateFormatString("dd/MM/yyyy");
		northPanel.add(startDateChooser);
		
		JLabel deadlinelbl = new JLabel("Deadline");
		deadlinelbl.setHorizontalAlignment(SwingConstants.CENTER);
		northPanel.add(deadlinelbl);
		
		deadlineDateChooser = new JDateChooser();
		deadlineDateChooser.setDateFormatString("dd/MM/yyyy");
		northPanel.add(deadlineDateChooser);
		
		JLabel endDatelbl = new JLabel("End Date");
		endDatelbl.setHorizontalAlignment(SwingConstants.CENTER);
		northPanel.add(endDatelbl);
		
		endDateChooser = new JDateChooser();
		endDateChooser.setDateFormatString("dd/MM/yyyy");
		northPanel.add(endDateChooser);
		
		loadExistingData(app.database.listOfMotors());

	}
	//method to display the existing data
	public void loadExistingData(ArrayList<Motor>list) {
		
		for(Motor m:list) {
			if(motorID==m.getMotorID()) {
				
				motorNameFld.setText(m.getMotorName());
				manufacturerFld.setText(m.getMotorManufacturer());
				clientFld.setText(m.getClient());
				motorFaultFld.setText(m.getMotor_fault());
				
				((JTextField)arrivingDateChooser.getDateEditor().getUiComponent()).setText(m.getArrivalDate());
				((JTextField)startDateChooser.getDateEditor().getUiComponent()).setText(m.getStartDate());
				((JTextField)deadlineDateChooser.getDateEditor().getUiComponent()).setText(m.getDeadline());
				((JTextField)endDateChooser.getDateEditor().getUiComponent()).setText(m.getEndDate());
				
				//((JTextField)comboBoxStatus.getEditor().getEditorComponent()).setText(m.getMotorStatus());
				//((JTextField)delayComboBox.getEditor().getEditorComponent()).setText(m.getMotorStatus());
				
				//"New", "In Progress", "Ready", "Inspection", "Completed", "Further Work", "Suspended"
				
				/*** pre-selected motor status ***/
				switch(m.getMotorStatus()) {
				case "New":
					comboBoxStatus.setSelectedIndex(0);
					break;
				case "In Progress":
					comboBoxStatus.setSelectedIndex(1);
					break;
				case "Ready":
					comboBoxStatus.setSelectedIndex(2);
					break;
				case "Inspection":
					comboBoxStatus.setSelectedIndex(3);
					break;
				case "Completed":
					comboBoxStatus.setSelectedIndex(4);
				    break;
				case "Further Work":
					comboBoxStatus.setSelectedIndex(5);
					break;
				case "Suspended":
					comboBoxStatus.setSelectedIndex(6);
					break;
				default:
					comboBoxStatus.setSelectedIndex(0);
				}
					
					/*** pre-selected delay status ***/
						if(m.getDelay().equals("No Delay")) {
							 delayComboBox.setSelectedIndex(0);
						}
						else if(m.getDelay().equals("Delayed")) {
						    delayComboBox.setSelectedIndex(1);
						}else if(m.getDelay().equals("Suspended")) {
							delayComboBox.setSelectedIndex(2);
						}
						
						/*** Retrieve the replacement parts string from 
						    the database, split the string using the delimiter +,
						     re-add them in the JList***/
						String myString=m.getReplacementParts();//get the replacement parts
						Scanner sc= new Scanner(myString).useDelimiter("[+]");//use a delimiter "+" to separate the string retrieved from db
                        while(sc.hasNext()) { //while the string is not null
                           listModel.addElement("+"+sc.next());//add elements
                         }
                        replacementPartsList.setModel(listModel);//re-set the model
				        descTextArea.setText(m.getDescription());
                   }
				
			}
		}
	}

