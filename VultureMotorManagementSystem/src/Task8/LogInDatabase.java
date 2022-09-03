package Task8;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class LogInDatabase {
	
	private DBConnection database;
	
	public LogInDatabase( DBConnection newDB) {
		database=newDB;
		//activate PRAGMA
		this.activateConstrainKeys();
	}
	
	
	// Method for Editing an User Account in DB
	public void updateUser(int userID, String username, String password, String userFirstname, String userLastname, String jobTitle) {
		String sqlString= new String("UPDATE user SET "
				+"username= '"+username+"', "
				+"password= '"+password+"', "
				+"user_firstName= '"+userFirstname+"', "
				+"user_lastName= '"+userLastname+"', "
				+"user_jobTitle= '"+jobTitle+"' "
				+"WHERE user_id= "+userID+";");
		
		boolean success=database.RunSQL(sqlString);
		if(!success) {
			System.out.println("Faild to run query: "+sqlString);
		}
		
	}
	
	//Method for removing a user from DB
	public void removeUser(int userID) {
		String sqlString= new String("Delete From user Where user_id= "+userID+";");
		
		boolean success=database.RunSQL(sqlString);
		if(!success) {
			System.out.println("Faild to run query: "+sqlString);
		}
		
	}
	
	
	//Method to add a new user in DB
	public void addUser(int userID, String username, String password, String userFirstname, String userLastname, String jobTitle) {
		String sqlString= new String("INSERT INTO user (user_id, username, password, user_firstName, user_lastName, user_jobTitle) VALUES ("
				+ userID +", '"+username+"', '"+password+"', '"+userFirstname+"', '"+userLastname+"', '"+jobTitle+"');");
		
		boolean success=database.RunSQL(sqlString);
		if(!success) {
			System.out.println("Faild to run query: "+sqlString);
		}
		
	}
	
	//Method to fetch all the user account info an place it in the UserAccount Class
	public ArrayList<UserAccount> getUserAccountInfo(){
		String sqlStatement= new String("SELECT user_id, username, password, user_firstName, user_lastName, user_jobTitle FROM user;");
	    ResultSet userList= database.RunSQLQuery(sqlStatement);
	    ArrayList<UserAccount> resultList= new ArrayList<>();
	    try {
			    while(userList.next()) {
			    	UserAccount newUser= new UserAccount();
			    	newUser.setUserID(userList.getInt(1));
			    	newUser.setUsername(userList.getString(2));
			    	newUser.setPassword(userList.getString(3));
			    	newUser.setUserFirstName(userList.getString(4));
			    	newUser.setUserLastName(userList.getString(5));
			    	newUser.setUserJob(userList.getString(6));
			    	resultList.add(newUser);
			    }
			
	} catch(SQLException e) {
		e.printStackTrace();
		System.out.println("Error when fetching the data from db to the user class");
		System.out.println("Check "+e.getMessage());
		
	}
	    return resultList;
  }
	
	
	
	
	//Method to get all the Technicians from the DB 
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
	
	
	//Delete Technician from DB
	public void deleteTech(int techID) {
		String sqlString= new String("Delete From technician Where tech_id = "+techID+";");
		
		boolean success=database.RunSQL(sqlString);
		if(!success) {
			System.out.println("Faild to run query: "+sqlString);
		}
	}
		
		//Add Technicians in DB -- Designed only for Human Resources Accounts
		public void addTech(int techID, String firstName, String lastName, int age, String skills, String healthStatus) {
			String sqlString= new String("INSERT INTO technician(tech_id, tech_firstname, tech_lastname, tech_age, tech_skills, health_status) VALUES"
					+ "( "+techID+", '"+firstName+"', '"+lastName+"', "+age+", '"+skills+"', '"+healthStatus+"' );");
			
			boolean success=database.RunSQL(sqlString);
			if(!success) {
				System.out.println("Faild to run query: "+sqlString);
			}
		}
			
		
		//Edit Technicians in DB -- Designed only for Human Resources Accounts
		public void editTech(int techID, String firstName, String lastName, int age, String skills, String healthStatus){
			String sqlString=new String("Update technician Set "
					+ "tech_id= "+techID+","
					+ "tech_firstname= '"+firstName+"', "
					+ "tech_lastname= '"+lastName+"', "
					+ "tech_age= "+age+", "
					+ "tech_skills= '"+skills+"', "
					+ "health_status= '"+healthStatus+"' "
					+ "Where tech_id= "+techID+";");

			boolean success=database.RunSQL(sqlString);
			if(!success) {
				System.out.println("Faild to run query: "+sqlString);
			}
			
		}
		
		//Get all task logs from the user table
		public ArrayList<TaskLogClass> taskLogList(){
			
			String sqlStatement=new String("Select log_id ,log_desc, log_dateTime, user_id, task_id From taskLog;");
			ResultSet result=database.RunSQLQuery(sqlStatement);
			ArrayList<TaskLogClass> list= new ArrayList<>();
			try {
				
				while(result.next()) {
					TaskLogClass task= new TaskLogClass();
					task.setLog_id(result.getInt(1));
					task.setLog_desc(result.getString(2));
					task.setLog_dateTime(result.getString(3));
					task.setUser_id(result.getInt(4));
					task.setTask_id(result.getInt(5));
					list.add(task);
				}
			}catch(SQLException e) {
				System.out.println("Failed to process query in getAllTech()");
				System.out.println("SQL attempted: "+sqlStatement);	
				System.out.println("Error: "+e.getErrorCode());
				System.out.println("Message: "+e.getMessage());	
				e.printStackTrace();
			}
	        return list;
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



