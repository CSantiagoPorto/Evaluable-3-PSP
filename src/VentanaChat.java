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
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class VentanaChat extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfMensaje;
	private JTextArea taConversacion;

	/**
	 * Launch the application.
	 */
	private String nombreUsuario;
	private String destinatario;
	private DataOutputStream dos;
	private DataInputStream dis;

	public VentanaChat(String nombreUsuario, String destinatario,DataOutputStream dos, DataInputStream dis) {
		this.nombreUsuario= nombreUsuario;
		this.destinatario= destinatario;
		this.dos=dos;
		this.dis=dis;
		
		setTitle("Chat con "+ destinatario);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCabecera = new JPanel();
		contentPane.add(panelCabecera, BorderLayout.NORTH);
		
		JLabel lblCabecera = new JLabel("Hablando con: "+ destinatario);
		lblCabecera.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelCabecera.add(lblCabecera);
		
		JPanel panelConversacion = new JPanel();
		contentPane.add(panelConversacion, BorderLayout.CENTER);
		panelConversacion.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(47, 15, 327, 197);
		panelConversacion.add(scrollPane);
		
		taConversacion = new JTextArea();
		scrollPane.setViewportView(taConversacion);
		
		JPanel panelEnviar = new JPanel();
		contentPane.add(panelEnviar, BorderLayout.SOUTH);
		
		tfMensaje = new JTextField();
		panelEnviar.add(tfMensaje);
		tfMensaje.setColumns(10);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String conversacion= tfMensaje.getText().trim();
				if(!conversacion.isEmpty()) {
					String mensaje= nombreUsuario + "->"+ destinatario + " : "+ conversacion;
					try {
						dos.writeUTF(mensaje);
						dos.flush();
						taConversacion.append("Tú: "+ conversacion+ "\n");
						tfMensaje.setText("");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		panelEnviar.add(btnEnviar);
		
		ManejoHilosCliente hiloCliente = new ManejoHilosCliente(dis, taConversacion);
		hiloCliente.start();

	}
	public void mostrarMensajeEnPantalla(String mensaje) {
	    taConversacion.append(mensaje + "\n");
	}

}
