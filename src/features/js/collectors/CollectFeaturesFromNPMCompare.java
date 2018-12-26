package features.js.collectors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import features.js.collectors.core.NPMCompareCollector;
import ranking.Item;

public class CollectFeaturesFromNPMCompare {
	
	public static void main(String[] args) throws IOException {
		
		NPMCompareCollector collector = new NPMCompareCollector();
		
		FileWriter fw = null;	
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
		
			fw = new FileWriter("D:/GHDataset/features/npmCompareFeatures.txt",false);	
			fr = new FileReader("D:/GHDataset/relevance/pkgRelevance.txt");
			
		    br = new BufferedReader(fr);
		    
		    String linea;
		    int count = 1;
		    
		    ArrayList<String> pkgs = new ArrayList<String>();
		    while((linea = br.readLine()) != null){
		    	pkgs.add(linea.split(";")[0]);
		    }
		    
		    for(String pkg:pkgs){
		    	String line = null;
		    	if(pkg.contains("@")||
		    			pkg.contains("/")){
			    	String urlName = pkg.replaceAll("@", "%40").replaceAll("/", "%2F");
			    	line = collector.collect(new Item(urlName)).toString();
		    	}else{
			    	line = collector.collect(new Item(linea.split(";")[0])).toString();
		    	}
		    	if(!"{}".equals(line)){
		    		fw.write(line + "\r\n");
		    		System.out.println(count++);
		    	}
		    }
		    
		} catch (Exception e1) {
			System.out.println("Error estableciendo conexion con NPM.");
			
		}finally {
			fw.close();
			fr.close();
			br.close();
		}
		
	}
}
