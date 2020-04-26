package tests;

import java.util.Date;

import com.mysql.cj.protocol.Resultset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import appointments.Appointment;
import appointments.AppointmentBuilder;
import database.DatabaseConnector;
import people.Client;
import people.ClientBuilder;
import people.Position;

public class TestRunner {
	static long now;
	public static void insertTestData() {
		try {
			now = System.currentTimeMillis();
			Client testTutor = new Client(-1, "testTutor", "testTutor", "testTutor@test.com", Position.TUTOR);
			Client testStudent = new Client(-2, "testStudent", "testStudent", "testStudent@test.com", Position.STUDENT);
			Appointment emptyTestAppointment = new Appointment(-1, new Timestamp(now + (24 * 60 * 60 * 1000)), new Timestamp(now + (26 * 60 * 60 * 1000)));
			DatabaseConnector db = new DatabaseConnector();
			db.updateDatabase(ClientBuilder.clientInsertQuery(testTutor));
			db.updateDatabase(ClientBuilder.clientInsertQuery(testStudent));
			ResultSet rs = db.runQuery("SELECT ID FROM Person WHERE lastName = \"testTutor\";");
			emptyTestAppointment.setTutorID(rs.getInt("ID"));
			db.updateDatabase(AppointmentBuilder.appointmentInsertQuery(emptyTestAppointment));
			db.close();
		} catch (Exception exep) {

		}


	}
	public static void tearDownTestData() {
		try {
			now = System.currentTimeMillis();
			DatabaseConnector db = new DatabaseConnector();
			db.updateDatabase("DELETE FROM Person WHERE lastName = \"testTutor\" or lastName = \"testStudent\";");
			db.close();
		} catch (Exception exep) {

		}


	}
	static public void UnitTest() {
		System.out.println("Hello World");
		insertTestData();
		DatabaseConnectorTest testConn = new DatabaseConnectorTest();
		ClientTest testPerson = new ClientTest();
		AppointmentTest testAppointment = new AppointmentTest();
		int tutorID = 0;
		int studentID = 0;

		try {
			DatabaseConnector db = new DatabaseConnector();
			ResultSet RS = db.runQuery("SELECT * FROM Person");
			while(RS.next()) {
				if(RS.getString("lastName").equals("testTutor")) {
					tutorID = RS.getInt("ID");
				}
				if(RS.getString("lastName").equals("testStudent")) {
					studentID = RS.getInt("ID");
				}
			}

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
			if(QueryMarshlerTest.unitTest()) {
				System.out.println("QueryMarshaller Passed Unit Test");
			} else {
				System.out.println("QueryMarshaller Failed Unit Test");
			}

			if(testConn.unitTest()) {
				System.out.println("Connector Passed Unit Test");
			} else {
				System.out.println("Connector Failed Unit Test");
			}
			SchedulerTest.unitTest(tutorID, studentID, now, db);
			tearDownTestData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
