package features;

import java.util.HashMap;

import ranking.Item;

public abstract class ComposedCalculator extends FeatureCalculator {
	
	protected HashMap<String,FeatureCalculator> calculators;
	
	public ComposedCalculator(String name, HashMap<String, FeatureCalculator> calculators) {
		super(name);
		this.calculators = calculators;
	}

	@Override
	public abstract Double calculate(Item item);


}
