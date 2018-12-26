package features.js.collectors.core;

import java.io.IOException;
import java.net.Proxy;

import org.json.JSONObject;
import org.jsoup.Jsoup;

import features.IDataCollector;
import ranking.Item;

public class NPMSCollector implements IDataCollector {

	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";

	@Override
	public JSONObject collect(Item item) {
		
		JSONObject data = downloadNPMSData(item.getName(), null);
			
		return data;
	}

	public JSONObject downloadNPMSData(String itemName, Proxy proxy) {
		JSONObject result = new JSONObject();
		String json = null;
		try {
			if (proxy != null) {
				json = Jsoup.connect("https://api.npms.io/v2/package/" + itemName).proxy(proxy).ignoreContentType(true).execute().body();
			} else {
				json = Jsoup.connect("https://api.npms.io/v2/package/" + itemName).ignoreContentType(true).execute().body();
			}
			result = new JSONObject(json);
		} catch (IOException e1) {
			System.out.println("Error estableciendo conexion con NPMS.");
		}
		return result;
	}

}
