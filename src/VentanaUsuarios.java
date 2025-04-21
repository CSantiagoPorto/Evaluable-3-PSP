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

	/**
	 * Launch the application.
	 */
	
	public VentanaUsuarios(String nombreUsuario, DataOutputStream dos) {
		this.nombreUsuario = nombreUsuario;
		this.dos = dos;
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
		
		JComboBox cbUsuarios = new JComboBox();
		cbUsuarios.setBounds(56, 91, 306, 21);
		contentPane.add(cbUsuarios);
		
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

		// Añadimos el usuario a la lista sincronizadamente
		synchronized (usuariosConectados) {
			usuariosConectados.add(nombreUsuario);
		}
	}
}
