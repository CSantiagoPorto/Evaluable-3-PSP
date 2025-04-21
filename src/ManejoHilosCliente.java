import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JComboBox;
//Para cada cliente escucha los mensajes que emite el servidor y va a actualizar la ventana
class ManejoHilosCliente extends Thread {
    private BufferedReader entrada;
    private Conversacion ventana;

    public ManejoHilosCliente(BufferedReader entrada, Conversacion ventana) {
        this.entrada = entrada;
        this.ventana = ventana;
    }

    public void run() {
        try {
            String linea;
            while ((linea = entrada.readLine()) != null) {
                if (linea.startsWith("Usuarios:")) {
                    String usuarios = linea.substring(9);
                    String[] lista = usuarios.split(",");

                    JComboBox combo = ventana.getCbDestinatario();
                    combo.removeAllItems();

                    for (String usuario : lista) {
                        if (!usuario.isEmpty()) {
                            ventana.agregarDestinatario(usuario.trim());
                        }
                    }
                } else {
                    ventana.mostrarMensajeEnPantalla(linea);
                }
            }
        } catch (IOException e) {
            System.out.println("(Cliente) Error en hilo: " + e.getMessage());
        }
    }
}