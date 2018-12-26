package features.js.collectors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import features.js.collectors.core.NPMSCollector;
import ranking.Item;

public class CollectFeaturesFromNPMs {
	
	public static void main(String[] args) throws IOException {
		
		NPMSCollector collector = new NPMSCollector();
		
		FileWriter fw = null;	
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
		
			fw = new FileWriter("D:/GHDataset/features/npmsFeatures.txt",false);	
			fr = new FileReader("D:/GHDataset/relevance/pkgRelevance.txt");
			
		    br = new BufferedReader(fr);
		    
		    String linea;
		    int count = 1;
		    while((linea = br.readLine()) != null){
		    	String line = null;
		    	if(linea.split(";")[0].contains("@")||
		    			linea.split(";")[0].contains("/")){
			    	String urlName = linea.split(";")[0].replaceAll("@", "%40").replaceAll("/", "%2F");
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
