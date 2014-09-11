import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


public class TestRegSheep {
	
	String serverAddress = "http://129.241.126.66/cgi-bin/regsheep.py";
	
	@Test
	public void CheckAddNormalUser() {
		ArrayList<String> parameters = new ArrayList<String>();
		ArrayList<String> keys = new ArrayList<String>();
			
		keys.add("username");
		parameters.add("test");
			
		keys.add("health");
		parameters.add("healthy");
			
		keys.add("weight");
		parameters.add("120");
			
		keys.add("age");
		parameters.add("6");
			
		keys.add("name");
		parameters.add("Testsheep");
			
		String status = dbClass.SendRequest(parameters, keys, serverAddress);
		System.out.println(status);
		assertEquals(status, "OK");
	}
}
