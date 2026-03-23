package com.goplicity.horticalneo.expledlib;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ResourceUtils {
	
	public static String getResourceFileContent(String archivo) {
		String contenido="";
        try (InputStream is = ResourceUtils.class.getClassLoader().getResourceAsStream(archivo)) {
            if (is == null) {
                throw new RuntimeException("No se encontró el archivo: " + archivo);
            }
            
            try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
                contenido = scanner.useDelimiter("\\A").next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return contenido;
	}
}
