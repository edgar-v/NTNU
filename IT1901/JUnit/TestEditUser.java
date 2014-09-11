import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;



public class TestEditUser {
	
	String serverAddress = "http://129.241.126.66/cgi-bin/edituser.py";
	
	
	
	@Test
	public void CheckAddNormalUser() {
		ArrayList<String> parameters = new ArrayList<String>();
		ArrayList<String> keys = new ArrayList<String>();
		
		keys.add("username");
		parameters.add("hannemtr");
		
		keys.add("password");
		parameters.add("yjalse");
	

		keys.add("oldpassord");
		parameters.add("yjalse");

		keys.add("email");
		parameters.add("hanne.marie@lifi.no");
		
		keys.add("tlf");
		parameters.add("91039293");
		
		keys.add("name");
		parameters.add("Hanne Marie");
		
		String status = dbClass.SendRequest(parameters, keys, serverAddress);
		System.out.println(status);
		assertEquals(status, "OK");
		status = dbClass.SendRequest(parameters, keys, serverAddress);
		assertEquals(status, "unused");
	}
}

