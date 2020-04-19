import tests.DatabaseConnectorTest;
import tests.PersonTest;
import tests.QueryMarshlerTest;
import tests.TestRunner;

public class Main {

	public static void main(String[] args) {
		boolean runProduction = false;
		
		if (!runProduction) {
			QueryMarshlerTest.unitTest();
		} else {
			//main production code goes here
		}
	}

}
