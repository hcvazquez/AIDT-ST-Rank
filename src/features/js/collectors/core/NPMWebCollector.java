package features.js.collectors.core;

import java.io.IOException;
import java.net.Proxy;
import java.util.HashMap;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import features.IDataCollector;
import ranking.Item;

public class NPMWebCollector implements IDataCollector {

	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
	public static HashMap<String, String> NPM_DATA = null;

	@Override
	public JSONObject collect(Item item) {	
		
		JSONObject result = downloadNPMWebPageData(item.getName(), null);
		
		//System.out.println(result.toString());
		
		return result;
	}

	public JSONObject downloadNPMWebPageData(String itemName, Proxy proxy) {
		JSONObject result = new JSONObject();
		result.append(NC.ID, itemName);
		Document doc = null;
		try {
			System.out.println("Downloading " + itemName);
			if (proxy != null) {
				doc = Jsoup.connect("https://www.npmjs.com/package/" + itemName).proxy(proxy).userAgent(USER_AGENT)
						.timeout(0).get();
			} else {
				doc = Jsoup.connect("https://www.npmjs.com/package/" + itemName).userAgent(USER_AGENT).timeout(0).get();
			}
			if (doc.select("div.sidebar") != null) {
				Elements box = doc.select("div.sidebar").select("ul.box");
				Elements li = box.get(0).select("li");
				result.append(NC.LAST_PUBLISHER,box.select("li.last-publisher a").select("span").text());
				result.append(NC.LAST_PUBLISH_DATE,box.select("li.last-publisher").select("span").last().attr("data-date"));
				result.append(NC.LAST_VERSION,li.get(1).select("strong").text());

				String arr[] = li.get(1).text().split(" ");
				for (int i = 0; i < arr.length; i++) {
					if (arr[i].startsWith("release")) {
						result.append(NC.NUM_VERSION, arr[i - 1]);
					}
				}
				
				li = box.get(1).select("li");
				result.append(NC.DL_LAST_DAY,li.get(0).select("strong").text());
				result.append(NC.DL_LAST_WEEK,li.get(1).select("strong").text());
				result.append(NC.DL_LAST_MONTH,li.get(2).select("strong").text());
				
				Elements h3s = doc.select("div.sidebar h3");
				for(Element h3:h3s){
					if(h3.text().startsWith("Dependents")){
						String parts[] = h3.text().split("\\)")[0].split("\\(");
						if(parts.length>1){
							result.append(NC.DEPENDENTS,parts[1]);
						}else{
							result.append(NC.DEPENDENTS,"0");
						}
					}
				}

			}
		} catch (IOException e1) {
			System.out.println("Error estableciendo conexion con NPM.");
		}

		return result;
	}

}
