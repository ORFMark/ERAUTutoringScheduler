package tests;

import people.Client;
import people.ClientBuilder;
import people.Position;

public class ClientTest {
	private Client testPerson;
	public ClientTest() {
		testPerson = new Client(1234, "Mark", "Burrell", "burrel1@my.erau.edu", Position.TUTOR);
	}
	
	public boolean unitTest() {
		if(!testPerson.getFirstName().equals("Mark") ) {
			System.out.print("Unit test of Person failed, first names not equal");
			return false;
		}
		if(!testPerson.getLastName().equals("Burrell")) {
			System.out.print("Unit test of Person failed, last names not equal");
			return false;
		}
		if(!testPerson.getEmail().equals("burrel1@my.erau.edu")) {
			System.out.print("Unit test of Person failed, emails not equal");
			return false;
		}
		if(testPerson.getID() != 1234) {
			System.out.print("Unit test of Person failed, IDs not equal");
			return false;
		}
		if(testPerson.getStatus() != Position.TUTOR) {
			System.out.println("Unit Test of Person Failed, Positons not equal");
			return false;
		}
		testPerson.setID(4321);
		testPerson.setEmail("test@test.com");
		testPerson.setLastName("Mactersterson");
		testPerson.setFirstName("Freddy");
		testPerson.setStatus(Position.STUDENT);
		if(!testPerson.getFirstName().equals("Freddy") ) {
			System.out.print("Unit test of Person failed, first names not equal");
			return false;
		}
		if(!testPerson.getLastName().equals("Mactersterson")) {
			System.out.print("Unit test of Person failed, last names not equal after set");
			return false;
		}
		if(!testPerson.getEmail().equals("test@test.com")) {
			System.out.print("Unit test of Person failed, emails not equal after set");
			return false;
		}
		if(testPerson.getID() != 4321) {
			System.out.print("Unit test of Person failed, IDs not equal after set");
			return false;
		}
		if(testPerson.getStatus() != Position.STUDENT) {
			System.out.println("Unit Test of Person Failed, Positons not equal after set");
			return false;
		}
		String testQuery = "INSERT INTO Person(firstName, lastName, email, type) VALUES (\"Freddy\", \"Mactersterson\", \"test@test.com\", STUDENT);";
		if(!(testQuery.equals(ClientBuilder.clientInsertQuery(testPerson)))) {
			System.out.println("Client insert query not expected value: \n\t    Got: " + ClientBuilder.clientInsertQuery(testPerson) + "\n\tExpected: " + testQuery);
			return false;
		}
		return true;
	}
}
