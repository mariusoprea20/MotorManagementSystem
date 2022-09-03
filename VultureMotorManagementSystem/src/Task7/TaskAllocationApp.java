/**
 * Marius Oprea - Computer science and Engineering (MO)
 * Student at Northumbria University Year ||
 * Motor Management System 
 */

package Task7;

import Task8.*;


public class TaskAllocationApp {


	
	protected TaskAllocationComponent theMainWindow;
	protected TaskAllocationDataBase dbQuery;
	
    /**
     * The TaskAllocationApp is declared in Task8.LogInWindow class.
     * When invoked, the logged in user name will be passed to TaskAllocationApp
     * and used to record when a task is  set to 'Completed' status.
     */
    private int userIDLog; // get the user id from Task8.LogInLauncher
    private String userName;// get the user name from Task8.LogInLauncher
    protected LogInLauncher logInLauncher; // passed as a parameter
	
	

	public TaskAllocationApp(LogInLauncher launcher) {
		logInLauncher=launcher;
	/** Pass the database connection from Task8.LogInLauncher **/
		dbQuery= new TaskAllocationDataBase(launcher.getDatabase());
		
		/**define and connect the TaskAllocationComponent to TaskAllocationApp**/
		theMainWindow= new TaskAllocationComponent(this);
		theMainWindow.setVisible(true);
	}
	
	/*** Assign a technician to a task ***/
	public  void updateTechList(int taskID, int techID) {
		dbQuery.UpdateTech(taskID, techID);
	
		theMainWindow.displayTableData(dbQuery.getAllTasks());
	}
	
	/*** Remove a technician from a task ***/
	public void removeTech(int taskID) {
		dbQuery.RemoveTech(taskID);
		
		theMainWindow.displayTableData(dbQuery.getAllTasks());
	}
	
	public void setStartDate(int taskID, String startDate) {
		
		dbQuery.updateStartDate(taskID, startDate);
		
		theMainWindow.displayTableData(dbQuery.getAllTasks());
	}
	
	public void setEndDate(int taskID, String endDate) {
		
		dbQuery.updateEndDate(taskID, endDate);
		
		theMainWindow.displayTableData(dbQuery.getAllTasks());
	}
	
	public int getUserIDLog() {
		return userIDLog;
	}

	public void setUserIDLog(int userIDLog) {
		this.userIDLog = userIDLog;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
