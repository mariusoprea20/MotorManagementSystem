/**
 * Marius Oprea - Computer science and Engineering (MO)
 * Student at Northumbria University Year ||
 * Motor Management System 
 */

package Task5;

import Task8.*;

public class JobProgressApp {


	
	private String userID;
	protected JPDatabase database;//database class that provides methods
	protected MotorListGUI motorWindow;//the main motor window
	protected LogInLauncher launcher;
	
	public JobProgressApp(LogInLauncher launcher) {
		this.launcher=launcher; // this will be passed in MotorListGUI to log out

	     //connect to the database
		 database = new JPDatabase(launcher.getDatabase());//pass the database connection from Task8.LogInLauncher
		 motorWindow=new MotorListGUI(this);// pass the reference in between the main motor window and JobProgress class
		 motorWindow.setVisible(true);//set it to visible 
		 motorWindow.displayMotors(database.listOfMotors());//display motors
		
		 
	}
	
	//display all motors
	public void displayAllMotors() {
	 motorWindow.displayMotors(database.listOfMotors());//display motors(used after it has been actioned on the list)
	}
	//add a motor
    public void addMotor(String motorName, String motorManufacturer, String client, String motorDesc, String motorFault, String deadline, String arrivalDate, String replacementParts, String status, String delayStatus) {
	database.addMotor(motorName, motorManufacturer, client, motorDesc, motorFault, deadline, arrivalDate, replacementParts, status, delayStatus);
	displayAllMotors();
  }
    
    //this method is designed to refresh the table in TerminatedMotor class when the status is updated
    public void displayTerminatedMotors() {
    	//if the TerminatedMotors class is activated and not null
    	if(motorWindow.terminatedMotors!=null) {
    	 motorWindow.terminatedMotors.displayFixedMotors(database.listOfMotors());
    	 motorWindow.terminatedMotors.displaySuspendedJobs(database.listOfMotors());
    	}
    }
    
    //this method updates the JTable of the class InspectionGUI
    public void refreshMotorInspectionList() {
    	//if the inspection window is activated  and not null
    	if(motorWindow.inspection!=null) {
    	 motorWindow.inspection.displayInspectionList();
    	}
    }
    
    //this method updates the JTable of completed motors in TerminatedMotors class
    public void refreshCompletedMotorsList() {
    	if(motorWindow.terminatedMotors!=null) {
    	motorWindow.terminatedMotors.displayFixedMotors(database.listOfMotors());
    	}
    }
    
    /***Getters and Setters***/
    public String getUserName() {
    	return userID;
    }
    public void setUserName(String newUserID) {
    	this.userID= newUserID;
    }
    

}
