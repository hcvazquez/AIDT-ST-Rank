package item.collectors;

public class TaskPipeline {

	public static void main(String[] args) throws Exception {
		//GetAllJAvascriptProjectGithubV4.main([]) //returns 1000 projects
		
		GetAllJAvascriptProjectGithubV3.main(args); //returns 1020 projects
		GetPackageJSONfromURLsFile.main(args);
		GetRelevanceFromPackageJSONs.main(args);
		
		/**
		 * This task depends on manual collected information about sides and concerns
		 */
		RemoveUnknownPkgs.main(args);
		
	}

}
