package appointments;

import java.sql.ResultSet;
import java.sql.Timestamp;

import database.DatabaseConnector;
import database.QueryMarshaller;
import people.Client;

public class Scheduler {
	//used to manage the Database queries
	DatabaseConnector db;

	//ought to be created at startup or remarkably close to it
	public Scheduler(DatabaseConnector db) {
		this.db = db;
	}

	//determines if two time blocks overlap. 
	private boolean timeOverlap(Timestamp start1, Timestamp end1, Timestamp start2, Timestamp end2) {
		boolean start1InBlock2 = start1.before(end2) && start1.after(start2);
		boolean start2InBlock1 = start2.before(end1) && start2.after(start1);
		boolean end1InBlock2 = end1.before(end2) && end1.after(start2);
		boolean end2InBlock1 = end2.before(end1) && end2.after(start2);
		boolean equality = (start1.equals(start2) || start1.equals(end2) || end1.equals(end2) || end1.equals(start2));
		return start1InBlock2 || start2InBlock1 || end1InBlock2 || end2InBlock1 || equality;
	}

	//Creates a new appointment in the DB with null course and tutor
	public int newAppoinmtent(Client tutor, Timestamp startTime, Timestamp endTime) {
		if (tutor == null || startTime == null || endTime == null) {
			return 0;
		} else if (startTime.after(endTime) || startTime.equals(endTime)) {
			return -1;
		} else {
			try {
				Appointment myAppt = new Appointment(tutor.getID(), startTime, endTime);
				ResultSet currentRS = db
						.runQuery(QueryMarshaller.buildGetQuery("appointment", "tutorID", tutor.getID()));
				while (currentRS.next()) {
					if (timeOverlap(startTime, endTime, currentRS.getTimestamp("startTime"),
							currentRS.getTimestamp("endTime"))) {
						currentRS.close();
						return -3;
					}
				}
				currentRS.close();
				db.updateDatabase(AppointmentBuilder.appointmentInsertQuery(myAppt));
			} catch (Exception e) {
				e.printStackTrace();
				return -4;
			}
			return 1;

		}
	}
}
