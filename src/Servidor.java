
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	public static final int PUERTO = 5000;
	public static final String RUTA_ARCHIVO = "usuarios.txt";

	public static void main(String[] args) {
		File archivo = new File(RUTA_ARCHIVO);
		try {
			if (!archivo.exists()) {
				archivo.createNewFile();
				System.out.println("Se ha creado el archivo");
			} else {
				System.out.println("El archivo ya existe. No es necesario crearlo");
			}

			ServerSocket serverSocket = new ServerSocket(PUERTO);
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("(Servidor) Conexión establecida...");
				ManejoHilosServidor manejador = new ManejoHilosServidor(socket);
				Thread hilo = new Thread(manejador);
				hilo.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
