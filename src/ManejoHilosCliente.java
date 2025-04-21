import java.io.DataInputStream;
import java.io.IOException;

public class ManejoHilosCliente extends Thread {

    private DataInputStream in;
    private Conversacion ventanaConversacion;

    public ManejoHilosCliente(DataInputStream in, Conversacion ventanaConversacion) {
        this.in = in;
        this.ventanaConversacion = ventanaConversacion;
    }

    public void run() {
        try {
            while (true) {
            	String mensaje = in.readUTF();
            	 if (mensaje.startsWith("USUARIO:")) {
                     String nombreDestinatario = mensaje.substring(8);
                     ventanaConversacion.agregarDestinatario(nombreDestinatario);
                 } else if (mensaje.equals("FIN_USUARIOS")) {
                     System.out.println("(Cliente) Lista de usuarios recibida.");
                 } else {
                     // Mostramos mensaje de chat
                     System.out.println("(Cliente) Mensaje recibido: " + mensaje);
                     ventanaConversacion.mostrarMensajeEnPantalla(mensaje);
                 }
               

                // Mostramos el mensaje en la ventana
                ventanaConversacion.mostrarMensajeEnPantalla(mensaje);
            }
        } catch (IOException e) {
            System.out.println("(Cliente) Conexión cerrada o error en el hilo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
