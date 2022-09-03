package Task8;

import java.util.Objects;

public class UserAccount {

	private int userID;
	private String username;
	private String password;
	private String userFirstName;
	private String userLastName;
	private String userJob;
	private String taskLog;
	
	public UserAccount() {
		this.userID = 0;
		this.username = null;
		this.password = null;
		this.userFirstName = null;
		this.userLastName = null;
		this.userJob = null;
		this.taskLog = null;
	}
	
	public UserAccount(int userID, String username, String password, String userFirstName, String userLastName,
			String userJob, String taskLog) {
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userJob = userJob;
		this.taskLog = taskLog;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserJob() {
		return userJob;
	}

	public void setUserJob(String userJob) {
		this.userJob = userJob;
	}

	public String getTaskLog() {
		return taskLog;
	}

	public void setTaskLog(String taskLog) {
		this.taskLog = taskLog;
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, taskLog, userFirstName, userID, userJob, userLastName, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAccount other = (UserAccount) obj;
		return Objects.equals(password, other.password) && Objects.equals(taskLog, other.taskLog)
				&& Objects.equals(userFirstName, other.userFirstName) && userID == other.userID
				&& Objects.equals(userJob, other.userJob) && Objects.equals(userLastName, other.userLastName)
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "UserAccount [userID=" + userID + ", username=" + username + ", password=" + password
				+ ", userFirstName=" + userFirstName + ", userLastName=" + userLastName + ", userJob=" + userJob
				+ ", taskLog=" + taskLog + "]";
	}

}
