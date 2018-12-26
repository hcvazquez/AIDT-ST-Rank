package test.accuracy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import features.FeatureCalculator;
import features.js.collectors.core.NC;
import features.js.collectors.core.NPMCompareCollector;
import features.js.collectors.core.NPMRegistryCollector;
import features.js.collectors.core.NPMSCollector;
import ranking.Item;

public class CreateGHPairwiseRank {
	
	protected static final String MENOR = "0";
	protected static final String MAYOR = "1";

	public static void main(String[] args) throws IOException {
		
		FileWriter fw = null;
		FileReader fr = null;
		BufferedReader br = null;
		FileReader fr2 = null;
		BufferedReader br2 = null;
		
		try {
		
			//fw = new FileWriter("D:/GHDataset/Dataset/dataSetPropuestaCSVLarge",false);
			
			fr = new FileReader("D:/GHDataset/Dataset/npmsFeaturesComplete.txt");
			fr2 = new FileReader("D:/GHDataset/Dataset/pairwiseRank.txt");
		    br = new BufferedReader(fr);
		    br2 = new BufferedReader(fr2);
		    
		    HashMap<String, Double> features = new HashMap<String, Double>();
		    
		    String linea;
		    int l = 1;
		    while((linea = br.readLine()) != null){
		    	//System.out.println(l++);
		    	//System.out.println(linea);
		    	JSONObject jitem = new JSONObject(linea);
		    	Double ghValue = getGHValue(jitem);
		    	features.put(jitem.getJSONObject(NC.S_COLLECTED).getJSONObject(NC.S_METADATA).getString(NC.NAME), ghValue);
		    }
		    
	    	int trues = 0;
	    	int count = 0;
	    	
		    while((linea = br2.readLine()) != null){
		    	String[] pair = linea.split(";");
		    	
		    	Double pair1 = features.get(pair[0]);
		    	Double pair2 = features.get(pair[1]);
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
		    		System.out.println(linea);
		    		//fw.write(pair[0]+","+pair[1]+","+FALSE+"\r\n");
		    	}
		    	
		    }
		    
		    System.out.println("Presicion = "+ ((double)trues/(double)count));  
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			
		}finally {
			//fw.close();
			//fw2.close();
			fr.close();
			br.close();
		}
		
	}
	

	private static Double getGHValue(JSONObject json) {
		
		/*if(json.getJSONObject(NC.S_COLLECTED).getJSONObject(NC.S_METADATA).getString(NC.NAME).equals("postcss-selector-not")){
			System.out.println("");
		}*/

		String featureVector = "";

		JSONObject c = json.getJSONObject(NC.S_COLLECTED);
		
		/**
		 * METADATA
		 * 14 features
		 */
		JSONObject m = c.getJSONObject(NC.S_METADATA);
		/*
		//has scope
		featureVector += (!m.getString(NC.S_SCOPE).startsWith("unscoped")?1:0)+K;
		
		//version>1 or stable
		featureVector += (!m.getString(NC.S_VERSION).startsWith("0")?1:0)+K;
		
		//hasDescription
		featureVector += (m.has(NC.S_DESCRIPTION)&&!m.getString(NC.S_DESCRIPTION).startsWith("ERROR")?1:0)+K;
		
		//has keywords
		featureVector += (m.has(NC.S_KEYS)?1:0)+K;
		
		//last release < 1 year
		featureVector += (m.getString(NC.S_RELEASE_DATE).startsWith("2017")||
				m.getString(NC.S_RELEASE_DATE).startsWith("2018")?1:0)+K;
		
		//number of maintainers
		featureVector += m.getJSONArray(NC.S_MAINTAINERS).length()+K;

		//number of links
		featureVector += m.getJSONObject(NC.S_LINKS).keySet().size()+K;
		
		//licence (categorical)
//		featureVector += (m.getString(NC.S_LICENCE)+K;
		
		//number of dependencies
		featureVector += (m.has(NC.S_DEPS)?m.getJSONObject(NC.S_DEPS).keySet().size():0)+K;
		
		//number of releases
		featureVector += m.getJSONArray(NC.S_RELEASES).getJSONObject(NC.S_RELEASES_LAST_MONTH)
				.getInt(NC.S_RELEASES_COUNT)+K;
		featureVector += m.getJSONArray(NC.S_RELEASES).getJSONObject(NC.S_RELEASES_LAST_THREE_MONTH)
				.getInt(NC.S_RELEASES_COUNT)+K;
		featureVector += m.getJSONArray(NC.S_RELEASES).getJSONObject(NC.S_RELEASES_LAST_SIX_MONTH)
				.getInt(NC.S_RELEASES_COUNT)+K;
		featureVector += m.getJSONArray(NC.S_RELEASES).getJSONObject(NC.S_RELEASES_LAST_YEAR)
				.getInt(NC.S_RELEASES_COUNT)+K;
		featureVector += m.getJSONArray(NC.S_RELEASES).getJSONObject(NC.S_RELEASES_LAST_TWO_YEAR)
				.getInt(NC.S_RELEASES_COUNT)+K;
		
		//hasSelectiveFiles
		featureVector += (m.has(NC.S_HAS_SEL_FILES)&& m.getBoolean(NC.S_HAS_SEL_FILES)?1:0)+K;
		*/
		
		/**
		 * NPM
		 * 8 features
		 */
		
		/*JSONObject n = c.getJSONObject(NC.S_NPM);
		
		//number of downloads
		featureVector += n.getJSONArray(NC.S_DOWNLOADS).getJSONObject(NC.S_DOWNLOADS_LAST_DAY)
				.getInt(NC.S_DOWNLOADS_COUNT)+K;
		featureVector += n.getJSONArray(NC.S_DOWNLOADS).getJSONObject(NC.S_DOWNLOADS_LAST_WEEK)
				.getInt(NC.S_DOWNLOADS_COUNT)+K;
		featureVector += n.getJSONArray(NC.S_DOWNLOADS).getJSONObject(NC.S_DOWNLOADS_LAST_MONTH)
				.getInt(NC.S_DOWNLOADS_COUNT)+K;
		featureVector += n.getJSONArray(NC.S_DOWNLOADS).getJSONObject(NC.S_DOWNLOADS_LAST_THREE_MONTH)
				.getInt(NC.S_DOWNLOADS_COUNT)+K;
		featureVector += n.getJSONArray(NC.S_DOWNLOADS).getJSONObject(NC.S_DOWNLOADS_LAST_SIX_MONTH)
				.getInt(NC.S_DOWNLOADS_COUNT)+K;
		featureVector += n.getJSONArray(NC.S_DOWNLOADS).getJSONObject(NC.S_DOWNLOADS_LAST_YEAR)
				.getInt(NC.S_DOWNLOADS_COUNT)+K;
		
		//dependents
		featureVector += n.getInt(NC.S_DEPENDENTS)+K;
		featureVector += n.getInt(NC.S_STARS)+K;
		*/

		
		
		/**
		 * GITHUB
		 * 15 features
		 */
		/*
		if(c.has(NC.S_GH)){//15 features
			
			featureVector += 1+K;
			
			JSONObject g = c.getJSONObject(NC.S_GH);
			
			//has homepage
			featureVector += (g.has(NC.S_GH_HOME)?1:0)+K;
			//gh stars
			featureVector += g.getInt(NC.S_GH_STARS)+K;
			//forks
			featureVector += g.getInt(NC.S_GH_FORKS)+K;
			//subscribers
			featureVector += g.getInt(NC.S_GH_SUBSCRIBERS)+K;
			
			//issues count
			featureVector += g.getJSONObject(NC.S_GH_ISSUES).getInt(NC.S_GH_ISSUES_COUNT)+K;
			//issues open
			featureVector += g.getJSONObject(NC.S_GH_ISSUES).getInt(NC.S_GH_ISSUES_OPEN)+K;		
			//issues disabled
			featureVector += (g.getJSONObject(NC.S_GH_ISSUES).getBoolean(NC.S_GH_ISSUES_DISABLED)?1:0)+K;
			
			//number of contributors
			featureVector += (g.has(NC.S_GH_CONTRIBUTORS)?g.getJSONArray(NC.S_GH_CONTRIBUTORS).length():0)+K;
			
			//commits
			//number of downloads
			featureVector += g.getJSONArray(NC.S_GH_COMMITS).getJSONObject(NC.S_GH_COMMITS_LAST_WEEK)
					.getInt(NC.S_GH_COMMITS_COUNT)+K;
			featureVector += g.getJSONArray(NC.S_GH_COMMITS).getJSONObject(NC.S_GH_COMMITS_LAST_MONTH)
					.getInt(NC.S_GH_COMMITS_COUNT)+K;
			featureVector += g.getJSONArray(NC.S_GH_COMMITS).getJSONObject(NC.S_GH_COMMITS_LAST_THREE_MONTH)
					.getInt(NC.S_GH_COMMITS_COUNT)+K;
			featureVector += g.getJSONArray(NC.S_GH_COMMITS).getJSONObject(NC.S_GH_COMMITS_LAST_SIX_MONTH)
					.getInt(NC.S_GH_COMMITS_COUNT)+K;
			featureVector += g.getJSONArray(NC.S_GH_COMMITS).getJSONObject(NC.S_GH_COMMITS_LAST_YEAR)
					.getInt(NC.S_GH_COMMITS_COUNT)+K;
			
			//number of contributors
			featureVector += (g.has(NC.S_GH_STATUSES)?g.getJSONArray(NC.S_GH_STATUSES).length():0)+K;
		
		}else{//15 features
			featureVector += 0+K;
			//fill with all ceros
			featureVector += "0,0,0,0,0,0,0,0,0,0,0,0,0,0"+K;
			
		}
		*/
		/**
		 * Source code
		 * 8 features
		 */
		/*
		if(c.has(NC.S_SOURCE)){//8 features
			
			featureVector += 1+K;
			
			JSONObject s = c.getJSONObject(NC.S_SOURCE);
			
			//hasReadmeScript
			featureVector += (s.has(NC.S_FILES)&& s.getJSONObject(NC.S_FILES).getInt(NC.S_README_SIZE)>0 ?1:0)+K;		
			//hasTest
			featureVector += (s.has(NC.S_FILES)&& s.getJSONObject(NC.S_FILES).getInt(NC.S_TEST_SIZE)>0 ?1:0)+K;		
			//hasChangeLog
			featureVector += (s.has(NC.S_FILES)&& s.getJSONObject(NC.S_FILES).has(NC.S_HAS_CHANGE_LOG) && s.getJSONObject(NC.S_FILES).getBoolean(NC.S_HAS_CHANGE_LOG)?1:0)+K;		
			
			//number of badges
			featureVector += (s.has(NC.S_BADGES)?s.getJSONArray(NC.S_BADGES).length():0)+K;
			//number of linters
			featureVector += (s.has(NC.S_LINTERS)?s.getJSONObject(NC.S_LINTERS).keySet().size():0)+K;
			//number of coverage
			featureVector += (s.has(NC.S_COVERAGE)?s.getDouble(NC.S_COVERAGE):0)+K;
			//number of outdated deps
			featureVector += s.has(NC.S_OUTDATED_DEPS)?s.getJSONObject(NC.S_OUTDATED_DEPS).keySet().size():0;
		}else{//8 features
			featureVector += 0+K;
			//fill with all ceros
			featureVector += "0,0,0,0,0,0,0";
			
		}*/
		int deps =(m.has(NC.S_DEPS)?m.getJSONObject(NC.S_DEPS).keySet().size():0);
		//return (double)1/((double) deps+1);
		int downloadsDay = c.getJSONObject(NC.S_NPM).getJSONArray(NC.S_DOWNLOADS).getJSONObject(NC.S_DOWNLOADS_LAST_DAY).getInt(NC.S_DOWNLOADS_COUNT);
		int downloadsWeek = c.getJSONObject(NC.S_NPM).getJSONArray(NC.S_DOWNLOADS).getJSONObject(NC.S_DOWNLOADS_LAST_WEEK).getInt(NC.S_DOWNLOADS_COUNT);
		
		return (double) downloadsDay - ((double)downloadsWeek/(double)7);
		//return c.has(NC.S_GH)?(double) c.getJSONObject(NC.S_GH).getInt(NC.S_GH_STARS):0.0;
		//return c.has(NC.S_GH)?(double) c.getJSONObject(NC.S_GH).getInt(NC.S_GH_FORKS)+c.getJSONObject(NC.S_GH).getInt(NC.S_GH_SUBSCRIBERS)+(double) c.getJSONObject(NC.S_GH).getInt(NC.S_GH_STARS):0.0;
	}
}
