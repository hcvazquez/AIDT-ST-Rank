package util;

import java.io.File;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class FeatureManager {

	private static FeatureManager instance = null;
	
	Configuration config;

	protected FeatureManager() {
		Configurations configs = new Configurations();
		try {
			config = configs.properties(new File("feature.properties"));
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static FeatureManager getInstance() {
		if (instance == null) {
			instance = new FeatureManager();
		}
		return instance;
	}

	public String getProperty(String property) {
		return config.getString(property);
	}

}