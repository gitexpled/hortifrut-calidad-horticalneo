package com.goplicity.horticalneo;

import java.io.IOException;

import javax.swing.JFormattedTextField;

import org.json.JSONArray;
import org.json.JSONObject;

import com.goplicity.horticalneo.expledlib.CurlRequest;
import com.goplicity.horticalneo.expledlib.ResourceUtils;
import com.goplicity.horticalneo.expledlib.StringUtils;


/**
 * Hello world!
 */
public class App {
	
	public static Boolean useTestData = false;
	public static String hfUrl = "https://syscalidad.hortifrut.com/";
	public static String neoUrl = "localhost:8000";
	//public static String neoUrl ="http://10.8.0.2:8000";
	
    public static void main(String[] args) throws IOException {
        //System.out.println("Hello World!");
    	start(args);
         
    }
    
    public static void start(String[] args) throws IOException{
    	
    	
    	String jtext = ResourceUtils.getResourceFileContent("test.json");
    	JSONArray jdata;// = new JSONArray(jtext);
    	
    	//curl neolitics
    	String dateNow = StringUtils.getStringDateFormat("yyyy-MM-dd'T'HH:mm:ss",1);
    	String dateNowl1 = StringUtils.getStringDateFormat("yyyy-MM-dd'T'HH:mm:ss",-10);
    	System.out.println(dateNow+" "+dateNowl1);
    	String[] neocurl = {
				"curl",
				"--location",
				neoUrl+"/api/batch/9e3675c9-1a60-486e-92f6-0f50cbc41793/cust?r="+dateNowl1+"&e="+dateNow,
				"--request","GET",
				};
        JSONObject cneo = CurlRequest.callCurl(neocurl);
		JSONObject otneo =null;
		if(StringUtils.isValidJson(cneo.optString("responseBody"))) {
			jdata = new JSONArray(cneo.optString("responseBody"));
		}else if(useTestData){
			jdata = new JSONArray(jtext);
		}else {
			otneo = cneo;
			otneo.put("error", 1);
			otneo.put("message", "no valid data");
			System.out.println(otneo);
			return;
		}
    	
    	
    	//System.out.println(jdata);
    	
    	int i=0;
    	for(Object o:jdata) {
    		JSONObject item = (JSONObject)o;
    		jdata.getJSONObject(i).getJSONObject("customer").put("logo", false);
    		
    		String statsUrl =neoUrl+"/api/stats/batch/"+item.getString("id");
    		JSONArray stats = localGetRequest(statsUrl);
    		jdata.getJSONObject(i).put("stats",stats);
    		
    		String samplesUrl =neoUrl+"/api/sample/"+item.getString("id")+"/batch";
    		JSONArray samples = localGetRequest(samplesUrl);
    		jdata.getJSONObject(i).put("samples",samples);
    		
    		i++;
    	}
    	//System.out.println(jdata);
    	String jsondatastring = jdata+"";
    	/*if(isWin()) {
    		//jsondatastring=jsondatastring.replaceAll("\"","\\\\\"");
		}else {
			//jsondatastring="'"+jsondatastring+"'";
		}*/
		/*
        String[] scurl = {
				"curl",
				"--location",
				hfUrl+"hana/_origen/neolitics/NeoliticsRequest.php",
				"--request","POST",
				"--header", "Content-Type: application/json",
				"--data",jsondatastring
				};
        JSONObject c = CurlRequest.callCurl(scurl);
		JSONObject ot =null;
		if(StringUtils.isValidJson(c.optString("responseBody"))) {
			ot = new JSONObject(c.optString("responseBody"));
		}else {
			ot = c;
		}
		*/
    	String url = hfUrl+"hana/_origen/neolitics/NeoliticsRequest.php";
    	JSONObject c;
    	JSONObject ot =null;
		try {
			c = CurlRequest.callCurlPOST(url,jsondatastring);
			if(StringUtils.isValidJson(c.optString("responseBody"))) {
				ot = new JSONObject(c.optString("responseBody"));
			}else {
				ot = c;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(ot);
    }
    
    public static boolean isWin() {
    	String osName = System.getProperty("os.name");

        if (osName != null && osName.startsWith("Windows")) {
            return true;
        } else {
            return false;
        }
    }
    
    public static JSONArray localGetRequest(String url) throws IOException {
    	JSONArray r = new JSONArray();
    	String[] neocurl = {
				"curl",
				"--location",
				url,
				"--request","GET",
				};
        JSONObject cneo = CurlRequest.callCurl(neocurl);
		JSONObject otneo =null;
		if(StringUtils.isValidJson(cneo.optString("responseBody"))) {
			r = new JSONArray(cneo.optString("responseBody"));
		}else {
			otneo = cneo;
			otneo.put("error", 1);
			otneo.put("message", "no valid data");
			System.out.println(otneo);
			r.put(otneo);
		}
		return r;
    }
}
