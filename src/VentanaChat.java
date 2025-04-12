import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VentanaChat extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfIP;
	private JTextField tfPuerto;
	private JTextField tfMensaje;

	/**
	 * Launch the application.
	 */
	private String nombreUsuario;
	private String destinatario;

	public VentanaChat(String nombreUsuario, String destinatario) {
		this.nombreUsuario= nombreUsuario;
		this.destinatario= destinatario;
		
		setTitle("Chat con "+ destinatario);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCabecera = new JPanel();
		contentPane.add(panelCabecera, BorderLayout.NORTH);
		
		JLabel lblIP = new JLabel("IP");
		panelCabecera.add(lblIP);
		
		tfIP = new JTextField();
		panelCabecera.add(tfIP);
		tfIP.setColumns(10);
		
		JLabel lblPuerto = new JLabel("Puerto");
		panelCabecera.add(lblPuerto);
		
		tfPuerto = new JTextField();
		panelCabecera.add(tfPuerto);
		tfPuerto.setColumns(10);
		
		JButton btnConectar = new JButton("Conectar");
		panelCabecera.add(btnConectar);
		
		JPanel panelConversacion = new JPanel();
		contentPane.add(panelConversacion, BorderLayout.CENTER);
		panelConversacion.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(47, 15, 327, 197);
		panelConversacion.add(scrollPane);
		
		JTextArea taConversacion = new JTextArea();
		scrollPane.setViewportView(taConversacion);
		
		JPanel panelEnviar = new JPanel();
		contentPane.add(panelEnviar, BorderLayout.SOUTH);
		
		tfMensaje = new JTextField();
		panelEnviar.add(tfMensaje);
		tfMensaje.setColumns(10);
		
		JButton btnEnviar = new JButton("Enviar");
		panelEnviar.add(btnEnviar);
	}
}
