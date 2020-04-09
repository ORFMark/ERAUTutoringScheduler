import tests.DatabaseConnectorTest;
import tests.PersonTest;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello World");
		//DatabaseConnectorTest testConn = new DatabaseConnectorTest();
		PersonTest testPerson = new PersonTest();
		//testConn.queryTest();
		testPerson.unitTest();
	}

}
