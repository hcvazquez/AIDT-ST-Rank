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

public class NPMCompareCollector implements IDataCollector {

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
		result.put(NC.ID, itemName);
		Document doc = null;
		try {
			System.out.println("Downloading " + itemName);
			if (proxy != null) {
				doc = Jsoup.connect("https://npmcompare.com/compare/" + itemName).proxy(proxy).userAgent(USER_AGENT)
						.timeout(0).get();
			} else {
				doc = Jsoup.connect("https://npmcompare.com/compare/" + itemName).userAgent(USER_AGENT).timeout(0).get();
			}
			Elements elems = doc.select("tr");
			if (elems != null) {
				int relatedIndex = -1;
				for(int i=0;i<elems.size();i++){
					Elements ths = elems.get(i).select("th");
					if(ths!=null && ths.size()==1 && ths.text().startsWith("Related / similar packages")){
						relatedIndex = i + 1;
					}
				}
				if(relatedIndex>-1){
					Elements as = elems.get(relatedIndex).select("td").get(0).select("a");
					for(Element a:as){
						result.append(NC.RELATED_ITEMS, a.text());
					}
				}
			}
			Element total = doc.select("td.total").first();
			if(total!=null){
				Double points = Double.valueOf(total.attr("value"));
				result.put(NC.NPM_COMPARATOR_POINTS, points);
			}
			
		} catch (IOException e1) {
			System.out.println("Error estableciendo conexion con NPM.");
		}

		return result;
	}
	
	public Double getScore(String itemName, Proxy proxy) {
		Double result = 0.0;
		Document doc = null;
		try {
			System.out.println("Downloading " + itemName);
			if (proxy != null) {
				doc = Jsoup.connect("https://npmcompare.com/compare/" + itemName).proxy(proxy).userAgent(USER_AGENT)
						.timeout(0).get();
			} else {
				doc = Jsoup.connect("https://npmcompare.com/compare/" + itemName).userAgent(USER_AGENT).timeout(0).get();
			}
			Element total = doc.select("td.total").first();
			if(total!=null){
				result = Double.valueOf(total.attr("value"));
			}
			
		} catch (IOException e1) {
			System.out.println("Error estableciendo conexion con NPM.");
		}

		return result;
	}

}
