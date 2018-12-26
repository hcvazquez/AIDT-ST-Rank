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

public class CreateNPMPairwiseRank {
	
	protected static final String FALSE = "0";
	protected static final String TRUE = "1";

	public static void main(String[] args) throws IOException {
		
		NPMSCollector collector = new NPMSCollector();
		
		FileWriter fw = null;
		FileWriter fw2 = null;
		FileReader fr = null;
		BufferedReader br = null;
		FileReader fr2 = null;
		BufferedReader br2 = null;
		
		try {
		
			fw = new FileWriter("D:/GHDataset/Dataset/dataTRUECSV",false);
			fw2 = new FileWriter("D:/GHDataset/Dataset/itemFeatures.txt",true);
			
			fr = new FileReader("D:/GHDataset/Dataset/itemFeatures.txt");
			fr2 = new FileReader("D:/GHDataset/Dataset/pairwiseRank.txt");
		    br = new BufferedReader(fr);
		    br2 = new BufferedReader(fr2);
		    
		    HashMap<String, Double> values = new HashMap<String, Double>();
		    
		    String linea;
		    
		    while((linea = br.readLine()) != null){
		    	JSONObject jitem = new JSONObject(linea);
		    	Double value = getValue(jitem);
		    	values.put(jitem.getJSONArray(NC.NAME).getString(0), value);
		    }
		    
	    	int trues = 0;
	    	int count = 0;
		    while((linea = br2.readLine()) != null){
		    	String[] pair = linea.split(";");
		    	
		    	Double pair1 = values.get(pair[0]);
		    	Double pair2 = values.get(pair[1]);
		    	/*if(pair1==null){
		    		System.out.println("Descargando "+pair[0]);
		    		String line = collector.collect(new Item(pair[0])).toString();
			    	fw2.write(line + "\r\n");
			    	pair1 = getValue(new JSONObject(line));
			    	values.put(pair[0], pair1);
		    	}
		    	if(pair2==null){
		    		System.out.println("Descargando "+pair[1]);
		    		String line = collector.collect(new Item(pair[1])).toString();
			    	fw2.write(line + "\r\n");
			    	pair2 = getValue(new JSONObject(line));
			    	values.put(pair[1], pair2);
		    	}*/
		    	if(pair1>pair2){
		    		count++;
		    		trues++;
		    		//fw.write(pair[0]+","+pair[1]+","+TRUE+"\r\n");
		    	}else{
		    		count++;
		    		//fw.write(pair[0]+","+pair[1]+","+FALSE+"\r\n");
		    	}
		    	
		    	//*** Important data augmentation insight

		    	//***
		    }
		    System.out.println("Presicion = "+ trues +"/"+count);
		} catch (Exception e1) {
			System.out.println("Error creando dataset.");
			
		}finally {
			fw.close();
			fw2.close();
			fr.close();
			br.close();
		}
		
	}
	
	private static double getValue(JSONObject jitem){
		/**
		 * TODO cambiar append por put en NPMSCollector
		 *  para poder leer un double en lugar de un array
		 */
		return jitem.getJSONArray(NC.S_SCORE_POPULARITY).getDouble(0)+
		jitem.getJSONArray(NC.S_SCORE_QUALITY).getDouble(0) +
		jitem.getJSONArray(NC.S_SCORE_MAINTENANCE).getDouble(0);
	}
}
