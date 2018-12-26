package features;

import util.FeatureManager;

public interface  IFeature {
	
	/**
	 * FEATURE NAMES CONSTANTS
	 */
	public static String POPULARITY = FeatureManager.getInstance().getProperty("POPULARITY");
	public static String SUPPORT = FeatureManager.getInstance().getProperty("SUPPORT");
	public static String OPINIONS = FeatureManager.getInstance().getProperty("OPINIONS");
	public static String EFFICIENCY = FeatureManager.getInstance().getProperty("EFFICIENCY");
	public static String MAINTAINABILITY = FeatureManager.getInstance().getProperty("MAINTAINABILITY");
	public static String PORTABILITY = FeatureManager.getInstance().getProperty("PORTABILITY");
	public static String INTEROPERABILITY = FeatureManager.getInstance().getProperty("INTEROPERABILITY");
	public static String RELIABILITY = FeatureManager.getInstance().getProperty("RELIABILITY");
	public static String REUSABILITY = FeatureManager.getInstance().getProperty("REUSABILITY");
	public static String LICENCE = FeatureManager.getInstance().getProperty("LICENCE");
	public static String LANGUAGE = FeatureManager.getInstance().getProperty("LANGUAGE");
	/**
	 * 
	 */
	
	public String getName();

	public void setName(String name);

	public Double getValue();

	public void setValue(Double value);

}
