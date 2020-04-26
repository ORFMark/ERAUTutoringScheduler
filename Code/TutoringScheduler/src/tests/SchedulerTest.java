package tests;

import java.sql.Timestamp;

import appointments.Scheduler;
import database.DatabaseConnector;

public class SchedulerTest {

	
	static void unitTest(int tutID, int stuID, long start, DatabaseConnector db) {
		Scheduler sch= new Scheduler(db);
		sch.newAppointment(tutID, stuID, "TST101", new Timestamp(start), new Timestamp(start + (15*60 * 1000)));
	}
}
