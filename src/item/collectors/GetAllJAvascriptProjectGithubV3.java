package item.collectors;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetAllJAvascriptProjectGithubV3 {

	private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) GraphiQL/0.6.3 Chrome/53.0.2785.143 Electron/1.4.13 Safari/537.36";

	public static void main(String[] args) throws Exception {

		GetAllJAvascriptProjectGithubV3 http = new GetAllJAvascriptProjectGithubV3();

		System.out.println("\nTesting - Send Http GET request and save file");
		http.saveGHurlsInfile(http.collectUrlsFromPosts());

	}

	// HTTP POST request
	private String sendGet(String index) throws Exception {

		String url = "https://api.github.com/search/repositories";
			
		String query = "q=is:public+language:JavaScript+pushed:>2018-01-01";
		String page = "page="+index;
		String type = "type=Repositories";
		String parameters = "?"+query+"&"+type+"&"+page;
		
		URL obj = new URL(url+parameters);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest heade
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("content-type", "application/json; charset=utf-8");
		//con.setRequestProperty("authorization", "Bearer edbaee93393b999684ee6ffbc2b3a26a882a718a");
		//String urlParameters = query;

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'GET' request to URL : " + url);
		//System.out.println("Post parameters : " + jsonObj.toString());
		if(responseCode != 200){
			System.out.println("Warning: Response Code " + responseCode);
		}
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
		
		return response.toString();

	}
	
	public ArrayList<String> collectUrlsFromPosts() throws Exception{
		
		ArrayList<String> urls = new ArrayList<String>();
		
		Integer index = 31;
		String response = sendGet(index.toString());
		
		JSONObject jsonObj = new JSONObject(response);
		
		int repositoryCount = jsonObj.getInt("total_count");
		
		JSONArray edges = jsonObj.getJSONArray("items");
		for(int i=0; i<edges.length(); i++){
			JSONObject node = edges.getJSONObject(i);
			urls.add(node.getString("html_url"));
		}
		
		int remainCount = repositoryCount - urls.size();
		System.out.println("Remaining repositories: "+remainCount);
		
		while(remainCount > 0 && index < 34){
			index++;
			response = sendGet(index.toString());
			
			jsonObj = new JSONObject(response);	
			
			edges = jsonObj.getJSONArray("items");
			for(int i=0; i<edges.length(); i++){
				JSONObject node = edges.getJSONObject(i);
				urls.add(node.getString("html_url"));
			}
			
			remainCount = repositoryCount - urls.size();
			System.out.println("Remaining repositories: "+remainCount);
			System.out.println("Number of page: "+ index);
			
			Thread.sleep((long)(Math.random() * 1000));
		}
		
		return urls;
	}

	private static void saveGHurlsInfile(ArrayList<String> urls) {
		String fichero = "D:/ghjspurlsV3.txt";
	    try {
	      FileWriter fw = new FileWriter(fichero,true);
	      for(String url:urls){
		  	fw.write(url + "\r\n");    	  
	      }
	      fw.close();
	    }
	    catch(Exception e) {
	      System.out.println("Excepcion escribiendo fichero "+ fichero + ": " + e);
	    }
		
	}
}