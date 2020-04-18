package tests;

import java.sql.Timestamp;

import appointments.Appointment;

public class AppointmentTest {
   private Appointment testAppointment;
   
    private int tutorID;
	private int studentID;
	private String course;
	private Timestamp startTime;
	private Timestamp endTime;
	
   public AppointmentTest() {
	   tutorID = 1;
	   studentID = 2;
	   course = "TST101";
	   startTime = new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
	   endTime = new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 48));
	   testAppointment = new Appointment(1, startTime, endTime);
   }
   
   public boolean runUnitTest() {
	   if((testAppointment.getEndTime() != endTime) || (testAppointment.getStartTime() != startTime) || testAppointment.getTutorID() != 1) {
		   System.out.println("Appointment FAILED: Constructor did not initalize");
		   return false;
	   }
	   testAppointment.setCourse(course);
	   if(!testAppointment.getCourse().equals(course)) {
		   System.out.println("Appointment FAILED: Expected Corse to be be " + course + "and got " + testAppointment.getCourse());
		   return false;
	   }
	   
	   testAppointment.setStudentID(studentID);
	   if(testAppointment.getStudentID() != studentID) {
		   System.out.println("Appointment Failed: Student IDs do not match expected value");
		   return false;
	   }
	   return true;
	   
   }
}
