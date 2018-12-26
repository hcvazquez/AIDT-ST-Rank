package features.js.collectors;

import java.io.IOException;

public class TaskPipeline {

	public static void main(String[] args) throws IOException {
		
		CollectFeaturesFromNPMs.main(args);
		CollectFeaturesFromNPMCompare.main(args);
		CollectFeaturesRelatedFromNPMs.main(args);
		

	}

}
