package Task5;

import java.sql.ResultSet;
import Task7.Technician;
import Task8.DBConnection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import Task7.Task;


public class JPDatabase {
	protected DBConnection database;
	
	public  JPDatabase(DBConnection database) {
		//create new connection to the DB
		this.database= database;
		//activate PRAGMA
		this.activateConstrainKeys();
	}
	
	
	/*** Add new motor in the database ***/
	public void addMotor(String motorName, String motorManufacturer, String client, String motorDesc, String motorFault, String deadline, String arrivalDate, String replacementParts, String status, String delayStatus) {
		String sqlStatement=new String("Insert Into motor(motor_name, motor_manufacturer, motor_client, motor_desc, motor_fault, job_arrivalDate,job_deadline, replacement_parts, job_status, job_delay)"
				+ "Values('"+motorName+"', '"+motorManufacturer+"', '"+client+"', '"+motorDesc+"', '"+motorFault+"', '"+arrivalDate+"', '"+deadline+"', '"+replacementParts+"', '"+status+"', '"+delayStatus+"');");
		
		boolean success= database.RunSQL(sqlStatement);
		if(!success) {
			System.out.println("Faild to run query "+sqlStatement);
		}
	}
	
	/*** Delete Motor from database ***/
	public void deleteMotor(int motorID){
		String sqlStatement=new String("Delete From motor Where motor_id="+motorID+";");
		
		boolean success=database.RunSQL(sqlStatement);
		if(!success) {
			System.out.println("Faild to run query "+sqlStatement);
		}
	}
	
	/***Update the motor status***/
	public void updateMotorStatus(int motorID, String motorStatus) {
		String sqlString= new String("Update motor Set job_status= '"+motorStatus+"' Where motor_id="+motorID+";");
		
		boolean success= database.RunSQL(sqlString);
		
		if(!success) {
			System.out.println("Failed to run query: "+sqlString);
		}
	}
	
	
	/*** Retrieve all motors from the database ***/
	public ArrayList<Motor> listOfMotors(){
		String sqlStatement= new String("Select motor_id, motor_name, motor_manufacturer,"
				                         + " motor_client, motor_desc, motor_fault, "
				                         + "job_startDate, job_endDate, job_deadline, "
				                         + "job_status, job_delay, replacement_parts, "
				                         + "notes, job_arrivalDate From motor;");
		ResultSet result= database.RunSQLQuery(sqlStatement);
		ArrayList<Motor> motorList= new ArrayList<>();
		
		try {
			while(result.next()) {
				Motor newMotor= new Motor();
				newMotor.setMotorID(result.getInt(1));
				newMotor.setMotorName(result.getString(2));
				newMotor.setMotorManufacturer(result.getString(3));
				newMotor.setClient(result.getString(4));
				newMotor.setDescription(result.getString(5));
				newMotor.setMotor_fault(result.getString(6));
				newMotor.setStartDate(result.getString(7));
				newMotor.setEndDate(result.getString(8));
				newMotor.setDeadline(result.getString(9));
				newMotor.setMotorStatus(result.getString(10));
				newMotor.setDelay(result.getString(11));
				newMotor.setReplacementParts(result.getString(12));
				newMotor.setNotes(result.getString(13));
				newMotor.setArrivalDate(result.getString(14));
				motorList.add(newMotor);
			}
			
		}catch(SQLException e) {
			System.out.println("Error. Could not fetch the data!");
			System.out.println("Check "+e.getMessage());
			e.printStackTrace();
			return null;
			
		}
		return motorList;
	}	
	
	/*** method to update the motor table ***/
	public void motorUpdate(int motorID, String motorName, String manufacturer, String client,  
			                String motorFault, String motorStatus, String motorDelay, String description, 
			                String arrivingDate, String deadline, String startDate, String endDate) {
		String sqlString= new String("Update motor Set motor_name= '"+motorName+"', motor_manufacturer= '"+manufacturer+"', "
				                      + "motor_client= '"+client+"', motor_fault= '"+motorFault+"', job_status= '"+motorStatus+"', "
				                      + "job_delay= '"+motorDelay+"', motor_desc= '"+description+"', "
				                      + "job_arrivalDate= '"+arrivingDate+"', job_deadline= '"+deadline+"', job_startDate= '"+startDate+"', "
				                      + "job_endDate= '"+endDate+"' WHERE motor_id="+motorID+";");
		
		boolean success= database.RunSQL(sqlString);
		
		if(!success) {
			System.out.println("Faild to run query "+sqlString);
		}
	}
	
	/*** method used to update the motor before inspection ***/
	public void updateMotorBeforeInspection(int motorID, String motorName, String manufacturer, String client, String motorFault,String description, 
		            String arrivingDate, String deadline, String startDate, String endDate) {
		String sqlString= new String("Update motor Set motor_name= '"+motorName+"', motor_manufacturer= '"+manufacturer+"', "
		                      + "motor_client= '"+client+"', motor_fault= '"+motorFault+"',  motor_desc= '"+description+"', "
		                      + "job_arrivalDate= '"+arrivingDate+"', job_deadline= '"+deadline+"', job_startDate= '"+startDate+"', "
		                      + "job_endDate= '"+endDate+"' WHERE motor_id="+motorID+";");
		
		boolean success= database.RunSQL(sqlString);
		
		if(!success) {
		System.out.println("Faild to run query "+sqlString);
}
}
	
	
	/*** metod to pass the MotorPanel.JList elements into the database ***/
	public void replacementPartsUpdate(int motorID, String element) {
		String sqlString= new String("Update motor Set replacement_parts= '"+element+"' WHERE motor_id= "+motorID+";");
		
		boolean success= database.RunSQL(sqlString);
		if(!success) {
			System.out.println("Faild to run query "+sqlString);
		}
	}
	
	//get the description for motor description class in order to display it 
	public String getDescription(int motor_id) {
		
		String sqlString=new String("Select motor_desc From motor Where motor_id="+motor_id+";");
		ResultSet result= database.RunSQLQuery(sqlString);
		String desc="";
		try {
			while(result.next()) {
				desc=result.getString(1);
			}		
		}catch(SQLException e) {
			System.out.println("Error. Could not fetch the data!");
			System.out.println("Check "+e.getMessage());
			e.printStackTrace();
			return null;
		}
		return desc;
	}
	
	//update the description of a motor
	public void updateDescription(String newDesc, int motor_id) {
		
		String sqlString= new String("Update motor Set motor_desc= '"+newDesc+"' Where motor_id= "+motor_id+";");
		
		boolean success=database.RunSQL(sqlString);
		
		if(!success) {
			System.out.println("Faild to run query "+sqlString);
		}
	}
	
	// retrieve the all inspections of motors from the database
	public ArrayList<Inspection> inspectionList(){
		
		String sqlStatement=new String("Select inspection_id, motor_id, inspection_result, inspection_date, notes, inspector_name From inspection");
		ResultSet result= database.RunSQLQuery(sqlStatement);
		ArrayList<Inspection> inspectionList= new ArrayList<>();
		
		try {
			while(result.next()) {
				Inspection inspection= new Inspection();
				inspection.setInspection_id(result.getInt(1));
				inspection.setMotorID(result.getInt(2));
				inspection.setInspection_result(result.getString(3));
				inspection.setInspection_date(result.getString(4));
				inspection.setNotes(result.getString(5));
				inspection.setInspector(result.getString(6));
				
				inspectionList.add(inspection);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error processing "+sqlStatement);
			System.out.println("Check "+e.getMessage());
			e.printStackTrace();
		}
		
		return  inspectionList;
	}
	
	// update the motor notes
	public void updateNotes(String motorNotes, int motorID) {
		String sqlString= new String("Update motor Set notes= '"+motorNotes+"' Where motor_id= "+motorID+";");
		
		boolean success= database.RunSQL(sqlString);
		
		if(!success) {
			System.out.println("Faild to run query "+sqlString);
		}
	}
	
	//method designed to suspend a motor
	public void suspendMotor(int motorID) {
		String sqlString= new String("Update motor Set job_status='Suspended', job_delay='Suspended' Where motor_id="+motorID+";");
		
		boolean success= database.RunSQL(sqlString);
		
		if(!success) {
			System.out.println("Faild to run query "+sqlString);
		}
		
	}
	
	/*** Retrieve the Expected Days of a task to be completed***/
	public void updateExpectedDays(int taskID, int expectedDays) {
		
		String sqlString = new String("UPDATE task SET task_expectedDays = "+ expectedDays +" WHERE task_id= "+ taskID+ ";");

		boolean success = database.RunSQL(sqlString);
		
		if(!success) {
			System.out.println("Failed to run query: "+sqlString);
		}
	}
	
	/** Retrieve all the tasks and store it into an local array to display it for later on **/
	public  ArrayList<Task> getAllTasks() {
		//String sqlStatement= new String(" SELECT task_id, task_type, task_desc, task_startDate, task_status, task_deadline, tech_id FROM task ;");
		String sqlStatement= new String(" SELECT task_id, task_type, task_desc, task_startDate, task_status, task_deadline, task_expectedDays , tech_id, task_endDate, notes, motor_id  FROM task;");
		ResultSet taskList= database.RunSQLQuery(sqlStatement);
		ArrayList<Task> result = new ArrayList<>();
		try {
				while(taskList.next()) {
					Task newTask= new Task();
					newTask.setTaskId(taskList.getInt(1));
					newTask.setTask_type(taskList.getString(2));
					newTask.setTask_desc(taskList.getString(3));
					newTask.setTask_startDate(taskList.getString(4));
					newTask.setTask_status(taskList.getString(5));
					newTask.setTask_deadline(taskList.getString(6));
					newTask.setTask_endDate(taskList.getString(9));
					newTask.setNotes(taskList.getString(10));
					
					/**** add the remaining days of the task******/
					LocalDate nowDate=LocalDate.now();
					DateTimeFormatter dtf= DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate task_deadline=LocalDate.parse(newTask.getTask_deadline(), dtf);
					long days=ChronoUnit.DAYS.between(nowDate, task_deadline);
				    updateExpectedDays(newTask.getTaskId(),(int)days);
	                /**** end of the algorithm***/
				    
					newTask.setRemainingDays(taskList.getInt(7));
					for(Technician t:getAllTech()) {
						if(taskList.getInt(8) == t.getTech_id()) {
						newTask.setTech(t);
						}
						newTask.setMotorID(taskList.getInt(11));
					}
					result.add(newTask);
				}
		}catch(SQLException e) {
			System.out.println("Failed to process query in getAllTasks()");
			System.out.println("SQL attempted: "+sqlStatement);	
			System.out.println("Error: "+e.getErrorCode());
			System.out.println("Message: "+e.getMessage());	
			e.printStackTrace();
		}
		return result;
	}
	
	/** Retrieve all the technicians and store them into an local array to display it for later **/
	public ArrayList<Technician> getAllTech(){
		String sqlStatement= new String("SELECT tech_id, tech_firstname, tech_lastname, tech_age, tech_skills, health_status FROM technician;");
		ResultSet techList= database.RunSQLQuery(sqlStatement);
		ArrayList<Technician> result= new ArrayList<>();
		
		try {
				while(techList.next()) {
					Technician newTech= new Technician();
					newTech.setTech_id(techList.getInt(1));
					newTech.setFirstName(techList.getString(2));
					newTech.setLastName(techList.getString(3));
					newTech.setAge(techList.getInt(4));
					newTech.setSkills(techList.getString(5));
					newTech.setHealth_status(techList.getString(6));
					result.add(newTech);
				}
		}catch(SQLException e) {
			System.out.println("Failed to process query in getAllTech()");
			System.out.println("SQL attempted: "+sqlStatement);	
			System.out.println("Error: "+e.getErrorCode());
			System.out.println("Message: "+e.getMessage());	
			e.printStackTrace();
		}
		return result;
	}
	

	
	//method to add a new task
	public void addNewTask(String taskType, String taskStatus, String startDate, String endDate, String deadline, String desc, String notes, int motorID, int daysLeft ) {
		String sqlString =new String("Insert Into task(task_type, task_status, task_startDate, task_endDate, task_deadline, task_desc, notes, motor_id, task_expectedDays) VALUES"
				+ "('"+taskType+"', '"+taskStatus+"', '" +startDate+"', '"+endDate+"', '"+deadline+"', '"+desc+"', '"+notes+"', "+motorID+", "+daysLeft+");");
		
		boolean success = database.RunSQL(sqlString);
				
				if(!success) {
					System.out.println("Failed to run query: "+sqlString);
				}
			}
	
	//method to edit a task in Motor Panel
	public void editTaskDetails(String taskType, String taskStatus, String startDate, String endDate, String deadline, String desc, String notes, int taskID, int daysLeft ) {
		String sqlString =new String("Update task Set task_type= '"+taskType+"', task_status='"+taskStatus+"', task_startDate= '"+startDate+"', "
				                   + "task_endDate= '"+endDate+"', task_deadline= '"+deadline+"', task_desc= '"+desc+"', notes= '"+notes+"', "
				                   + "task_expectedDays= "+daysLeft+" Where task_id="+taskID+";");
		
		boolean success = database.RunSQL(sqlString);
				
				if(!success) {
					System.out.println("Failed to run query: "+sqlString);
				}
	}
	//method to delete a task
	public void deleteTask(int taskID) {
		String sqlString= new String("Delete From task Where task_id="+taskID+";");
		boolean success = database.RunSQL(sqlString);
		
		if(!success) {
			System.out.println("Failed to run query: "+sqlString);
		}
	}
	
	/*** Assign a technician to a particular task in database ***/
	public void updateTech(int taskID, int techID) {
		// Should probably add a message if the student does not exist
		String sqlString = new String("UPDATE task SET tech_id= "+ techID +" WHERE task_id= "+ taskID+ ";");

		boolean success = database.RunSQL(sqlString);
		
		if(!success) {
			System.out.println("Failed to run query: "+sqlString);
		}
	}
	
	
	/*** Used to Remove a technician from a task and update the database ***/
	public void removeTech(int taskID) {
		// Should probably add a message if the student does not exist
		String sqlString = new String("UPDATE task SET tech_id= "+ null +" WHERE task_id= "+ taskID+ ";");

		boolean success = database.RunSQL(sqlString);
		
		if(!success) {
			System.out.println("Failed to run query: "+sqlString);
		}
	}
	
	
   /*** method to add new inspection into the database ***/
	public void addInspection(int motorID, String inspectorName, String inspResult, String inspDate, String notes) {
		
		String sqlString= new String("Insert Into inspection (motor_id, inspector_name, inspection_result, inspection_date, notes)"
				+ "VALUES("+motorID+", '"+inspectorName+"', '"+inspResult+"', '"+inspDate+"', '"+notes+"');");
		
		boolean success = database.RunSQL(sqlString);
		
		if(!success) {
			System.out.println("Failed to run query: "+sqlString);
		}
	}
	
	/*** method for updating an existing inspection in the database ***/
	public void updateInspection(int motorID, String inspectorName, String inspResult, String inspDate, String notes) {
		
		String sqlString= new String("Update inspection SET motor_id= "+motorID+", inspector_name= '"+inspectorName+
				                     "', inspection_result= '"+inspResult+"', inspection_date= '"+inspDate+"', notes= '"+notes+"';");
		
		boolean success = database.RunSQL(sqlString);
		
		if(!success) {
			System.out.println("Failed to run query: "+sqlString);
		}
	}
	
	// activate the constrains between foreign keys
	public void activateConstrainKeys() {
		String sqlString= new String("PRAGMA foreign_keys = ON");
		
		boolean success=database.RunSQL(sqlString);
		if(!success) {
			System.out.println("Faild to run query: "+sqlString);
		}
	}
}
