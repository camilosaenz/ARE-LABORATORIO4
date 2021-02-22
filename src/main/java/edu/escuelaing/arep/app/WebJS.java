package edu.escuelaing.arep.app;

/**
 * Clase para mostrar js en app web
 * @author Camilo
 *
 */
public class WebJS {
	
	/**
	 * Muestra js en app web, thanks.html
	 * @return
	 */
    @Web("/thanks.html")
    public static String returnHtmlWithJS(){
        return "<html><title>thanks</title><head><script src=\"/index.js\"></script></head>"
        		+ "<body style = \"background: url(https://i.pinimg.com/originals/44/ac/f0/44acf0c89a96f3cd5e3aaaa6c7c61dfc.jpg) no-repeat ; background-size: 100% 100%;\">\r\n"
        		+ "</body>"
        		+ "</html>";
    }
	
}
