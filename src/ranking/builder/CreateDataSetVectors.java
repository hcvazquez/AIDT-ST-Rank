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

public class CreateDataSetVectors {
	
	protected static final String MENOR = "0";
	protected static final String MAYOR = "1";
	private static final int NUMBER_OF_FEATURES = 45;
	
    
    //TEMP
    static HashMap<String, Integer> depsCount = new HashMap<String, Integer>();
    //FIN TEMP
    

	public static void main(String[] args) throws IOException {
		
		NPMSCollector collector = new NPMSCollector();
		
		FileWriter fw = null;
		FileWriter fw2 = null;
		FileReader fr = null;
		BufferedReader br = null;
		FileReader fr2 = null;
		BufferedReader br2 = null;
		
		try {
		
			fw = new FileWriter("D:/GHDataset/datasets/dataSetPropuestaCSVVectors",false);
			//fw2 = new FileWriter("D:/GHDataset/Dataset/itemFeatures2.txt",true);
			
			fr = new FileReader("D:/GHDataset/features/npmsFeaturesExtended.txt");
			fr2 = new FileReader("D:/GHDataset/rank/pairwiseRank.txt");
		    br = new BufferedReader(fr);
		    br2 = new BufferedReader(fr2);
		    
		    ArrayList<String> features = new ArrayList<String>();
		    ArrayList<String> uniques = new ArrayList<String>();
		    
		    String linea;
		    int l = 1;
		    while((linea = br.readLine()) != null){
		    	//System.out.println(l++);
		    	//System.out.println(linea);
		    	JSONObject jitem = new JSONObject(linea);
		    	String name = jitem.getJSONObject(NC.S_COLLECTED).getJSONObject(NC.S_METADATA).getString(NC.NAME);
		    	if(!uniques.contains(name)){
		    		String featureVector = name +
		    				"," + getFeatureVectorFineGranularity(jitem);
		    		uniques.add(name);
		    		features.add(featureVector);
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
		    	System.out.println(p);
		    	fw.write(p+"\r\n");
		    }
		    
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			
		}finally {
			fw.close();
			//fw2.close();
			fr.close();
			br.close();
		}
		
	}

	private static String getFeatureHeads() {
		String heads = "name"+K+"has_scope1"+K+ "version_stable1"+K+"has_description1"+K+"has_keywords1"+K+
				"last_release1"+K+"#maintainers1"+K+"#links1"+K+"#dependencies1"+K+"#releases_month1"+K+
				"#releases_3month1"+K+"#releases_6month1"+K+"#releases_year1"+K+"#releases_2year1"+K+
				"has_selective_files1"+K+"#downloads_day1"+K+"#downloads_week1"+K+"#downloads_month1"+K+
				"#downloads_3month1"+K+"#downloads_6month1"+K+"#downloads_year1"+K+"#dependents1"+K+
				"npm_stars1"+K+"has_github1"+K+"has_home1"+K+"gh_stars1"+K+"forks1"+K+"subscribers1"+K+"issues1"+K+
				"open_issues1"+K+"issues_disabled1"+K+"contributors1"+K+"commits_week1"+K+"commits_month1"+K+
				"commits_3month1"+K+"commits_6month1"+K+"commits_year1"+K+"#statuses1"+K+"is_open_sourse1"+K+
				"has_readme1,has_test1"+K+"has_changelog1"+K+"#badges1"+K+"#linters1"+K+"#coverage1"+K+
				"#outdated_deps1";
		/*for(int i=0;i<NUMBER_OF_FEATURES;i++){
			heads += "item1f" + i + ",";
		}
		heads = heads.substring(0,heads.length()-1);*/
		return heads;
	}

	public static String K = ",";
	
	private static String getFeatureVectorFineGranularity(JSONObject json) {
		
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
		
		//number of dependencies - feature 8
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
		
		
		/**
		 * NPM
		 * 8 features
		 */
		
		JSONObject n = c.getJSONObject(NC.S_NPM);
		
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
		//stars
		featureVector += n.getInt(NC.S_STARS)+K;
		

		
		
		/**
		 * GITHUB
		 * 15 features
		 */
		
		if(c.has(NC.S_GH)){//15 features
			
			featureVector += 1+K;
			
			JSONObject g = c.getJSONObject(NC.S_GH);
			
			//has homepage
			featureVector += (g.has(NC.S_GH_HOME)?1:0)+K;
			//gh stars feature 25
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
			
			//statuses
			featureVector += (g.has(NC.S_GH_STATUSES)?g.getJSONArray(NC.S_GH_STATUSES).length():0)+K;
		
		}else{//15 features
			featureVector += 0+K;
			//fill with all ceros
			featureVector += "0,0,0,0,0,0,0,0,0,0,0,0,0,0"+K;
			
		}
		
		/**
		 * Source code
		 * 8 features
		 */
		
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
			
		}
		
		return featureVector;
	}
}
