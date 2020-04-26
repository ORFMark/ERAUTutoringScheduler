package tests;

import java.sql.ResultSet;
import java.sql.Timestamp;

import appointments.Appointment;
import appointments.AppointmentBuilder;

public class AppointmentTest {
	private ResultSet appointmentRS;
    private Appointment testAppointment;
    private Appointment testAppointment2;
    private Appointment testAppointment3;
   
    private int appointmentID;
    private int tutorID;
	private int studentID;
	private String course;
	private Timestamp startTime;
	private Timestamp endTime;
	
   public AppointmentTest() {
       // Test values
	   appointmentID = 1;
	   tutorID = 1;
	   studentID = 2;
	   course = "TST101";
	   startTime = new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
	   endTime = new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 48));
	   // Create test appointments
	   testAppointment = new Appointment(tutorID, startTime, endTime);
	   testAppointment2 = new Appointment(tutorID, studentID, course, startTime, endTime);
	   testAppointment3 = new Appointment(appointmentID, tutorID, studentID, course, startTime, endTime);
   }
   
   public boolean runUnitTest() {
   	   // Testing to see if the testAppointment constructor initialized
	   if((testAppointment.getEndTime() != endTime) || (testAppointment.getStartTime() != startTime) || testAppointment.getTutorID() != tutorID) {
		   System.out.println("Appointment FAILED: Constructor for testAppointment did not initalize");
		   return false; // Test failed
	   }
	   
	   // Testing to see if the testAppointment2 constructor initialized
	   if((testAppointment2.getEndTime() != endTime) || (testAppointment2.getStartTime() != startTime) || testAppointment2.getTutorID() != tutorID
			   || testAppointment2.getStudentID() != studentID || testAppointment2.getCourse() != course) {
		   System.out.println("Appointment FAILED: Constructor for testAppointment2 did not initalize");
		   return false; // Test failed
	   }
	   
	   // Testing to see if the testAppointment3 constructor initialized
	   if((testAppointment3.getEndTime() != endTime) || (testAppointment3.getStartTime() != startTime) || testAppointment3.getTutorID() != tutorID
			   || testAppointment3.getStudentID() != studentID || testAppointment3.getCourse() != course || testAppointment3.getId() != appointmentID) {
		   System.out.println("Appointment FAILED: Constructor for testAppointment3 did not initalize");
		   return false; // Test failed
	   }
	   
	   // Testing to see if appointment ID can be set
	   testAppointment.setId(appointmentID);
	   if(testAppointment.getId() != appointmentID) {
		   System.out.println("Appointment FAILED: Expected Appointment ID to be be " + appointmentID + "and got " + testAppointment.getId());
		   return false; // Test failed
	   }
	   
	   // Testing to see if tutor ID can be set
	   testAppointment.setTutorID(tutorID);
	   if(testAppointment.getTutorID() != tutorID) {
		   System.out.println("Appointment FAILED: Expected Tutor ID to be be " + tutorID + "and got " + testAppointment.getTutorID());
		   return false; // Test failed
	   }
	   
	   // Testing to see if student ID can be set
	   testAppointment.setStudentID(studentID);
	   if(testAppointment.getStudentID() != studentID) {
		   System.out.println("Appointment FAILED: Expected Student ID to be be " + studentID + "and got " + testAppointment.getStudentID());
		   return false; // Test failed
	   }
	   
	   // Testing to see if course can be set
	   testAppointment.setCourse(course);
	   if(!testAppointment.getCourse().equals(course)) {
		   System.out.println("Appointment FAILED: Expected Course to be be " + course + "and got " + testAppointment.getCourse());
		   return false; // Test failed
	   }
	   
	   // Testing to see if start time can be set
	   testAppointment.setStartTime(startTime);
	   if(!testAppointment.getStartTime().equals(startTime)) {
		   System.out.println("Appointment FAILED: Expected Start Time to be be " + startTime + "and got " + testAppointment.getStartTime());
		   return false; // Test failed
	   }
	   
	   // Testing to see if end time can be set
	   testAppointment.setEndTime(endTime);
	   if(!testAppointment.getEndTime().equals(endTime)) {
		   System.out.println("Appointment FAILED: Expected End Time to be be " + endTime + "and got " + testAppointment.getEndTime());
		   return false; // Test failed
	   }
	   
	   // Testing to see if appointment can be added to the database
	   AppointmentBuilder.appointmentInsertQuery(testAppointment2);
	   
	   return true; // All unit tests succeeded 
   }
}
