package Task7;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class DatePicker extends JFrame {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	protected TaskAllocationApp launcher;
	protected int taskID; // retrieve the selected task id from TaskAllocationComponent class
	private JDateChooser dateChooserStartDate;
	private JDateChooser dateChooserEndDate;

	public DatePicker(TaskAllocationApp newLauncher) {
	    launcher= newLauncher;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 428, 166);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		//ADD THE START DATE
		JButton btnSetStartDate = new JButton("Set Start Date");
		btnSetStartDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			// retrieve the startDate from the  JDateChooser and convert them into a string
             String startDate= ((JTextField)dateChooserStartDate.getDateEditor().getUiComponent()).getText();
           //if the text is empty, set the date to null
			 if(startDate.equals("")) {
				 launcher.setStartDate(taskID," ");
			 }else {
	             try {
	            	 // make sure the users insert only dates
	            	 new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
	            	 
	            	 // add the dates in the database and refresh the main window table
	            	 launcher.setStartDate(taskID, startDate);
	            	 
	             }catch(ParseException ex) {
	            	 //display a window dialog if the users enter random numbers or letters
	            	JOptionPane.showMessageDialog(null, "Please entery only dates!", "WARNING",JOptionPane.PLAIN_MESSAGE);
	             }
			   }
			}
		});
		panel.add(btnSetStartDate);
		
		//ADD THE END DATE
		JButton btnSetEndDate = new JButton("Set End Date");
		btnSetEndDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// retrieve the endDate from the  JDateChooser and convert them into a string
				 String endDate= ((JTextField)dateChooserEndDate.getDateEditor().getUiComponent()).getText();
				 //if the text is empty, set the date to null
				 if(endDate.equals("")) {
					 launcher.setEndDate(taskID," ");
				 }else {
		            try {
		            	 // make sure the users insert only dates
		            	 new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
		            	 
		            	 // add the dates in the database and refresh the main window table
		            	 launcher.setEndDate(taskID, endDate);
		            	 
		             }catch(ParseException ex) {
		            	 //display a window dialog if the users enter random numbers or letters
		            	JOptionPane.showMessageDialog(null, "Please entery only dates!", "WARNING",JOptionPane.PLAIN_MESSAGE);
		             }
				 }
			}
		});
		panel.add(btnSetEndDate);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblStartDagte = new JLabel("Start Date");
		panel_1.add(lblStartDagte);
		
	    dateChooserStartDate = new JDateChooser();
		dateChooserStartDate.setDateFormatString("dd/MM/yyyy");
		panel_1.add(dateChooserStartDate);
		
		JLabel lblEndDate = new JLabel("End Date");
		panel_1.add(lblEndDate);
		
	    dateChooserEndDate = new JDateChooser();
		dateChooserEndDate.setDateFormatString("dd/MM/yyyy");
		panel_1.add(dateChooserEndDate);
		
		
	}
}
