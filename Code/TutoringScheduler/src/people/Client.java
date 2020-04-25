package people;

/**
 * @author      Mark Burrell <markrb0609@gmail.com>
 * @version     1.2                 (current version number of program)
 * @since       1.0          (the version of the package this class was first added to)
 */


/** 
 * 
 * Representation of a user of the software 
 * @author Mark Burrell
 * @param ID the ID in the database of the person
 * @param firstName the persons first name
 * @param lastName the client's last na,e
 * @param email the user's email address
 * @param status the users status/permissions in the system
 */
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
