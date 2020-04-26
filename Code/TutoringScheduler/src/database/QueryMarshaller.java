package database;

/**
 * @author      Mark Burrell <markrb0609@gmail.com>
 * @version     1.2                 (current version number of program)
 * @since       1.0          (the version of the package this class was first added to)
 */


public class QueryMarshaller {
     
	
	/**
	 * Builds an abstract insertion query for a SQL databaes
	 *
	 * @param  table String containing the table name
	 * @param fields a string array of the feild names
	 * @param values a array of values. Call .toString methods on enums
	 * 
	 * @return a properly formatted SQL insert query, suitable for use with the DatbaseConnector.updateDatabase method	 
	 * */
	public static String buildInsertQuery(String table, String fields[], Object values[]) {
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
	
	/**
	 * Builds an abstract insertion query for a SQL database assuming all values are present and in proper order
	 *
	 *
	 * @param  table String containing the table name
	 * @param values a array of values. Call .toString methods on enums
	 * 
	 * @return a properly formatted SQL insert query, suitable for use with the DatbaseConnector.updateDatabase method	 
	 * */
	public static String buildInsertQuery(String table, Object values[]) {
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
	
	/**
	 * Builds a query to delete a record from the table with the specified ID
	 *
	 *
	 * @param  table String containing the table name
	 * @param id the Integer ID of the record
	 * 
	 * @return a properly formatted SQL delete query, suitable for use with the DatbaseConnector.updateDatabase method	 
	 * */
	public static String buildDeleteQuery(String table, int id) {
		return "DELETE FROM " + table + " WHERE ID = " + Integer.toString(id) + ";";
	}

	/**
	 * Builds a query to get a record from the database with the specified ID
	 *
	 *
	 * @param  table String containing the table name
	 * @param id the Integer ID of the record
	 * 
	 * @return a properly formatted SQL select query, suitable for use with the DatbaseConnector.runQuery method	 
	 * */
	public static String buildGetQuery(String table, int id) {
		return "SELECT * FROM " + table + " WHERE ID = " + Integer.toString(id) + ";";
	}
	
	/**
	 * Builds a query to get a record from the database where a field matches the specifed valued
	 *
	 * @param  table String containing the table name
	 * @param id Object containing the value to be compared with
	 * @param field the field name
	 * 
	 * @return a properly formatted SQL select query, suitable for use with the DatbaseConnector.runQuery method	 
	 * */
	public static String buildGetQuery(String table, String feild, Object id) {
		if(id instanceof String) {
			return "SELECT * FROM " + table + " WHERE " + feild + " = \"" + id + "\";";
		} else if (id instanceof Boolean) {
			return "SELECT * FROM " + table + " WHERE " + feild + " = " + id.toString().toUpperCase() + ";";
		} else {
			return "SELECT * FROM " + table + " WHERE " + feild + " = " + id.toString() + ";";
		}
	}
	
}
