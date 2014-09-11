import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.ws.ProtocolException;


public class dbClass {
	public static String SendRequest(ArrayList<String> parameters, ArrayList<String> keys, String serverAddress) {
		try {
			URL url = new URL(serverAddress);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setReadTimeout(10000);
			connection.connect();
			
			String urlParameters = "";
			if (parameters.size() != 0) {
				urlParameters += keys.get(0) + "=" + parameters.get(0);
			}
			for (int i = 1; i < parameters.size(); i++) {
				urlParameters += "&" + URLEncoder.encode(keys.get(i), "UTF-8") + "=" + URLEncoder.encode(parameters.get(i), "UTF-8");
			}
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			
			connection.connect();
			
			return connection.getResponseMessage();	
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "FAIL";	
	}
}
