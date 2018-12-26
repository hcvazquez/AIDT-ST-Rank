package ranking.builder;

public class TaskPipelineWithSplits {

	public static void main(String[] args) throws Exception {
		
		//BuildLiswiseRank.main(args);	
		
		//BuildListwiseRankBySideWithSplit.main(args);	
		BuildPairwiseRankWithSplit.main(args);
		
		BuildPairwiseVectorTrainingSetWithSplits.main(args);
		
		//CreateDataSetScores.main(args);
		//CreateDataSetVectors.main(args);
	}

}
