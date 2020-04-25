package tests;

import database.QueryMarshaller;

public class QueryMarshlerTest {

	public static boolean unitTest() {
		String exampleQuery = "INSERT INTO people(firstName, lastName, isTutor, bio) VALUES (\"Mark\", \"Burrell\", TRUE, \"Junior SE student\");";
		String fieldList[] = {"firstName", "lastName", "isTutor", "bio"};
		Object valueList[] = {"Mark", "Burrell", true, "Junior SE student"};
		String testQuery = QueryMarshaller.buildInsertQuery("people", fieldList, valueList);
		if(!testQuery.equals(exampleQuery)) {
			System.out.print("Queries for field option do not match!\n\t Expected: " + exampleQuery + "\n\t Got: " + testQuery + "\n");
			return false;
		}
		exampleQuery = "INSERT INTO people VALUES(\"Mark\", \"Burrell\", TRUE, \"Junior SE student\", 244);";
		Object newValList[] = {"Mark", "Burrell", true, "Junior SE student", 244};
		testQuery = QueryMarshaller.buildInsertQuery("people", newValList);
		if(!testQuery.equals(exampleQuery)) {
			System.out.print("Queries for all option do not match!\n\t Expected: " + exampleQuery + "\n\t Got: " + testQuery + "\n");
			return false;
		}
		
		exampleQuery ="DELETE FROM appointments WHERE ID = 1;";
		testQuery = QueryMarshaller.buildDeleteQuery("appointments", 1);
		if(!testQuery.equals(exampleQuery)) {
			System.out.print("Delete queries do not match!\n\t Expected: " + exampleQuery + "\n\t Got: " + testQuery + "\n");
			return false;
		}
		exampleQuery = "SELECT * FROM person WHERE ID = 1;";
		testQuery = QueryMarshaller.buildGetQuery("person", 1);
		if(!exampleQuery.equals(testQuery)) {
			System.out.print("get queries do not match!\n\t Expected: " + exampleQuery + "\n\t Got: " + testQuery + "\n");
			return false;
		}
		exampleQuery = "SELECT * FROM person WHERE NUM = 1;";
		testQuery = QueryMarshaller.buildGetQuery("person", "NUM", 1);
		if(!exampleQuery.equals(testQuery)) {
			System.out.print("get queries do not match!\n\t Expected: " + exampleQuery + "\n\t Got: " + testQuery + "\n");
			return false;
		}
		return true;
	}
}