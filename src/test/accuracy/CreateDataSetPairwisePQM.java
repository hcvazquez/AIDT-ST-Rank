package test.accuracy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

import features.js.collectors.core.NC;
import features.js.collectors.core.NPMCompareCollector;
import features.js.collectors.core.NPMRegistryCollector;
import features.js.collectors.core.NPMSCollector;
import ranking.Item;

public class CreateDataSetPairwisePQM {
	
	protected static final String MENOR = "0";
	protected static final String MAYOR = "1";

	public static void main(String[] args) throws IOException {
		
		NPMSCollector collector = new NPMSCollector();
		
		FileWriter fw = null;
		FileWriter fw2 = null;
		FileReader fr = null;
		BufferedReader br = null;
		FileReader fr2 = null;
		BufferedReader br2 = null;
		
		try {
		
			fw = new FileWriter("D:/GHDataset/Dataset/dataSetCSV",false);
			fw2 = new FileWriter("D:/GHDataset/Dataset/itemFeatures2.txt",true);
			
			fr = new FileReader("D:/GHDataset/Dataset/itemFeatures2.txt");
			fr2 = new FileReader("D:/GHDataset/Dataset/pairwiseRank.txt");
		    br = new BufferedReader(fr);
		    br2 = new BufferedReader(fr2);
		    
		    HashMap<String, String> features = new HashMap<String, String>();
		    
		    String linea;
		    
		    while((linea = br.readLine()) != null){
		    	JSONObject jitem = new JSONObject(linea);
		    	String featureVector = getFeatureVector(jitem);
		    	features.put(jitem.getJSONArray(NC.NAME).getString(0), featureVector);
		    }
		    
		    while((linea = br2.readLine()) != null){
		    	String[] pair = linea.split(";");
		    	
		    	String pair1 = features.get(pair[0]);
		    	String pair2 = features.get(pair[1]);
		    	if(pair1==null){
		    		System.out.println("Descargando "+pair[0]);
		    		String line = collector.collect(new Item(pair[0])).toString();
			    	fw2.write(line + "\r\n");
			    	pair1 = getFeatureVector(new JSONObject(line));
			    	features.put(pair[0], pair1);
		    	}
		    	if(pair2==null){
		    		System.out.println("Descargando "+pair[1]);
		    		String line = collector.collect(new Item(pair[1])).toString();
			    	fw2.write(line + "\r\n");
			    	pair2 = getFeatureVector(new JSONObject(line));
			    	features.put(pair[1], pair2);
		    	}
		    	fw.write(pair1+","+pair2+","+MAYOR+"\r\n");
		    	
		    	//*** Important data augmentation insight
		    	fw.write(pair2+","+pair1+","+MENOR+"\r\n");
		    	//***
		    }
		    
		} catch (Exception e1) {
			System.out.println("Error creando dataset.");
			
		}finally {
			fw.close();
			fw2.close();
			fr.close();
			br.close();
		}
		
	}

	private static String getFeatureVector(JSONObject jitem) {
		/**
		 * TODO cambiar append por put en NPMSCollector
		 *  para poder leer un double en lugar de un array
		 */
		return 	jitem.getJSONArray(NC.S_SCORE_POPULARITY).getDouble(0)
    			+","+ jitem.getJSONArray(NC.S_SCORE_QUALITY).getDouble(0) +","+
    			jitem.getJSONArray(NC.S_SCORE_MAINTENANCE).getDouble(0);
	}
}
