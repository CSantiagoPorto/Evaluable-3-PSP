import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ManejoHilosCliente extends Thread {
	private DataInputStream in;
	private DataOutputStream out;
	private Socket socket;
//Va a recibir los flujos para guardarlos y usarlos en el menú
	public ManejoHilosCliente(DataInputStream in, DataOutputStream out) {
		//super();
		this.in = in;
		this.out = out;
	}
	
	
	public void run() {}

}
