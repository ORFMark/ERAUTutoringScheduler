package appointments;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import database.DatabaseConnector;
import database.QueryMarshaller;
import people.Client;

public class AppointmentBuilder {

    // Creates a new appointment object
	public static Appointment buildAppointment(ResultSet appointmentRS) {
		try {
			Appointment newAppointment = new Appointment(appointmentRS.getInt("ID"), appointmentRS.getInt("tutor")
					, appointmentRS.getInt("student"), appointmentRS.getString("course"), appointmentRS.getTimestamp("startTime")
					, appointmentRS.getTimestamp("endTime"));
			return newAppointment;
		} catch (SQLException e) {
			System.out.println("ERROR: Could not build appointment");
			e.printStackTrace();
			return null;
		}
		
	}
	
	// Retrieves an appointment from the database that matches the ID
	public Appointment getAppointment(int id, DatabaseConnector db) {
		ResultSet apptRS = db.runQuery(QueryMarshaller.buildGetQuery("Appointment", id)); 
		return buildAppointment(apptRS);
	}
	
	// Inserts created appointment object into the database
	public static String appointmentInsertQuery(Appointment appointment) {
		String fieldArray[] = {"tutor", "student", "course", "startTime", "endTime"};
		Object valueArray[] = {appointment.getTutorID(), appointment.getStudentID()
				, appointment.getCourse(), appointment.getStartTime()
				, appointment.getEndTime()};
		return QueryMarshaller.buildInsertQuery("Appointment", fieldArray, valueArray);
	}
}
