import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;

public class ManejoHilosServidor implements Runnable{
	
	private Socket socket;
	File archivo= new File(Servidor.RUTA_ARCHIVO);
	
	

	public ManejoHilosServidor(Socket socket) {
	//	super();
		this.socket = socket;
	}



	@Override
	public void run() {
		// Vamos a crear el flujo de datos
		try {
			DataInputStream dis= new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			String nombreCliente=dis.readUTF();
			System.out.println("Cliente conectado :"+ nombreCliente);
			String mensaje = "";
			while(true) {
				mensaje= dis.readUTF();
				String partes[]=mensaje.split("->");//HAY QUE CAMBIAR EL MENSAJE
				//METER ALGO QUE SE DIFERENCIE MEJOR
				String emisor= partes[0].trim();
				String[] receptorTexto=partes[1].split(":",2);
				String receptor= receptorTexto[0].trim();
				String texto=receptorTexto[1].trim();
				String nombreArchivo=generarNombreArchivo(emisor,receptor);
				guardarMensaje(nombreArchivo, "[" + emisor + " → " + receptor + "] " + texto);
				//Este método escribe el archivo
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private String generarNombreArchivo(String emisor, String receptor) {
	    if (emisor.compareTo(receptor) < 0) {
	    	//Las cadenas de texto no son idénticas, cambia el mensaje
	    	//Si son iguales devuelve 0
	    	//Si es nenor devuelve neg
	    	//Si es mayor devuelve positivo
	    	//Como quiero que genere sólo 1 archivo y no 2 dependiendo de quien responde uso este método 
	        return emisor + "_" + receptor + ".txt";
	    } else {
	        return receptor + "_" + emisor + ".txt";
	    }
	}

	private synchronized void guardarMensaje(String archivo, String linea) {
	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
	        bw.write(linea);
	        bw.newLine();
	        bw.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


}
