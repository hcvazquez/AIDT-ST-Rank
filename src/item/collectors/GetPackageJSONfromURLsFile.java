package item.collectors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GetPackageJSONfromURLsFile {
	
	private final String WORKSPACE = "D:/GHDataset/packageJSONs/";

	private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) GraphiQL/0.6.3 Chrome/53.0.2785.143 Electron/1.4.13 Safari/537.36";

	public static void main(String[] args) throws Exception {

		GetPackageJSONfromURLsFile http = new GetPackageJSONfromURLsFile();

		System.out.println("\nTesting - Send Http GET request and save file");
		http.getPkgJSONsFromURLs("D:/GHDataset/ghjspurlsV3.txt");

	}
	
	private void getPkgJSONsFromURLs(String urlsFile) throws Exception{
		FileReader fr = new FileReader(urlsFile);
	    BufferedReader br = new BufferedReader(fr);
	    String linea;
	    while((linea = br.readLine()) != null){
	    	String[] urlParts = linea.split("/");
	    	String response = sendGet(linea);
	    	if(response!=null){
	    		savePkgJsonInfile(WORKSPACE+urlParts[urlParts.length-1]+".json",response);
	    		System.out.println("File saved: "+urlParts[urlParts.length-1]+".json");
	    	}else{
	    		System.out.println(linea);
	    	}
	    }
	}

	// HTTP POST request
	private String sendGet(String projectURL) throws Exception {

		String url = projectURL;
		//https://raw.githubusercontent.com/facebook/react/master/package.json
		url = url.replaceAll("https://github.com/", "https://raw.githubusercontent.com/")+"/master/package.json";
		
		URL obj = new URL(url);
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
			//System.out.println("Warning: Response Code " + responseCode);
			return null;
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
		//System.out.println(response.toString());
		
		return response.toString();

	}

	private static void savePkgJsonInfile(String fichero,String pkgjson) {
	    try {
	      FileWriter fw = new FileWriter(fichero,true);
		  fw.write(pkgjson);
	      fw.close();
	    }
	    catch(Exception e) {
	      System.out.println("Excepcion escribiendo fichero "+ fichero + ": " + e);
	    }
		
	}
}