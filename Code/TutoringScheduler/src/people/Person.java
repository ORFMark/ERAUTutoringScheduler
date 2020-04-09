package people;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Person {
	private int ID;
	private String firstName;
	private String lastName;
	private String email;
	private Position status;

	public Person(int ID, String firstName, String lastName, String email, Position status) {
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.status = status;
	}

	public Person(int ID, String firstName, String lastName, String email) {
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.status = Position.STUDENT;
	}

	public Person(ResultSet personRS) throws SQLException {

		this.firstName = personRS.getString("firstName");
		this.lastName = personRS.getString("lastName");
		this.email = personRS.getString("email");
		this.ID = personRS.getInt("ID");
		switch(personRS.getString("Type")) {
		case "STUDENT":
			this.status = Position.STUDENT;
			break;
		case "TUTOR":
			this.status = Position.TUTOR;
			break;
		case "SUPERVISOR":
			this.status = Position.SUPERVISIOR;
			break;
		default:
			this.status = Position.STUDENT;
		}
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Position getStatus() {
		return status;
	}

	public void setStatus(Position status) {
		this.status = status;
	}
	
}
