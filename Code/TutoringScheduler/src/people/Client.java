package people;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Client {
	protected int ID;
	protected String firstName;
	protected String lastName;
	protected String email;
	protected Position status;

	public Client(int ID, String firstName, String lastName, String email, Position status) {
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.status = status;
	}

	public Client(int ID, String firstName, String lastName, String email) {
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.status = Position.STUDENT;
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
