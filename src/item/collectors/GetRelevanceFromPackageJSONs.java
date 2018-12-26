package item.collectors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

public class GetRelevanceFromPackageJSONs {
	
	private final String WORKSPACE = "D:/GHDataset/relevance/";
	private final String PKG_FOLDER = "D:/GHDataset/packageJSONs/";
	
	
	private final int TOTAL_PROJECTS = 1020; //TODO remove harcoded value

	private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) GraphiQL/0.6.3 Chrome/53.0.2785.143 Electron/1.4.13 Safari/537.36";

	public static void main(String[] args) throws Exception {

		GetRelevanceFromPackageJSONs http = new GetRelevanceFromPackageJSONs();

		System.out.println("\nTesting - Send Http GET request and save file");
		http.getPkgJSONsFromURLsCounted("D:/GHDataset/ghjspurlsV3.txt");

	}
	

	private void getPkgJSONsFromURLsCounted(String urlsFile) throws Exception{
		
		HashMap<String,Double> devDependencies = new HashMap<String,Double>();
		HashMap<String,Double> dependencies = new HashMap<String,Double>();
		
		FileReader fr = new FileReader(urlsFile);
	    BufferedReader br = new BufferedReader(fr);
	    
	    String linea;
	    int line_number = 0;
	    while((linea = br.readLine()) != null){
	    	String[] urlParts = linea.split("/");
	    	String json = getJSONPackage(linea);
	    	if(json!=null){
	    		ArrayList<String> deps = getDependencies(json,false);
	    		for(String dep:deps){
	    			if(!dependencies.containsKey(dep)){
	    				dependencies.put(dep,getPkgRelevance(line_number));
	    			}else{
	    				dependencies.put(dep,dependencies.get(dep)+getPkgRelevance(line_number));
	    			}
	    		}
	    		deps = getDependencies(json,true);
	    		for(String dep:deps){
	    			if(!devDependencies.containsKey(dep)){
	    				devDependencies.put(dep,getPkgRelevance(line_number));
	    			}else{
	    				devDependencies.put(dep,devDependencies.get(dep)+getPkgRelevance(line_number));
	    			}
	    		}
	    	}else{
	    		System.out.println(linea);
	    	}
	    	line_number++;
	    }
	    saveDependenciesCountInfile(WORKSPACE+"pkgRelevance.txt",dependencies);
	    saveDependenciesCountInfile(WORKSPACE+"devPkgRelevance.txt",devDependencies);
		System.out.println("Files saved in "+WORKSPACE);
	}
	
	private Double getPkgRelevance(int line_number) {
		double projectRelevance = TOTAL_PROJECTS-line_number;
		System.out.println(projectRelevance+" log = "+ (Math.log(1+projectRelevance)/Math.log(10)));
		return Math.log(1+projectRelevance);
	}


	private String getJSONPackage(String linea) throws Exception {
		String[] urlParts = linea.split("/");
		String file = PKG_FOLDER+urlParts[urlParts.length-1]+".json";
		String json = getJSONPackageFromDisk(file);
		if(json==null){
			//json = getJSONPackageFromGH(linea);
		}	
		return json;
	}


	private String getJSONPackageFromDisk(String file) {
	    try {
	      FileReader fr = new FileReader(file);
	      BufferedReader br = new BufferedReader(fr);
	      return br.readLine();
	    }
	    catch(Exception e) {
	      //System.out.println("No se encontro en el disco el archivo "+ file);
	      return null;
	    }		
	}


	public static ArrayList<String> getDependencies(String linea, boolean dev) {
	      ArrayList<String> dependencies = new ArrayList<String>();  
		 
	      JsonReader rdr = Json.createReader(new StringReader(linea)); 
	      JsonObject obj = rdr.readObject();
	      JsonObject devDependencies = dev?obj.getJsonObject("devDependencies"):obj.getJsonObject("dependencies");
	      if(devDependencies!=null){
		      for(String dependency:devDependencies.keySet()){
		    	  dependencies.add(dependency); 
		      }
	      }

		  return dependencies;

	}

	// HTTP POST request
	private String getJSONPackageFromGH(String projectURL) throws Exception {

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

	private static void saveDependenciesInfile(String fichero, ArrayList<String> dependencies) {
	    try {
	      FileWriter fw = new FileWriter(fichero,false);
	      for(String dep:dependencies){
	    	 fw.write(dep+ "\r\n");
	      }
	      fw.close();
	    }
	    catch(Exception e) {
	      System.out.println("Excepcion escribiendo fichero "+ fichero + ": " + e);
	    }
		
	}
	
	private static void saveDependenciesCountInfile(String fichero, HashMap<String, Double> dependencies) {
	    try {
	      FileWriter fw = new FileWriter(fichero,false);
	      ArrayList<String> deps = new ArrayList<String>();
	      deps.addAll(dependencies.keySet());
	      Collections.sort(deps, new Comparator<String>() {
	          @Override
	          public int compare(String dep2, String dep1)
	          {
	              return  dependencies.get(dep1).compareTo(dependencies.get(dep2));
	          }
	      });
	      for(String dep:deps){
	    	 fw.write(dep+";"+dependencies.get(dep)+ "\r\n");
	      }
	      fw.close();
	    }
	    catch(Exception e) {
	      System.out.println("Excepcion escribiendo fichero "+ fichero + ": " + e);
	    }
		
	}
}