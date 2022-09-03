package Task5;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Task7.Task;

public class TaskGUI extends JFrame {

	private JPanel contentPane;
	protected JTable table;
	private int motorID;
	private JobProgressApp app;
	private AddTaskGUI addTask;
	private EditTaskGUI editTask;
	private int taskID;
	private AssignTechDialog techDialog;

	public TaskGUI(JobProgressApp app, int motorID) {
		setTitle("List of Tasks - Motor ID: "+motorID);
		
		this.motorID=motorID;
		this.app=app;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1193, 739);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel southPanel = new JPanel();
		contentPane.add(southPanel, BorderLayout.SOUTH);
		
		/*** method to assign a technician to a task ***/
		JButton assingTechBtn = new JButton("Assign Tech");
		assingTechBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow= table.getSelectedRow();//select the row from JTable
				if(selectedRow>=0) {
					int taskID= (int)table.getValueAt(selectedRow, 0); // get the task id from the JTable
					addTechMethod(taskID);// add tech to a task
					displayTableData(app.database.getAllTasks()); //refresh the JTable
					
				}
			}
		});
		southPanel.add(assingTechBtn);
		
		/*** method to remove a tech from a task ***/
		JButton removeTechBtn = new JButton("Remove Tech");
		removeTechBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow= table.getSelectedRow();//select the row from JTable
				if(selectedRow>=0) {
					int taskID= (int)table.getValueAt(selectedRow, 0); //retrieve the task id from the JTable
					//confirm the task id with the user
					int result=JOptionPane.showConfirmDialog(null, "Do you want to remove technician from task "+taskID+"?","Confirmation", JOptionPane.YES_NO_OPTION);
					if(result==JOptionPane.YES_OPTION) { // if the task id is correct
					app.database.removeTech(taskID);//remove tech from database
					displayTableData(app.database.getAllTasks()); //refresh the JTable
					}
				}
			}
		});
		southPanel.add(removeTechBtn);
		
		//close the window
		JButton closeBtn = new JButton("Close Tasks");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		southPanel.add(closeBtn);
		
		JPanel northPanel = new JPanel();
		contentPane.add(northPanel, BorderLayout.NORTH);
		
		//add new task
		JButton addTaskBtn = new JButton("Add");
		addTaskBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewTask();// method invoked to add a new task
			}
		});
		northPanel.add(addTaskBtn);
		
		/*** edit the task ***/
		JButton editTaskBtn = new JButton("Edit");
		editTaskBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow= table.getSelectedRow();//select the row from JTable
				if(selectedRow>=0) {
					taskID=(int)table.getValueAt(selectedRow, 0);
					//pass the taskID and open the task editor
					openTaskEditor(taskID);
				}
				
			}
		});
		northPanel.add(editTaskBtn);
		
		/*** method created to delete the desired task ***/
		JButton deleteTaskBtn = new JButton("Delete");
		deleteTaskBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow= table.getSelectedRow(); //select the row from JTable
				if(selectedRow>=0) {
					int taskID= (int)table.getValueAt(selectedRow, 0);
					//confirm the task id with the user
					int option= JOptionPane.showConfirmDialog(null,"Do you want to delete this task?", "Confirmation", JOptionPane.YES_NO_OPTION);
					if(option==JOptionPane.YES_OPTION) {// if the task id is correct
						app.database.deleteTask(taskID); //delete the task
						displayTableData(app.database.getAllTasks());//update the JTable
					}
				}
			}
		});
		northPanel.add(deleteTaskBtn);
		
		JPanel centerPanel = new JPanel();
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					"TaskID", "Task", "Status", "Deadline", "Days", "Start Date", "End Date", "Tech"
			}
		));
		scrollPane.setViewportView(table);
		displayTableData(app.database.getAllTasks());
		
	}

	/** Display the data **/
	void displayTableData(ArrayList<Task> tableData){
		
		
		// Empty the existing data
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		
		for( Task s: tableData) {
			if(s.getMotorID()==motorID) {
				if(s.getTech()!=(null)) {
				    tableModel.addRow(new Object[] {s.getTaskId(), s.getTask_type(), s.getTask_status(), s.getTask_deadline(), s.getRemainingDays(), s.getTask_startDate(), s.getTask_endDate(),  s.getTech().getFirstName()+" "+s.getTech().getLastName()});
			    }
				else {
					tableModel.addRow(new Object[] {s.getTaskId(), s.getTask_type(), s.getTask_status(), s.getTask_deadline(), s.getRemainingDays(), s.getTask_startDate(), s.getTask_endDate(), " "});
				
				}
			}
		}	
	}
	
	//method to add a new task and passing the required parameters to class AddTaskGUI
	public void addNewTask() {
		addTask= new AddTaskGUI(app,motorID,this);
		addTask.setVisible(true);
	}
	//method created to open the EditTaskGUI class whilst passing the parameters needed
	public void openTaskEditor(int taskID) {
		editTask= new EditTaskGUI(app,this,taskID);
		editTask.setVisible(true);
	}
	//method created to open AssignTechDialog and pass the right parameters to assign a tech to a task
	public void addTechMethod(int taskID) {
		techDialog= new AssignTechDialog(app, taskID, this);
		techDialog.setVisible(true);
	}
}
