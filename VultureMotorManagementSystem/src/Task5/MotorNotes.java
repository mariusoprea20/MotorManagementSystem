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

public class MotorNotes extends JFrame {

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
	

	public MotorNotes(JobProgressApp app, int motorID) {
		
		this.app=app;
		this.motorID=motorID;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 658, 381);
		setTitle("Edit Notes");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		
		JPanel mainPanel = new JPanel();
		contentPane.add(mainPanel, BorderLayout.CENTER);
		textArea = new JTextArea(rows,columns);
		//set the textArea to the notes held in database if any
		for(Motor m:app.database.listOfMotors()) {
			if(m.getMotorID()==motorID) {
				textArea.setText(m.getNotes());
			}	
		}
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
				String notes= textArea.getText();
                app.database.updateNotes(notes, motorID);
				textArea.setEditable(false);
			}
		});
		mainPanel.add(saveButton);
		
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mainPanel.add(scrollPane);
		
		
	}

}