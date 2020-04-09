package tests;

import people.Person;
import people.Position;

public class PersonTest {
	private Person testPerson;
	public PersonTest() {
		testPerson = new Person(1234, "Mark", "Burrell", "burrel1@my.erau.edu", Position.TUTOR);
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
		return true;
	}
}
