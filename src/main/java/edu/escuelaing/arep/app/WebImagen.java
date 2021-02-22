package edu.escuelaing.arep.app;

/**
 * Clase para mostar imagen en app web
 * @author Camilo
 *
 */
public class WebImagen {
	
	/**
	 * Utiliza la imagen stormtrooper.png para mostrar en pagina de app web
	 * @return retorna imagen en HD de app web
	 */
    @Web("/stormtrooper.html")
    public static String imagen() {
        return "<html><title>Fondo de Pantalla</title><body><img src=\"/stormtrooper.png\" width=\"1900\" height=\"950\"></body></html>";
    }
    
}
