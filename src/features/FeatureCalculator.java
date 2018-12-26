package features;

import ranking.Item;

public abstract class FeatureCalculator {
	
	protected String name; 

	public FeatureCalculator(String name) {
		super();
		this.name = name;
	}

	public abstract Double calculate(Item item);
	
	public String getName(){
		return name;
	}

}
