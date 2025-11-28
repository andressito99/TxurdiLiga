package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import clases.Equipo;
import clases.Jugador;
import clases.Temporada;

public class JugadoresWindow extends JFrame implements WindowListener {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> comboBoxTemporada;
    private JComboBox<String> comboBoxEquipo;
    private DefaultListModel<String> listModelJugadores; // Modelo para la lista de jugadores
    private JList<String> listJugadores; // Lista de jugadores
    private ArrayList<Temporada> temporadas;
    private ArrayList<Equipo> equiposActuales;
    private boolean hayCambiosNoGuardados = false;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JugadoresWindow frame = new JugadoresWindow();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public JugadoresWindow() {
        setTitle("Gestion Jugadores - Txurdi Liga");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
        setBounds(100, 100, 730, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Panel superior con el logo
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        JLabel lblNewLabel = new JLabel(new ImageIcon(JugadoresWindow.class.getResource("/img/imagenes/logotxurdi.png")));
        panel.add(lblNewLabel);

        // Panel central con la lista de jugadores
        JPanel panel_1 = new JPanel();
        contentPane.add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new BorderLayout(0, 0));

        // Panel para los combobox de temporada y equipo
        JPanel panel_2 = new JPanel();
        panel_1.add(panel_2, BorderLayout.NORTH);

        JPanel panel_3 = new JPanel();
        panel_2.add(panel_3);
        comboBoxEquipo = new JComboBox<>();
        panel_3.add(comboBoxEquipo);

        JPanel panel_4 = new JPanel();
        panel_2.add(panel_4);
        JLabel lblNewLabel_1 = new JLabel("Temporada");
        panel_4.add(lblNewLabel_1);
        comboBoxTemporada = new JComboBox<>();
        panel_4.add(comboBoxTemporada);

        // Lista de jugadores con DefaultListModel
        listModelJugadores = new DefaultListModel<>();
        listJugadores = new JList<>(listModelJugadores);
        JScrollPane scrollPanelJugadores = new JScrollPane(listJugadores);
        panel_1.add(scrollPanelJugadores, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panel_6 = new JPanel(new GridBagLayout());
        panel_1.add(panel_6, BorderLayout.SOUTH);

        JButton btnAgregar = new JButton("Agregar");
        GridBagConstraints gbc_btnAgregar = new GridBagConstraints();
        gbc_btnAgregar.insets = new Insets(0, 0, 0, 5);
        gbc_btnAgregar.gridx = 4;
        gbc_btnAgregar.gridy = 0;
        panel_6.add(btnAgregar, gbc_btnAgregar);

        JButton btnModificar = new JButton("Modificar");
        GridBagConstraints gbc_btnModificar = new GridBagConstraints();
        gbc_btnModificar.insets = new Insets(0, 0, 0, 5);
        gbc_btnModificar.gridx = 6;
        gbc_btnModificar.gridy = 0;
        panel_6.add(btnModificar, gbc_btnModificar);

        JButton btnEliminar = new JButton("Eliminar");
        GridBagConstraints gbc_btnEliminar = new GridBagConstraints();
        gbc_btnEliminar.insets = new Insets(0, 0, 0, 5);
        gbc_btnEliminar.gridx = 8;
        gbc_btnEliminar.gridy = 0;
        panel_6.add(btnEliminar, gbc_btnEliminar);

        JButton btnDetalles = new JButton("Detalles");
        GridBagConstraints gbc_btnDetalles = new GridBagConstraints();
        gbc_btnDetalles.insets = new Insets(0, 0, 0, 5);
        gbc_btnDetalles.gridx = 10;
        gbc_btnDetalles.gridy = 0;
        panel_6.add(btnDetalles, gbc_btnDetalles);

        JButton btnGuardar = new JButton("Guardar");
        GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
        gbc_btnGuardar.insets = new Insets(0, 0, 0, 5);
        gbc_btnGuardar.gridx = 12;
        gbc_btnGuardar.gridy = 0;
        panel_6.add(btnGuardar, gbc_btnGuardar);

        JButton btnAtras = new JButton("Atras");
        contentPane.add(btnAtras, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarEquipos());
        btnAtras.addActionListener(e -> {
        	if (hayCambiosNoGuardados) {
                int opcion = JOptionPane.showConfirmDialog(
                		JugadoresWindow.this,
                    "Hay cambios no guardados. ¿Desea guardar antes de salir?",
                    "Cambios no guardados",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    guardarEquipos();
                } else if (opcion == JOptionPane.NO_OPTION) {
                    gestionAdmin ventanaAdmin = new gestionAdmin();
                    ventanaAdmin.setVisible(true);
                    dispose();
                }
                // Si elige CANCELAR, no se hace nada (no se cierra la ventana)
            } else {
                gestionAdmin ventanaAdmin = new gestionAdmin();
                ventanaAdmin.setVisible(true);
                dispose();
            } // Cerrar la ventana
        });
        
        // Cargar temporadas y equipos
        cargarTemporadas();
        actualizarEquipos();

        // Listeners para los combobox
        comboBoxTemporada.addActionListener(e -> actualizarEquipos());
        comboBoxEquipo.addActionListener(e -> actualizarJugadores());

        
        btnAgregar.addActionListener(e -> {
            int indiceTemporada = comboBoxTemporada.getSelectedIndex();
            int indiceEquipo = comboBoxEquipo.getSelectedIndex();

            if (indiceTemporada >= 0 && indiceEquipo >= 0 && indiceEquipo < equiposActuales.size()) {
                Temporada tempSeleccionada = temporadas.get(indiceTemporada);
                Equipo equipoSeleccionado = equiposActuales.get(indiceEquipo);

                
                
             // Verificar si el equipo ya tiene 15 jugadores
                if (equipoSeleccionado.getJugadores().size() >= 15) {
                    JOptionPane.showMessageDialog(null, "El equipo ya tiene 15 jugadores. No se pueden agregar más.", "Límite alcanzado", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                JTextField nombreField = new JTextField();
                JTextField edadField = new JTextField();
                
                // Lista desplegable para la posición
                String[] posiciones = {"Delantero", "Defensa", "Mediocampista", "Portero"};
                JComboBox<String> posicionComboBox = new JComboBox<>(posiciones);
                
                JButton btnSeleccionarImagen = new JButton("Seleccionar Imagen");
                JLabel imagenLabel = new JLabel();
                
                final File[] archivoImagenSeleccionada = {null}; // Usar File para guardar la imagen seleccionada
                btnSeleccionarImagen.addActionListener(event -> {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        archivoImagenSeleccionada[0] = fileChooser.getSelectedFile();
                        imagenLabel.setIcon(new ImageIcon(archivoImagenSeleccionada[0].getAbsolutePath()));
                    }
                });

                JPanel panel1 = new JPanel();
                panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
                panel1.add(new JLabel("Nombre:"));
                panel1.add(nombreField);
                panel1.add(new JLabel("Edad:"));
                panel1.add(edadField);
                panel1.add(new JLabel("Posición:"));
                panel1.add(posicionComboBox);
                panel1.add(btnSeleccionarImagen);
                panel1.add(imagenLabel);

                int result = JOptionPane.showConfirmDialog(null, panel1, "Agregar Jugador", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        String nombre = nombreField.getText();
                        int edad = Integer.parseInt(edadField.getText());
                        String posicion = (String) posicionComboBox.getSelectedItem();
                        
                        // Verificar que los campos no estén vacíos y que se haya seleccionado una imagen
                        if (nombre.isEmpty() || posicion.isEmpty() || archivoImagenSeleccionada[0] == null) {
                            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Especificar la ruta en la que deseas guardar la imagen
                        String rutaDestino = "C:/xampp/htdocs/Temporada2_Grupo3_LM/img/" + equipoSeleccionado.getNombre().toLowerCase().replaceAll("\\s+", "_") + "" + nombre + ".png"; // Cambia esta ruta a la que necesites
                        File destino = new File(rutaDestino);

                        // Copiar la imagen al nuevo destino
                        Files.copy(archivoImagenSeleccionada[0].toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        
                        // Crear el jugador con la imagen copiada
                        ImageIcon imagen = new ImageIcon(destino.getAbsolutePath());
                        Jugador nuevoJugador = new Jugador(nombre, edad, posicion, equipoSeleccionado, imagen);
                        tempSeleccionada.agregarJugadorAEquipo(equipoSeleccionado.getNombre(), nuevoJugador);
                        JOptionPane.showMessageDialog(null, "Jugador agregado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        actualizarJugadores();
                        hayCambiosNoGuardados = true;
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Edad debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error al guardar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una temporada y un equipo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        // Listener para el botón "Eliminar"
        btnEliminar.addActionListener(e -> {
            int indiceTemporada = comboBoxTemporada.getSelectedIndex();
            int indiceEquipo = comboBoxEquipo.getSelectedIndex();
            int indiceJugador = listJugadores.getSelectedIndex(); // Índice del jugador seleccionado

            if (indiceTemporada >= 0 && indiceEquipo >= 0 && indiceJugador >= 0) {
                Temporada tempSeleccionada = temporadas.get(indiceTemporada);
                Equipo equipoSeleccionado = equiposActuales.get(indiceEquipo);

                String nombreJugador = listModelJugadores.getElementAt(indiceJugador).split(" - ")[0]; // Obtener el nombre del jugador
                int confirmacion = JOptionPane.showConfirmDialog(
                        this,
                        "¿Está seguro de que desea eliminar a " + nombreJugador + "?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmacion == JOptionPane.YES_OPTION) {
                    tempSeleccionada.eliminarJugadorDeEquipo(equipoSeleccionado.getNombre(), nombreJugador);
                    JOptionPane.showMessageDialog(this, "Jugador eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    actualizarJugadores();
                    hayCambiosNoGuardados = true;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un jugador para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        
     // Listener para el botón "Modificar"
        
        btnModificar.addActionListener(e -> {
        	 int indiceTemporada = comboBoxTemporada.getSelectedIndex();
             int indiceEquipo = comboBoxEquipo.getSelectedIndex();
             int indiceJugador = listJugadores.getSelectedIndex(); // Índice del jugador seleccionado

             if (indiceTemporada >= 0 && indiceEquipo >= 0 && indiceJugador >= 0) {
                 Temporada tempSeleccionada = temporadas.get(indiceTemporada);
                 Equipo equipoSeleccionado = equiposActuales.get(indiceEquipo);

                 String nombreJugador = listModelJugadores.getElementAt(indiceJugador).split(" - ")[0]; // Obtener el nombre del jugador
                 Jugador jugadorSeleccionado = tempSeleccionada.obtenerJugadorDeEquipo(equipoSeleccionado.getNombre(), nombreJugador);

                 if (jugadorSeleccionado != null) {
                        // Crear un panel para modificar los datos del jugador
                        JTextField nombreField = new JTextField(jugadorSeleccionado.getNombre());
                        JTextField edadField = new JTextField(String.valueOf(jugadorSeleccionado.getEdad()));
                        String[] posiciones = {"Delantero", "Defensa", "Mediocampista", "Portero"};
                        JComboBox<String> posicionComboBox = new JComboBox<>(posiciones);
                        posicionComboBox.setSelectedItem(jugadorSeleccionado.getPosicion());

                        JButton btnSeleccionarImagen = new JButton("Seleccionar Imagen");
                        JLabel imagenLabel = new JLabel(jugadorSeleccionado.getImagen());

                        final File[] archivoImagenSeleccionada = {null};
                        btnSeleccionarImagen.addActionListener(event -> {
                            JFileChooser fileChooser = new JFileChooser();
                            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                                archivoImagenSeleccionada[0] = fileChooser.getSelectedFile();
                                imagenLabel.setIcon(new ImageIcon(archivoImagenSeleccionada[0].getAbsolutePath()));
                            }
                        });

                        JPanel panelModificar = new JPanel();
                        panelModificar.setLayout(new BoxLayout(panelModificar, BoxLayout.Y_AXIS));
                        panelModificar.add(new JLabel("Nombre:"));
                        panelModificar.add(nombreField);
                        panelModificar.add(new JLabel("Edad:"));
                        panelModificar.add(edadField);
                        panelModificar.add(new JLabel("Posición:"));
                        panelModificar.add(posicionComboBox);
                        panelModificar.add(btnSeleccionarImagen);
                        panelModificar.add(imagenLabel);

                        int result = JOptionPane.showConfirmDialog(null, panelModificar, "Modificar Jugador", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            try {
                                String nombre = nombreField.getText();
                                int edad = Integer.parseInt(edadField.getText());
                                String posicion = (String) posicionComboBox.getSelectedItem();

                                if (nombre.isEmpty() || posicion.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                // Si se seleccionó una nueva imagen, copiarla al destino
                                if (archivoImagenSeleccionada[0] != null) {
                                    String rutaDestino = "C:/xampp/htdocs/Temporada2_Grupo3_LM/img/" + equipoSeleccionado.getNombre().toLowerCase().replaceAll("\\s+", "_") + "" + nombre + ".png";
                                    File destino = new File(rutaDestino);
                                    Files.copy(archivoImagenSeleccionada[0].toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                    jugadorSeleccionado.setImagen(new ImageIcon(destino.getAbsolutePath()));
                                }

                                // Actualizar los datos del jugador
                                jugadorSeleccionado.setNombre(nombre);
                                jugadorSeleccionado.setEdad(edad);
                                jugadorSeleccionado.setPosicion(posicion);

                                JOptionPane.showMessageDialog(null, "Jugador modificado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                actualizarJugadores();
                                hayCambiosNoGuardados = true;
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Edad debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, "Error al guardar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                }
            } else {
            	 JOptionPane.showMessageDialog(this, "Seleccione un jugador para ver los detalles.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
     // Listener para el botón "Detalles"
        btnDetalles.addActionListener(e -> {
            int indiceTemporada = comboBoxTemporada.getSelectedIndex();
            int indiceEquipo = comboBoxEquipo.getSelectedIndex();
            int indiceJugador = listJugadores.getSelectedIndex(); // Índice del jugador seleccionado

            if (indiceTemporada >= 0 && indiceEquipo >= 0 && indiceJugador >= 0) {
                Temporada tempSeleccionada = temporadas.get(indiceTemporada);
                Equipo equipoSeleccionado = equiposActuales.get(indiceEquipo);

                String nombreJugador = listModelJugadores.getElementAt(indiceJugador).split(" - ")[0]; // Obtener el nombre del jugador
                Jugador jugadorSeleccionado = tempSeleccionada.obtenerJugadorDeEquipo(equipoSeleccionado.getNombre(), nombreJugador);

                if (jugadorSeleccionado != null) {
                    // Crear un JPanel para mostrar los detalles
                    JPanel panelDetalles = new JPanel();
                    panelDetalles.setLayout(new BoxLayout(panelDetalles, BoxLayout.Y_AXIS)); // Organizar los componentes en columna
                    
                    JLabel lblNombre = new JLabel("Nombre: " + jugadorSeleccionado.getNombre());
                    JLabel lblEdad = new JLabel("Edad: " + jugadorSeleccionado.getEdad());
                    JLabel lblPosicion = new JLabel("Posición: " + jugadorSeleccionado.getPosicion());
                    JLabel lblEquipo = new JLabel("Equipo: " + equipoSeleccionado.getNombre());
                    // Suponiendo que getImagen() devuelve una URL o una ruta de archivo
                    JLabel lblImagen = new JLabel(jugadorSeleccionado.getImagen()); // Asegúrate de que esto devuelve una imagen válida.

                    // Añadir los JLabels al JPanel
                    panelDetalles.add(lblNombre);
                    panelDetalles.add(lblEdad);
                    panelDetalles.add(lblPosicion);
                    panelDetalles.add(lblEquipo);
                    panelDetalles.add(lblImagen);

                    // Mostrar el panel en un JOptionPane
                    JOptionPane.showMessageDialog(this, panelDetalles, "Detalles del Jugador", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un jugador para ver los detalles.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        

        // Resto de la lógica (btnAgregar, btnModificar, btnDetalles, etc.)
    }

    private void cargarTemporadas() {
        Temporada temp = new Temporada(0, ""); // Instancia temporal para cargar temporadas
        temporadas = temp.cargarTemporadas();

        for (Temporada t : temporadas) {
            comboBoxTemporada.addItem(t.getNombre());
        }

        if (!temporadas.isEmpty()) {
            comboBoxTemporada.setSelectedIndex(0);
            actualizarEquipos();
            actualizarJugadores();
        }
    }

    private void actualizarEquipos() {
        int indiceTemporada = comboBoxTemporada.getSelectedIndex();
        if (indiceTemporada >= 0 && indiceTemporada < temporadas.size()) {
            Temporada tempSeleccionada = temporadas.get(indiceTemporada);
            equiposActuales = tempSeleccionada.getListEquipos();
            comboBoxEquipo.removeAllItems();
            for (Equipo equipo : equiposActuales) {
                comboBoxEquipo.addItem(equipo.getNombre());
            }
        }
    }

    private void actualizarJugadores() {
        int indiceTemporada = comboBoxTemporada.getSelectedIndex();
        int indiceEquipo = comboBoxEquipo.getSelectedIndex();

        if (indiceTemporada >= 0 && indiceTemporada < temporadas.size() && indiceEquipo >= 0 && indiceEquipo < equiposActuales.size()) {
            Temporada tempSeleccionada = temporadas.get(indiceTemporada);
            Equipo equipoSeleccionado = equiposActuales.get(indiceEquipo);

            ArrayList<Jugador> jugadores = tempSeleccionada.obtenerJugadoresDeEquipo(equipoSeleccionado.getNombre());

            listModelJugadores.clear(); // Limpiar la lista antes de añadir nuevos elementos

            if (jugadores.isEmpty()) {
                listModelJugadores.addElement("No hay jugadores en este equipo");
            } else {
                for (Jugador jugador : jugadores) {
                    listModelJugadores.addElement(jugador.getNombre() + " - Edad: " + jugador.getEdad() + " - Posición: " + jugador.getPosicion());
                }
            }
        }
    }

    // Métodos de WindowListener
    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        if (hayCambiosNoGuardados) {
            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "Hay cambios no guardados. ¿Desea guardar antes de salir?",
                    "Cambios no guardados",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (opcion == JOptionPane.YES_OPTION) {
                guardarEquipos();
            } else if (opcion == JOptionPane.NO_OPTION) {
                dispose();
            }
        } else {
            dispose();
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}

    private void guardarEquipos() {
    	int indiceTemporada = comboBoxTemporada.getSelectedIndex();
        int indiceEquipo = comboBoxEquipo.getSelectedIndex();

        if (indiceTemporada >= 0 && indiceEquipo >= 0 && indiceEquipo < equiposActuales.size()) {
            Temporada tempSeleccionada = temporadas.get(indiceTemporada);
            Equipo equipoSeleccionado = equiposActuales.get(indiceEquipo);

            
            
         // Verificar si el equipo ya tiene 15 jugadores
            if (equipoSeleccionado.getJugadores().size() < 15) {
                JOptionPane.showMessageDialog(null, "hay equipo que no tienen 15 jugadores.agregar más.", "Límite NO alcanzado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            } else {
        Temporada.guardarTemporadas(temporadas); // Llama al método de guardar
        
        gestionAdmin ventanaAdmin = new gestionAdmin();
        ventanaAdmin.setVisible(true);
        dispose();
        //System.exit(0);
            }
    }
}