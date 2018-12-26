package ranking.builder;

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
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import features.js.collectors.core.NC;



public class BuildLiswiseRank{

	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
	
	public static void main(String[] args) throws Exception {
		
		saveRankPackages("D:/GHDataset/relevance/pkgRelevance.txt","D:/GHDataset/features/npmCompareFeatures.txt", null);
		
	}

	public static void saveRankPackages(String pkgRelevanceFile, String npmCompareFeaturesFile, Proxy proxy) throws IOException {
				
		FileWriter fw = null;	
		FileReader fr = null;
		BufferedReader br = null;
		FileReader fr2 = null;
		BufferedReader br2 = null;
		FileReader fr3 = null;
		BufferedReader br3 = null;
		
		try {
		
			fw = new FileWriter("D:/GHDataset/rank/listwiseRank2.txt",false);	
			fr = new FileReader(pkgRelevanceFile);
			fr2 = new FileReader(npmCompareFeaturesFile);
		    br = new BufferedReader(fr);
		    br2 = new BufferedReader(fr2);
		    		    
		    HashMap<String, Double> pkgRelevances = new HashMap<String, Double>();
		    
		    String linea;

		    while((linea = br.readLine()) != null){ 	
		    	pkgRelevances.put(linea.split(";")[0],Double.valueOf(linea.split(";")[1]));
		    }
		    
		    
		    /*
		     * Is for verify if we have concerns information about the packages
		     */
		    fr3 = new FileReader("D:/GHDataset/concerns/sides.txt");
		    br3 = new BufferedReader(fr3);
		    ArrayList<String> isInIsFRontFile = new ArrayList<String>();
		    while((linea = br3.readLine()) != null){
		    	if(linea.startsWith("//") && linea.split("//").length > 1){

		    	}else if(linea.split(";").length > 1){
		    		isInIsFRontFile.add(linea.split(";")[0]);
		    	}
		    }
		    
		    ArrayList<String> result = new ArrayList<String>();
		    ArrayList<String> uniques = new ArrayList<String>();
		    ArrayList<String> noRelevances = new ArrayList<String>();
		    ArrayList<String> relevances = new ArrayList<String>();
		    while((linea = br2.readLine()) != null){ 	
		    	//System.out.println(linea);
		    	JSONObject compareFeatures = new JSONObject(linea);
		    	if(compareFeatures.has(NC.RELATED_ITEMS)){
		    		String id = compareFeatures.getString(NC.ID);
			    	JSONArray relatedItems = compareFeatures.getJSONArray(NC.RELATED_ITEMS);
				    ArrayList<String> deps = new ArrayList<String>();
				    deps.add(id);
				    deps.addAll((List<String>)(Object)relatedItems.toList());
				    Collections.sort(deps, new Comparator<String>() {
				          @Override
				          public int compare(String dep2, String dep1)
				          {
				        	  if(pkgRelevances.get(dep1)==null){
				        		  if(!noRelevances.contains(dep1)){
				        			  noRelevances.add(dep1);
				        		  }
					        	  if(pkgRelevances.get(dep2)==null){
					        		  if(!noRelevances.contains(dep2)){
					        			  noRelevances.add(dep2);
					        		  }
					        	  }
				        		  return -1;
				        	  }
				        	  if(pkgRelevances.get(dep2)==null){
				        		  if(!noRelevances.contains(dep2)){
				        			  noRelevances.add(dep2);
				        		  }
				        		  return 1;
				        	  }
				        	  //TODO i need a criteria for ties 18/03/2018
				        	  if(pkgRelevances.get(dep1).compareTo(pkgRelevances.get(dep2))==0){
				        		  System.out.println(dep1+";"+dep2+" EMPATE");
				        	  }
			        		  if(!relevances.contains(dep1)){
			        			  relevances.add(dep1);
			        		  }
			        		  if(!relevances.contains(dep2)){
			        			  relevances.add(dep2);
			        		  }
				              return  pkgRelevances.get(dep1).compareTo(pkgRelevances.get(dep2));
				          }
				    });
				    String resultLine = "";
				    
			        for(String dep:deps){
			        	if(isInIsFRontFile.contains(dep)){
					        if(!uniques.contains(dep)){
					        	uniques.add(dep);
					        }			        	
			        		resultLine = resultLine + dep +";";
			        	}else{
			        		//System.out.println(dep);//Deberia taggear a mano esto
			        	}
				    }
	
			        if(!"".equals(resultLine)&& resultLine.split(";").length>1){
			        	result.add(resultLine.substring(0, resultLine.length()-1)+ "\r\n");
			        }        

		        }
		    }
		    
	      for(String line:result){
	    	 //System.out.println(line); 
	    	 fw.write(line);
	      }
	      fw.close();
	      
	      System.out.println("Unique Length: "+uniques.size());
	      System.out.println("With Reps Length: "+result.size());
	      System.out.println("Reps Length: "+ (result.size()-uniques.size()));
	      System.out.println("No Relevances Length: "+noRelevances.size());
	      System.out.println("Relevances Length: "+relevances.size());
		    
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally {
			fw.close();
			fr.close();
			br.close();
			fr2.close();
			br2.close();
			fr3.close();
			br3.close();
		}

	}

}
