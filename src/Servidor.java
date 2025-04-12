import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	private ServerSocket serverSocket;
	protected static final String RUTA_ARCHIVO= "conversacion.txt";
	
	

	public Servidor(int puerto) throws IOException {
		
		serverSocket= new ServerSocket(puerto);//Guarda el ServerSocket para aceptar las conexiones luego
		//Ahora vamos con la creación de archivo si no existe
		File archivo = new File(RUTA_ARCHIVO);
		if(!archivo.exists()) {
			archivo.createNewFile();
			System.out.println("Se ha creado el archivo");
		}else {
			System.out.println("El archivo ya existe. No es necesario crearlo");
		}
		while(true) {
			Socket socket = serverSocket.accept();//Aceptamos las conxiones en bucle infinito
			System.out.println("(Servidor) Conexión establecida...");
			//AQUÍ CREAREMOS LOS HILOS CUANDO HAYA CONSTRUIDO MANEJOHILOSSERVIDOR
		}
		
		
	}



	public static void main(String[] args) {
		try {
			new Servidor(5000);
		} catch (IOException e) {
			System.out.println("(Servidor) Error al iniciar: " + e.getMessage());
			e.printStackTrace();
		}

	}

}
