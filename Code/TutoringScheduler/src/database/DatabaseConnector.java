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
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.Level;


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
	private Logger databaseLogger = Logger.getLogger(DatabaseConnector.class.getName());
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
	 * @throws Exception if a connection cannot be made
	 */
	public DatabaseConnector() throws Exception {
		isValid = false;
		String userName = null;
		String passsword = null;
		String port = null;
		String host = null;
		String databaseName = null;
		FileInputStream file = null;

		databaseLogger.setLevel(Level.ALL);


		try {
			FileHandler fh = new FileHandler("DatabaseConnector.log", true);
			fh.setLevel(Level.ALL);
			databaseLogger.addHandler(fh);
		} catch (IOException e) {
			databaseLogger.log(Level.SEVERE, "File logger not working", e);
		}

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
			databaseLogger.log(Level.SEVERE, "Failed to load config file!");
			e.printStackTrace();
			host = "prclab1.erau.edu";
			userName = "erauprescott";
			passsword = "erauprescott";
			port = "3306";
			databaseName = "sakila";
		} finally {
			file.close();
		}
		String connectionUrl = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
		System.out.println(connectionUrl);
		try {
			database = DriverManager.getConnection(connectionUrl, userName, passsword);
			if (database != null) {
				System.out.println("Successfully connected to " + host);
				databaseLogger.log(Level.INFO, "Successfully connected to " + host);
			}
		} catch (Exception exc) {
			/*Catch block ensrures the database nulls out and alerts the user that the DB was not connected to*/
			System.out.println("COULD NOT CONNECT TO " + host.toUpperCase());
			databaseLogger.log(Level.WARNING, "COULD NOT CONNECT TO " + host.toUpperCase());
			database = null;
			throw exc;
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
		databaseLogger.log(Level.INFO, "Attempting to connect to database " + database + "at host " + host);
		try {
			this.database = DriverManager.getConnection(connectionURL, user, password);
		} catch (SQLException e) {
			System.out.println("Connectioned Failed");
			databaseLogger.log(Level.WARNING, "Connection Failed");
			database = null;
			e.printStackTrace();
		}
	}


	/**
	 * Runs a query against the database                           (1)
	 *
	 * @param  query the properly formatted SQL query.          (3)
	 * @return ResultSet containing the result of the query
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
				databaseLogger.log(Level.INFO, "Database: " + Boolean.toString(database != null) + " closed: "
						+ Boolean.toString(database.isClosed()));
				throw new Exception();
			}
		} catch (Exception exc) {
			System.out.print("Failed to execute query");
			databaseLogger.log(Level.WARNING, "Failed to execute query");
			exc.printStackTrace();
			rs = null;
		}
		return rs;
	}





	/**
	 * Runs a update like an insert or delete on the database
	 *
	 * @param  updateQuery A string containing the properly formatted SQL update query..
	 * @return successfulUpdate 1 if sucessful, 0 if not
	 */
	public int updateDatabase(String updateQuery) {
		int successfulUpdate = 0;
		try {
			if (database != null && !database.isClosed()) {
				dbStatement = database.createStatement();
				dbStatement.executeUpdate(updateQuery);
				successfulUpdate = 1;
			} else {
				System.out.println("Database: " + Boolean.toString(database != null) + " closed: "
						+ Boolean.toString(database.isClosed()));
				databaseLogger.log(Level.INFO, "Database: " + Boolean.toString(database != null) + " closed: "
						+ Boolean.toString(database.isClosed()));
				successfulUpdate = 0;
				throw new Exception();
			}
			
		} catch (Exception e) {
			System.out.print("Failed to execute query");
			databaseLogger.log(Level.WARNING, "Failed to execture query");
			e.printStackTrace();
			successfulUpdate = 0;
		}
		return successfulUpdate;
	}


	/**
	 * Runs a set of updates against the database with transaction mode enabled
	 *
	 * @param  queryList A list-type data structure containing the queries (in order) to be executed. they will either all execute or none will
	 * @return transactionSucess 1 if the transaction is successful, 0 if not	 
	 * */
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

		} catch (SQLException sqlExc) {
			transactionSuccess = 0;
			try {
				database.rollback();
			} catch (SQLException sqlExc1) {
				databaseLogger.log(Level.WARNING, "Error: ", sqlExc1);
				sqlExc1.printStackTrace();
			}
			sqlExc.printStackTrace();
			databaseLogger.log(Level.WARNING, "Error: " , sqlExc);
		} finally {
			try {
				database.setAutoCommit(true);
			} catch (SQLException exc) {
				exc.printStackTrace();
				databaseLogger.log(Level.WARNING, "Fatal Error: ", exc);
				System.exit(0);
			}
		}
		return transactionSuccess;
	}


	public Connection getDatabase() {
		return database;
	}

	/**
	 * Closes the database and frees resources
	 *
	 * 	 * 	 */
	public void close() {
		if (dbStatement != null) {
			try {
				dbStatement.close();
			} catch (SQLException exc) {
				exc.printStackTrace();
				databaseLogger.log(Level.WARNING, "Error: ", exc);
			}
		}
		if (database != null) {
			try {
				database.close();
			} catch (SQLException exc) {
				exc.printStackTrace();
				databaseLogger.log(Level.WARNING, "Error: ", exc);
			}
		}
		dbStatement = null;
		database = null;

	}

	protected void finalize() {
		if (dbStatement != null) {
			try {
				dbStatement.close();
			} catch (SQLException exc) {
				exc.printStackTrace();
				databaseLogger.log(Level.WARNING, "Error: ", exc);
			}
			dbStatement = null;
		}
		if (database != null) {
			try {
				database.close();
			} catch (SQLException exc) {
				exc.printStackTrace();
				databaseLogger.log(Level.WARNING, "Error: ", exc);
			}
			database = null;
		}
	}

}
