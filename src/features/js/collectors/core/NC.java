package features.js.collectors.core;

public class NC {

   public static final String README = "readme";
   public static final String JSON_VALUE = "value";
   
   public static final String ID = "id";
   public static final String DESCR = "description";
   public static final String HOME = "homepage";
   public static final String KEYS = "keywords";
   public static final String NAME = "name";
   public static final String VERSION = "version";

   public static final String REPO = "repository";
   public static final String BUGS = "bugs";
   public static final String LICENSE = "license";
   public static final String MAINTAINERS = "maintainers";
   public static final String DEPS = "dependencies";
   public static final String DEVDEPS = "devDependencies";
   public static final String CONTRIBUTORS = "contributors";
   public static final String LAST_PUBLISHER = "last_publisher";
   public static final String LAST_PUBLISH_DATE = "last_publish_date";
   public static final String LAST_VERSION = "last_version";
   public static final String NUM_VERSION = "num_versions";
   public static final String DL_LAST_DAY = "downloads_last_day";
   public static final String DL_LAST_WEEK = "downloads_last_week";
   public static final String DL_LAST_MONTH = "downloads_last_month";
   
   public static final String DEPENDENTS = "dependents";
   
   /**
    * NPM Comparator
    */
   public static final String RELATED_ITEMS = "related_items";
   public static final String NPM_COMPARATOR_POINTS = "comparator_points";
   
   /**
    * NPMS constants
    * 
    */
   
   public static final String S_ANALYZED_AT = "analyzedAt";
   public static final String S_COLLECTED = "collected";//json
   //COLLECTED METADATA
   	 public static final String S_METADATA = "metadata";//json	
   		public static final String S_NAME = "name";
   		public static final String S_SCOPE = "scope";
   		public static final String S_VERSION = "version";
   		public static final String S_DESCRIPTION = "description";
   		public static final String S_KEYS = "keywords";//array
   		public static final String S_RELEASE_DATE = "date";
   		public static final String S_PUBLISHER = "publisher";//json
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
   		public static final String S_HAS_TEST_SCRIPT = "hasTestScript";
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
	        /** "distribution": {
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
	         },**/
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

   
}