package ventanas;

import clases.Equipo;
import clases.Jugador;
import clases.Temporada;
import clases.log;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class TemporadasWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private ArrayList<Temporada> temporadas;
    private DefaultListModel<String> listModel;
    private JList<String> list;
    private log log = new log();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TemporadasWindow frame = new TemporadasWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TemporadasWindow() {
        setTitle("Gestion Temporadas - Txurdi Liga");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Inicializar la lista de temporadas
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        actualizarListaTemporadas();
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(TemporadasWindow.class.getResource("/img/imagenes/logotxurdi.png")));
        panel.add(lblNewLabel);

        JPanel panel_1 = new JPanel();
        contentPane.add(panel_1, BorderLayout.SOUTH);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel_1.rowHeights = new int[]{0, 0};
        gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panel_1.setLayout(gbl_panel_1);

        JButton btnAtras = new JButton("Atras");
        btnAtras.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 gestionAdmin ventanaAdmin = new gestionAdmin();
                 ventanaAdmin.setVisible(true);
                 dispose();
            }
        });
        GridBagConstraints gbc_btnAtras = new GridBagConstraints();
        gbc_btnAtras.gridwidth = 2;
        gbc_btnAtras.insets = new Insets(0, 0, 0, 5);
        gbc_btnAtras.gridx = 6;
        gbc_btnAtras.gridy = 0;
        panel_1.add(btnAtras, gbc_btnAtras);

        JPanel panel_2 = new JPanel();
        contentPane.add(panel_2, BorderLayout.CENTER);
        panel_2.setLayout(new BorderLayout(0, 0));

        JPanel panel_3 = new JPanel();
        panel_2.add(panel_3, BorderLayout.SOUTH);

        JButton btnNewButton = new JButton("Agregar");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	agregarTemporada();
            }
        });
        panel_3.add(btnNewButton);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	 modificarTemporada();
            	
            }
        });
        panel_3.add(btnModificar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 eliminarTemporada();
            }
        });
        panel_3.add(btnEliminar);

        JButton btnVerDetalles = new JButton("Ver Detalles");
        btnVerDetalles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = list.getSelectedIndex();
                if (selectedIndex != -1) {
                    Temporada temporadaSeleccionada = temporadas.get(selectedIndex);
                    JOptionPane.showMessageDialog(TemporadasWindow.this,
                        "Nombre: " + temporadaSeleccionada.getNombre() + "\n" +
                        "Estado: " + temporadaSeleccionada.getEstado() + "\n" +
                         "Equipos: " + temporadaSeleccionada.getListEquipos().size() + "\n" +
                         "Jornadas: " + temporadaSeleccionada.getListJornadas().size(),
                        "Detalles de la Temporada",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(TemporadasWindow.this, "Seleccione una temporada para ver detalles.");
                }
            }
        });
        panel_3.add(btnVerDetalles);

        JPanel panel_4 = new JPanel();
        panel_2.add(panel_4, BorderLayout.CENTER);
        panel_4.add(new JScrollPane(list));
    }

    private void actualizarListaTemporadas() {
        listModel.clear();
        Temporada temp = new Temporada(0, ""); // Instancia temporal para cargar temporadas
        temporadas = temp.cargarTemporadas();

        for (Temporada t : temporadas) {
        	listModel.addElement("" +t.getNombre() + " Estado: " + t.getEstado());
        }
    }
    
    // Método para agregar nueva temporada
    @SuppressWarnings("static-access")
	private void agregarTemporada() {

        // Crear equipos
        ArrayList<Jugador> jugadores = new ArrayList<>();
        
        String nombreTemporada = JOptionPane.showInputDialog(
            this, 
            "Ingrese el nombre de la nueva temporada:",
            "Agregar Temporada",
            JOptionPane.PLAIN_MESSAGE
        );

        if (nombreTemporada != null && !nombreTemporada.trim().isEmpty()) {
            // Generar ID único
            int nuevoId = temporadas.isEmpty() ? 1 : temporadas.get(temporadas.size() - 1).getId_temporada() + 1;
            
            
          

            // Cargar la imagen predeterminada
            ImageIcon logoPredeterminado = new ImageIcon("C:\\xampp\\htdocs\\Temporada2_Grupo3_LM\\img\\escudos\\escudo.png");
            // Crear equipos y asignarles la imagen renombrada
            Equipo equipo1 = new Equipo("Real Madrid", "1909", "madrid", jugadores, 0, 0, Temporada.cambiarNombreImagen(logoPredeterminado, "real_madrid"));
            Equipo equipo2 = new Equipo("Barcelona", "1989", "barcelona", jugadores, 0, 0, Temporada.cambiarNombreImagen(logoPredeterminado, "barcelona"));
            Equipo equipo3 = new Equipo("Atletico Madrid", "1979", "madrid", jugadores, 0, 0, Temporada.cambiarNombreImagen(logoPredeterminado, "atletico_madrid"));
            Equipo equipo4 = new Equipo("Valencia", "1969", "valencia", jugadores, 0, 0,Temporada.cambiarNombreImagen(logoPredeterminado, "valencia"));
            Equipo equipo5 = new Equipo("Betis", "1999", "sevilla", jugadores, 0,0, Temporada.cambiarNombreImagen(logoPredeterminado, "betis"));
            Equipo equipo6 = new Equipo("Athletic Club", "1959", "bilbao", jugadores, 0, 0,Temporada.cambiarNombreImagen(logoPredeterminado, "athletic_club"));

            // Crear jugadores y asignarlos a los equipos con posiciones específicas
            for (Equipo equipo : new Equipo[]{equipo1, equipo2, equipo3, equipo4, equipo5, equipo6}) {
            	int jugadorNum = 1; // Inicia en 1 para cada equipo

                // Asignar 4 delanteros
                for (int i = 0; i < 4; i++) {
                    String nombreJugador = "Jugador_" + jugadorNum;
                    String rutaImagen = "C:/xampp/htdocs/Temporada2_Grupo3_LM/img/" + equipo.getNombre().toLowerCase().replaceAll("\\s+", "_") + "/Jugador_" + jugadorNum + ".png";
                    
                    // Verificar si existe la imagen del jugador
                    if (Temporada.verificarExistenciaImagen(rutaImagen)) {
                        ImageIcon imagenJugador = new ImageIcon(rutaImagen);
                        Jugador delantero = new Jugador(nombreJugador, 25 + (jugadorNum % 10), "Delantero", equipo, imagenJugador);
                        equipo.getJugadores().add(delantero);
                        System.out.println("Creado: " + nombreJugador + " en " + equipo.getNombre());
                        System.out.println("Ruta: " + nombreJugador + " en " + rutaImagen);
                    } else {
                        // Si no existe la imagen, coger la imagen por defecto y copiarla
                        String rutaImagenPredeterminada = "C:/xampp/htdocs/Temporada2_Grupo3_LM/img/jugadores/Jugador_" + jugadorNum + ".png";
                        if (Temporada.verificarExistenciaImagen(rutaImagenPredeterminada)) {
                            try {
                                // Copiar la imagen predeterminada al equipo
                                Files.copy(new File(rutaImagenPredeterminada).toPath(),
                                           new File(rutaImagen).toPath(), StandardCopyOption.REPLACE_EXISTING);
                                ImageIcon imagenJugador = new ImageIcon(rutaImagen);
                                Jugador delantero = new Jugador(nombreJugador, 25 + (jugadorNum % 10), "Delantero", equipo, imagenJugador);
                                equipo.getJugadores().add(delantero);
                                System.out.println("Creado: " + nombreJugador + " en " + equipo.getNombre());
                                System.out.println("Ruta: " + nombreJugador + " en " + rutaImagen);
                            } catch (IOException ex) {
                                System.out.println("¡Error al copiar la imagen predeterminada de " + nombreJugador + "!");
                            }
                        } else {
                            System.out.println("¡Advertencia! La imagen de " + nombreJugador + " no existe en la ruta: " + rutaImagenPredeterminada);
                        }
                    }
                    jugadorNum++;
                }

                // Asignar 4 defensas
                for (int i = 0; i < 4; i++) {
                    String nombreJugador = "Jugador_" + jugadorNum;
                    String rutaImagen = "C:/xampp/htdocs/Temporada2_Grupo3_LM/img/" + equipo.getNombre().toLowerCase().replaceAll("\\s+", "_") + "/Jugador_" + jugadorNum + ".png";
                    
                    // Verificar si existe la imagen del jugador
                    if (Temporada.verificarExistenciaImagen(rutaImagen)) {
                        ImageIcon imagenJugador = new ImageIcon(rutaImagen);
                        Jugador defensa = new Jugador(nombreJugador, 25 + (jugadorNum % 10), "Defensa", equipo, imagenJugador);
                        equipo.getJugadores().add(defensa);
                        System.out.println("Creado: " + nombreJugador + " en " + equipo.getNombre());
                        System.out.println("Ruta: " + nombreJugador + " en " + rutaImagen);
                    } else {
                        // Si no existe la imagen, coger la imagen por defecto y copiarla
                        String rutaImagenPredeterminada = "C:/xampp/htdocs/Temporada2_Grupo3_LM/img/jugadores/Jugador_" + jugadorNum + ".png";
                        if (Temporada.verificarExistenciaImagen(rutaImagenPredeterminada)) {
                            try {
                                // Copiar la imagen predeterminada al equipo
                                Files.copy(new File(rutaImagenPredeterminada).toPath(),
                                           new File(rutaImagen).toPath(), StandardCopyOption.REPLACE_EXISTING);
                                ImageIcon imagenJugador = new ImageIcon(rutaImagen);
                                Jugador defensa = new Jugador(nombreJugador, 25 + (jugadorNum % 10), "Defensa", equipo, imagenJugador);
                                equipo.getJugadores().add(defensa);
                                System.out.println("Creado: " + nombreJugador + " en " + equipo.getNombre());
                                System.out.println("Ruta: " + nombreJugador + " en " + rutaImagen);
                            } catch (IOException ex) {
                                System.out.println("¡Error al copiar la imagen predeterminada de " + nombreJugador + "!");
                            }
                        } else {
                            System.out.println("¡Advertencia! La imagen de " + nombreJugador + " no existe en la ruta: " + rutaImagenPredeterminada);
                        }
                    }
                    jugadorNum++;
                }

                // Asignar 4 mediocampistas
                for (int i = 0; i < 4; i++) {
                    String nombreJugador = "Jugador_" + jugadorNum;
                    String rutaImagen = "C:/xampp/htdocs/Temporada2_Grupo3_LM/img/" + equipo.getNombre().toLowerCase().replaceAll("\\s+", "_") + "/Jugador_" + jugadorNum + ".png";
                    
                    // Verificar si existe la imagen del jugador
                    if (Temporada.verificarExistenciaImagen(rutaImagen)) {
                        ImageIcon imagenJugador = new ImageIcon(rutaImagen);
                        Jugador mediocampista = new Jugador(nombreJugador, 25 + (jugadorNum % 10), "Mediocampista", equipo, imagenJugador);
                        equipo.getJugadores().add(mediocampista);
                        System.out.println("Creado: " + nombreJugador + " en " + equipo.getNombre());
                        System.out.println("Ruta: " + nombreJugador + " en " + rutaImagen);
                    } else {
                        // Si no existe la imagen, coger la imagen por defecto y copiarla
                        String rutaImagenPredeterminada = "C:/xampp/htdocs/Temporada2_Grupo3_LM/img/jugadores/Jugador_" + jugadorNum + ".png";
                        if (Temporada.verificarExistenciaImagen(rutaImagenPredeterminada)) {
                            try {
                                // Copiar la imagen predeterminada al equipo
                                Files.copy(new File(rutaImagenPredeterminada).toPath(),
                                           new File(rutaImagen).toPath(), StandardCopyOption.REPLACE_EXISTING);
                                ImageIcon imagenJugador = new ImageIcon(rutaImagen);
                                Jugador mediocampista = new Jugador(nombreJugador, 25 + (jugadorNum % 10), "Mediocampista", equipo, imagenJugador);
                                equipo.getJugadores().add(mediocampista);
                                System.out.println("Creado: " + nombreJugador + " en " + equipo.getNombre());
                                System.out.println("Ruta: " + nombreJugador + " en " + rutaImagen);
                            } catch (IOException ex) {
                                System.out.println("¡Error al copiar la imagen predeterminada de " + nombreJugador + "!");
                            }
                        } else {
                            System.out.println("¡Advertencia! La imagen de " + nombreJugador + " no existe en la ruta: " + rutaImagenPredeterminada);
                        }
                    }
                    jugadorNum++;
                }

                // Asignar 3 poprteo
                for (int i = 0; i < 3; i++) {
                    String nombreJugador = "Jugador_" + jugadorNum;
                    String rutaImagen = "C:/xampp/htdocs/Temporada2_Grupo3_LM/img/" + equipo.getNombre().toLowerCase().replaceAll("\\s+", "_") + "/Jugador_" + jugadorNum + ".png";
                    
                    // Verificar si existe la imagen del jugador
                    if (Temporada.verificarExistenciaImagen(rutaImagen)) {
                        ImageIcon imagenJugador = new ImageIcon(rutaImagen);
                        Jugador portero = new Jugador(nombreJugador, 25 + (jugadorNum % 10), "Portero", equipo, imagenJugador);
                        equipo.getJugadores().add(portero);
                        System.out.println("Creado: " + nombreJugador + " en " + equipo.getNombre());
                        System.out.println("Ruta: " + nombreJugador + " en " + rutaImagen);
                    } else {
                        // Si no existe la imagen, coger la imagen por defecto y copiarla
                        String rutaImagenPredeterminada = "C:/xampp/htdocs/Temporada2_Grupo3_LM/img/jugadores/Jugador_" + jugadorNum + ".png";
                        if (Temporada.verificarExistenciaImagen(rutaImagenPredeterminada)) {
                            try {
                                // Copiar la imagen predeterminada al equipo
                                Files.copy(new File(rutaImagenPredeterminada).toPath(),
                                           new File(rutaImagen).toPath(), StandardCopyOption.REPLACE_EXISTING);
                                ImageIcon imagenJugador = new ImageIcon(rutaImagen);
                                Jugador portero = new Jugador(nombreJugador, 25 + (jugadorNum % 10), "Portero", equipo, imagenJugador);
                                equipo.getJugadores().add(portero);
                                System.out.println("Creado: " + nombreJugador + " en " + equipo.getNombre());
                                System.out.println("Ruta: " + nombreJugador + " en " + rutaImagen);
                            } catch (IOException ex) {
                                System.out.println("¡Error al copiar la imagen predeterminada de " + nombreJugador + "!");
                            }
                        } else {
                            System.out.println("¡Advertencia! La imagen de " + nombreJugador + " no existe en la ruta: " + rutaImagenPredeterminada);
                        }
                    }
                    jugadorNum++;
                }
            }
            
            
            
            Temporada nuevaTemporada = new Temporada(nuevoId, nombreTemporada, "Inactiva");
            
            
            // Agregar equipos a las temporadas
            for (Equipo equipo : new Equipo[]{equipo1, equipo2, equipo3, equipo4, equipo5, equipo6}) {
            	nuevaTemporada.agregarEquipo(equipo);
            }

            
            //  Agregar la generación de jornadas correctamente**
            nuevaTemporada.crearJornadasRobin();
            
            temporadas.add(nuevaTemporada);
            
            Temporada.guardarTemporadas(temporadas);
            actualizarListaTemporadas();
            
            log.add("Se creo una temporada" + nuevaTemporada, 3);
            JOptionPane.showMessageDialog(this, "Temporada agregada exitosamente!");
        } else {
            JOptionPane.showMessageDialog(this, 
                "El nombre no puede estar vacío", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para modificar temporada existente
    @SuppressWarnings("static-access")
	private void modificarTemporada() {
        int selectedIndex = list.getSelectedIndex();
        
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione una temporada para modificar", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        Temporada tempSeleccionada = temporadas.get(selectedIndex);

        // Crear el panel con los campos de entrada
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        // Campo de texto para el nuevo nombre
        JTextField nombreField = new JTextField(tempSeleccionada.getNombre());
        
        // ComboBox para seleccionar el estado
        String[] estados = {"En Curso", "Inactiva", "Finalizada"};
        JComboBox<String> estadoComboBox = new JComboBox<>(estados);
        estadoComboBox.setSelectedItem(tempSeleccionada.getEstado());

        // Agregar los componentes al panel
        panel.add(new JLabel("Nuevo nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Estado:"));
        panel.add(estadoComboBox);

        // Mostrar el panel en una ventana de confirmación
        int resultado = JOptionPane.showConfirmDialog(this, panel, "Modificar Temporada",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            String nuevoNombre = nombreField.getText().trim();
            String nuevoEstado = (String) estadoComboBox.getSelectedItem();

            // Validar que el nombre no esté vacío
            if (nuevoNombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si ya hay otra temporada en curso
            if (nuevoEstado.equals("En Curso")) {
            	System.out.println("En curso");
                for (Temporada t : temporadas) {
                    if (t.getEstado().equals("En Curso") && t != tempSeleccionada) {
                        JOptionPane.showMessageDialog(this, 
                            "Ya existe otra temporada en curso.\nDebe finalizarla antes de iniciar otra.", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return; // Evita cambiar el estado si ya hay una en curso
                    }
                }
            }

            // Aplicar los cambios
            tempSeleccionada.setNombre(nuevoNombre);
            tempSeleccionada.setEstado(nuevoEstado);

            // Guardar cambios y actualizar la lista
            Temporada.guardarTemporadas(temporadas);
            actualizarListaTemporadas();
            log.add("Se Modifico una temporada" + tempSeleccionada, 3);
            JOptionPane.showMessageDialog(this, "Temporada modificada exitosamente!");
        }
    }


    // Método para eliminar temporada
    @SuppressWarnings("static-access")
	private void eliminarTemporada() {
        int selectedIndex = list.getSelectedIndex();
        
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione una temporada para eliminar", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro de eliminar esta temporada?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            temporadas.remove(selectedIndex);
            Temporada.guardarTemporadas(temporadas);
            actualizarListaTemporadas();
            log.add("Se Elimeno una temporada" + selectedIndex, 1);
            JOptionPane.showMessageDialog(this, "Temporada eliminada exitosamente!");
        }
    }
}