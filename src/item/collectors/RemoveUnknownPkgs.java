package item.collectors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class RemoveUnknownPkgs{

	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
	
	public static void main(String[] args) throws Exception {
		
		saveRankPackages("D:/GHDataset/relevance/pkgRelevance.txt", null);
		
	}

	public static void saveRankPackages(String pkgCounts, Proxy proxy) throws IOException {
				
		FileWriter fw = null;
		FileWriter fw2 = null;
		FileReader fr = null;
		BufferedReader br = null;
		FileReader fr3 = null;
		BufferedReader br3 = null;
		
		try {
		
			fw = new FileWriter("D:/GHDataset/relevance/pkgRelevanceKnown.txt",false);
			fw2 = new FileWriter("D:/GHDataset/relevance/pkgRelevanceUnknown.txt",false);
			
			fr = new FileReader(pkgCounts);
		    br = new BufferedReader(fr);
		    
		    fr3 = new FileReader("D:/GHDataset/concerns/sides.txt");
		    br3 = new BufferedReader(fr3);
		        
		    ArrayList<String> result = new ArrayList<String>();
		    
		    String linea;
		    
		    ArrayList<String> isInIsFRontFile = new ArrayList<String>();
		    while((linea = br3.readLine()) != null){
		    	if(linea.startsWith("//") && linea.split("//").length > 1){
		    		//System.out.println(linea);
		    	}else if(linea.split(";").length > 1){

		    		isInIsFRontFile.add(linea.split(";")[0]);
		    	}
		    }

		    while((linea = br.readLine()) != null){ 	
		    	if(isInIsFRontFile.contains(linea.split(";")[0])){
		    		fw.write(linea+ "\r\n");
		    	}else{
		    		fw2.write(linea+ "\r\n");
		    	}
		    }    
	      
		    
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally {
			fw.close();
			fw2.close();
			fr.close();
			br.close();
			fr3.close();
			br3.close();
		}

	}

}
