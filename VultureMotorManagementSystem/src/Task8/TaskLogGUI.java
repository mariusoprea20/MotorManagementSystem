package Task8;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class TaskLogGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textFieldSearchTask;
	private LogInLauncher launcher;
	public TaskLogGUI(LogInLauncher newLauncher) {
		launcher=newLauncher;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 905, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		
		JButton btnCloseButton = new JButton("Close");
		btnCloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPanel.add(btnCloseButton);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Date/Time", "Task ID", "User", "Task Log"
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
		table.getColumnModel().getColumn(0).setPreferredWidth(121);
		table.getColumnModel().getColumn(1).setPreferredWidth(46);
		table.getColumnModel().getColumn(2).setPreferredWidth(35);
		table.getColumnModel().getColumn(3).setPreferredWidth(484);
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblSearchTask = new JLabel("Search Task");
		panel.add(lblSearchTask);
		
		textFieldSearchTask = new JTextField();
		textFieldSearchTask.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
            //TODO: when user types in the textField, filter the data
				DefaultTableModel filteredTask=(DefaultTableModel)table.getModel();
				filteredTask.setRowCount(0);
				
				String txtSearch=textFieldSearchTask.getText();//get the input text
				for(TaskLogClass log: launcher.dbQuery.taskLogList()) {
					//convert the existing (log) task_id into text
					String taskID=Integer.toString(log.getTask_id());
					
					//if the task_id contains the text, display the log where the task_id matches
					if(!(txtSearch.equals("")) && taskID.contains(txtSearch)) {
						filteredTask.addRow(new Object[] {log.getLog_dateTime(),log.getTask_id(), log.getUser_id(), log.getLog_desc()});
					}
					//else re-display all logs
					else if(txtSearch.equals("") && taskID.contains(txtSearch)) {
						filteredTask.addRow(new Object[] {log.getLog_dateTime(),log.getTask_id(), log.getUser_id(), log.getLog_desc()});
					}
				}
			}
		});
		panel.add(textFieldSearchTask);
		textFieldSearchTask.setColumns(10);
		this.displayTaskLogs(launcher.dbQuery.taskLogList());
	}
	
	//Method to display the logs
	private void displayTaskLogs(ArrayList<TaskLogClass> logList) {
		DefaultTableModel listOfLogs=(DefaultTableModel) table.getModel();
		listOfLogs.setRowCount(0);

		for(TaskLogClass log:logList) {

			listOfLogs.addRow(new Object[] {log.getLog_dateTime(),log.getTask_id(), log.getUser_id(), log.getLog_desc()});
		}

	}

}
