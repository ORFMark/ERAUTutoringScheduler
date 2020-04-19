package appointments;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class Appointment {
	private int id;
	private int tutorID;
	private int studentID;
	private String course;
	private Timestamp startTime;
	private Timestamp endTime;
	
	public Appointment(int tutorID, Timestamp start, Timestamp end) {
		this.tutorID = tutorID;
		this.studentID = 0;
		this.course = null;
		this.startTime = start;
		this.endTime = end;
	}
	
	
	public Appointment(int id, int tutorID, int studentID, String course, Timestamp startTime, Timestamp endTime) {
		this.id = id;
		this.tutorID = tutorID;
		this.studentID = studentID;
		this.course = course;
		this.startTime = startTime;
		this.endTime = endTime;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getTutorID() {
		return tutorID;
	}

	public void setTutorID(int tutorID) {
		this.tutorID = tutorID;
	}

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
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
