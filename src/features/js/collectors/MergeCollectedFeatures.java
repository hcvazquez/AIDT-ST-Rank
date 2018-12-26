package features.js.collectors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import features.js.collectors.core.NPMSCollector;
import ranking.Item;

public class MergeCollectedFeatures {
	
	public static void main(String[] args) throws IOException {
		
		NPMSCollector collector = new NPMSCollector();
		
		FileWriter fw = null;	
		FileReader fr = null;
		BufferedReader br = null;
		FileReader fr2 = null;
		BufferedReader br2 = null;
		
		try {
		
			fw = new FileWriter("D:/GHDataset/Dataset/DB_NPMS_FEATURES.txt",true);	
			
			fr = new FileReader("D:/GHDataset/features/npmCompareFeatures.txt");
			br = new BufferedReader(fr);
			
			fr2 = new FileReader("D:/GHDataset/features/npmsFeatures.txt");
			br2 = new BufferedReader(fr2);
		    
		    String linea;
		    int count = 1;
		    HashMap<String, String> npmsFeatures = new HashMap<String, String>();
		    while((linea = br2.readLine()) != null){
		    	JSONObject json = new JSONObject(linea);
		    	if(!json.keySet().isEmpty()){
		    		npmsFeatures.put(json.getJSONObject("collected").getJSONObject("metadata").getString("name"), linea);
		    	}
		    	//fw.write(linea + "\r\n");
		    }
		    
		    while((linea = br.readLine()) != null){
		    	JSONObject json = new JSONObject(linea);
		    	String line = npmsFeatures.get(json.getString("id"));
		    	if(line == null){
		    		String urlName = linea.split(";")[0].replaceAll("@", "%40").replaceAll("/", "%2F");
		    		line = collector.collect(new Item(urlName)).toString();
		    		fw.write(line + "\r\n");
		    	}	    	
		    	
		    	if(json.has("related_items")){
			    	JSONArray related = json.getJSONArray("related_items");
			    	for(Object rel : related.toList()){
			    		if(npmsFeatures.get((String) rel) == null){
				    		String urlName = ((String) rel).replaceAll("@", "%40").replaceAll("/", "%2F");
				    		line = collector.collect(new Item(urlName)).toString();
				    		if(line.startsWith("{}")){
				    			System.out.println(urlName);
				    		}
				    		fw.write(line + "\r\n");
				    		//System.out.println(line);
			    		}
			    	}
		    	}
	    		System.out.println(count++);
		    }
		    
		} catch (Exception e1) {
			System.out.println("Error estableciendo conexion con NPM.");
			
		}finally {
			fw.close();
			fr.close();
			br.close();
			fr2.close();
			br2.close();
		}
		
	}
}
