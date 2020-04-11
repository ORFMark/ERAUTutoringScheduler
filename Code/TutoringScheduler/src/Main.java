import tests.DatabaseConnectorTest;
import tests.PersonTest;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello World");
		DatabaseConnectorTest testConn = new DatabaseConnectorTest();
		PersonTest testPerson = new PersonTest();
		testConn.queryTest();
		if(testPerson.unitTest()) {
			System.out.println("Person Unit Test Passed");
		} else {
			System.out.println("Person Unit Test Failed");
		}
		
	}

}
