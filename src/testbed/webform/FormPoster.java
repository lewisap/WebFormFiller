package testbed.webform;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class FormPoster {
	public static final String URL = "http://www.wildstar-online.com/global/includes/php/beta_signup.php";
	
	// config settings read from settings.properties
	public static String MAIL_PREFIX;
	public static String MAIL_SUFFIX;
	public static Integer WAIT_DELAY;
	public static Integer START_NUM;
	public static Integer STOP_NUM;
	
	public static List<String> getEmailAddresses() {
		List<String> addresses = new ArrayList<String>();
		for (int i = START_NUM; i <= STOP_NUM; i++) {
			addresses.add(MAIL_PREFIX + i + MAIL_SUFFIX);
		}
		
		return addresses;
	}
	
	public static void initProperties() {
		Properties props = new Properties();
		
		try {
			props.load(new FileInputStream("settings.properties"));
			
			MAIL_PREFIX 	= props.getProperty("prefix");
			MAIL_SUFFIX 	= props.getProperty("suffix");
			WAIT_DELAY 		= Integer.parseInt(props.getProperty("delay"));
			START_NUM 		= Integer.parseInt(props.getProperty("start"));
			STOP_NUM 		= Integer.parseInt(props.getProperty("stop"));
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
	
	public static List<NameValuePair> getFormParams(String email) {
		/*
		 * "name":"Bob%20Barker"
		 * "email":"bob@aol.com"
		 * "country":"United%20States%20of%20America"
		 * "connection-speed":"DSL%20%20Cable"
		 * "career":"none"
		 * "company":""
		 * "site":""
		 * "group_name":""
		 * "group-leader-name":""
		 * "group-leadership-email":""
		 * "group-size":"10"
		 * "group-website":""
		 * "meet_system_requirements":"Yes"
		 * "belong_gaming_groups":"No"
		 * "game_titles":"{}"
		 * "forum_nicknames":"{}"
		 * "language":"en"
		 */
		// 2 real params:  'data' and 'lan'
		List <NameValuePair> params = new ArrayList <NameValuePair>();
		
		// build the 'data' from the post here
		StringBuffer buff = new StringBuffer();
		buff.append("{");
		
		buff.append("\"name\":\"Adam\",");
		buff.append("\"email\":\"" + email + "\",");
		buff.append("\"country\":\"United%20States%20of%20America\",");
		buff.append("\"connection-speed\":\"DSL%20%20Cable\",");
		buff.append("\"career\":\"none\",");
		buff.append("\"company\":\"\",");
		buff.append("\"site\":\"\",");
		buff.append("\"group-name\":\"\",");
		buff.append("\"group-leader-name\":\"\",");
		buff.append("\"group-leadership-email\":\"\",");
		buff.append("\"group-size\":\"10\",");
		buff.append("\"group-website\":\"\",");
		buff.append("\"meet_system_requirements\":\"Yes\",");
		buff.append("\"belong_gaming_groups\":\"No\",");
		buff.append("\"game_titles\":\"{}\",");
		buff.append("\"forum_nicknames\":\"{}\",");
		buff.append("\"language\":\"en\"");
		
		buff.append("}");
		
		//System.out.println(buff.toString());
		
		params.add(new BasicNameValuePair("data", buff.toString()));
		params.add(new BasicNameValuePair("lan", "en"));
        
        return params;
	}
	
	public static void setPostHeaders(HttpPost post) {
		post.setHeader("Accept-Encoding", "gzip, deflate");
		post.setHeader("Cache-Control", "no-cache");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Host", "www.wildstar-online.com");
		post.setHeader("Referer", "http://www.wildstar-online.com/en/beta/");
		post.setHeader("X-Requested-With", "XMLHttpRequest");
	}
	
	public static void main(String[] args) throws Exception {
		initProperties();

		for(String email: getEmailAddresses()) {
			System.out.println("PROCESSING: " + email);
        	HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(URL);
            setPostHeaders(post);
            post.setEntity(new UrlEncodedFormEntity(getFormParams(email)));
            
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
            	System.out.println("\t\t" + line);
            }
            
            post.releaseConnection(); 
            TimeUnit.MINUTES.sleep(WAIT_DELAY);
        }
	}
}
