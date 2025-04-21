import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ManejoHilosServidor implements Runnable {
	
	private Socket socket; // socket conectado del cliente
	File archivo = new File(Servidor.RUTA_ARCHIVO); // archivo donde se guardan los mensajes (no se usa aquí hay uno por conversación)
	private static final Map<String, DataOutputStream> mapaUsuarios = new HashMap<>(); // mapa con los usuarios conectados y su flujo de salida

	public ManejoHilosServidor(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		// Vamos a crear el flujo de datos
		try {
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

			// Leemos las credenciales y comprobamos si son válidas
			String credenciales = dis.readUTF(); // por ejemplo: "ana:1234"
			System.out.println("(Servidor) Credenciales recibidas: " + credenciales);
			if (!credencialesValidas(credenciales)) {
				dos.writeUTF("ERROR: Credenciales inválidas.");
				socket.close();
				return;
			}

			// Leemos el nombre del cliente (ya autenticado)
			String nombreCliente = dis.readUTF();
			System.out.println("(Servidor) Cliente autenticado: " + nombreCliente);

			// Añadimos al cliente al mapa de usuarios conectados
			synchronized (mapaUsuarios) {
				mapaUsuarios.put(nombreCliente, dos);
				System.out.println("(Servidor) Usuario conectado añadido al combo: " + nombreCliente);
			}

			// Enviamos la lista de usuarios conectados en este mismo hilo
			synchronized (mapaUsuarios) {
				for (String usuario : mapaUsuarios.keySet()) {
					if (!usuario.equals(nombreCliente)) {
						dos.writeUTF("USUARIO:" + usuario);
					}
				}
				dos.writeUTF("FIN_USUARIOS");
				System.out.println("(Servidor) Lista de usuarios enviada.");
			}

			// Comenzamos a recibir mensajes de este cliente
			String mensaje = "";
			while (true) {
				mensaje = dis.readUTF();
				String partes[] = mensaje.split("->"); // Formato: Ana->Luis: hola qué tal
				String emisor = partes[0].trim();

				String[] receptorTexto = partes[1].split(":", 2);
				String receptor = receptorTexto[0].trim();
				String texto = receptorTexto[1].trim();

				String nombreArchivo = generarNombreArchivo(emisor, receptor); // genera nombre de archivo único por conversación
				guardarMensaje(nombreArchivo, "[" + emisor + " → " + receptor + "] " + texto); // guardamos en archivo
				
				// Reenvío el mensaje al destinatario si está conectado
				synchronized (mapaUsuarios) {
					if (mapaUsuarios.containsKey(receptor)) {
						DataOutputStream destino = mapaUsuarios.get(receptor);
						destino.writeUTF("[" + emisor + "] " + texto);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("(Servidor) Error en hilo de cliente -> " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Método para generar el nombre del archivo compartido entre emisor y receptor
	private String generarNombreArchivo(String emisor, String receptor) {
		if (emisor.compareTo(receptor) < 0) {
			return emisor + "_" + receptor + ".txt";
		} else {
			return receptor + "_" + emisor + ".txt";
		}
	}

	// Método sincronizado para guardar mensajes en el archivo
	private synchronized void guardarMensaje(String archivo, String linea) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
			bw.write(linea);
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Comprobamos si el usuario está registrado
	private boolean credencialesValidas(String credenciales) {
		File archivoUsuarios = new File("usuarios.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(archivoUsuarios))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				if (linea.trim().equalsIgnoreCase(credenciales.trim())) {
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
