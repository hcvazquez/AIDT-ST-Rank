package ranking;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.github.wnameless.json.flattener.JsonFlattener;

import features.js.collectors.core.NC;

public class Item {
	
	protected String name;
	protected HashMap<String, Object> features;
	
		
	public Item(String name) {
		super();
		this.name = name;
		this.features = new HashMap<String, Object>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addFeature(String name , Object value){
		features.put(name , value);
	}
	
	public Object getFeature(String name){
		return features.get(name);
	}

	public void fillFromJSON(JSONObject json) {
 
		   /**
		    * NPMS constants
		    * 
		    */
		   features.put(NC.S_ANALYZED_AT, json.getString(NC.S_ANALYZED_AT));
		   JSONObject collected = json.getJSONObject(NC.S_COLLECTED);
		   
		   //Metadata
		   JSONObject md = json.getJSONObject(NC.S_METADATA);
		   features.put(NC.S_SCOPE, md.getString(NC.S_SCOPE));   
		   features.put(NC.S_VERSION, md.getString(NC.S_VERSION));
		   features.put(NC.S_DESCRIPTION, md.getString(NC.S_DESCRIPTION));
		   features.put(NC.S_KEYS, md.getJSONArray(NC.S_KEYS));
		   features.put(NC.S_RELEASE_DATE, md.getString(NC.S_RELEASE_DATE));
		   features.put(NC.S_PUBLISHER+"."+NC.S_PUBLISHER_USER, md.getJSONObject(NC.S_PUBLISHER).getString(NC.S_PUBLISHER_USER));
		   
		   /*		public static final String S_NAME = "name";
		   		public static final String S_SCOPE = "scope";
		   		features.put(NC.S_VERSION = "version";
		   		features.put(NC.S_DESCRIPTION = "description";
		   		features.put(NC.S_KEYS = "keywords";//array
		   		features.put(NC.S_RELEASE_DATE = "date";
		   		features.put(NC.S_PUBLISHER = "publisher";//json
		   			public static final String S_PUBLISHER_USER = "username";
		   		public static final String S_MAINTAINERS = "maintainers";//array
		   		public static final String S_LINKS = "links";//json
		   			public static final String S_LINK_NPM = "npm";
		   			public static final String S_LINK_HOMEPAGE = "homepage";
		   			public static final String S_LINK_REPO = "repository";
		   			public static final String S_LINK_BUGS = "bugs";
		   		public static final String S_LICENCE = "license";
		   		public static final String S_DEPS = "dependencies";//json
		   		public static final String S_RELEASES = "releases";//array
		   			public static final int S_RELEASES_LAST_MONTH = 0;//json
		   			public static final int S_RELEASES_LAST_THREE_MONTH = 1;//json
		   			public static final int S_RELEASES_LAST_SIX_MONTH = 2;//json
		   			public static final int S_RELEASES_LAST_YEAR = 3;//json
		   			public static final int S_RELEASES_LAST_TWO_YEAR = 4;//json
		   				public static final String S_RELEASES_COUNT = "count";
		   		public static final String S_HAS_SEL_FILES = "hasSelectiveFiles";	
			    public static final String S_README = "readme";
			//COLLECTED NPM
			 public static final String S_NPM = "npm";//json
			 	public static final String S_DOWNLOADS = "downloads";//array
					public static final int S_DOWNLOADS_LAST_DAY = 0;//json
					public static final int S_DOWNLOADS_LAST_WEEK = 1;//json
					public static final int S_DOWNLOADS_LAST_MONTH = 2;//json
					public static final int S_DOWNLOADS_LAST_THREE_MONTH = 3;//json
					public static final int S_DOWNLOADS_LAST_SIX_MONTH = 4;//json
					public static final int S_DOWNLOADS_LAST_YEAR = 5;//json
						public static final String S_DOWNLOADS_COUNT = "count"; 	
				public static final String S_DEPENDENTS = "dependentsCount";
				public static final String S_STARS = "starsCount";
				 
			//COLLECTED GITHUB
			 public static final String S_GH = "github";//json
			 	public static final String S_GH_HOME = "homepage";
			 	public static final String S_GH_STARS = "starsCount";
			 	public static final String S_GH_FORKS = "forksCount";
			 	public static final String S_GH_SUBSCRIBERS = "subscribersCount";
			 	public static final String S_GH_ISSUES =  "issues";//json
			 		public static final String S_GH_ISSUES_COUNT = "count";
			 		public static final String S_GH_ISSUES_OPEN = "openCount";
			         "distribution": {
			           "3600": 2394,
			           "10800": 1081,
			           "32400": 1149,
			           "97200": 1434,
			           "291600": 1128,
			           "874800": 1084,
			           "2624400": 941,
			           "7873200": 878,
			           "23619600": 929,
			           "70858800": 763,
			           "212576400": 200
			         },
			 		public static final String S_GH_ISSUES_DISABLED = "isDisabled";
			 	public static final String S_GH_CONTRIBUTORS = "contributors";//array
			 	public static final String S_GH_COMMITS = "commits";//array
					public static final int S_GH_COMMITS_LAST_WEEK = 0;//json
					public static final int S_GH_COMMITS_LAST_MONTH = 1;//json
					public static final int S_GH_COMMITS_LAST_THREE_MONTH = 2;//json
					public static final int S_GH_COMMITS_LAST_SIX_MONTH = 3;//json
					public static final int S_GH_COMMITS_LAST_YEAR = 4;//json
						public static final String S_GH_COMMITS_COUNT = "count"; 

				public static final String S_GH_STATUSES = "statuses";//array

			//COLLECTED SOURCE CODE
			 public static final String S_SOURCE = "source";//json
			 	public static final String S_FILES = "files";//json
			 		public static final String S_README_SIZE = "readmeSize";
			 		public static final String S_TEST_SIZE = "testsSize";
			 		public static final String S_HAS_CHANGE_LOG = "hasChangelog";
				public static final String S_BADGES = "badges";//array
				public static final String S_LINTERS = "linters";//json
				public static final String S_COVERAGE = "coverage";
				public static final String S_OUTDATED_DEPS = "outdatedDependencies";//json of jsons
				
		   //EVALUATION
		   public static final String S_EVALUATION = "evaluation";//json
			 	public static final String S_QUALITY = "quality";//json
			 		public static final String S_QUALITY_CAREFULNESS = "carefulness";
			 		public static final String S_QUALITY_TESTS = "tests";
			 		public static final String S_QUALITY_HEALTH = "health";
			 		public static final String S_QUALITY_BRANDING = "branding";
				public static final String S_POPULARITY = "popularity";//json
			 		public static final String S_POP_COMMUNITY = "communityInterest";
			 		public static final String S_POP_DOWNLOADS_COUNT = "downloadsCount";
			 		public static final String S_POP_DOWNLOADS_ACC = "downloadsAcceleration";
			 		public static final String S_POP_DEPENDENTS_COUNT = "dependentsCount";
				public static final String S_MAINTENANCE = "maintenance";//json
			 		public static final String S_MAINT_RELEASE_FRECUENCY = "releasesFrequency";
			 		public static final String S_MAINT_COMMITS_FRECUENCY = "commitsFrequency";
			 		public static final String S_MAINT_OPEN_ISSUES = "openIssues";
			 		public static final String S_MAINT_ISSUES_DISTRIBUTION = "issuesDistribution";
			 		
		   //SCORE
		   public static final String S_SCORE = "score";//json
			 	public static final String S_SCORE_FINAL = "final";
				public static final String S_SCORE_DETAIL = "detail";//json
					public static final String S_SCORE_QUALITY = "quality";
					public static final String S_SCORE_POPULARITY = "popularity";
					public static final String S_SCORE_MAINTENANCE = "maintenance";
		*/
	}
	
}
