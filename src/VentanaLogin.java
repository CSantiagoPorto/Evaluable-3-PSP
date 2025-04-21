import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class VentanaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNombre;
	private JPasswordField tfContrasena;
	private JButton btnConectar;
	private String nombre;
	private String contrasena;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin frame = new VentanaLogin();
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
	public VentanaLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JPanel panelArriba = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, panelArriba, 11, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, panelArriba, 5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panelArriba, 62, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panelArriba, 421, SpringLayout.WEST, contentPane);
		contentPane.add(panelArriba);
		panelArriba.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel lblNombreUsuario = new JLabel("Nombre de Usuario");
		panelArriba.add(lblNombreUsuario);
		
		tfNombre = new JTextField();
		panelArriba.add(tfNombre);
		tfNombre.setColumns(10);
		
		JLabel lblContrasena = new JLabel("Contraseña");
		panelArriba.add(lblContrasena);
		
		tfContrasena = new JPasswordField();
		panelArriba.add(tfContrasena);
		tfContrasena.setColumns(10);
		
		btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nombre = tfNombre.getText(); // Obtenemos el nombre
				contrasena = new String(tfContrasena.getPassword()); // Obtenemos la contraseña
				
				try {
					String ip = "localhost";
					int puerto = 5000;
					Socket socket = new Socket(ip, puerto); // Conexión con el servidor
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
					DataInputStream dis = new DataInputStream(socket.getInputStream());

					dos.writeUTF(nombre + ":" + contrasena); // Enviamos credenciales
					dos.flush();

					String respuesta = dis.readUTF(); // Leemos respuesta
					if (respuesta.startsWith("ERROR")) {
					    JOptionPane.showMessageDialog(null, respuesta);
					    socket.close();
					    return;
					}

					dos.writeUTF(nombre); // Enviamos el nombre solo si login fue correcto
					dos.flush();

					javax.swing.SwingUtilities.invokeLater(() -> {
					    VentanaUsuarios ventanaUsuarios = new VentanaUsuarios(nombre, dos, dis);
					    ventanaUsuarios.setVisible(true);
					    dispose();
					});


				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnConectar, 81, SpringLayout.SOUTH, panelArriba);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnConectar, 175, SpringLayout.WEST, contentPane);
		contentPane.add(btnConectar);
	}

	public JButton getBtnConectar() {
		return btnConectar;
	}
}
