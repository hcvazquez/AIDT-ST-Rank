package features.js.collectors.core;

import java.io.BufferedReader;
import java.io.FileReader;
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
import util.ConfigManager;

public class NPMRegistryCollector implements IDataCollector {

	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
	public static HashMap<String, String> NPM_DATA = null;

	@Override
	public JSONObject collect(Item item) {	
		
		JSONObject result = new JSONObject();
		
		if(NPM_DATA==null)loadNPMDataInMemory();
		
		JSONObject data = new JSONObject(NPM_DATA.get(item.getName()));
		
		result.append(NC.NAME,getData(data,NC.NAME) );
		result.append(NC.VERSION,getData(data,NC.VERSION) );
		result.append(NC.DESCR,getData(data,NC.DESCR) );
		result.append(NC.KEYS,getData(data,NC.KEYS) );
		result.append(NC.HOME,getData(data,NC.HOME) );
		result.append(NC.REPO,getData(data,NC.REPO) );
		result.append(NC.BUGS,getData(data,NC.BUGS) );
		result.append(NC.LICENSE,getData(data,NC.LICENSE) );
		result.append(NC.MAINTAINERS,getData(data,NC.MAINTAINERS) );
		result.append(NC.CONTRIBUTORS,getData(data,NC.CONTRIBUTORS) );
		result.append(NC.DEPS,getData(data,NC.DEPS) );
		result.append(NC.DEVDEPS,getData(data,NC.DEVDEPS) );
		
		//System.out.println(result.toString());
		
		return result;
	}

	public void loadNPMDataInMemory() {

		NPM_DATA = new HashMap<String, String>();
		String npm_file = ConfigManager.getInstance().getProperty("json_file_path");

		try {
			FileReader fr = new FileReader(npm_file);
			BufferedReader br = new BufferedReader(fr);
			int count=0;
			String linea;
			while ((linea = br.readLine()) != null) {
				if (',' == linea.charAt(linea.length() - 1)) {
					linea = linea.substring(0, linea.length() - 1);
				}
		    	String[] split = linea.split("\\{\"id\":\"");
		    	if(split.length>1){
		    		String name = split[1].split("\",\"")[0];
		    		NPM_DATA.put(name, linea);
		    		System.out.println(count++);
		    	}
			}
			fr.close();
		} catch (Exception e) {
			System.out.println("Excepcion leyendo fichero " + npm_file + ": " + e);
		}

	}

	private String getData(JSONObject json, String key) {
		String data = "";
		try {
			data = json.getJSONObject(NC.JSON_VALUE).get(key).toString();
			data = data.split("#readme")[0];
		} catch (Exception e) {
			data = "";
		}
		return data;
	}

}
