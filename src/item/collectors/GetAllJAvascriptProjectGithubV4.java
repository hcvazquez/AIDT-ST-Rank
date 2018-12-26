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

public class GetAllJAvascriptProjectGithubV4 {

	private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) GraphiQL/0.6.3 Chrome/53.0.2785.143 Electron/1.4.13 Safari/537.36";

	public static void main(String[] args) throws Exception {

		GetAllJAvascriptProjectGithubV4 http = new GetAllJAvascriptProjectGithubV4();

		System.out.println("\nTesting - Send Http POST request and save file");
		http.saveGHurlsInfile(http.collectUrlsFromPosts());

	}

	// HTTP POST request
	private String sendPost(String index) throws Exception {

		String url = "https://api.github.com/graphql";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		
		String query = null;
		if(index == null){
			query = "{"
        		+ "search(query: \"is:public language:JavaScript pushed:>2018-01-01\", type: REPOSITORY, first: 99) {"
        		+ "repositoryCount    "
        		+ "pageInfo {"
        		+ "      endCursor"
        		+ "      startCursor"
        		+ "      hasNextPage"
        		+ " }    "
        		+ "edges {"
        		+ "      node {"
        		+ "        ... on Repository {"
        		+ "          name"
        		+ "          url"
        		+ "          createdAt"
        		+ "          updatedAt"
        		+ "          primaryLanguage {"
        		+ "            name"
        		+ "          }"
        		+ "        }"
        		+ "      }"
        		+ "    }"
        		+ "  }"
        		+ "}";
		}else{
			query = "{"
	        		+ "search(query: \"is:public language:JavaScript pushed:>2018-01-01\", type: REPOSITORY, first: 100, after:\""+index+"\") {"
	        		+ "repositoryCount    "
	        		+ "pageInfo {"
	        		+ "      endCursor"
	        		+ "      startCursor"
	        		+ "      hasNextPage"
	        		+ " }    "
	        		+ "edges {"
	        		+ "      node {"
	        		+ "        ... on Repository {"
	        		+ "          name"
	        		+ "          url"
	        		+ "          createdAt"
	        		+ "          updatedAt"
	        		+ "          primaryLanguage {"
	        		+ "            name"
	        		+ "          }"
	        		+ "        }"
	        		+ "      }"
	        		+ "    }"
	        		+ "  }"
	        		+ "}";
		}
		
        JSONObject jsonObj = new JSONObject();     
        jsonObj.put("query", query);

        //add reuqest heade
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("content-type", "application/json; charset=utf-8");
		con.setRequestProperty("authorization", "Bearer edbaee93393b999684ee6ffbc2b3a26a882a718a");
		//String urlParameters = query;

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(jsonObj.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
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
		
		String response = sendPost(null);
		
		JSONObject jsonObj = new JSONObject(response);
		
		int repositoryCount = jsonObj.getJSONObject("data").getJSONObject("search").getInt("repositoryCount");
		
		JSONArray edges = jsonObj.getJSONObject("data").getJSONObject("search").getJSONArray("edges");
		for(int i=0; i<edges.length(); i++){
			JSONObject node = edges.getJSONObject(i).getJSONObject("node");
			urls.add(node.getString("url"));
		}
		
		int remainCount = repositoryCount - urls.size();
		System.out.println("Remaining repositories: "+remainCount);
		
		JSONObject pageInfo =  jsonObj.getJSONObject("data").getJSONObject("search").getJSONObject("pageInfo");
		Boolean hasNextPage = Boolean.valueOf(pageInfo.get("hasNextPage").toString());
		
		while(hasNextPage){
			String index = pageInfo.get("endCursor").toString();
			response = sendPost(index);
			
			jsonObj = new JSONObject(response);	
			
			edges = jsonObj.getJSONObject("data").getJSONObject("search").getJSONArray("edges");
			for(int i=0; i<edges.length(); i++){
				JSONObject node = edges.getJSONObject(i).getJSONObject("node");
				urls.add(node.getString("url"));
			}
			
			pageInfo =  jsonObj.getJSONObject("data").getJSONObject("search").getJSONObject("pageInfo");
			hasNextPage = Boolean.valueOf(pageInfo.get("hasNextPage").toString());
			
			remainCount = repositoryCount - urls.size();
			System.out.println("Remaining repositories: "+remainCount);
		}
		
		return urls;
	}

	private static void saveGHurlsInfile(ArrayList<String> urls) {
		String fichero = "D:/ghjspurls.txt";
	    try {
	      FileWriter fw = new FileWriter(fichero,false);
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