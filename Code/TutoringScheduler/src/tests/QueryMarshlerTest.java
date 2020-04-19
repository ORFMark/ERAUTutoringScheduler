package tests;

import database.QueryMarshaller;

public class QueryMarshlerTest {

	public static boolean unitTest() {
		String exampleQuery = "INSERT INTO people(firstName, lastName, isTutor, bio) VALUES (\"Mark\", \"Burrell\", TRUE, \"Junior SE student\");";
		String fieldList[] = {"firstName", "lastName", "isTutor", "bio"};
		Object valueList[] = {"Mark", "Burrell", true, "Junior SE student"};
		String testQuerey = QueryMarshaller.buildInsertQuerey("people", fieldList, valueList);
		if(!testQuerey.equals(exampleQuery)) {
			System.out.print("Queries for field option do not match!\n\t Expected: " + exampleQuery + "\n\t Got: " + testQuerey + "\n");
			return false;
		}
		exampleQuery = "INSERT INTO people VALUES(\"Mark\", \"Burrell\", TRUE, \"Junior SE student\");";
		testQuerey = QueryMarshaller.buildInsertQuerey("people", valueList);
		if(!testQuerey.equals(exampleQuery)) {
			System.out.print("Queries for all option do not match!\n\t Expected: " + exampleQuery + "\n\t Got: " + testQuerey + "\n");
			return false;
		}
		
		exampleQuery ="DELETE FROM appointments WHERE ID = 1;";
		testQuerey = QueryMarshaller.buildDeleteQuerey("appointments", 1);
		if(!testQuerey.equals(exampleQuery)) {
			System.out.print("Delete queries do not match!\n\t Expected: " + exampleQuery + "\n\t Got: " + testQuerey + "\n");
			return false;
		}
		System.out.print("QueryMarshler Unit Test Passed");
		return true;
	}
}
