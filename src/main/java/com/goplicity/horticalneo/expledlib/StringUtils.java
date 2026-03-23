package com.goplicity.horticalneo.expledlib;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StringUtils {
	static public String join(List<String> list, String conjunction)
	{
	   StringBuilder sb = new StringBuilder();
	   boolean first = true;
	   for (String item : list)
	   {
	      if (first)
	         first = false;
	      else
	         sb.append(conjunction);
	      sb.append(item);
	   }
	   return sb.toString();
	}
	
	static public String join(String list[], String conjunction)
	{
	   StringBuilder sb = new StringBuilder();
	   boolean first = true;
	   for (String item : list)
	   {
	      if (first)
	         first = false;
	      else
	         sb.append(conjunction);
	      sb.append(item);
	   }
	   return sb.toString();
	}
	
	public static String capitalizarCadaPalabra(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }

        String[] palabras = texto.toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String palabra : palabras) {
            if (palabra.length() > 0) {
                sb.append(Character.toUpperCase(palabra.charAt(0)))
                  .append(palabra.substring(1))
                  .append(" ");
            }
        }

        return sb.toString().trim();
    }
	
	public static boolean isValidJson(String jsonString) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            return false;
        }
        try {
            new JSONObject(jsonString); // Try parsing as a JSON object
            return true;
        } catch (JSONException e) {
            try {
                new JSONArray(jsonString); // Try parsing as a JSON array
                return true;
            } catch (JSONException ne) {
                return false; // Not a valid JSON object or array
            }
        }
    }
	
	public static String getStringDateFormat(String format) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        //yyyy-MM-dd HH:mm:ss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String formattedDateTime = currentDateTime.format(formatter);
        return formattedDateTime;
	}
	
	public static String getStringDateFormat(String format,int days) {
		LocalDateTime currentDateTime = LocalDateTime.now().plusHours(days);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String formattedDateTime = currentDateTime.format(formatter);
        return formattedDateTime;
		
	}
}
