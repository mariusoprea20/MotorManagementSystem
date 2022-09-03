/**
 * Marius Oprea - Computer science and Engineering (MO)
 * Student at Northumbria University Year ||
 * Motor Management System 
 */
package Task8;


public class LogInLauncher {

	
	
	protected LogInWindow logInWindow;
	protected LogInDatabase dbQuery;
	protected DBConnection database; 
	
	public static void main(String [] args) {
		new LogInLauncher();
	}
	

	public LogInLauncher() {
		database=  new DBConnection();
		database.Connect("C:\\Users\\mariu\\eclipse-workspace\\VultureMotorManagementSystem\\sqlite-tools-win32-x86-3370200\\database.db");
		dbQuery= new LogInDatabase(database);
		logInWindow= new LogInWindow(this);
		logInWindow.setVisible(true);
		
	}
	
	//Method to remove the user and refresh the JTable in HRAccount Class
	public void  removeUser(int selectedUserId) {
		//remove the user
		dbQuery.removeUser(selectedUserId);
	}
	
	//Method to add a new user and refresh the JTable in AddUser Class
	public void addNewUser(int userID, String username, String password, String userFirstname, String userLastname, String jobTitle) {
		//add new user
		dbQuery.addUser(userID, username, password, userFirstname, userLastname, jobTitle);
	}
	
	//Method to edit an existing user
	public void editUser(int userID, String username, String password, String userFirstname, String userLastname, String jobTitle) {
		//edit the user in the DB
		dbQuery.updateUser(userID, username, password, userFirstname, userLastname, jobTitle);
	}
	
	public DBConnection getDatabase() {
		return database;
	}
	 
}
