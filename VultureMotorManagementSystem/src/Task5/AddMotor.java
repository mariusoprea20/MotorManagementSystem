package Task5;

import java.awt.BorderLayout;


import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.awt.event.ActionEvent;

public class AddMotor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField motorNameFld;
	private JTextField manufacturerFld;
	private JTextField clientFld;
	private JTextField motorFaultTxt;
	private JTextField motorDescTxt;
	private JTextField replacementPartsTxt;
	private JDateChooser arrivingDateChooser;
	private JDateChooser deadlineDateChooser;
	private JobProgressApp jpApp;

	
	/**
	 * Create the frame.
	 */
	public AddMotor(JobProgressApp app) {
		
		this.jpApp=app;
		setTitle("Add Motor");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1046, 648);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel southPanel = new JPanel();
		contentPane.add(southPanel, BorderLayout.SOUTH);
		
		JButton addMotorBtn = new JButton("Add Motor");
		addMotorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//retrieve all text input from the user
				String motorName=motorNameFld.getText();
				String manufacturer=manufacturerFld.getText();
				String client=clientFld.getText();
				String motorFault=motorFaultTxt.getText();
				String desc=motorDescTxt.getText();
                String arrivalDate=((JTextField)arrivingDateChooser.getDateEditor().getUiComponent()).getText();
                String replacementPart=replacementPartsTxt.getText();
                String deadline=((JTextField)deadlineDateChooser.getDateEditor().getUiComponent()).getText();
                String status="New"; //set status to New always
                String delayStatus="No Delay";
                
                
                //if fields are empty, display warning message
                if(motorName.isEmpty() || manufacturer.isEmpty()||client.isEmpty()||motorFault.isEmpty()||desc.isEmpty()||arrivalDate.isEmpty()||deadline.isEmpty()) {
                	JOptionPane.showMessageDialog(null, "Please complete all the fields!", "Warning",  JOptionPane.WARNING_MESSAGE);
                }
                else {
                	try {
                		//check if the user enters dates for the right input
                	    new SimpleDateFormat("dd/MM/yyyy").parse(arrivalDate);
                	    new SimpleDateFormat("dd/MM/yyyy").parse(deadline);
                	    //add a new motor
                	jpApp.addMotor(motorName, manufacturer, client, desc, motorFault, deadline, arrivalDate,replacementPart,status, delayStatus);
                	dispose();
                	}catch(ParseException ex) {
                		//display error message
                		JOptionPane.showMessageDialog(null,"Please enter only dates for arrival and deadline!", "Warning", JOptionPane.PLAIN_MESSAGE);
                	}
                }
			}
		});
		southPanel.add(addMotorBtn);
		
		/*** cancel window ***/
		JButton cancelMotorBtn = new JButton("Cancel");
		cancelMotorBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
				
		});
		southPanel.add(cancelMotorBtn);
		
		JPanel mainPanel = new JPanel();
		contentPane.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel motorNameLbl = new JLabel("Motor Name");
		motorNameLbl.setSize(new Dimension(100,100));
		mainPanel.add(motorNameLbl);
		
		motorNameFld = new JTextField();
		mainPanel.add(motorNameFld);
		motorNameFld.setColumns(10);
		
		JLabel manufacturerLbl = new JLabel("Manufacturer");
		mainPanel.add(manufacturerLbl);
		
		manufacturerFld = new JTextField();
		mainPanel.add(manufacturerFld);
		manufacturerFld.setColumns(10);
		
		JLabel clientLbl = new JLabel("Client");
		mainPanel.add(clientLbl);
		
		clientFld = new JTextField();
		mainPanel.add(clientFld);
		clientFld.setColumns(10);
		
		JLabel motorFaultLbl = new JLabel("Motor Fault");
		mainPanel.add(motorFaultLbl);
		
		motorFaultTxt = new JTextField();
		mainPanel.add(motorFaultTxt);
		motorFaultTxt.setColumns(10);
		
		JLabel motorDescriptionLbl = new JLabel("Motor Description");
		mainPanel.add(motorDescriptionLbl);
		
		motorDescTxt = new JTextField();
		mainPanel.add(motorDescTxt);
		motorDescTxt.setColumns(10);
		
		JLabel arrivingDateLbl = new JLabel("Arriving Date");
		mainPanel.add(arrivingDateLbl);
		
	    arrivingDateChooser = new JDateChooser();
		arrivingDateChooser.setDateFormatString("dd/MM/yyyy");
		arrivingDateChooser.getCalendarButton().setFont(new Font("Times New Roman", Font.PLAIN, 14));
		mainPanel.add(arrivingDateChooser);
		
		
		JLabel replacementPartsLbl = new JLabel("Replacement Parts");
		mainPanel.add(replacementPartsLbl);
		
		replacementPartsTxt = new JTextField();
		mainPanel.add(replacementPartsTxt);
		replacementPartsTxt.setColumns(10);
		
		JLabel deadlineLbl = new JLabel("Deadline");
		mainPanel.add(deadlineLbl);
		
	    deadlineDateChooser = new JDateChooser();
		deadlineDateChooser.setDateFormatString("dd/MM/yyyy");
		mainPanel.add(deadlineDateChooser);
	}

}
