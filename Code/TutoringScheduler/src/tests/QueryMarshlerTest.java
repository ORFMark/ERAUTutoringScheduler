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
		exampleQuery = "INSERT INTO people VALUES(\"Mark\", \"Burrell\", TRUE, \"Junior SE student\");";
		testQuery = QueryMarshaller.buildInsertQuery("people", valueList);
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
		return true;
	}
}
