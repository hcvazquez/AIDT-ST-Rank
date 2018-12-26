package ranking.builder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;



public class BuildPairwiseRank{

	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
	
	public static void main(String[] args) throws Exception {
		
		saveRankPackages("D:/GHDataset/rank/listwiseRank.txt", null);
		
	}

	public static void saveRankPackages(String pkgRels, Proxy proxy) throws IOException {
				
		FileWriter fw = null;
		FileWriter fw2 = null;

		FileReader fr = null;
		BufferedReader br = null;
		
		try {
		
			fw = new FileWriter("D:/GHDataset/rank/pairwiseRank.txt",false);	
			fw2 = new FileWriter("D:/GHDataset/rank/pairwiseRankInconsistencies.txt",false);	

			fr = new FileReader(pkgRels);
		    br = new BufferedReader(fr);
		    
		    ArrayList<String> pairs = new ArrayList<String>();
		    ArrayList<String> inconsistencies = new ArrayList<String>();
		    ArrayList<String> inconsistenciesAux = new ArrayList<String>();
		    
		    String linea;
		    
		    while((linea = br.readLine()) != null){ 	
		    	String [] arr = linea.split(";");
		    	ArrayList<String> pairList = generatePairs(arr);
		    	for(String p:pairList){
		    		if(!pairs.contains(p)){
		    			pairs.add(p);
		    		}
		    	}
		    }
		    
		    for(String pair:pairs){
			    for(String pair2:pairs){
			    	if(pair.split(";")[0].equals(pair2.split(";")[1]) &&
			    			pair2.split(";")[0].equals(pair.split(";")[1])	){
			    		if(!inconsistencies.contains(pair) &&
			    				!inconsistencies.contains(pair2)){
			    			inconsistencies.add(pair);	
			    		}
			    		inconsistenciesAux.add(pair);
			    		inconsistenciesAux.add(pair2);
			    	}
			    }
		    }
		    
		    Collections.sort(inconsistencies, new Comparator<String>() {
		          @Override
		          public int compare(String pair1, String pair2)
		          {
		              return  pair1.compareTo(pair2);
		          }
		    });
		   
		    
	      for(String line:pairs){
	    	  if(!inconsistenciesAux.contains(line)){
	    		  fw.write(line + "\r\n");
	    	  }
	      }
	      
	      for(String line:inconsistencies){
	    	  fw2.write(line + "\r\n");
	      }
		    
		} catch (Exception e1) {
			System.out.println("Error estableciendo conexion con NPM.");
		}finally {
			fw.close();
		    fw2.close();
			fr.close();
			br.close();
		}

	}

	private static ArrayList<String> generatePairs(String[] arr) {
		ArrayList<String> pairs = new ArrayList<String>();
	    for(int i=0;i<arr.length-1;i++){
	    	String item = arr[i];
		    for(int e=i+1;e<arr.length;e++){
		    	String item2 = arr[e];
		    	pairs.add(item+";"+item2);
		    }
	    }
		return pairs;
	}

}
