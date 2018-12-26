package ranking.builder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.json.JSONObject;

import features.FeatureCalculator;
import features.js.collectors.core.NC;
import features.js.collectors.core.NPMCompareCollector;
import features.js.collectors.core.NPMRegistryCollector;
import features.js.collectors.core.NPMSCollector;
import ranking.Item;

public class CreateDataSetScores {
	
	protected static final String MENOR = "0";
	protected static final String MAYOR = "1";
	private static final int NUMBER_OF_FEATURES = 4;
	
    
    //TEMP
    static HashMap<String, Integer> depsCount = new HashMap<String, Integer>();
    //FIN TEMP
    

	public static void main(String[] args) throws IOException {
		
		NPMCompareCollector collector = new NPMCompareCollector();
		
		FileWriter fw = null;
		FileWriter fw2 = null;

		FileReader fr = null;
		BufferedReader br = null;
		FileReader fr2 = null;
		BufferedReader br2 = null;
		
		try {
		
			fw = new FileWriter("D:/GHDataset/datasets/dataSetPropuestaCSVScores",false);
			fw2 = new FileWriter("D:/GHDataset/features/npmCompareFeaturesExtended.txt",false);
			
			fr = new FileReader("D:/GHDataset/features/npmsFeaturesExtended.txt");
			fr2 = new FileReader("D:/GHDataset/features/npmCompareFeatures.txt");
		    br = new BufferedReader(fr);
		    br2 = new BufferedReader(fr2);
		    
		    ArrayList<String> features = new ArrayList<String>();
		    HashMap<String,Double> comparator = new HashMap<String,Double>();
		    ArrayList<String> uniques = new ArrayList<String>();
		    
		    String linea;
		    while((linea = br2.readLine()) != null){
		    	fw2.write(linea+"\r\n");
		    	try{
		    		JSONObject jitem = new JSONObject(linea);
			    	String name = jitem.getString(NC.ID);
			    	Double score = jitem.getDouble(NC.NPM_COMPARATOR_POINTS);
			    	comparator.put(name, score);
		    	}catch(Exception e){
		    		JSONObject jitem = new JSONObject(linea);
			    	String name = jitem.getString(NC.ID);
			    	comparator.put(name, 0.0);
		    		//System.out.println(l);
		    	}
		    }
		    
		    while((linea = br.readLine()) != null){
		    	JSONObject jitem = new JSONObject(linea);
		    	String name = jitem.getJSONObject(NC.S_COLLECTED).getJSONObject(NC.S_METADATA).getString(NC.NAME);
		    	if(!uniques.contains(name)){
		    		String featureVector = name +
		    				"," + getScoreVector(jitem);
		    		uniques.add(name);
		    		Double score = comparator.get(name);
		    		if(score==null){
		    			JSONObject json = null;
		    			if(name.contains("@")||
				    			name.contains("/")){
					    	String urlName = name.replaceAll("@", "%40").replaceAll("/", "%2F");
					    	json = collector.collect(new Item(urlName));
				    	}else{
					    	json = collector.collect(new Item(name));
				    	}
				    	score = json.getDouble(NC.NPM_COMPARATOR_POINTS);
				    	comparator.put(name, score);
				    	json.put(NC.ID, name);
				    	System.out.println(json.toString());
				    	fw2.write(json.toString()+"\r\n");
		    		}
		    		features.add(featureVector+K+score);
		    	}
		    }
		    
		    		    
		    for(String p:features){
		    	if(p.split(",").length != NUMBER_OF_FEATURES + 1){
		    		System.out.println("ERROR en la longitud del vector de features");
		    		//System.out.println(key+" "+features.get(key).split(",").length);
		    		//System.out.println(features.get(key));
		    	}
		    }
		    
		    fw.write(getFeatureHeads()+"\r\n");
		    for(String p:features){
		    	//System.out.println(p);
		    	fw.write(p+"\r\n");
		    }
		    
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			
		}finally {
			fw.close();
			//fw2.close();
			fr.close();
			br.close();
			fr2.close();
			br2.close();
		}
		
	}

	private static String getFeatureHeads() {
		String heads = "name"+K+
				NC.S_SCORE_QUALITY+K+NC.S_SCORE_POPULARITY+K+NC.S_SCORE_MAINTENANCE+
				K +	NC.NPM_COMPARATOR_POINTS;
		return heads;
	}

	public static String K = ",";
	
	private static String getScoreVector(JSONObject json) {
		

		String featureVector = "";
	
		/**
		 * Score
		 * 3 features
		 */
		
		JSONObject e = json.getJSONObject(NC.S_SCORE);
		
		/*
		 * Quality
		 */
		JSONObject q = e.getJSONObject(NC.S_SCORE_DETAIL);
		featureVector += q.getDouble(NC.S_SCORE_QUALITY)+K;
		featureVector += q.getDouble(NC.S_SCORE_POPULARITY)+K;
		featureVector += q.getDouble(NC.S_SCORE_MAINTENANCE);
		
		return featureVector;
	}
}
