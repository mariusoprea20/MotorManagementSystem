package Task7;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TaskNotes extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static final int columns=60;
	private static final int rows=15;
	private TaskAllocationApp launcher;
	protected JTextArea textArea;
	protected int taskID;

	// TaskNotes class is passed to TaskAllocationComponent class to retrieve the notes from the database
	public TaskNotes(TaskAllocationApp newLauncher) {
		launcher=newLauncher;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 658, 381);
		setTitle("Notes");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		
		JButton EditButton = new JButton("Edit");
		EditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setEditable(true);
			}
		});
		buttonPanel.add(EditButton);
		
		JButton CloseButton = new JButton("Close");
		CloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPanel.add(CloseButton);
		
		JPanel mainPanel = new JPanel();
		contentPane.add(mainPanel, BorderLayout.CENTER);
		 
		//passed to TaskAllocationDatabase
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String notes= textArea.getText();
				launcher.dbQuery.updateNotes(taskID,notes);
				
				textArea.setEditable(false);
			}
		});
		mainPanel.add(saveButton);
		
		textArea = new JTextArea(rows,columns);
		textArea.setEditable(false);
		mainPanel.add(textArea);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mainPanel.add(scrollPane);
		
		
	}

}
