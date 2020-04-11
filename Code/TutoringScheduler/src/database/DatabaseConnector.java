/*
 * Author: Burrell, Mark R.
 * Purpose: To serve as an interface between a SQL database and a java program. 
 */

package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.Properties;


/**
 * @author      Mark Burrell <markrb0609@gmail.com>
 * @version     1.2                 (current version number of program)
 * @since       1.0          (the version of the package this class was first added to)
 */

public class DatabaseConnector {
	private Connection database;
	private Statement dbStatement;
	private Properties config;
	private boolean isValid;
	
	/**
	 * Default Database Connector that loads based on the config file
	 * <p>
	 * requires the following config fields
	 * UserName
	 * Password
	 * DB
	 * host
	 * port
	 * </p>
	 */
	public DatabaseConnector() throws Exception {
		isValid = false;
		String userName = null;
		String passsword = null;
		String port = null;
		String host = null;
		String databaseName = null;
		FileInputStream file = null;
		config = new Properties();
		try {
			file = new FileInputStream("config.properties");
			config.load(file);
			userName = config.getProperty("UserName");
			passsword = config.getProperty("Password");
			port = config.getProperty("port");
			host = config.getProperty("host");
			databaseName = config.getProperty("DB");
			
		} catch (Exception e) {
			System.out.println("Failed to load config file!");
			e.printStackTrace();
			host = "prclab1.erau.edu";
			userName = "erauprescott";
			passsword = "erauprescott";
			port = "3306";
			databaseName = "sakila";
		} finally {
			file.close();
		}
		String connectionUrl = "jdbc:mysql://" + host + ":" + port + "/" + database;
		try {
			database = DriverManager.getConnection(connectionUrl, userName, passsword);
			if (database != null) {
				System.out.println("Successfully connected to " + host);
			}
		} catch (Exception e) {
			/*Catch block ensrures the database nulls out and alerts the user that the DB was not connected to*/
			System.out.println("COULD NOT CONNECT TO " + host.toUpperCase());
			database = null;
			throw e;
		}
	}

	
	/**
	 * Allows for user-specified database locations and hots                    
	 *
	 * @param  host The host URL with the port
	 * @param database the database name
	 * @param user the username for the connection account
	 * @param password the password for the connection account      
	 */
	public DatabaseConnector(String host, String database, String user, String password) {
		String connectionURL = "jdbc:mysql://" + host + "/" + database;
		System.out.println("Attempting to connect to database " + database + "at host " + host);
		try {
			this.database = DriverManager.getConnection(connectionURL, user, password);
		} catch (SQLException e) {
			System.out.println("Connectioned Failed");
			database = null;
			e.printStackTrace();
		}
	}

	
	/**
	 * Runs a query against the database                           (1)
	 *
	 * @param  query the properly formatted SQL query.          (3)
	 * @return ResultSet containing the result of the querey
	 */
	public ResultSet runQuery(String query) {
		ResultSet rs = null;
		try {
			if (database != null && !database.isClosed()) {
				dbStatement = database.createStatement();
				rs = dbStatement.executeQuery(query);
			} else {
				System.out.println("Database: " + Boolean.toString(database != null) + " closed: "
						+ Boolean.toString(database.isClosed()));
			}
		} catch (Exception e) {
			System.out.print("Failed to execute querey");
			e.printStackTrace();
			rs = null;
		}
		return rs;
	}

	
	

	
	/**
	 * Runs a update like an insert or delete on the database
	 *
	 * @param  updateQuerey A string containing the properly formatted SQL update querey..
	 */
	public int updateDatabase(String updateQuery) {
		int successfulUpdate = 0;
		try {
			if (database != null && !database.isClosed()) {
				dbStatement = database.createStatement();
				dbStatement.executeUpdate(updateQuery);
			} else {
				System.out.println("Database: " + Boolean.toString(database != null) + " closed: "
						+ Boolean.toString(database.isClosed()));
			}
			successfulUpdate = 1;
		} catch (Exception e) {
			System.out.print("Failed to execute querey");
			e.printStackTrace();
			successfulUpdate = 0;
		}
		return successfulUpdate;
	}

	
	public int runTransaction(AbstractList<String> queryList) {
		int transactionSuccess = 1;
		try {
			database.setAutoCommit(false);
			Iterator<String> i = queryList.iterator();
			while(i.hasNext()) {
				if(updateDatabase(i.next()) == 0) {
					database.rollback();
					transactionSuccess = 0;
				}
			}
			if(transactionSuccess == 1) {
			     database.commit();
			}
			
		} catch (SQLException e) {
			transactionSuccess = 0;
			try {
				database.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				database.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		return transactionSuccess;
	}

	
	public Connection getDatabase() {
		return database;
	}

	public void close() {
		if (dbStatement != null) {
			try {
				dbStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (database != null) {
			try {
				database.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		dbStatement = null;
		database = null;

	}
	
	protected void finalize() {
		if (dbStatement != null) {
			try {
				dbStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbStatement = null; 
		}
		if (database != null) {
			try {
				database.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			database = null;
		}
	}

}
