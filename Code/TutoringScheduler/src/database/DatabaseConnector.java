package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnector {
	private Connection database;
	private Statement smt;
	private Properties config;
	private boolean isValid;
	public DatabaseConnector() throws Exception {
		isValid = false;
		String user = null;
		String pass = null;
		String port = null;
		String host = null;
		String db = null;
		FileInputStream file = null;
		config = new Properties();
		try {
			file = new FileInputStream("config.properties");
			config.load(file);
			user = config.getProperty("UserName");
			pass = config.getProperty("Password");
			port = config.getProperty("port");
			host = config.getProperty("host");
			db = config.getProperty("DB");
			
		} catch (Exception e) {
			System.out.println("Failed to load config file!");
			e.printStackTrace();
			host = "prclab1.erau.edu";
			user = "erauprescott";
			pass = "erauprescott";
			port = "3306";
			db = "sakila";
		} finally {
			file.close();
		}
		String connectionUrl = "jdbc:mysql://" + host + ":" + port + "/" + database;
		try {
			database = DriverManager.getConnection(connectionUrl, user, pass);
			if (database != null) {
				System.out.println("Successfully connected to " + host);
			}
		} catch (Exception e) {
			System.out.println("COULD NOT CONNECT TO " + host.toUpperCase());
			database = null;
			throw e;
		}
	}

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

	public ResultSet runQuery(String query) {
		ResultSet rs = null;
		try {
			if (database != null && !database.isClosed()) {
				smt = database.createStatement();
				rs = smt.executeQuery(query);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void updateDatabase(String updateQuery) {
		try {
			if (database != null && !database.isClosed()) {
				smt = database.createStatement();
				smt.executeUpdate(updateQuery);
			} else {
				System.out.println("Database: " + Boolean.toString(database != null) + " closed: "
						+ Boolean.toString(database.isClosed()));
			}
		} catch (Exception e) {
			System.out.print("Failed to execute querey");
			e.printStackTrace();
		}
	}


	public Connection getDatabase() {
		return database;
	}

	public void close() {
		if (smt != null) {
			try {
				smt.close();
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

	}
	protected void finalize() {
		if (smt != null) {
			try {
				smt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			smt = null; 
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
