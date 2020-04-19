package appointments;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import database.DatabaseConnector;
import database.QueryMarshaller;
import people.Person;

public class AppointmentBuilder {

	public static Appointment buildAppointment(ResultSet appointmentRS) {
		try {
			Appointment newAppointment = new Appointment(appointmentRS.getInt("ID"), appointmentRS.getInt("tutor")
					, appointmentRS.getInt("student"), appointmentRS.getString("course"), appointmentRS.getTimestamp("startTime")
					, appointmentRS.getTimestamp("endTime"));
			return newAppointment;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public Appointment personBuilder(int id, DatabaseConnector db) {
		ResultSet apptRS = db.runQuery(QueryMarshaller.buildGetQuery("Appointment", id)); 
		return buildAppointment(apptRS);
	}
	
	public static String getInsertQuery(Appointment appointment) {
		String feildArray[] = {"tutor", "student", "course", "startTime", "endTime"};
		Object valueArray[] = {appointment.getTutorID(), appointment.getStudentID()
				, appointment.getCourse(), appointment.getStartTime()
				, appointment.getEndTime()};
		return QueryMarshaller.buildInsertQuerey("appointment", feildArray, valueArray);
	}
}
