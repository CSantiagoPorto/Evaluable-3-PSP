import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaUsuarios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String nombreUsuario;

	/**
	 * Launch the application.
	 */
	
	public VentanaUsuarios() {
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
				VentanaChat ventanaChat = new VentanaChat(yo, otro);
				ventanaChat.setVisible(true);
				dispose();
			}
		});
		btnIniciarConversacion.setBounds(138, 232, 163, 21);
		contentPane.add(btnIniciarConversacion);
	}
}
