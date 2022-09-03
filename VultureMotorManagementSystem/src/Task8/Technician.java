package Task8;

import java.util.Objects;

public class Technician {

	private int tech_id;
	private String firstName;
	private String lastName;
	private int age;
	private String skills;
	private String health_status;
	
	public Technician() {
		this.tech_id = 0;
		this.age = 0;
		this.skills = null;
		this.health_status = null;
		firstName="";
		lastName="";
	}
	
	public Technician(int tech_id, String firstName, String lastName, int age, String skills, String health_status) {
		this.tech_id = tech_id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.skills = skills;
		this.health_status = health_status;
	}
	
	public int getTech_id() {
		return tech_id;
	}
	public void setTech_id(int tech_id) {
		this.tech_id += tech_id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName += firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	public String getHealth_status() {
		return health_status;
	}
	public void setHealth_status(String health_status) {
		this.health_status = health_status;
	}
	@Override
	public String toString() {
		return "Technician [tech_id=" + tech_id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age
				+ ", skills=" + skills + ", health_status=" + health_status + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(age, firstName, health_status, lastName, skills, tech_id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Technician other = (Technician) obj;
		return age == other.age && Objects.equals(firstName, other.firstName)
				&& Objects.equals(health_status, other.health_status) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(skills, other.skills) && tech_id == other.tech_id;
	}
}
