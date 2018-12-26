package ranking.builder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

import features.js.collectors.core.NC;
import features.js.collectors.core.NPMSCollector;

public class BuildPairwiseRankBySide {
	
	protected static final int FRONT_SIDE = 1;
	protected static final int BACK_SIDE = 0;
	protected static final int FRONT_AND_BACK_SIDE = 2;
	protected static final String ONLY_FRONT = "OnlyFront"; 
	protected static final String ONLY_BACK = "OnlyBack"; 
	protected static final String FRONT = "Front"; 
	protected static final String BACK = "Back";

	public static void main(String[] args) throws IOException {
		
		String npmFeaturesFile = "D:/GHDataset/features/npmsFeaturesExtended.txt";
		String pairwiseRankFile = "D:/GHDataset/rank/pairwiseRank.txt";
		String sidesFile = "D:/GHDataset/concerns/sides.txt";

		
		createPairwiseRankBySide(ONLY_FRONT, npmFeaturesFile, pairwiseRankFile, sidesFile);
		createPairwiseRankBySide(ONLY_BACK, npmFeaturesFile, pairwiseRankFile, sidesFile);
		createPairwiseRankBySide(FRONT, npmFeaturesFile, pairwiseRankFile, sidesFile);
		createPairwiseRankBySide(BACK, npmFeaturesFile, pairwiseRankFile, sidesFile);
		
		
	}

	private static void createPairwiseRankBySide(String dest, String npmFeaturesFile, String pairwiseRankFile, String sidesFile) throws IOException {
		
		FileWriter fw2 = null;
		FileReader fr = null;
		BufferedReader br = null;
		FileReader fr2 = null;
		BufferedReader br2 = null;
		FileReader fr3 = null;
		BufferedReader br3 = null;
		
		try {
		
			fw2 = new FileWriter("D:/GHDataset/rank/pairwiseRank"+dest+".txt",false);
			
			fr = new FileReader(npmFeaturesFile);
			fr2 = new FileReader(pairwiseRankFile);
			fr3 = new FileReader(sidesFile);
		    br = new BufferedReader(fr);
		    br2 = new BufferedReader(fr2);
		    br3 = new BufferedReader(fr3);
		        
		    String linea;
		    
		    HashMap<String, Integer> side = new HashMap<String, Integer>();
		    HashMap<String,String> concerns = new HashMap<String,String>();
	    	String actual_concern = "";
		    while((linea = br3.readLine()) != null){
		    	if(linea.startsWith("//") && linea.split("//").length > 1
		    			&& !concerns.containsKey(linea.split("//")[1])){
		    		actual_concern = linea.split("//")[1];
		    	}else if(linea.split(";").length > 1){
		    		if(linea.split(";")[0].equals("numeral")){
		    			//System.out.println("x");
		    		}
		    		side.put(linea.split(";")[0], Integer.valueOf(linea.split(";")[1]));
		    		concerns.put(linea.split(";")[0],actual_concern);
		    	}
		    }
		    
		    while((linea = br2.readLine()) != null){
		    	String[] pair = linea.split(";");    	
		    	String pair1 = pair[0];
		    	String pair2 = pair[1];
		    	//System.out.println(linea+" "+side.get(pair1)+" "+side.get(pair2));
	    		if(ONLY_FRONT.equals(dest) && side.get(pair1) == FRONT_SIDE && side.get(pair2) == FRONT_SIDE){
		    		//System.out.println(linea);
			    	fw2.write(linea + "\r\n");
	    		}
	    		if(ONLY_BACK.equals(dest) && side.get(pair1) == BACK_SIDE && side.get(pair2) == BACK_SIDE){
		    		//System.out.println(linea);
			    	fw2.write(linea + "\r\n");
	    		}
	    		if(FRONT.equals(dest) && side.get(pair1) >= FRONT_SIDE && side.get(pair2) >= FRONT_SIDE){
		    		//System.out.println(linea);
			    	fw2.write(linea + "\r\n");
	    		}
	    		if(BACK.equals(dest) && side.get(pair1) != FRONT_SIDE && side.get(pair2) != FRONT_SIDE){
		    		//System.out.println(linea);
			    	fw2.write(linea + "\r\n");
	    		}
		    }
		    
		} catch (Exception e1) {
			e1.printStackTrace();
			
		}finally {
			fw2.close();
			fr.close();
			br.close();
			fr2.close();
			br2.close();
			fr3.close();
			br3.close();
		}
		
	}
}

