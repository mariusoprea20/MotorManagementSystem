package Task5;

import java.util.Objects;


public class Motor {
	private int motorID;
	private String motorName;
	private String motorManufacturer;
	private String client;
	private String description;
	private String motor_fault;
	private String startDate;
	private String endDate;
	private String deadline;
	private String motorStatus;
	private String delay;
	private String replacementParts;
	private String notes;
	private String arrivalDate;
	
	public Motor() {
		this.motorID = 0;
		this.motorName = null;
		this.motorManufacturer = null;
		this.client = null;
		this.description = null;
		this.motor_fault = null;
		this.startDate = null;
		this.endDate = null;
		this.deadline = null;
		this.motorStatus = null;
		this.delay = null;
		this.replacementParts = null;
		this.notes = null;
		this.arrivalDate="";
	}
	
	public Motor(int motorID, String motorName, String motorManufacturer, String client, String description,
			String motor_fault, String startDate, String endDate, String deadline, String motorStatus, String delay,
			String replacementParts, String notes, String arrivingDate) {
		super();
		this.motorID = motorID;
		this.motorName = motorName;
		this.motorManufacturer = motorManufacturer;
		this.client = client;
		this.description = description;
		this.motor_fault = motor_fault;
		this.startDate = startDate;
		this.endDate = endDate;
		this.deadline = deadline;
		this.motorStatus = motorStatus;
		this.delay = delay;
		this.replacementParts = replacementParts;
		this.notes = notes;
		this.arrivalDate=arrivingDate;
	}
	public String getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(String arrivingDate) {
		this.arrivalDate=arrivingDate;
	}

	public int getMotorID() {
		return motorID;
	}

	public void setMotorID(int motorID) {
		this.motorID = motorID;
	}

	public String getMotorName() {
		return motorName;
	}

	public void setMotorName(String motorName) {
		this.motorName = motorName;
	}

	public String getMotorManufacturer() {
		return motorManufacturer;
	}

	public void setMotorManufacturer(String motorManufacturer) {
		this.motorManufacturer = motorManufacturer;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMotor_fault() {
		return motor_fault;
	}

	public void setMotor_fault(String motor_fault) {
		this.motor_fault = motor_fault;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getMotorStatus() {
		return motorStatus;
	}

	public void setMotorStatus(String motorStatus) {
		this.motorStatus = motorStatus;
	}

	public String getDelay() {
		return delay;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}

	public String getReplacementParts() {
		return replacementParts;
	}

	public void setReplacementParts(String replacementParts) {
		this.replacementParts = replacementParts;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}


	@Override
	public String toString() {
		return "Motor [motorID=" + motorID + ", motorName=" + motorName + ", motorManufacturer=" + motorManufacturer
				+ ", client=" + client + ", description=" + description + ", motor_fault=" + motor_fault
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", deadline=" + deadline + ", motorStatus="
				+ motorStatus + ", delay=" + delay + ", replacementParts=" + replacementParts + ", notes=" + notes
				+  ", arrivingDate=" + arrivalDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(arrivalDate, client, deadline, delay, description, endDate, motorID,
				motorManufacturer, motorName, motorStatus, motor_fault, notes, replacementParts, startDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Motor other = (Motor) obj;
		return Objects.equals(arrivalDate, other.arrivalDate) && Objects.equals(client, other.client)
				&& Objects.equals(deadline, other.deadline) && Objects.equals(delay, other.delay)
				&& Objects.equals(description, other.description) && Objects.equals(endDate, other.endDate)
				&& motorID == other.motorID
				&& Objects.equals(motorManufacturer, other.motorManufacturer)
				&& Objects.equals(motorName, other.motorName) && Objects.equals(motorStatus, other.motorStatus)
				&& Objects.equals(motor_fault, other.motor_fault) && Objects.equals(notes, other.notes)
				&& Objects.equals(replacementParts, other.replacementParts)
				&& Objects.equals(startDate, other.startDate);
	}
	
   
	
	
	
	
}
