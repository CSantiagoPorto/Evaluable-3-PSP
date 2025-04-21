
import java.io.*;
import java.net.Socket;
//Aquí abro el socket para tender el puente entre el cliente y el servidor
public class Cliente {
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;

    public Cliente(String host, int puerto) throws IOException {
        socket = new Socket(host, puerto);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        salida = new PrintWriter(socket.getOutputStream(), true);
    }

    public BufferedReader getEntrada() {
        return entrada;
    }

    public PrintWriter getSalida() {
        return salida;
    }

    public Socket getSocket() {
        return socket;
    }
}
