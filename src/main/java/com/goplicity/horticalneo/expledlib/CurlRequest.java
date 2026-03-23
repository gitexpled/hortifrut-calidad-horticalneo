package com.goplicity.horticalneo.expledlib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class CurlRequest {
	
	public CurlRequest() {
		
	}
	
	public static JSONObject callCurl(String[] curl) throws IOException {
		//System.out.println(curl);
		JSONObject r = new JSONObject();
		Process process = Runtime.getRuntime().exec(curl);
		
		
        String sresult = getInputStream2String(process.getInputStream());
        //System.out.println("Body: "+sresult);
        r.put("responseBody",sresult);
        try {
        	 //JSONObject oresult = new JSONObject(sresult);
        }catch(Exception ex) {
        	System.out.println( getInputStream2String(process.getErrorStream()));
        	r.put("error",1);
        	r.put("Message","Respuesta sin contenido "+ex.getMessage());
        	return r;
        }
        
        r.put("error",0);
		r.put("message","OK");
        return r;
	}
	
	public static String getInputStream2String(InputStream inputStream) throws IOException {
		StringBuilder outputBuilder = new StringBuilder();
		try (
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
		     String line;
             while ((line = reader.readLine()) != null) {
                outputBuilder.append(line).append("\n");
             }
        }
		
        String result = outputBuilder.toString();
        return result;
	}
	
	public static JSONObject callCurlPOST(String targetURL,String json) throws Exception {
        
		URL url = new URL(targetURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");

        // Indicando que enviamos JSON
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        // Enviar JSON al servidor
        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes("UTF-8"));
        os.flush();
        os.close();

        // Leer respuesta
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream()), "UTF-8"));

        StringBuilder response = new StringBuilder();
        String output;

        while ((output = br.readLine()) != null) {
            response.append(output);
        }
        
        JSONObject r = new JSONObject();
        conn.disconnect();
        //System.out.println(response.toString());
        r.put("responseBody",response.toString());
        try {
        	 //JSONObject oresult = new JSONObject(sresult);
        }catch(Exception ex) {
        	r.put("error",1);
        	r.put("Message","Respuesta sin contenido "+ex.getMessage());
        	return r;
        }
        return r;
    }

}
