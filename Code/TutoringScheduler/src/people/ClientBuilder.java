package people;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnector;
import database.QueryMarshaller;

public class ClientBuilder {

	/**
	 * Builds a Client out of a SQL Resultset. Must be on the row to build
	 *
	 * @param  personRS the ResultSet of the Client to be represented
	 * @return a client on success, null on failure
	 *  
	 * */
	public static Client clientBuilder(ResultSet personRS) {
		Client client;
		try {
			client = new Client(personRS.getInt("ID"), personRS.getString("firstName")
					, personRS.getString("lastName"), personRS.getString("email")
					, Position.valueOf(personRS.getString("type")));
		} catch (SQLException e) {
			client = null;
			e.printStackTrace();
		}
		return client;
	}

	/**
	 * gets a client with the specified ID from the Datbasebase Connector db
	 *
	 * @param  id integer ID of the client
	 * @param  db The databaseConnector attached to the DB
	 * @return a client on success, null on failure
	 *  
	 * */
	public static Client clientBuilder(int id, DatabaseConnector db) {
		ResultSet personRS = db.runQuery(QueryMarshaller.buildGetQuery("Person", id));
		try {
			if(personRS.next()) {
				return clientBuilder(personRS);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * builds a SQL Insert Query for a client
	 *
	 * @param  client the Client to be inserted
	 * @param 
	 * @return a string containing the SQL query to insert the client into the DB
	 *  
	 * */
	public static String clientInsertQuery(Client client) {
		String fieldList[] = {"firstName", "lastName", "email", "type"};
		Object valueList[] = {client.getFirstName(), client.getLastName(), client.getEmail(), client.getStatus().toString()};
		return QueryMarshaller.buildInsertQuery("Person", fieldList, valueList);
	}

}
