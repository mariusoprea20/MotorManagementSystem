 package Task7;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import Task8.LogInWindow;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.SystemColor;

public class TaskAllocationComponent extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final int limitTaskLife; // constant used for displaying tasks no older than 31 days
	protected JTable table;
	protected TaskAllocationApp launcher;// will be used to call database query functions
	


	/*** Connect the main window with TaskAllocationApp ***/
	public TaskAllocationComponent(TaskAllocationApp newLauncher) {
		launcher= newLauncher; // exchange references between the classes
		limitTaskLife=-30; // set the life display of a task
		
		setForeground(SystemColor.text);
		setBackground(new Color(0, 0, 0));
		setTitle("Task Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 1212, 603);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(Color.BLACK);
		setJMenuBar(menuBar);
		
		JMenu mnFilterMenu = new JMenu("Filter");
		mnFilterMenu.setBackground(Color.WHITE);
		menuBar.add(mnFilterMenu);
		
		//Method to display all tasks when the JMenuItem is clicked
		JMenuItem mntmAllTasks = new JMenuItem("All");
		mntmAllTasks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayTableData(launcher.dbQuery.getAllTasks());
			}
		});
		mnFilterMenu.add(mntmAllTasks);
		
		//Method to display the new Tasks
		JMenuItem mntmNewTask = new JMenuItem("New");
		mntmNewTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				displayTaskStatus("New");
			}
		});
		mnFilterMenu.add(mntmNewTask);
		
		
		//Method to display High Priority Tasks
		JMenuItem mntmHighPriorityTasks = new JMenuItem("High Priority");
		mntmHighPriorityTasks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				displayTaskStatus("High Priority");
			}
		});
		mnFilterMenu.add(mntmHighPriorityTasks);
		
		//Method to display In Progress Tasks
		JMenuItem mntmInProgressTask = new JMenuItem("In Progress");
		mntmInProgressTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayTaskStatus("In Progress");
			}
		});
		
		//Method to display Normal Priority Tasks
		JMenuItem mntmNormalPriority = new JMenuItem("Normal Priority");
		mntmNormalPriority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayTaskStatus("Normal Priority");
			}
		});
		mnFilterMenu.add(mntmNormalPriority);
		mnFilterMenu.add(mntmInProgressTask);
		
		//Method to display Completed Tasks
		JMenuItem mntmCompleted = new JMenuItem("Completed");
		mntmCompleted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayTaskStatus("Completed");
				
			}
		});
		mnFilterMenu.add(mntmCompleted);
		
		//Method to display Assigned Tasks
		JMenuItem mntmAssignedTasks = new JMenuItem("Assigned");
		mntmAssignedTasks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Clear the Table first
				 DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				 tableModel.setRowCount(0);
				for(Task t:launcher.dbQuery.getAllTasks()) {
					if(t.getRemainingDays()>=limitTaskLife){ // display the tasks younger than 31 days
						if(!(t.getTech()==null)) {
						 tableModel.addRow(new Object[] {t.getTaskId(), t.getTask_type(), t.getTask_status(), t.getTask_deadline(), t.getRemainingDays(),t.getTask_startDate(), t.getTask_endDate(), t.getTech().getFirstName()+" "+t.getTech().getLastName()});
						}
					}
				}
			}
		});
		mnFilterMenu.add(mntmAssignedTasks);
		
		//Method to display Unassigned Tasks
		JMenuItem mntmUnassignedTasks = new JMenuItem("Unassigned");
		mntmUnassignedTasks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Clear the table first
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				 tableModel.setRowCount(0);
				for(Task t:launcher.dbQuery.getAllTasks()) {
					if(t.getRemainingDays()>=limitTaskLife){ // display the tasks younger than 31 days
						if(t.getTech()==null) {
						 tableModel.addRow(new Object[] {t.getTaskId(), t.getTask_type(), t.getTask_status(), t.getTask_deadline(), t.getRemainingDays(),t.getTask_startDate(), t.getTask_endDate(),  " "});
						}
					}
				}
			}
		});
		mnFilterMenu.add(mntmUnassignedTasks);
		
		//Method to display Overdue Tasks
		JMenuItem mntmOverdueItem = new JMenuItem("Overdue");
		mntmOverdueItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Clear the table first
			  DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			  tableModel.setRowCount(0);
			  for(Task t:launcher.dbQuery.getAllTasks()) {
				  if(t.getRemainingDays()>=limitTaskLife){ // display the tasks younger than 31 days
				    if(t.getRemainingDays()<0) {
					  if(t.getTech()!=null) {
							tableModel.addRow(new Object[] {t.getTaskId(), t.getTask_type(), t.getTask_status(), t.getTask_deadline(), t.getRemainingDays(),t.getTask_startDate(), t.getTask_endDate(), t.getTech().getFirstName()+" "+t.getTech().getLastName()});
						    }
							else {
							tableModel.addRow(new Object[] {t.getTaskId(), t.getTask_type(), t.getTask_status(), t.getTask_deadline(), t.getRemainingDays(), t.getTask_startDate(), t.getTask_endDate(), " "});
						}
					 }
				 }
			  }
			}
		});
		mnFilterMenu.add(mntmOverdueItem);
		
		
		JMenu mnSetStatusMenu = new JMenu("Set Status");
		menuBar.add(mnSetStatusMenu);
		
		//Method to set the "New" Status
		JMenuItem mntmNewStatus = new JMenuItem("New"); 
		mntmNewStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow= table.getSelectedRow();
				if(selectedRow>=0) {
					int taskID= (int)table.getValueAt(selectedRow, 0);
					launcher.dbQuery.updateTaskStatus(taskID, "New");
					displayTableData(launcher.dbQuery.getAllTasks());
				}
			}
		});
		mnSetStatusMenu.add(mntmNewStatus);
		
		//Method to set the High Priority Status
		JMenuItem mntmHighPriority2 = new JMenuItem("High Priority"); 
		mntmHighPriority2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow= table.getSelectedRow();
				if(selectedRow>=0) {
					int taskID= (int)table.getValueAt(selectedRow, 0);
					launcher.dbQuery.updateTaskStatus(taskID, "High Priority");
					displayTableData(launcher.dbQuery.getAllTasks());
				}
			}
		});
		mnSetStatusMenu.add(mntmHighPriority2);
		
		//Method to set the Normal Priority Status
		JMenuItem mntmNormalPriority2 = new JMenuItem("Normal Priority");
		mntmNormalPriority2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow= table.getSelectedRow();
				if(selectedRow>=0) {
					int taskID= (int)table.getValueAt(selectedRow, 0);
					launcher.dbQuery.updateTaskStatus(taskID, "Normal Priority");
					displayTableData(launcher.dbQuery.getAllTasks());
				}
			}
		});
		mnSetStatusMenu.add(mntmNormalPriority2);
		
		//Method to set the In Progress Status
		JMenuItem mntmInProgress2 = new JMenuItem("In Progress");
		mntmInProgress2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow= table.getSelectedRow();
				if(selectedRow>=0) {
					int taskID= (int)table.getValueAt(selectedRow, 0);
					launcher.dbQuery.updateTaskStatus(taskID, "In Progress");
					displayTableData(launcher.dbQuery.getAllTasks());
				}
			}
		});
		mnSetStatusMenu.add(mntmInProgress2);
		
		//Method to set the Completed Status
		JMenuItem mntmCompleted2 = new JMenuItem("Completed");
		mntmCompleted2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow= table.getSelectedRow();
				if(selectedRow>=0) {
					int taskID= (int)table.getValueAt(selectedRow, 0);
					launcher.dbQuery.updateTaskStatus(taskID, "Completed");
					displayTableData(launcher.dbQuery.getAllTasks());
					
					
				      /** Method 'insertTaskLog' is invoked to log each action:
				       * 1.Random ID
				       * 2.A string containing the username retrieved from the username textField + the new username
				       * 3.The date and time
				       * 4.The user ID that matches the currently logged user
				       */
				      LocalDateTime localDT=LocalDateTime.now();
				      DateTimeFormatter dtf= DateTimeFormatter.ofPattern("dd/MM/yyyy h:mm a");
				      String date= dtf.format(localDT);	//date&time						      
				      int randomLogID= (int) ((Math.random() * (10000 - 1000)) + 1000);//ID in range 
				      HashSet<Integer>randomLogIDList= new HashSet<>();
				      if(!(randomLogIDList.contains(randomLogID))) { // make sure the logID is unique
				    	  randomLogIDList.add(randomLogID);
				          launcher.dbQuery.insertTaskLog(randomLogID, taskID, launcher.getUserName()+" has completed the task.",  date, launcher.getUserIDLog());
				      }
				      /** end of the script **/
				}
			}
		});
		mnSetStatusMenu.add(mntmCompleted2);
		
		JMenu mnTimeManager = new JMenu("Time Manager");
		menuBar.add(mnTimeManager);
		
		// Set the Start & End Date of a task
		JMenuItem mntmSetDate = new JMenuItem("Start/End Date");
		mntmSetDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow= table.getSelectedRow();
				if(selectedRow>=0) {
					// pass the launcher to use it's query methods in the DatePicker Class
					DatePicker calendar= new DatePicker(launcher); 
					// pass the selected task id to the DatePicker Class
					calendar.taskID=(int)table.getValueAt(selectedRow, 0); 
					calendar.setVisible(true);
				}
			}
		});
		mnTimeManager.add(mntmSetDate);
		
		JMenuItem mntmNotes = new JMenuItem("Notes");
		mntmNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow=table.getSelectedRow();
				if(selectedRow>=0) {
					//Pass the launcher to use its methods
					TaskNotes taskNotes= new TaskNotes(launcher);
					taskNotes.setVisible(true);
					//Set the JTextArea to the notes that reside in the database
					int selectedTaskID=(int)table.getValueAt(selectedRow, 0);
					taskNotes.taskID= selectedTaskID; // pass the task id to TaskNotes class
					for(Task tsk:launcher.dbQuery.getAllTasks()) {
						if(tsk.getTaskId()==selectedTaskID) {
							taskNotes.textArea.setText(tsk.getNotes());
						}
					}
				}
			}
		});
		
		/*** display the charts for the average handling time ***/
		JMenuItem chartMenuItem = new JMenuItem("Charts");
		chartMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				XYSeries series =new XYSeries("Time"); //get the xy axis
				int i=1;
				try {
					//iterate through all tasks
			     	for(Task t:launcher.dbQuery.getAllTasks()) {
			     		//make sure the tasks are completed
				            if(!(t.getTask_startDate()==null) && !(t.getTask_endDate()==null) && !(t.getTask_startDate().equals("")) && !(t.getTask_endDate().equals(""))) {
				     		LocalDate startDate=LocalDate.parse(t.getTask_startDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")); //get the start date of each task
				     		LocalDate endDate=LocalDate.parse(t.getTask_endDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));//get the completed date of each task
				     		double days=ChronoUnit.DAYS.between(startDate, endDate);// get the amount of days between start and end date
				     		i++;
				     		/**
				     		 * first argument passes the amount of tasks in the database 1,2,3...+
				     		 * the second argument passes the days worked on each task
				     		 */
				     		series.add(i,days);
			            }
			     	}
					}catch(DateTimeParseException ex) {
						ex.printStackTrace();
						System.out.println("Error. Faild to execute due to: "+ex.getMessage());
					}
				//create XY data set and pass the XY axis
				XYSeriesCollection dataset= new XYSeriesCollection(series);
				//create the chart and pass it into a new chart frame
				JFreeChart chart= ChartFactory.createXYBarChart( "Average Handling Task Time", "Tasks", false,"Days", dataset, PlotOrientation.VERTICAL,false,false,false);
		        ChartFrame frame= new ChartFrame("My Chart", chart);
		        frame.setBounds(100, 100, 1232, 697);
		        frame.setVisible(true);

			}
		});
		mnTimeManager.add(chartMenuItem);
		mnTimeManager.add(mntmNotes);
		
		JPanel FillningPanelJMenuBar = new JPanel();
		FillningPanelJMenuBar.setBackground(Color.BLACK);
		menuBar.add(FillningPanelJMenuBar);
		
		JButton btnLogOutButton = new JButton("Log Out");
		btnLogOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				 LogInWindow logOut= new LogInWindow(launcher.logInLauncher);
				 logOut.setVisible(true);
			}
		});
		btnLogOutButton.setForeground(Color.YELLOW);
		btnLogOutButton.setBackground(SystemColor.desktop);
		menuBar.add(btnLogOutButton);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel buttonPanelSouth = new JPanel();
		buttonPanelSouth.setBackground(new Color(0, 0, 0));
		contentPane.add(buttonPanelSouth, BorderLayout.SOUTH);
		
		//Method to display the description of a selected task
		JButton btnDescriptionButton = new JButton("Description");
		btnDescriptionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//select the row
				int rowToShow= table.getSelectedRow();
				if(rowToShow>=0) {
						for(Task s:launcher.dbQuery.getAllTasks()){
						    //if the selected Task ID equals any Task ID from the DB
							if(((int)table.getValueAt(rowToShow, 0))== s.getTaskId()) {
								
							/**
							 * Call TaskDescription Class and set its text field
							 * to the description that matches the Task ID above
							 */
			                TaskDescription taskDescription= new TaskDescription();
			                taskDescription.txtrDisplayDesc.setText(s.getTask_desc());
							taskDescription.setVisible(true);	
						}	
					}
				}
			}
		});
		buttonPanelSouth.add(btnDescriptionButton);
		
		//Method to Assign Technicians to the Tasks
		JButton btnAssignButton = new JButton("Assign Tech");
		btnAssignButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowToShow= table.getSelectedRow();
				if(rowToShow>=0) {
					//Pass the launcher to AssignTechDialog Class to use its methods
					AssignTechDialog techDialog = new AssignTechDialog(launcher);
					techDialog.setVisible(true);
					//Pass the selected Task ID to AssignTechDialog Class to allow assigning a technician to it
					techDialog.taskID=(int)table.getValueAt(rowToShow, 0);
				}
               }	
			});
		
		buttonPanelSouth.add(btnAssignButton);
		
		//Method to remove a Technician from a task
		JButton btnRemoveButton = new JButton("Remove Tech");
		btnRemoveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow= table.getSelectedRow();
				if(selectedRow>=0) {
					int selectedTaskID=(int)table.getValueAt(selectedRow, 0);
					for(Task t:launcher.dbQuery.getAllTasks()) {
						if(t.getTaskId()==selectedTaskID) {
						launcher.removeTech(selectedTaskID);
						}
					}
					
 				}
			}
		});
		buttonPanelSouth.add(btnRemoveButton);
		
		//Method to auto assign technicians
		JButton btnAutoAssign = new JButton("Auto-Assign");
		btnAutoAssign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=0; // counter
				//for each technician from the database
				for(Task t:launcher.dbQuery.getAllTasks()) {
					//assign tech by tech to each task i++
	                	launcher.updateTechList(t.getTaskId(), launcher.dbQuery.getAllTech().get(i).getTech_id());
	                	i++;
	                	// if the counter 'i' reached the end of the list, then 'i' starts again from 0
	                	if(i>launcher.dbQuery.getAllTech().size()-1) {
	                		i=0;
	                	}
	                	
	                }
			}
		});
		buttonPanelSouth.add(btnAutoAssign);
		
		//Filter the Technicians by their names
		JButton btnTechTaskButton = new JButton("Tech/Task"); 
		btnTechTaskButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Pass the launcher to AssignedTechFilter Class to use its functions
				AssignedTechFilter atf=new AssignedTechFilter(launcher);
				atf.setVisible(true);
			}
		});
		buttonPanelSouth.add(btnTechTaskButton);
		
		JPanel northPanel = new JPanel();
		northPanel.setBackground(new Color(0, 0, 0));
		northPanel.setForeground(Color.BLACK);
		contentPane.add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("TASK ALLOCATION");
		lblNewLabel.setForeground(new Color(255, 255, 0));
		lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		northPanel.add(lblNewLabel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setBackground(new Color(192, 192, 192));
		table.setForeground(Color.BLACK);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"TaskID", "Task", "Status", "Deadline", "Days", "Start Date", "End Date", "Tech"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.getColumnModel().getColumn(3).setPreferredWidth(52);
		table.getColumnModel().getColumn(4).setPreferredWidth(26);
		table.getColumnModel().getColumn(5).setPreferredWidth(35);
		table.getColumnModel().getColumn(6).setPreferredWidth(47);
		scrollPane.setViewportView(table);
		
		//display the data held in the database
		displayTableData(launcher.dbQuery.getAllTasks());
	}
	/** Display the data **/
	void displayTableData(ArrayList<Task> tableData){
		
		
		// Empty the existing data
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		
		for( Task s: tableData) {
			if(s.getRemainingDays()>=limitTaskLife){ // display the tasks younger than 31 days
				if(s.getTech()!=(null)) {
				tableModel.addRow(new Object[] {s.getTaskId(), s.getTask_type(), s.getTask_status(), s.getTask_deadline(), s.getRemainingDays(), s.getTask_startDate(), s.getTask_endDate(),  s.getTech().getFirstName()+" "+s.getTech().getLastName()});
			    }
				else {
					tableModel.addRow(new Object[] {s.getTaskId(), s.getTask_type(), s.getTask_status(), s.getTask_deadline(), s.getRemainingDays(), s.getTask_startDate(), s.getTask_endDate(), " "});
				
				}
			}
		}	
	}
	
	// Method used to display the status  by passing a status name as a parameter 
	void displayTaskStatus(String status) {
		  DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		  tableModel.setRowCount(0);
		  for(Task t:launcher.dbQuery.getAllTasks()) {
			 if(t.getRemainingDays()>=limitTaskLife){ // display the tasks younger than 31 days
			 //if the parameter matches any of the tasks with that status, then display the tasks
			  if(t.getTask_status().equals(status)) {
				  if(t.getTech()!=null) {
						tableModel.addRow(new Object[] {t.getTaskId(), t.getTask_type(), t.getTask_status(), t.getTask_deadline(), t.getRemainingDays(), t.getTask_startDate(), t.getTask_endDate(), t.getTech().getFirstName()+" "+t.getTech().getLastName()});
					    }
						else {
							tableModel.addRow(new Object[] {t.getTaskId(), t.getTask_type(), t.getTask_status(), t.getTask_deadline(), t.getRemainingDays(), t.getTask_startDate(), t.getTask_endDate(), " "});
						}
				 }
			 }
		  }
	}

}
