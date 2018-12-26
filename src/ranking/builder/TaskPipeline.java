package ranking.builder;

public class TaskPipeline {

	public static void main(String[] args) throws Exception {
		
		BuildLiswiseRank.main(args);
		BuildPairwiseRank.main(args);
		
		BuildPairwiseRankBySide.main(args);
		
		BuildPairwiseVectorTrainingSet.main(args);
		
		CreateDataSetScores.main(args);
		CreateDataSetVectors.main(args);
	}

}
