import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class VentanaUsuarios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String nombreUsuario;
	private DataOutputStream dos;
	private DataInputStream dis;
	private static List<String> usuariosConectados = new ArrayList<>();
	private JComboBox cbUsuarios;

	/**
	 * Launch the application.
	 */
	
	public VentanaUsuarios(String nombreUsuario, DataOutputStream dos, DataInputStream dis) {
		this.nombreUsuario = nombreUsuario;
		this.dos = dos;
		this.dis = dis;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblusuarios = new JLabel("Usuarios conectados: ");
		lblusuarios.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblusuarios.setBounds(56, 46, 318, 13);
		contentPane.add(lblusuarios);
		
		cbUsuarios = new JComboBox();
		cbUsuarios.setBounds(56, 91, 306, 21);
		contentPane.add(cbUsuarios);
		
		// Lanzamos un hilo para recibir la lista de usuarios sin bloquear la interfaz
		new Thread(() -> {
		    try {
		        while (true) {
		            String mensaje = dis.readUTF();
		            if (mensaje.equals("FIN_USUARIOS")) {
		                break;
		            }
		            if (mensaje.startsWith("USUARIO:")) {
		                String nombre = mensaje.substring(8); // Quita "USUARIO:"
		                javax.swing.SwingUtilities.invokeLater(() -> {
		                    cbUsuarios.addItem(nombre);
		                });
		            }
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}).start();

		JButton btnIniciarConversacion = new JButton("Iniciar conversación");
		btnIniciarConversacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String yo = nombreUsuario; 
				String otro = cbUsuarios.getSelectedItem().toString(); // usuario seleccionado del combo
				VentanaChat ventanaChat = new VentanaChat(yo, otro, dos, dis);
				ventanaChat.setVisible(true);
				dispose();
			}
		});
		btnIniciarConversacion.setBounds(138, 232, 163, 21);
		contentPane.add(btnIniciarConversacion);

		/* Añadimos el usuario a la lista sincronizadamente
		synchronized (usuariosConectados) {
			usuariosConectados.add(nombreUsuario);
		}*/
	}
}
