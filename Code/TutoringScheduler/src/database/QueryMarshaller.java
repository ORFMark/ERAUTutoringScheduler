/**
 * 
 */
package database;

import appointments.Appointment;

/**
 * @author markr
 *
 */

/* TODO */
public class QueryMarshaller {
     
	public static String buildInsertQuerey(String table, String fields[], Object values[]) {
    	 if(fields.length != values.length || fields.length <= 0) {
    		 //System.out.println("Invalid arguments, fields and values must be of the same length and contain more then 0 values");
    		 return null;
    	 }
    	 String query = "INSERT INTO ";
    	 query += table + "(";
    	 for(int idx = 0; idx < fields.length-1; idx++) {
    		 query += fields[idx] + ", ";
    	 }
    	 query += fields[fields.length - 1] + ") VALUES (";
    	 for(int idx = 0; idx < values.length-1; idx++) {
    		if(values[idx] instanceof String) {
    			query += "\""+values[idx].toString() + "\"";
    		} else if (values[idx] instanceof Boolean) {
    			query += values[idx].toString().toUpperCase();
    		} else {
    			query += values[idx].toString();
    		}
    		query += ", ";
    	 }
    	 if(values[values.length-1] instanceof String) {
 			query += "\""+values[values.length-1].toString() + "\"";
 		} else if (values[values.length-1] instanceof Boolean) {
 			query += values[values.length-1].toString().toUpperCase();
 		} else {
 			query += values[values.length-1].toString();
 		}
 		query += ");";
    	 return query;
     }
	
	public static String buildInsertQuerey(String table, Object values[]) {
   	 if( values.length <= 0) {
   		 //System.out.println("Invalid arguments, must insert more then 0 values");
   		 return null;
   	 }
   	 String query = "INSERT INTO ";
   	 query += table + " VALUES(";
   	 for(int idx = 0; idx < values.length-1; idx++) {
 		if(values[idx] instanceof String) {
 			query += "\""+values[idx].toString() + "\"";
 		} else if (values[idx] instanceof Boolean) {
 			query += values[idx].toString().toUpperCase();
 		} else {
 			query += values[idx].toString();
 		}
 		query += ", ";
 	 }
 	 if(values[values.length-1] instanceof String) {
			query += "\""+values[values.length-1].toString() + "\"";
		} else if (values[values.length-1] instanceof Boolean) {
			query += values[values.length-1].toString().toUpperCase();
		} else {
			query += values[values.length-1].toString();
		}
		query += ");";
 	 return query;
    }
	
	
	public static String buildDeleteQuerey(String table, int id) {
		return "DELETE FROM " + table + " WHERE ID = " + Integer.toString(id) + ";";
	}

	public static String buildGetQuery(String table, int id) {
		return "SELECT * FROM " + table + "WHERE ID = " + Integer.toString(id) + ";";
	}
	
	public static String buildGetQuery(String table, String feild, Object id) {
		if(id instanceof String) {
			return "SELECT * FROM " + table + "WHERE " + feild + " = \"" + id + "\";";
		} else if (id instanceof Boolean) {
			return "SELECT * FROM " + table + "WHERE " + feild + " = \"" + id.toString().toUpperCase() + "\";";
		} else {
			return "SELECT * FROM " + table + "WHERE " + feild + " = \"" + id.toString() + "\";";
		}
	}
	
}
