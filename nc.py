README = "readme"
JSON_VALUE = "value"

ID = "id"
DESCR = "description"
HOME = "homepage"
KEYS = "keywords"
NAME = "name"
VERSION = "version"

REPO = "repository"
BUGS = "bugs"
LICENSE = "license"
MAINTAINERS = "maintainers"
DEPS = "dependencies"
DEVDEPS = "devDependencies"
CONTRIBUTORS = "contributors"
LAST_PUBLISHER = "last_publisher"
LAST_PUBLISH_DATE = "last_publish_date"
LAST_VERSION = "last_version"
NUM_VERSION = "num_versions"
DL_LAST_DAY = "downloads_last_day"
DL_LAST_WEEK = "downloads_last_week"
DL_LAST_MONTH = "downloads_last_month"

DEPENDENTS = "dependents"

'''
NPM Comparator
'''
RELATED_ITEMS = "related_items"
NPM_COMPARATOR_POINTS = "comparator_points"

'''
NPMS constants

'''

S_ANALYZED_AT = "analyzedAt"
S_COLLECTED = "collected"#json
#COLLECTED METADATA
S_METADATA = "metadata"#json
S_NAME = "name"
S_SCOPE = "scope"
S_VERSION = "version"
S_DESCRIPTION = "description"
S_KEYS = "keywords"#array
S_RELEASE_DATE = "date"
S_PUBLISHER = "publisher"#json
S_PUBLISHER_USER = "username"
S_MAINTAINERS = "maintainers"#array
S_LINKS = "links"#json
S_LINK_NPM = "npm"
S_LINK_HOMEPAGE = "homepage"
S_LINK_REPO = "repository"
S_LINK_BUGS = "bugs"
S_LICENCE = "license"
S_DEPS = "dependencies"#json
S_RELEASES = "releases"#array
S_RELEASES_LAST_MONTH = 0#json
S_RELEASES_LAST_THREE_MONTH = 1#json
S_RELEASES_LAST_SIX_MONTH = 2#json
S_RELEASES_LAST_YEAR = 3#json
S_RELEASES_LAST_TWO_YEAR = 4#json
S_RELEASES_COUNT = "count"
S_HAS_SEL_FILES = "hasSelectiveFiles"
S_HAS_TEST_SCRIPT = "hasTestScript"
S_README = "readme"
#COLLECTED NPM
S_NPM = "npm"#json
S_DOWNLOADS = "downloads"#array
S_DOWNLOADS_LAST_DAY = 0#json
S_DOWNLOADS_LAST_WEEK = 1#json
S_DOWNLOADS_LAST_MONTH = 2#json
S_DOWNLOADS_LAST_THREE_MONTH = 3#json
S_DOWNLOADS_LAST_SIX_MONTH = 4#json
S_DOWNLOADS_LAST_YEAR = 5#json
S_DOWNLOADS_COUNT = "count" 
S_DEPENDENTS = "dependentsCount"
S_STARS = "starsCount"
 
#COLLECTED GITHUB
S_GH = "github"#json
S_GH_HOME = "homepage"
S_GH_STARS = "starsCount"
S_GH_FORKS = "forksCount"
S_GH_SUBSCRIBERS = "subscribersCount"
S_GH_ISSUES =  "issues"#json
S_GH_ISSUES_COUNT = "count"
S_GH_ISSUES_OPEN = "openCount"
S_GH_ISSUES_DISABLED = "isDisabled"
S_GH_CONTRIBUTORS = "contributors"#array
S_GH_COMMITS = "commits"#array
S_GH_COMMITS_LAST_WEEK = 0#json
S_GH_COMMITS_LAST_MONTH = 1#json
S_GH_COMMITS_LAST_THREE_MONTH = 2#json
S_GH_COMMITS_LAST_SIX_MONTH = 3#json
S_GH_COMMITS_LAST_YEAR = 4#json
S_GH_COMMITS_COUNT = "count" 

S_GH_STATUSES = "statuses"#array

#COLLECTED SOURCE CODE
S_SOURCE = "source"#json
S_FILES = "files"#json
S_README_SIZE = "readmeSize"
S_TEST_SIZE = "testsSize"
S_HAS_CHANGE_LOG = "hasChangelog"
S_BADGES = "badges"#array
S_LINTERS = "linters"#json
S_COVERAGE = "coverage"
S_OUTDATED_DEPS = "outdatedDependencies"#json of jsons

#EVALUATION
S_EVALUATION = "evaluation"#json
S_QUALITY = "quality"#json
S_QUALITY_CAREFULNESS = "carefulness"
S_QUALITY_TESTS = "tests"
S_QUALITY_HEALTH = "health"
S_QUALITY_BRANDING = "branding"
S_POPULARITY = "popularity"#json
S_POP_COMMUNITY = "communityInterest"
S_POP_DOWNLOADS_COUNT = "downloadsCount"
S_POP_DOWNLOADS_ACC = "downloadsAcceleration"
S_POP_DEPENDENTS_COUNT = "dependentsCount"
S_MAINTENANCE = "maintenance"#json
S_MAINT_RELEASE_FRECUENCY = "releasesFrequency"
S_MAINT_COMMITS_FRECUENCY = "commitsFrequency"
S_MAINT_OPEN_ISSUES = "openIssues"
S_MAINT_ISSUES_DISTRIBUTION = "issuesDistribution"
 
#SCORE
S_SCORE = "score"#json
S_SCORE_FINAL = "final"
S_SCORE_DETAIL = "detail"#json
S_SCORE_QUALITY = "quality"
S_SCORE_POPULARITY = "popularity"
S_SCORE_MAINTENANCE = "maintenance"

#SEPARATOR
K = ","

def getFeatureVector(data):

    featureVector = ""
    c = data[S_COLLECTED]
    m = c[S_METADATA]
    
    #name
    featureVector += m[NAME] + K 
    
    #has scope
    featureVector += ('0' if(m[S_SCOPE].startswith("unscoped")) else '1') + K 
    
    #version>1 or stable
    featureVector += ('0' if(m[S_VERSION].startswith("0")) else '1') + K

    #hasDescription
    featureVector +=  ('1' if(S_DESCRIPTION in m and not m[S_DESCRIPTION].startswith("ERROR")) else '0') + K
    
    #has keywords
    featureVector += ('1' if(S_KEYS in m) else '0')+K; 
    
    #last release < 1 year
    featureVector += ('1' if(m[S_RELEASE_DATE].startswith("2017") or m[S_RELEASE_DATE].startswith("2018")) else '0') + K
 
    #number of maintainers
    featureVector += str(len(m[S_MAINTAINERS]))+K;
    
    #number of links
    featureVector += str(len(m[S_LINKS].keys()))+K;

    #licence (categorical)
    #featureVector += (m.getString(S_LICEncE)+K;

    #number of dependencies - feature 8
    featureVector += (str(len(m[S_DEPS].keys())) if(S_DEPS in m) else '0')+K;

    #number of releases
    featureVector += str(m[S_RELEASES][S_RELEASES_LAST_MONTH][S_RELEASES_COUNT])+K;
    featureVector += str(m[S_RELEASES][S_RELEASES_LAST_THREE_MONTH][S_RELEASES_COUNT])+K;
    featureVector += str(m[S_RELEASES][S_RELEASES_LAST_SIX_MONTH][S_RELEASES_COUNT])+K;
    featureVector += str(m[S_RELEASES][S_RELEASES_LAST_YEAR][S_RELEASES_COUNT])+K;
    featureVector += str(m[S_RELEASES][S_RELEASES_LAST_TWO_YEAR][S_RELEASES_COUNT])+K;

    #hasSelectiveFiles
    featureVector += ('1' if(S_HAS_SEL_FILES in m and m[S_HAS_SEL_FILES]) else '0')+K;
    
    '''NPM
    8 features'''
    
    n = c[S_NPM]

    #number of downloads
    featureVector += str(n[S_DOWNLOADS][S_DOWNLOADS_LAST_DAY][S_DOWNLOADS_COUNT])+K;
    featureVector += str(n[S_DOWNLOADS][S_DOWNLOADS_LAST_WEEK][S_DOWNLOADS_COUNT])+K;
    featureVector += str(n[S_DOWNLOADS][S_DOWNLOADS_LAST_MONTH][S_DOWNLOADS_COUNT])+K;
    featureVector += str(n[S_DOWNLOADS][S_DOWNLOADS_LAST_THREE_MONTH][S_DOWNLOADS_COUNT])+K;
    featureVector += str(n[S_DOWNLOADS][S_DOWNLOADS_LAST_SIX_MONTH][S_DOWNLOADS_COUNT])+K;
    featureVector += str(n[S_DOWNLOADS][S_DOWNLOADS_LAST_YEAR][S_DOWNLOADS_COUNT])+K;

    #dependents
    featureVector += str(n[S_DEPENDENTS])+K;
    #stars
    featureVector += str(n[S_STARS])+K;
    
    
    '''GITHUB
    15 features'''

    if(S_GH in c):

        featureVector += '1'+K;

        g = c[S_GH];

        #has homepage
        featureVector += ('1' if (S_GH_HOME in g) else '0') +K;
        #gh stars feature 25
        featureVector += str(g[S_GH_STARS])+K;
        #forks
        featureVector += str(g[S_GH_FORKS])+K;
        #subscribers
        featureVector += str(g[S_GH_SUBSCRIBERS])+K;

        #issues count
        featureVector += str(g[S_GH_ISSUES][S_GH_ISSUES_COUNT])+K;
        #issues open
        featureVector += str(g[S_GH_ISSUES][S_GH_ISSUES_OPEN])+K;
        #issues disabled
        featureVector += ('1' if(g[S_GH_ISSUES][S_GH_ISSUES_DISABLED]) else '0')+K;

        #number of contributors
        featureVector += (str(len(g[S_GH_CONTRIBUTORS])) if(S_GH_CONTRIBUTORS in g) else '0')+K;

        #commits
        #number of downloads
        featureVector += str(g[S_GH_COMMITS][S_GH_COMMITS_LAST_WEEK][S_GH_COMMITS_COUNT])+K;
        featureVector += str(g[S_GH_COMMITS][S_GH_COMMITS_LAST_MONTH][S_GH_COMMITS_COUNT])+K;
        featureVector += str(g[S_GH_COMMITS][S_GH_COMMITS_LAST_THREE_MONTH][S_GH_COMMITS_COUNT])+K;
        featureVector += str(g[S_GH_COMMITS][S_GH_COMMITS_LAST_SIX_MONTH][S_GH_COMMITS_COUNT])+K;
        featureVector += str(g[S_GH_COMMITS][S_GH_COMMITS_LAST_YEAR][S_GH_COMMITS_COUNT])+K;

        #statuses
        featureVector += (str(len(g[S_GH_STATUSES])) if(S_GH_STATUSES in g) else '0')+K;

    else:
        featureVector += '0'+K;
        #fill with all ceros
        featureVector += "0,0,0,0,0,0,0,0,0,0,0,0,0,0"+K;
    
    '''Source code
    8 features'''

    if(S_SOURCE in c):

        featureVector += '1'+K;

        s = c[S_SOURCE];

        #hasReadmeScript
        featureVector += ('1' if(S_FILES in s and s[S_FILES][S_README_SIZE]>0) else '0')+K;
        #hasTest
        featureVector += ('1' if(S_FILES in s and s[S_FILES][S_TEST_SIZE]>0) else '0')+K;
        #hasChangeLog
        featureVector += ('1' if(S_FILES in s and S_HAS_CHANGE_LOG in s[S_FILES] and s[S_FILES][S_HAS_CHANGE_LOG]) else '0')+K;
        
        #number of badges
        featureVector += (str(len(s[S_BADGES])) if(S_BADGES in s) else '0')+K;
        #number of linters
        featureVector += (str(len(s[S_LINTERS])) if(S_LINTERS in s) else '0')+K;
        #number of coverage
        featureVector += (str(s[S_COVERAGE]) if(S_COVERAGE in s) else '0')+K;
        #number of outdated deps
        featureVector += (str(len(s[S_OUTDATED_DEPS])) if(S_OUTDATED_DEPS in s) else '0');
   
    else:
        featureVector += '0'+K;
        #fill with all ceros
        featureVector += "0,0,0,0,0,0,0";
    
    return featureVector;


def getScoreVector(data):

    featureVector = ""
    c = data[S_COLLECTED]
    m = c[S_METADATA]
    
    #name
    featureVector += m[NAME] + K 
    
    #scores
    s = data[S_SCORE]
    d = s[S_SCORE_DETAIL]
    
    featureVector += str(d[S_SCORE_QUALITY]) + K
    featureVector += str(d[S_SCORE_POPULARITY]) + K
    featureVector += str(d[S_SCORE_MAINTENANCE])
    
    return featureVector
