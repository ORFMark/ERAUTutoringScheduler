package appointments;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class Appointment {
	private int tutorID;
	private int studentID;
	private String course;
	private Timestamp startTime;
	private Timestamp endTime;
	
	Appointment(int tutorID, Timestamp start, Timestamp end) {
		this.tutorID = tutorID;
		this.studentID = 0;
		this.course = null;
		this.startTime = start;
		this.endTime = end;
	}
	
	Appointment(int tutorID, int studentID, String course, Timestamp start, Timestamp end) {
		this.tutorID = tutorID;
		this.studentID = studentID;
		this.course = course;
		this.startTime = start;
		this.endTime = end;
	}
	
	Appointment(ResultSet apptRS) throws SQLException {
		this.tutorID = apptRS.getInt("tutorID");
		this.studentID = apptRS.getInt("studentID");
		this.course = apptRS.getString("course");
		this.startTime = apptRS.getTimestamp("startTime");
		this.endTime = apptRS.getTimestamp("endTime");
		
	}
}
