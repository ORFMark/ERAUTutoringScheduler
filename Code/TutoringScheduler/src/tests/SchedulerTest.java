package tests;

import java.sql.Timestamp;

import appointments.Scheduler;
import database.DatabaseConnector;
import people.Client;
import people.ClientBuilder;

public class SchedulerTest {

	
	static void unitTest(int tutID, int stuID, long start, DatabaseConnector db) {
		Scheduler sch= new Scheduler(db);
		int rc = sch.newAppointment(tutID, stuID, "TST101", new Timestamp(start), new Timestamp(start + (15*60 * 1000)));
		if( rc != 1) {
			System.out.println("Scheduler Unit Test Failed, got a return code of " + Integer.toString(rc) + " from first appt schedule expected 1");	
		}
		rc = sch.newAppointment(ClientBuilder.clientBuilder(tutID, db), new Timestamp(start + (20*60 * 1000)), new Timestamp(start + (40*60 * 1000)));
		if( rc != 1) {
			System.out.println("Scheduler Unit Test Failed, got a return code of " + Integer.toString(rc) + " from empty appointment schedule expected 1");	
		}
		rc = sch.newAppointment(ClientBuilder.clientBuilder(tutID, db), ClientBuilder.clientBuilder(stuID, db), "TST101", new Timestamp(start + (20*60 * 01000)), new Timestamp(start + (40*60 * 1000)));
		if( rc != 1) {
			System.out.println("Scheduler Unit Test Failed, got a return code of " + Integer.toString(rc) + " from second schedule expected 1");	
		}
	}
}
