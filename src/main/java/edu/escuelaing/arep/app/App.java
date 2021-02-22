package edu.escuelaing.arep.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;

/**
 * Clase principal
 * @author Camilo
 *
 */
public class App {
	
	public ServerSocket serverSocket;
	public Socket socket;
	public PrintWriter writer;
	public BufferedReader buffer;
	private HTTPConexion conexion;
	
	/**
	 * Constructor de la clase
	 */
    public App() {
    	
        int port = getPort();
        conexion = new HTTPConexion();
        
        try {
        	
            serverSocket = new ServerSocket(port);
            
        } catch (IOException e) {
        	
            System.err.println("Could not listen on port: " + port);
            System.exit(1);
            
        }
        
        socket = null;
        writer = null;
        buffer = null;
        
    }
    
    /**
     * Clase Main encargada de correr el proceso
     * @param args parametro pa correr
     * @throws IOException Exception
     */
	public static void main( String[] args ) throws IOException{
        new App().start();
    }
	
	/**
	 * Por medio de un puerto correra la app
	 * @return puerto por el que corre el servicio
	 */
    private int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
	}

    /**
     * Inicia la app web
     * @throws IOException Exception
     */
    public void start() throws IOException {
    	
    	conexion.start();
    	
        while (true) {
        	
            try {
            	
                System.out.println("Ok");
                socket = serverSocket.accept();
                
            } catch (IOException e) {
            	
                System.err.println("Fallo");
                System.exit(1);
                
            }

            writer = new PrintWriter(
                    socket.getOutputStream(), true);
            buffer = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            String input;
            StringBuilder constructor = new StringBuilder();
            Pattern pattern = Pattern.compile("GET (/[^\\s]*)");
            Matcher matcher = null;
            
            while ((input = buffer.readLine()) != null) {
            	
                System.out.println("Recibí: " + input);
                constructor.append(input);
                
                if (!buffer.ready()) {
                    matcher = pattern.matcher(constructor.toString());
                    
                    if (matcher.find()) {
                    	
                        String archivo = matcher.group().substring(4);
                        System.out.println("VALUE: " + archivo);
                        
                        if (!conexion.recurso(archivo)) {
                            request(archivo);
                        } else {
                        	
                            writer.println("HTTP/1.1 200 \r\nAccess-Control-Allow-Origin: *\r\nContent-Type: text/html\r\n\r\n");
                            writer.println(conexion.getRecurso(archivo));
                            
                        }
                    }
                    
                    break;
                    
                }
            }
            
            writer.close();
            buffer.close();
            socket.close();
        }
    }
    
    /**
     * Relaciona los recursos con la app web
     * @param archivo string de app web
     * @throws IOException Exception
     */
	private void request(String archivo) throws IOException {
		
		String path = "src/main/resources/";
        String change = FilenameUtils.getExtension(archivo);
        boolean valor = false;
        
        switch (change) {
        
            case "png":
            	path += "image/" + archivo;
            	valor = true;
                break;
            case "js":
            	path += "js/" + archivo;
                break;
            case "html":
            	path += "web/" + archivo;
                break;
                
        }

        File file = new File(path);
        
        if (file.exists() && !file.isDirectory()) {
        	
            String header = generarEncabezado(valor, change, file.length());
            
            if (valor) {
                FileInputStream input = new FileInputStream(path);
                OutputStream output = socket.getOutputStream();
                for (char c : header.toCharArray()) {
                	output.write(c);
                }
                
                int a;
                while ((a = input.read()) > -1) {
                	output.write(a);
                	
                }
                
                output.flush();
                input.close();
                output.close();
                
            } else {
            	
                writer.println(header);
                BufferedReader br = new BufferedReader(new FileReader(file));

                StringBuilder stringBuilder = new StringBuilder();
                String string;
                
                while ((string = br.readLine()) != null) {
                    stringBuilder.append(string);
                }
                
                writer.println(stringBuilder.toString());
                br.close();
            }
        } else {
        	writer.println("HTTP/1.1 404\r\nAccess-Control-Allow-Origin: *\r\n\r\n<html><body><h1>404 NOT FOUND (" + archivo + ")</h1></body></html>");
        }
	}
	
	/**
	 * Generara el encabezado
	 * @param valor booleano de app web
	 * @param change string de app web
	 * @param length longitud file
	 * @return retorna encabezado de app web
	 */
	private String generarEncabezado(boolean valor, String change, long length) {
		
        String encabezado = null;
        if (valor) {
        	encabezado = "HTTP/1.1 200 \r\nAccess-Control-Allow-Origin: *\r\nContent-Type: image/" + change + "\r\nConnection: close\r\nContent-Length:" + length + "\r\n\r\n";
        } else {
        	encabezado = "HTTP/1.1 200 \r\nAccess-Control-Allow-Origin: *\r\nContent-Type: text/html\r\n\r\n";
        }
        
        return encabezado;
        
	}
}
