package tests;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnector;
public class DatabaseConnectorTest {
   
   private DatabaseConnector testConnector;  
   private ResultSet rs = null; 
   public DatabaseConnectorTest() {
	   try {
		   try {
		   testConnector = new DatabaseConnector();
		   } catch (Exception e) {
			   e.printStackTrace();
			   return;
		   }
	   } catch (Exception e) {
		   e.printStackTrace();
		   testConnector = null;
	   }
   }
   
   /**
	 * prints a result set in semi-readable format                          (1)
	 * @TODO: Clean up the print to make it fully readable
	 *
	 * @param  rs Resultset to be printed. 
	 */
	public void printResultSet(ResultSet rs) {
		int columnsNumber;
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1)
						System.out.print(", ");
					String columnValue = rs.getString(i);
					System.out.print(rsmd.getColumnName(i) + " " + columnValue);
				}
				System.out.println("");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
   public boolean queryTest() {
	   rs = testConnector.runQuery("SELECT * FROM Person;");
	   return !(rs == null);
   }
   
   public boolean updateTest() {
	   return testConnector.updateDatabase("INSERT INTO Person(firstName, lastName, email) VALUES(  \"Fred\", \"FLINTSTONE\", \"flint@stone.com\");") == 1;
   }
   
   public boolean unitTest() {
	   if(!queryTest()) {
		   System.out.println("\tFailed QueryTest");
		   return false;
	   }
	   if(updateTest()) {
		   System.out.println("\tUpdate reported success");
	   } else {
		   System.out.println("\tUpdate reported failure");
	   }
	   ResultSet rs = testConnector.runQuery("SELECT * FROM Person WHERE firstName = \"Fred\" and lastName = \"FLINTSTONE\";");
	   if(rs == null) {
		   System.out.println("Recored not found in DB");
		   return false;
	   } else {
		   ArrayList<String> qList = new ArrayList<String>();
		   qList.add("DELETE FROM Person WHERE firstName = \"Fred\" and lastName = \"FLINTSTONE\";");
		   if(testConnector.runTransaction(qList) == 1) {
			   return true;
		   } else {
			   System.out.println("\tTransaction Failed");
			   return false;
		   }
		   
	   }
   }
   
}
