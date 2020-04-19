package people;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnector;
import database.QueryMarshaller;

public class PersonBuilder {

	public Person personBuilder(ResultSet personRS) {
		Person person;
		try {
			person = new Person(personRS.getInt("ID"), personRS.getString("firstName")
					, personRS.getString("lastName"), personRS.getString("email")
					, Position.valueOf(personRS.getString("type")));
		} catch (SQLException e) {
			person = null;
			e.printStackTrace();
		}
		return person;
	}

	public Person personBuilder(int id, DatabaseConnector db) {
		ResultSet personRS = db.runQuery(QueryMarshaller.buildGetQuery("Person", id)); 
		return personBuilder(personRS);
	}
	
	public String personInsertQuery(Person person) {
		String fieldList[] = {"firstName", "lastName", "email", "type"};
		Object valueList[] = {person.getFirstName(), person.getLastName(), person.getEmail(), person.getStatus()};
		return QueryMarshaller.buildInsertQuerey("Person", fieldList, valueList);
	}

}
