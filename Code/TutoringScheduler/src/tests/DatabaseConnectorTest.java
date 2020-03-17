package tests;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnector;
public class DatabaseConnectorTest {
	@SuppressWarnings("unused")
   private DatabaseConnector testConnector; 
	@SuppressWarnings("unused") 
   private ResultSet rs = null; 
   public DatabaseConnectorTest() {
	   try {
		   try {
		   testConnector = new DatabaseConnector("prclab1.erau.edu", "sakila", "erauprescott", "erauprescott");
		   } catch (Exception e) {
			   e.printStackTrace();
			   return;
		   }
	   } catch (Exception e) {
		   e.printStackTrace();
		   testConnector = null;
	   }
   }
   
   public void queryTest() {
	   rs = testConnector.runQuery("SELECT * FROM actor;");
	   if (rs == null) {
		   System.out.println("QUEREY FAILED");
	   } else {
		   System.out.println("QUERY PASSED");
	   }
   }
   
   public void updateTest() {
	   testConnector.updateDatabase("INSERT INTO actor VALUES (first_name, last_name) \"Fred\", \"FLINTSTONE\";");
   }
   
}
