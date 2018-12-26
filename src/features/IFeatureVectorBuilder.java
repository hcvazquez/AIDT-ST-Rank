package features;

import ranking.Item;

public interface IFeatureVectorBuilder {

	public String getFeatureVector(Item item) throws Exception;
	
}
