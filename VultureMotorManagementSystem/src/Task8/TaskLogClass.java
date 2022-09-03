package Task8;

import java.util.Objects;

public class TaskLogClass {

	private int log_id;
	private String log_desc;
	private String log_dateTime;
	private int user_id;
	private int task_id;


	public TaskLogClass() {
		this.log_id=0;
		this.log_desc=null;
		this.log_dateTime=null;
		this.user_id=0;
		
	}

	public TaskLogClass(int log_id, String log_desc, String log_dateTime, int user_id) {
		super();
		this.log_id = log_id;
		this.log_desc = log_desc;
		this.log_dateTime = log_dateTime;
		this.user_id = user_id;
	}

	public int getLog_id() {
		return log_id;
	}

	public void setLog_id(int log_id) {
		this.log_id = log_id;
	}

	public String getLog_desc() {
		return log_desc;
	}

	public void setLog_desc(String log_desc) {
		this.log_desc = log_desc;
	}

	public String getLog_dateTime() {
		return log_dateTime;
	}

	public void setLog_dateTime(String log_dateTime) {
		this.log_dateTime = log_dateTime;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	public int getTask_id() {
		return task_id;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(log_dateTime, log_desc, log_id, user_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskLogClass other = (TaskLogClass) obj;
		return Objects.equals(log_dateTime, other.log_dateTime) && Objects.equals(log_desc, other.log_desc)
				&& log_id == other.log_id && user_id == other.user_id;
	}

	@Override
	public String toString() {
		return "TaskLogClass [log_id=" + log_id + ", log_desc=" + log_desc + ", log_dateTime=" + log_dateTime
				+ ", user_id=" + user_id + "]";
	}
	
}
