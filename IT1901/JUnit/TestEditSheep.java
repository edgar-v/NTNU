import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;



public class TestEditSheep {
	
	String serverAddress = "http://129.241.126.66/cgi-bin/editsheep.py";
	
	
	
	@Test
	public void CheckAddNormalUser() {
		ArrayList<String> parameters = new ArrayList<String>();
		ArrayList<String> keys = new ArrayList<String>();
		
		keys.add("sheepid");
		parameters.add("26500");
		
		keys.add("age");
		parameters.add("2011-09-09");
		
		keys.add("weight");
		parameters.add("5");
		
		keys.add("comment");
		parameters.add("Halvd�d");
		
		keys.add("name");
		parameters.add("Boris");
		
		String status = dbClass.SendRequest(parameters, keys, serverAddress);
		System.out.println(status);
		assertEquals(status, "OK");
		status = dbClass.SendRequest(parameters, keys, serverAddress);
		assertEquals(status, "unused");
	}
}

