import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

//import Cliente.Cliente;

//import Cliente.ManejoHilosCliente;

public class Cliente {
	
	private String serverIP;//la IP
	private int serverPort;//puerto del servidor
	private Socket socket;// puente de conexión
	private DataInputStream is;//Flujo lectura
	private DataOutputStream out;//flujo escritura
	public Cliente(String serverIP, int serverPort) {//Guarda los valores de IP y puerto para poder conectarse
		super();
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		 
	}
	
	public void start() throws UnknownHostException, IOException {//Inicia la conexión
		System.out.println("(Cliente) Estableciendo conexión...");
		socket=new Socket(serverIP, serverPort);//Creamos el puente, conectando el socket al servidor
		is=new DataInputStream(socket.getInputStream());//Creamos el flujo de entrada y salida
		out=new DataOutputStream(socket.getOutputStream());
		System.out.println("(Cliente) Conexión establecida...");
		
		
		//Iniciamos el hilo para el cliente
		//ManejoHilosCliente hilo= new ManejoHilosCliente(is, out);
		//hilo.start();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		try {
            Cliente cliente = new Cliente("localhost", 5000);
            cliente.start();
            
        } catch (IOException e) {
            System.out.println("(Cliente) Error: " + e.getMessage());
        }
	}

}
