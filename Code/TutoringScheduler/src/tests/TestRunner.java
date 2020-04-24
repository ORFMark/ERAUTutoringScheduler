package tests;

public class TestRunner {
	
	static public void UnitTest() {
		System.out.println("Hello World");
		DatabaseConnectorTest testConn = new DatabaseConnectorTest();
		ClientTest testPerson = new ClientTest();
		AppointmentTest testAppointment = new AppointmentTest();
		if(testPerson.unitTest()) {
			System.out.println("Person Unit Test Passed");
		} else {
			System.out.println("Person Unit Test Failed");
		}
		if (testAppointment.runUnitTest()) {
			System.out.println("Appointment Unit Test Passed");
		} else {
			System.out.println("Appointment Unit Test Failed");
		}
		QueryMarshlerTest.unitTest();
		testConn.queryTest();
	}
}
