package ventanas;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import clases.Gestion;
import clases.Temporada;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class gestionAdmin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private GridBagConstraints gbc_1;
    private GridBagConstraints gbc_2;
    private GridBagConstraints gbc_3;
    private GridBagConstraints gbc_4;
    private GridBagConstraints gbc_5;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                gestionAdmin frame = new gestionAdmin();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public gestionAdmin() {
    	String username = Gestion.obtenerUltimoUsuario();
    	
        setTitle("Gestión Administrativa - Txurdi Liga");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400); // Ajustar tamaño de la ventana
        setLocationRelativeTo(null); // Centrar ventana

        contentPane = new JPanel(new GridBagLayout());
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Logo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblLogo = new JLabel(new ImageIcon(gestionAdmin.class.getResource("/img/imagenes/logotxurdi.png")));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblLogo, gbc);

        // Botón "Gestionar equipos"
        gbc_1 = new GridBagConstraints();
        gbc_1.insets = new Insets(0, 0, 5, 5);
        gbc_1.gridx = 0;
        gbc_1.gridy = 2;
        gbc_1.gridwidth = 1;

        JButton btnGestEq = new JButton("Gestionar equipos");
        btnGestEq.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		EquiposWindow ventanaEquipos = new EquiposWindow();  // Ventana gestión de equipos
        		ventanaEquipos.setVisible(true);  // Mostrar la ventana
                dispose();  // Cerrar la ventana
        	}
        });
        
        JLabel lblNewLabel = new JLabel("Bienvenido, " + username);
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 1;
        gbc_lblNewLabel.gridy = 1;
        contentPane.add(lblNewLabel, gbc_lblNewLabel);
        contentPane.add(btnGestEq, gbc_1);

        // Botón "Gestionar resultados"
        gbc_2 = new GridBagConstraints();
        gbc_2.insets = new Insets(0, 0, 5, 0);
        gbc_2.gridx = 2;
        gbc_2.gridy = 2;

        JButton btnGestRes = new JButton("Gestionar resultados");
        btnGestRes.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JornadasWindow ventanaResultado = new JornadasWindow();  // Ventana gestión de equipos
        		ventanaResultado.setVisible(true);  // Mostrar la ventana
                dispose();  // Cerrar la ventana
        	}
        });
        contentPane.add(btnGestRes, gbc_2);

        // Botón "Gestionar jugadores"
        gbc_3 = new GridBagConstraints();
        gbc_3.insets = new Insets(0, 0, 5, 5);
        gbc_3.gridx = 0;
        gbc_3.gridy = 3;

        JButton btnGestJug = new JButton("Gestionar jugadores");
        btnGestJug.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JugadoresWindow ventanaJugadores = new JugadoresWindow();  // Ventana gestión de equipos
        		ventanaJugadores.setVisible(true);  // Mostrar la ventana
                dispose();  // Cerrar la ventana
        	}
        });
        contentPane.add(btnGestJug, gbc_3);

        // Botón "Gestionar temporadas"
        gbc_4 = new GridBagConstraints();
        gbc_4.insets = new Insets(0, 0, 5, 0);
        gbc_4.gridx = 2;
        gbc_4.gridy = 3;

        JButton btnGestTemp = new JButton("Gestionar temporadas");
        btnGestTemp.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TemporadasWindow ventanaTemporada = new TemporadasWindow();  // Ventana gestión de equipos
        		ventanaTemporada.setVisible(true);  // Mostrar la ventana
                dispose();  // Cerrar la ventana
        	}
        });
        contentPane.add(btnGestTemp, gbc_4);

        // Botón "Cerrar sesión"
        gbc_5 = new GridBagConstraints();
        gbc_5.insets = new Insets(0, 0, 5, 5);
        gbc_5.gridx = 0;
        gbc_5.gridy = 4;

        JButton btnCerrar = new JButton("Cerrar sesión");
        btnCerrar.setHorizontalAlignment(SwingConstants.LEFT);
        btnCerrar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int opcion =  JOptionPane.showConfirmDialog(gestionAdmin.this, "¿Está seguro que desea cerrar sesión?", "Info",  JOptionPane.YES_NO_CANCEL_OPTION);
        		switch (opcion) {
    			case JOptionPane.YES_OPTION:
					LoginWindow ventanaLogin = new LoginWindow();  // Ventana gestión de equipos
					ventanaLogin.setVisible(true);  // Mostrar la ventana
	                dispose();  // Cerrar la ventana
					break;
    			case JOptionPane.NO_OPTION:
    			case JOptionPane.CANCEL_OPTION:
    			case JOptionPane.CLOSED_OPTION:
    				return;
    			}
        	}
        });
        contentPane.add(btnCerrar, gbc_5);
        
        JButton btnGestAdm = new JButton("Gestionar administradores");
        btnGestAdm.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		UsuariosWindow ventanaUsuario = new UsuariosWindow();  // Ventana gestión de equipos
        		ventanaUsuario.setVisible(true);  // Mostrar la ventana
                dispose();  // Cerrar la ventana
        	}
        });
        GridBagConstraints gbc_btnGestAdm = new GridBagConstraints();
        gbc_btnGestAdm.insets = new Insets(0, 0, 5, 0);
        gbc_btnGestAdm.gridx = 2;
        gbc_btnGestAdm.gridy = 4;
        contentPane.add(btnGestAdm, gbc_btnGestAdm);
    }
}
