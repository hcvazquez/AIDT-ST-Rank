package features;

import org.json.JSONObject;

import ranking.Item;

public interface IDataCollector {

	public JSONObject collect(Item item);

}
