package features.js.collectors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import features.js.collectors.core.NC;
import features.js.collectors.core.NPMSCollector;
import ranking.Item;

public class CollectFeaturesRelatedFromNPMs {
	
	public static void main(String[] args) throws IOException {
		
		NPMSCollector collector = new NPMSCollector();
		
		FileWriter fw = null;	
		FileReader fr = null;
		BufferedReader br = null;
		FileReader fr2 = null;
		BufferedReader br2 = null;
		
		try {
		
			fw = new FileWriter("D:/GHDataset/features/npmsFeaturesExtended.txt",false);	
			fr = new FileReader("D:/GHDataset/features/npmsFeatures.txt");
			fr2 = new FileReader("D:/GHDataset/features/npmCompareFeatures.txt");	
			
		    br = new BufferedReader(fr);
		    br2 = new BufferedReader(fr2);
		    
		    String linea;
		    
		    ArrayList<String> pkgs = new ArrayList<String>();
		    ArrayList<String> result = new ArrayList<String>();
		    while((linea = br.readLine()) != null){
		    	System.out.println(linea);
		    	result.add(linea);
		    	JSONObject item = new JSONObject(linea);
		    	pkgs.add(item.getJSONObject(NC.S_COLLECTED).getJSONObject(NC.S_METADATA).getString(NC.NAME));
		    }
		    
		    while((linea = br2.readLine()) != null){ 	;
		    	JSONObject compareFeatures = new JSONObject(linea);
		    	if(compareFeatures.has(NC.RELATED_ITEMS)){
					JSONArray relatedItems = compareFeatures.getJSONArray(NC.RELATED_ITEMS);
					for(String rel:(List<String>)(Object)relatedItems.toList()){
						if(!pkgs.contains(rel)){
							System.out.println("Downloading... "+rel);
							String line = "";
							if(rel.contains("@")||
									rel.contains("/")){
						    	String urlName = rel.replaceAll("@", "%40").replaceAll("/", "%2F");
						    	line = collector.collect(new Item(urlName)).toString();
					    	}else{
						    	line = collector.collect(new Item(rel)).toString();
					    	}
					    	if(!"{}".equals(line)){
					    		result.add(line);
					    	}
						}
					}
		    	}
		    }
		    
		   for(String line:result){
				fw.write(line+"\r\n");
		   }

		    
		} catch (Exception e1) {
			System.out.println("Error estableciendo conexion con NPM." + e1.getMessage());
			
		}finally {
			fw.close();
			fr.close();
			br.close();
		}
		
	}
}
