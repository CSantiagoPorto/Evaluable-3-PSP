import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.*;
//Se conecta, muestra los mensajes y los envía
public class Conversacion extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField tfNombre;
    private JTextField tfMensaje;
    private JTextArea taConversacion;
    private JComboBox<String> cbDestinatario;
    private Cliente cliente;
    private PrintWriter salida;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Conversacion frame = new Conversacion();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Conversacion() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel panelCabecera = new JPanel();
        contentPane.add(panelCabecera, BorderLayout.NORTH);

        panelCabecera.add(new JLabel("Nombre"));
        tfNombre = new JTextField(10);
        panelCabecera.add(tfNombre);

        JButton btnConectar = new JButton("Conectar");
        panelCabecera.add(btnConectar);

        JPanel panelCentro = new JPanel(null);
        contentPane.add(panelCentro, BorderLayout.CENTER);
        taConversacion = new JTextArea();
        taConversacion.setEditable(false);
        taConversacion.setBounds(10, 0, 460, 200);
        panelCentro.add(taConversacion);

        JPanel panelInferior = new JPanel();
        contentPane.add(panelInferior, BorderLayout.SOUTH);

        panelInferior.add(new JLabel("Destinatario"));
        cbDestinatario = new JComboBox<>();
        panelInferior.add(cbDestinatario);

        tfMensaje = new JTextField(15);
        panelInferior.add(tfMensaje);

        JButton btnEnviar = new JButton("Enviar");
        panelInferior.add(btnEnviar);

        btnConectar.addActionListener(e -> {
            try {
                String nombre = tfNombre.getText().trim();
                if (nombre.isEmpty()) return;

                cliente = new Cliente("localhost", 5000);
                salida = cliente.getSalida();
                salida.println(nombre);

                ManejoHilosCliente hilo = new ManejoHilosCliente(cliente.getEntrada(), this);
                hilo.start();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        btnEnviar.addActionListener(e -> {
            String mensaje = tfMensaje.getText().trim();
            if (!mensaje.isEmpty() && cbDestinatario.getSelectedItem() != null) {
                String destinatario = cbDestinatario.getSelectedItem().toString();
                salida.println(destinatario + ":" + mensaje);
                tfMensaje.setText("");
            }
        });
    }

    public void mostrarMensajeEnPantalla(String mensaje) {
        taConversacion.append(mensaje + "\n");
    }

    public JComboBox<String> getCbDestinatario() {
        return cbDestinatario;
    }

    public void agregarDestinatario(String destinatario) {
        cbDestinatario.addItem(destinatario);
    }
}