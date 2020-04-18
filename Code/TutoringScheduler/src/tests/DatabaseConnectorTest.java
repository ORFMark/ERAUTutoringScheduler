package tests;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import database.DatabaseConnector;
public class DatabaseConnectorTest {
   
   private DatabaseConnector testConnector;  
   private ResultSet rs = null; 
   public DatabaseConnectorTest() {
	   try {
		   try {
		   testConnector = new DatabaseConnector("prclab1.erau.edu", "sakila", "burrelm1", "roBot!cs4450");
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
	
   public void queryTest() {
	   rs = testConnector.runQuery("SELECT * FROM actor;");
	   if (rs == null) {
		   System.out.println("QUEREY FAILED");
	   } else {
		   System.out.println("QUERY PASSED");
		   printResultSet(rs);
	   }
   }
   
   public void updateTest() {
	   testConnector.updateDatabase("INSERT INTO actor VALUES (first_name, last_name) \"Fred\", \"FLINTSTONE\";");
   }
   
}
