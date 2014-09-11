import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;



public class TestRecovery {
	
	String serverAddress = "http://129.241.126.66/cgi-bin/recovery.py";
	
	
	
	@Test
	public void CheckAddNormalUser() {
		ArrayList<String> parameters = new ArrayList<String>();
		ArrayList<String> keys = new ArrayList<String>();
		
		keys.add("username");
		parameters.add("hannemtr");

	}
}

