package Task5;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MotorDesc extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static final int columns=60;
	private static final int rows=15;
	private JTextArea textArea;
	private JobProgressApp app;
	private int motorID;
	
    //pass the main class and the JTextArea from the MotorPanel to update it
	public MotorDesc(JobProgressApp app, int motorID, JTextArea txtArea) {
		
		this.app=app;
		this.motorID=motorID;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 658, 381);
		setTitle("Edit Description");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		
		JPanel mainPanel = new JPanel();
		contentPane.add(mainPanel, BorderLayout.CENTER);
		textArea = new JTextArea(rows,columns);
		textArea.setText(app.database.getDescription(motorID));
		textArea.setEditable(false);
		mainPanel.add(textArea);
		
		//enable the text area
		JButton EditButton = new JButton("Edit");
		EditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setEditable(true);
			}
		});
		buttonPanel.add(EditButton);
		
		//close the window
		JButton CloseButton = new JButton("Close");
		CloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPanel.add(CloseButton);
		
		 
		// save the new description
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String notes= textArea.getText(); // get the text from the textArea
                app.database.updateDescription(notes, motorID);// update it in the database
                txtArea.setText(notes);// set the textArea of MotorPanel class to the text retrieved above
				textArea.setEditable(false);// set this.textArea to false editable
			}
		});
		mainPanel.add(saveButton);
		
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mainPanel.add(scrollPane);
		
		
	}

}
