package people;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnector;
import database.QueryMarshaller;

public class ClientBuilder {

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

	public static Client clientBuilder(int id, DatabaseConnector db) {
		ResultSet personRS = db.runQuery(QueryMarshaller.buildGetQuery("Person", id)); 
		return clientBuilder(personRS);
	}
	
	public static String clientInsertQuery(Client client) {
		String fieldList[] = {"firstName", "lastName", "email", "type"};
		Object valueList[] = {client.getFirstName(), client.getLastName(), client.getEmail(), client.getStatus()};
		return QueryMarshaller.buildInsertQuery("Person", fieldList, valueList);
	}

}
