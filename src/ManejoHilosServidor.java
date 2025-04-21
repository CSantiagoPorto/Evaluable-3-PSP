import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

//Se encarga de cada cliente, guarda su nombre, escucha los mensajes y luego los reenvía

class ManejoHilosServidor implements Runnable {
    private Socket socket;
    private static final Map<String, PrintWriter> usuariosConectados = new HashMap<>();
    //Esto es un mapa que me va a guardar los nombres y las contraseñas
    private String nombre;

    public ManejoHilosServidor(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
        	//cambio a bf y pw porque los datainput no estaban funcionando bien
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            // Leemos el nombre que nos manda
            nombre = entrada.readLine();
            synchronized (usuariosConectados) {
                usuariosConectados.put(nombre, salida);
            }

            // Mandamos la lista lista de usuarios
            enviarListaUsuarios();

            // Recibir mensajes y reenviar
            String linea;
            while ((linea = entrada.readLine()) != null) {
                if (linea.contains(":")) {
                    String[] partes = linea.split(":", 2);
                    String destino = partes[0];
                    String mensaje = partes[1];

                    synchronized (usuariosConectados) {
                        PrintWriter destinoSalida = usuariosConectados.get(destino);
                        if (destinoSalida != null) {
                            destinoSalida.println(nombre + ": " + mensaje);
                        }
                        // El emisor también ve su mensaje
                        usuariosConectados.get(nombre).println(nombre + ": " + mensaje);
                    }

                    guardarMensaje(nombre, destino, mensaje);
                }
            }

        } catch (IOException e) {
            System.out.println("(Servidor) Error: " + e.getMessage());
        } finally {
            synchronized (usuariosConectados) {
                usuariosConectados.remove(nombre);
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void enviarListaUsuarios() {
        synchronized (usuariosConectados) {
            StringBuilder sb = new StringBuilder();
            for (String u : usuariosConectados.keySet()) {
                if (!u.equals(nombre)) {
                    sb.append(u).append(",");
                }
            }
            if (sb.length() > 0) sb.setLength(sb.length() - 1);
            usuariosConectados.get(nombre).println("Usuarios:" + sb);
        }
    }

    private synchronized void guardarMensaje(String emisor, String receptor, String mensaje) {
        String archivo = (emisor.compareTo(receptor) < 0 ? emisor + "_" + receptor : receptor + "_" + emisor) + ".txt";
        try (FileWriter fw = new FileWriter(archivo, true)) {
            fw.write(emisor + ": " + mensaje + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}