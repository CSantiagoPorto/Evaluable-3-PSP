import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Conversacion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNombre;
	private JTextField tfMensaje;
	private JTextArea taConversacion;
	private JComboBox cbDestinatario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Conversacion frame = new Conversacion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Conversacion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCabecera = new JPanel();
		contentPane.add(panelCabecera, BorderLayout.NORTH);
		
		JLabel lblNombre = new JLabel("Nombre");
		panelCabecera.add(lblNombre);
		
		tfNombre = new JTextField();
		panelCabecera.add(tfNombre);
		tfNombre.setColumns(10);
		
		JButton btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panelCabecera.add(btnConectar);
		
		JPanel panelCentro = new JPanel();
		contentPane.add(panelCentro, BorderLayout.CENTER);
		panelCentro.setLayout(null);
		
		taConversacion = new JTextArea();
		taConversacion.setBounds(10, 0, 406, 178);
		panelCentro.add(taConversacion);
		
		JPanel panelInferior = new JPanel();
		contentPane.add(panelInferior, BorderLayout.SOUTH);
		
		JLabel lblDestinatario = new JLabel("Destinatario");
		panelInferior.add(lblDestinatario);
		
		cbDestinatario = new JComboBox();
		cbDestinatario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nombre = tfNombre.getText().trim();
					if (nombre.isEmpty()) return;

					// Creo el cliente
					Cliente cliente = new Cliente("localhost", 5000);
					cliente.start();
					DataInputStream dis = cliente.getInputStream();
					DataOutputStream dos = cliente.getOutputStream();

					// Envío el nombr
					dos.writeUTF(nombre);

					// Lanzo hilo para recibir usuarios y mensajes
					new Thread(() -> {
						try {
							while (true) {
								String mensaje = dis.readUTF();
								if (mensaje.startsWith("USUARIO:")) {
									String usuario = mensaje.substring(8);
									cbDestinatario.addItem(usuario); // Añadir al combo
								} else if (mensaje.startsWith("FIN_USUARIOS")) {
									System.out.println("Lista de usuarios recibida.");
								} else {
									// Añadimos el mensaje a la conversación
									taConversacion.append(mensaje + "\n");
								}
							}
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}).start();

				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		panelInferior.add(cbDestinatario);
		
		tfMensaje = new JTextField();
		panelInferior.add(tfMensaje);
		tfMensaje.setColumns(10);
	}
	public void mostrarMensajeEnPantalla(String mensaje) {
	    taConversacion.append(mensaje + "\n");
	}

	public JTextArea getTaConversacion() {
		return taConversacion;
	}
	
	public JComboBox getCbDestinatario() {
		return cbDestinatario;
	}
public void agregarDestinatario(String Destinatario) {
	cbDestinatario.addItem(Destinatario);
		
	}
}
