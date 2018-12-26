import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import features.js.collectors.core.NC;
import features.js.collectors.core.NPMCompareCollector;


public class GetNPMComparePointsFromNamesFile {

	
	public static void main(String[] args) throws IOException {
		
		NPMCompareCollector collector = new NPMCompareCollector();
		
		FileReader fr = null;
		BufferedReader br = null;
		FileReader fr2 = null;
		BufferedReader br2 = null;
		
		try {
			fr = new FileReader("D:/GHDataset/Concerns2017/names.txt");
		    br = new BufferedReader(fr);
		 
			fr2 = new FileReader("D:/GHDataset/features/npmCompareFeaturesExtended.txt");
		    br2 = new BufferedReader(fr2);
		    
		    HashMap<String,Double> comparator = new HashMap<String,Double>();
		    
		    String linea;
		    while((linea = br2.readLine()) != null){
		    	try{
		    		//System.out.println(linea);
		    		JSONObject jitem = new JSONObject(linea);
			    	String name = jitem.getString(NC.ID);
			    	Double score = jitem.getDouble(NC.NPM_COMPARATOR_POINTS);
			    	comparator.put(name, score);
		    	}catch(Exception e){
		    		JSONObject jitem = new JSONObject(linea);
			    	String name = jitem.getString(NC.ID);
			    	comparator.put(name, 0.0);
		    	}
		    }
		    
		    ArrayList<String> names = new ArrayList<String>();
		    while((linea = br.readLine()) != null){
		    	String name = linea;
		    	names.add(name);
		    	System.out.println("Analizando "+name);
		    	
		    	Double score = comparator.get(name);
	    		if(score==null){

	    			if(name.contains("@")||
			    			name.contains("/")){
				    	String urlName = name.replaceAll("@", "%40").replaceAll("/", "%2F");
				    	score = collector.getScore(urlName, null);
			    	}else{
				    	score = collector.getScore(name,null);
			    	}
			    	comparator.put(name, score);		    	
	    		}
		    }
		    
		    System.out.println("name,"+ NC.NPM_COMPARATOR_POINTS);
		    for(String key:names){
		    	System.out.println(key+','+comparator.get(key));
		    }
		    
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			
		}finally {
			fr.close();
			br.close();
			fr2.close();
			br2.close();
		}

	}

}
