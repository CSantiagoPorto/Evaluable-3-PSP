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
			String mensaje = "";
			while(true) {
				mensaje= dis.readUTF();
				System.out.println("El mensaje recibido es: "+ mensaje);
				BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
				bw.write(mensaje);
				bw.newLine();
				bw.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
