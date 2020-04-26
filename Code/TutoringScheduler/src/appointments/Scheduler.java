package appointments;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import database.DatabaseConnector;
import database.QueryMarshaller;
import people.Client;
import people.Position;

public class Scheduler {
	// used to manage the Database queries
	DatabaseConnector db;

	// ought to be created at startup or remarkably close to it
	public Scheduler(DatabaseConnector db) {
		this.db = db;
	}

	// determines if two time blocks overlap.
	private boolean timeOverlap(Timestamp start1, Timestamp end1, Timestamp start2, Timestamp end2) {
		boolean start1InBlock2 = start1.before(end2) && start1.after(start2);
		boolean start2InBlock1 = start2.before(end1) && start2.after(start1);
		boolean end1InBlock2 = end1.before(end2) && end1.after(start2);
		boolean end2InBlock1 = end2.before(end1) && end2.after(start2);
		boolean equality = (start1.equals(start2) || start1.equals(end2) || end1.equals(end2) || end1.equals(start2));
		return start1InBlock2 || start2InBlock1 || end1InBlock2 || end2InBlock1 || equality;
	}
	
	
	private boolean timeEnclosed(Timestamp start1, Timestamp end1, Timestamp start2, Timestamp end2) {
		return (start1.after(start1) || start1.equals(start2)) && (end1.before(end2) || end1.equals(end2));
	}

	// Creates a new appointment in the DB with null course and tutor
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

	/**
	 * Finds and inserts a new appointment between the tutor and student into the DB
	 *
	 * @param  tutor Client representing the tutor. cannot have Position.STUDENT as status
	 * @param  student Client representing the student
	 * @param course String containing the course code
	 * @param startTime Timestamp representation of the appointment start
	 * @param endTime Timestamp representation of the appointment endtime
	 * @return transactionSucess 0 for invalid args, -1 for startTime after endtime, -2 transaction insertion failed 
	 * @return -3 could not find a appointment to insert into, 1 for success
	 *  
	 * */
	public int newAppointment(Client tutor, Client student, String course, Timestamp startTime, Timestamp endTime) {
		if(tutor == null || tutor.getStatus() == Position.STUDENT || student == null || course == null || startTime == null || endTime == null) {
			return 0;
		}
		if(startTime.after(endTime) || startTime.equals(endTime)) {
			return -1;
		}
		ResultSet apptRS = db.runQuery(QueryMarshaller.buildGetQuery("appointment", "tutor", tutor.getID()));
		if(apptRS != null) {
			try {

				while(apptRS.next()) {
					if(timeEnclosed(startTime, endTime, apptRS.getTimestamp("startTime"), apptRS.getTimestamp("endTime")) && apptRS.getInt("student") != 0) {
						Appointment emptyAppointment = AppointmentBuilder.buildAppointment(apptRS);
						int emptyApptID = emptyAppointment.getId();
						ArrayList<String> queryList = new ArrayList<String>();
						queryList.add(QueryMarshaller.buildDeleteQuery("appointment", emptyApptID));
						if(!startTime.equals(emptyAppointment.getStartTime())) {
							Appointment preccedingAppointment = new Appointment(tutor.getID(), emptyAppointment.getStartTime(), startTime);
							queryList.add(AppointmentBuilder.appointmentInsertQuery(preccedingAppointment));
						}
						if(!endTime.equals(emptyAppointment.getEndTime())) {
							Appointment postceedingAppointment = new Appointment(tutor.getID(), endTime, emptyAppointment.getEndTime());
							queryList.add(AppointmentBuilder.appointmentInsertQuery(postceedingAppointment));
						}
						Appointment newAppointment = new Appointment(0, tutor.getID(), student.getID(), course, startTime, endTime);
						queryList.add(AppointmentBuilder.appointmentInsertQuery(newAppointment));
						if(db.runTransaction(queryList) == 1) {
							return 1;
						} else {
							apptRS.close();
							return -2;
						}


					}
				}
				return -3;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -3;
	}
	
	/**
	 * Finds and inserts a new appointment between the tutor and student into the DB
	 *
	 * @param  tutor int ID of a tutor
	 * @param  student int ID of the student
	 * @param course String containing the course code
	 * @param startTime Timestamp representation of the appointment start
	 * @param endTime Timestamp representation of the appointment endtime
	 * @return transactionSucess 0 for invalid args, -1 for startTime after endtime, -2 transaction insertion failed 
	 * @return -3 could not find a appointment to insert into, 1 for success
	 *  
	 * */
	public int newAppointment(int tutor, int student, String course, Timestamp startTime, Timestamp endTime) {
		if(course == null || startTime == null || endTime == null) {
			return 0;
		}
		if(startTime.after(endTime) || startTime.equals(endTime)) {
			return -1;
		}
		ResultSet apptRS = db.runQuery(QueryMarshaller.buildGetQuery("Appointment", "tutor", tutor));
		if(apptRS != null) {
			try {

				while(apptRS.next()) {
					if(timeEnclosed(startTime, endTime, apptRS.getTimestamp("startTime"), apptRS.getTimestamp("endTime")) && apptRS.getInt("student") != 0) {
						Appointment emptyAppointment = AppointmentBuilder.buildAppointment(apptRS);
						int emptyApptID = emptyAppointment.getId();
						ArrayList<String> queryList = new ArrayList<String>();
						queryList.add(QueryMarshaller.buildDeleteQuery("Appointment", emptyApptID));
						if(!startTime.equals(emptyAppointment.getStartTime())) {
							Appointment preccedingAppointment = new Appointment(tutor, emptyAppointment.getStartTime(), startTime);
							queryList.add(AppointmentBuilder.appointmentInsertQuery(preccedingAppointment));
						}
						if(!endTime.equals(emptyAppointment.getEndTime())) {
							Appointment postceedingAppointment = new Appointment(tutor, endTime, emptyAppointment.getEndTime());
							queryList.add(AppointmentBuilder.appointmentInsertQuery(postceedingAppointment));
						}
						Appointment newAppointment = new Appointment(0, tutor, student, course, startTime, endTime);
						queryList.add(AppointmentBuilder.appointmentInsertQuery(newAppointment));
						if(db.runTransaction(queryList) == 1) {
							return 1;
						} else {
							apptRS.close();
							return -2;
						}


					}
				}
				return -3;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -3;
	}
}
