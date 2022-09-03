package Task5;

import java.util.Objects;

public class Inspection {

	private int inspection_id;
	private String inspection_result;
	private String inspection_date;
	private String notes;
	private int motor_id;
	private String inspectorName;
	

	public Inspection() {
		this.inspection_id = 0;
		this.inspection_result ="";
		this.inspection_date = "";
		this.notes = "";
		this.motor_id = 0;
		this.inspectorName="";
	}
	public Inspection(int inspection_id, String inspection_result, String inspection_date, String notes,
			int motor_id, String inspectorName){
		super();
		this.inspection_id = inspection_id;
		this.inspection_result = inspection_result;
		this.inspection_date = inspection_date;
		this.notes = notes;
		this.motor_id = motor_id;
		this.inspectorName=inspectorName;
	}
	
	public int getInspection_id() {
		return inspection_id;
	}

	public void setInspection_id(int inspection_id) {
		this.inspection_id = inspection_id;
	}

	public String getInspection_result() {
		return inspection_result;
	}

	public void setInspection_result(String inspection_result) {
		this.inspection_result = inspection_result;
	}

	public String getInspection_date() {
		return inspection_date;
	}

	public void setInspection_date(String inspection_date) {
		this.inspection_date = inspection_date;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getMotorID() {
		return motor_id;
	}

	public void setMotorID(int newMotor) {
		this.motor_id = newMotor;
	}
	
	public String getInspector() {
		return inspectorName;
	}
	public void setInspector(String inspectorName) {
		this.inspectorName = inspectorName;
	}

	@Override
	public String toString() {
		return "Inspection [inspection_id=" + inspection_id + ", inspection_result=" + inspection_result
				+ ", inspection_date=" + inspection_date + ", notes=" + notes + ", motor_id=" + motor_id
				+ ", inspectorName=" + inspectorName + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(inspection_date, inspection_id, inspection_result, inspectorName, motor_id, notes);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inspection other = (Inspection) obj;
		return Objects.equals(inspection_date, other.inspection_date) && inspection_id == other.inspection_id
				&& Objects.equals(inspection_result, other.inspection_result) && inspectorName == other.inspectorName
				&& motor_id == other.motor_id && Objects.equals(notes, other.notes);
	}
}
