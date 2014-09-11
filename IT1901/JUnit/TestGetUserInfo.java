import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;



public class TestGetUserInfo {
	
	String serverAddress = "http://129.241.126.66/cgi-bin/getuserinfo.py";
	
	
	
	@Test
	public void CheckAddNormalUser() {
		ArrayList<String> parameters = new ArrayList<String>();
		ArrayList<String> keys = new ArrayList<String>();
		
		keys.add("username");
		parameters.add("hannemtr");
		
		String status = dbClass.SendRequest(parameters, keys, serverAddress);
		System.out.println(status);
		assertEquals(status, "OK");
		status = dbClass.SendRequest(parameters, keys, serverAddress);
		assertEquals(status, "unused");
	}
}

