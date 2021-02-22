package edu.escuelaing.arep.app;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

/**
 * Esta clase se encargara de realizar la conexion en el servidor web
 * @author Camilo
 *
 */
public class HTTPConexion {
	
	public Map<String, Method> url;
	
	/**
	 * Constructor de la clase
	 */
	public HTTPConexion() {
		url = new HashMap<>();
	}
	
	/**
	 * Metodo para obtener el recurso
	 * @param recurso valor dado
	 * @return respuesta del recurso
	 */
	public String getRecurso(String recurso) {
       String response = null;
       
        try {
        	
        	response = (String) url.get(recurso).invoke(null);
        	
        } catch (IllegalAccessException | InvocationTargetException e) {
        	
            e.printStackTrace();
            System.out.println("Error invoking method");
            response = "ERROR";
            
        }
        return response;
    }
	
	/**
	 * Este metodo utiliza una url como recurso para app web
	 * @param recurso
	 * @return recurso
	 */
	public boolean recurso(String recurso) {
		return url.containsKey(recurso);
	}
	
	/**
	 * Merodo que inicia app web
	 */
	public void start() {
		
        String paquete = "edu.escuelaing.arep.app";
        Reflections reflexion = new Reflections(paquete, new SubTypesScanner(false));
        Set<Class<? extends Object>> allClasses = reflexion.getSubTypesOf(Object.class);
        
        for (Class clase : allClasses) {
        	
            for (Method metodo : clase.getDeclaredMethods()) {
            	
            	if (metodo.isAnnotationPresent(Web.class)) {
                    //url.put(metodo.getAnnotation(Web.class).value(), metodo);
                    url.put(metodo.getAnnotation(Web.class).value(), metodo);
                    
                }
            }
        }
	}

}
