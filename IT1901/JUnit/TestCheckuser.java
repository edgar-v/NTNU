import static org.junit.Assert.*;

import org.junit.Test;

import java.awt.List;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.ws.ProtocolException;

public class TestCheckuser {
	
	String serverAddress = "http://129.241.126.66/cgi-bin/checkuser.py";
	
	@Test
	public void CheckCorrectUser() {
		
		
		ArrayList<String> parameters = new ArrayList<String>();
		ArrayList<String> keys = new ArrayList<String>();
		
		keys.add("username");
		parameters.add("edgar");
		
		keys.add("password");
		parameters.add("lol");
		
		String status = dbClass.SendRequest(parameters, keys, serverAddress);
		System.out.println(status);
		assertEquals(status, "OK");
	}
	
	@Test
	public void TestWithWrongPassword() {
		ArrayList<String> parameters = new ArrayList<String>();
		ArrayList<String> keys = new ArrayList<String>();
		
		keys.add("username");
		parameters.add("edgar");
		
		keys.add("password");
		parameters.add("dsadads");
		
		String status = dbClass.SendRequest(parameters, keys, serverAddress);
		System.out.println(status);
		assertEquals(status, "Authorization Required");
	}
	
	@Test
	public void TestWithNoPassword() {
		ArrayList<String> parameters = new ArrayList<String>();
		ArrayList<String> keys = new ArrayList<String>();
		
		keys.add("username");
		parameters.add("edgar");
		
		String status = dbClass.SendRequest(parameters, keys, serverAddress);
		System.out.println(status);
		assertEquals(status, "Bad Request");
	}
	
	@Test
	public void TestWithNoUsername() {
		ArrayList<String> parameters = new ArrayList<String>();
		ArrayList<String> keys = new ArrayList<String>();
		
		keys.add("password");
		parameters.add("dsadads");
		
		String status = dbClass.SendRequest(parameters, keys, serverAddress);
		System.out.println(status);
		assertEquals(status, "Bad Request");
	}
	
	@Test
	public void TestWithNoPasswordAndNoUsername() {
		ArrayList<String> parameters = new ArrayList<String>();
		ArrayList<String> keys = new ArrayList<String>();
		
		String status = dbClass.SendRequest(parameters, keys, serverAddress);
		System.out.println(status);
		assertEquals(status, "Bad Request");
	}
	
	@Test
	public void TestWithEmptyPassword() {
		ArrayList<String> parameters = new ArrayList<String>();
		ArrayList<String> keys = new ArrayList<String>();
		
		keys.add("username");
		parameters.add("edgar");
		
		keys.add("password");
		parameters.add("");
		
		String status = dbClass.SendRequest(parameters, keys, serverAddress);
		System.out.println(status);
		assertEquals(status, "Bad Request");
	}
}
