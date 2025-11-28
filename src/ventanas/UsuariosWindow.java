package ventanas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import clases.log;
import clases.usuario;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class UsuariosWindow extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    public static ArrayList<usuario> listaUsuarios;
    private JList<usuario> usuariosList;
    private DefaultListModel<usuario> dlm;
    private log log = new log();

    private JButton btnAnadir, btnEliminar, btnAtras, btnEditar;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UsuariosWindow frame = new UsuariosWindow();
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
    public UsuariosWindow() {
        // Crear algunos usuarios de ejemplo si el archivo está vacío
        listaUsuarios = usuario.cargarUsuarios();  // Intentar cargar los usuarios desde el archivo

        setTitle("Gestión Equipos - Txurdi Liga");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 718, 402);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(UsuariosWindow.class.getResource("/img/imagenes/logotxurdi.png")));
        panel.add(lblNewLabel);

        JPanel panel_1 = new JPanel();
        contentPane.add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new BorderLayout(0, 0));

        JPanel panel_2 = new JPanel();
        panel_1.add(panel_2, BorderLayout.NORTH);
        panel_2.setLayout(new BorderLayout(0, 0));

        JPanel panel_3 = new JPanel();
        panel_2.add(panel_3, BorderLayout.WEST);

        btnAnadir = new JButton("Añadir");
        btnAnadir.addActionListener(this);
        panel_3.add(btnAnadir);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(this);
        panel_3.add(btnEliminar);

        btnEditar = new JButton("Editar");
        btnEditar.addActionListener(this);
        panel_3.add(btnEditar);

        JPanel panel_4 = new JPanel();
        panel_2.add(panel_4, BorderLayout.EAST);

        JLabel lblNewLabel_1 = new JLabel("Administrador = rol 1  Árbitro = rol 2  Usuario= rol 3 Invitado= rol 4");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
        panel_2.add(lblNewLabel_1, BorderLayout.CENTER);

        JPanel panel_5 = new JPanel();
        panel_1.add(panel_5, BorderLayout.CENTER);
        panel_5.setLayout(new BorderLayout(0, 0));

        JPanel panel_6 = new JPanel();
        panel_5.add(panel_6, BorderLayout.SOUTH);
        GridBagLayout gbl_panel_6 = new GridBagLayout();
        gbl_panel_6.columnWidths = new int[]{0, 0, 0};
        gbl_panel_6.rowHeights = new int[]{0, 0};
        gbl_panel_6.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gbl_panel_6.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panel_6.setLayout(gbl_panel_6);

        btnAtras = new JButton("Atrás");
        btnAtras.addActionListener(this);

        GridBagConstraints gbc_btnAtras = new GridBagConstraints();
        gbc_btnAtras.gridx = 1;
        gbc_btnAtras.gridy = 0;
        panel_6.add(btnAtras, gbc_btnAtras);

        JPanel panel_7 = new JPanel();
        panel_5.add(panel_7, BorderLayout.CENTER);
        panel_7.setLayout(new BorderLayout(0, 0));

        JPanel panel_8 = new JPanel();
        panel_7.add(panel_8, BorderLayout.SOUTH);

        dlm = new DefaultListModel<usuario>();

        usuariosList = new JList<usuario>(dlm);
        panel_7.add(usuariosList, BorderLayout.CENTER);

        for (usuario u : listaUsuarios) {
            dlm.addElement(u);
        }

    }

    // actionPerformed
    @SuppressWarnings({ "static-access", "static-access" })
	@Override
    public void actionPerformed(ActionEvent ae) {

        // obtengo sobre que componente se ha realizado la acción
        Object o = ae.getSource();

        if (o == btnAnadir) {
            añadirUsuario ventanaUsuario = new añadirUsuario();  // Ventana gestión de equipos
            ventanaUsuario.setVisible(true);  // Mostrar la ventana
            dispose();  // Cerrar la ventana
        } else if (o == btnAtras) {
            gestionAdmin ventanaAdmin = new gestionAdmin();  // Ventana gestión de equipos
            ventanaAdmin.setVisible(true);  // Mostrar la ventana
            dispose();  // Cerrar la ventana
        } else if (o == btnEliminar) {
            // obtengo cuantas opciones hay seleccionadas en la lista
            int[] indices = usuariosList.getSelectedIndices();
            int numeroOpciones = indices.length;
            if (numeroOpciones <= 0) {
                // si NO hay opciones seleccionadas
                JOptionPane.showMessageDialog(this, "Error. No hay usuarios seleccionados", "Error", JOptionPane.ERROR_MESSAGE, null);
            } else {
                int opcion = JOptionPane.showConfirmDialog(UsuariosWindow.this, "¿Está seguro que desea borrar?", "Info", JOptionPane.YES_NO_CANCEL_OPTION);
                switch (opcion) {
                    case JOptionPane.YES_OPTION:
                        for (int i = indices.length - 1; i >= 0; i--) {
                            usuario usuarioSeleccionado = dlm.getElementAt(indices[i]);

                            // Comprobamos si el usuario seleccionado es "Itxiar"
                            if (usuarioSeleccionado.getNombre().equals("Itxiar")) {
                                JOptionPane.showMessageDialog(this, "No se puede eliminar el usuario 'Itxiar'", "Error", JOptionPane.ERROR_MESSAGE);
                                return; // No eliminamos el usuario, salimos del método
                            }

                            // Si no es "Itxiar", lo eliminamos
                            listaUsuarios.remove(indices[i]);
                            dlm.remove(indices[i]);
                            
                            log.add("Usurio: "+indices[i] + " a sido Eliminado.", 1);
                        }
                        // Guardar los usuarios en el archivo
                        usuario.guardarUsuarios(listaUsuarios);
                        break;
                    case JOptionPane.NO_OPTION:
                    case JOptionPane.CANCEL_OPTION:
                    case JOptionPane.CLOSED_OPTION:
                        return;
                }
            }
        } else if (o == btnEditar) {
            int[] indices = usuariosList.getSelectedIndices();
            if (indices.length <= 0) {
                JOptionPane.showMessageDialog(this, "Error. No hay usuarios seleccionados", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (indices.length > 1) {
                JOptionPane.showMessageDialog(this, "Error. No se puede seleccionar más de un usuario", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                usuario usuarioSeleccionado = dlm.getElementAt(indices[0]);

                // Verificar si el usuario seleccionado es un administrador (rol 1)
                if (usuarioSeleccionado.getRol() == 1) {
                    JOptionPane.showMessageDialog(this, "No tienes permisos para editar a otro administrador.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Detener la ejecución para evitar la edición
                }

                // Crear ventana de edición con los datos actuales del usuario
                JTextField txtContraseña = new JTextField(usuarioSeleccionado.getContrasena());
                JComboBox<String> comboBoxRol = new JComboBox<>(new String[]{"Administrador", "Árbitro", "Usuario", "Invitado"});
                comboBoxRol.setSelectedItem(usuarioSeleccionado.getRol());

                // Panel para mostrar los campos de edición
                JPanel panelEdicion = new JPanel(new GridLayout(2, 2));
                panelEdicion.add(new JLabel("Contraseña:"));
                panelEdicion.add(txtContraseña);
                panelEdicion.add(new JLabel("Rol:"));
                panelEdicion.add(comboBoxRol);

                int option = JOptionPane.showConfirmDialog(this, panelEdicion, "Editar Usuario", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    usuarioSeleccionado.setContrasena(txtContraseña.getText());

                    // Mapeo de roles a números
                    Map<String, Integer> rolMap = new HashMap<>();
                    rolMap.put("Administrador", 1);
                    rolMap.put("Árbitro", 2);
                    rolMap.put("Usuario", 3);
                    rolMap.put("Invitado", 4);

                    // Obtener el rol seleccionado del JComboBox
                    String rolSeleccionado = (String) comboBoxRol.getSelectedItem();
                    int selectedRol = rolMap.get(rolSeleccionado);

                    // Asignar el rol correctamente al usuario
                    usuarioSeleccionado.setRol(selectedRol);

                    // Llamar al método editarUsuario para actualizar los datos
                    usuario.editarUsuario(usuarioSeleccionado);

                    // Actualizar la lista en la interfaz
                    dlm.setElementAt(usuarioSeleccionado, indices[0]);
                    log.add(usuarioSeleccionado + "a sido modificado.", 1);
                }
            }
        }

    }
}
