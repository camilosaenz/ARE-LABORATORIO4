package edu.escuelaing.arep.app;

/**
 * Esta clase generara el inicio para app web
 * @author Camilo
 *
 */
public class WebHTML {
	
	/**
	 * Clase que genera inicio de html
	 * @return pagina html de inicio (home.html)
	 */
	@Web("/home.html")
    public static String sayHello() {
		
        return "<html>"
		        + "<head>"
		    		+ "<title>START WARS</title>"
		    		+ "<meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>"
		    	+ "</head>"
		    	    + "<div style=\"padding:30px; width:96.9%; color:white; font-size:150%; position:absolute ; top:450px ; left:0px\">"
		    	    	+ "<center>\"No importa nada el tamaño. Mírame a mí. Me juzgas por mi tamaño, ¿eh? Y no deberías hacerlo, para mí el aliado es la Fuerza, y es un poderoso aliado. La vida la crea, la hace crecer, nos penetra y nos rodea... - Maestro Yoda\"</center>"
		    	    	+ "<center>"
		    	    + "<form action=\"https://uefachampionsleague-arep-rcsr.herokuapp.com/Apps/about\">"
		    	    	+ "<input type='submit' value='Thanks'>"
		    	    	+ "</form>"
		    	    	+ "<form action=\"https://uefachampionsleague-arep-rcsr.herokuapp.com/UEFA-CHAMPIONS-LEAGUE.png\">"
		    	    	+ "<input type='submit' value='imagen' />"
		    	    + "</form>"
		    	    + "</center>"
		    	    + "</div>"
		    	    + "</body>"
		    			+ "<img src =\"https://www.xtrafondos.com/wallpapers/star-wars-logo-3654.jpg\" height=\"100%\" width=\"100%\" />"
		    			+ "</body>"
		    		+ "<script src=\"index.js\"></script>"
		    	+ "</html>";
    }
}
