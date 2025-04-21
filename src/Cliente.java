import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente {
	private String serverIP;
	private int serverPort;
	private Socket socket;
	private DataInputStream is;
	private DataOutputStream out;

	public Cliente(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}

	public void start() throws IOException {
		System.out.println("(Cliente) Estableciendo conexión...");
		socket = new Socket(serverIP, serverPort);
		is = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		System.out.println("(Cliente) Conexión establecida...");
	}

	public DataInputStream getInputStream() {
		return is;
	}

	public DataOutputStream getOutputStream() {
		return out;
	}

	public Socket getSocket() {
		return socket;
	}
}
