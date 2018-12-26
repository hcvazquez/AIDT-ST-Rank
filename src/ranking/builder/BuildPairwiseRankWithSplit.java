package ranking.builder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;



public class BuildPairwiseRankWithSplit{

	protected static final String ONLY_FRONT = "OnlyFront"; 
	protected static final String ONLY_BACK = "OnlyBack"; 
	protected static final String FRONT = "Front"; 
	protected static final String BACK = "Back";
	protected static final String ALL = "";
	
	protected static final String TRAIN = "Train";
	protected static final String TEST = "Test";

	public static void main(String[] args) throws IOException {
		
		String listwiseRankFile = "D:/GHDataset/rank/splits/listwiseRank";
		String pairwiseRankFile = "D:/GHDataset/rank/splits/pairwiseRank";
		
		createPairwiseRankPackages(ONLY_FRONT, listwiseRankFile, pairwiseRankFile,TRAIN);
		createPairwiseRankPackages(ONLY_BACK, listwiseRankFile, pairwiseRankFile,TRAIN);
		createPairwiseRankPackages(FRONT, listwiseRankFile, pairwiseRankFile,TRAIN);
		createPairwiseRankPackages(BACK, listwiseRankFile, pairwiseRankFile,TRAIN);
		createPairwiseRankPackages(ALL, listwiseRankFile, pairwiseRankFile,TRAIN);
		
		createPairwiseRankPackages(ONLY_FRONT, listwiseRankFile, pairwiseRankFile,TEST);
		createPairwiseRankPackages(ONLY_BACK, listwiseRankFile, pairwiseRankFile,TEST);
		createPairwiseRankPackages(FRONT, listwiseRankFile, pairwiseRankFile,TEST);
		createPairwiseRankPackages(BACK, listwiseRankFile, pairwiseRankFile,TEST);
		createPairwiseRankPackages(ALL, listwiseRankFile, pairwiseRankFile,TEST);
		
	}

	public static void createPairwiseRankPackages(String dest, String listwiseRankFile,String pairwiseRankFile, String split) throws IOException {
				
		FileWriter fw = null;
		FileWriter fw2 = null;

		FileReader fr = null;
		BufferedReader br = null;
		
		try {
		
			fw = new FileWriter(pairwiseRankFile+dest+split+".txt",false);	
			fw2 = new FileWriter(pairwiseRankFile+split+"Inconsistencies.txt",false);	

			fr = new FileReader(listwiseRankFile+dest+split+".txt");
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
