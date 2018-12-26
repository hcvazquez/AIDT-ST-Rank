package features;

import java.util.ArrayList;

import org.json.JSONObject;

import ranking.Item;

public class FeatureVectorBuilder implements IFeatureVectorBuilder{

	protected ArrayList<FeatureCalculator> calculators;
	
	public FeatureVectorBuilder(ArrayList<FeatureCalculator> calculators) {
		super();
		this.calculators = calculators;
	}

	@Override
	public String getFeatureVector(Item item) {
		
			
			String fvector = "";
			
			for(FeatureCalculator calculator:calculators){
				fvector = fvector + calculator.calculate(item) + ",";
			}
			
			return fvector.substring(0,fvector.length()-1);

		
	}
	
	public String getHeadRow(){
		
		String hvector = "";
		
		for(FeatureCalculator calculator:calculators){
			hvector = hvector + calculator.getName() + ",";
		}
		
		return hvector.substring(0,hvector.length()-1);
	}

}
