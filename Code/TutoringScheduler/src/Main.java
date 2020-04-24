import tests.DatabaseConnectorTest;
import tests.AppointmentTest;
import tests.ClientTest;
import tests.QueryMarshlerTest;
import tests.TestRunner;

public class Main {

	public static void main(String[] args) {
		boolean runProduction = false;
		
		if (!runProduction) {
			TestRunner.UnitTest();
		} else {
			//main production code goes here
		}
	}

}
