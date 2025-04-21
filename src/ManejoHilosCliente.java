import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JTextArea;

public class ManejoHilosCliente extends Thread {
	private DataInputStream in;
	private DataOutputStream out;
	private Socket socket;
	private VentanaChat ventanaChat;
	private JTextArea taConversacion;
//Va a recibir los flujos para guardarlos y usarlos en el menú
	public ManejoHilosCliente(DataInputStream in, DataOutputStream out, VentanaChat vc) {
		//super();
		this.in = in;
		this.out = out;
		this.ventanaChat=vc;
	}
	
	 public ManejoHilosCliente(DataInputStream in, JTextArea taConversacion) {
	        this.in = in;
	        this.taConversacion = taConversacion;
	    }
	
	
	public void run() {
		while(true) {
			try {
				String mensaje=in.readUTF();
				taConversacion.append(mensaje+"\n");
				System.out.println("Cliente: Mensaje recibido del servidor: "+mensaje);
				ventanaChat.mostrarMensajeEnPantalla(mensaje);

				
			} catch (IOException e) {
				System.out.println("Cliente: Conexión cerrada o error: " + e.getMessage());
				e.printStackTrace();
			}
				
		}
		
	}

}
