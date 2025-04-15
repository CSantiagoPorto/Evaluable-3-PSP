import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class VentanaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNombre;
	private JLabel lbIP;
	private JTextField tfIP;
	private JTextField tfPuerto;
	private JButton btnConectar;
	private String nombre;
	private String ip;
	private int puerto;

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
		panelArriba.setLayout(new GridLayout(3, 2, 0, 0));
		
		JLabel lblNombreUsuario = new JLabel("Nombre de Usuario");
		panelArriba.add(lblNombreUsuario);
		
		tfNombre = new JTextField();
		panelArriba.add(tfNombre);
		tfNombre.setColumns(10);
		
		lbIP = new JLabel("IP del Servidor");
		panelArriba.add(lbIP);
		
		tfIP = new JTextField();
		panelArriba.add(tfIP);
		tfIP.setColumns(10);
		
		JLabel lblPuerto = new JLabel("Puerto");
		panelArriba.add(lblPuerto);
		
		tfPuerto = new JTextField();
		panelArriba.add(tfPuerto);
		tfPuerto.setColumns(10);
		
		btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nombre= tfNombre.getText();//Obtenemos el nombre
				ip=tfIP.getText();//Obtenemos la IP
				puerto=Integer.parseInt(tfPuerto.getText());
				//Convierto el texto del campo tfPuerto en un int
				
				Socket socket;
				try {
					socket = new Socket(ip, puerto);//Creo la conexión con el servidor
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
					//Accedo al flujo de salida. Hay que envolverlo en dos porque necesito poder usar
					//write, que socket no puede
					dos.writeUTF(nombre);
					dos.flush();
					VentanaUsuarios ventanaUsuarios = new VentanaUsuarios(nombre, dos);
					ventanaUsuarios.setVisible(true);
					dispose();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
