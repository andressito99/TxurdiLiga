package clases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Temporada implements Serializable {
    private static final long serialVersionUID = 115633587383446069L;
    private int id_temporada; // Identificador único de la temporada
    private String nombre; // Nombre de la temporada
    private String estado; // Estado de finalización de la temporada
    private ArrayList<Jornada> listJornadas; // Lista de jornadas de la temporada
    private ArrayList<Equipo> listEquipos; // Lista de equipos de la temporada
    private ArrayList<Temporada> temporadas;
    private boolean Finalizada;

    // Constructor
    public Temporada(int id_temporada, String nombre, String estado) {
        this.id_temporada = id_temporada;
        this.nombre = nombre;
        this.estado = estado;
        this.listJornadas = new ArrayList<>();
        this.listEquipos = new ArrayList<>();
    }

    // Constructor NOMBRE Y NUMERO
    public Temporada(int id_temporada, String nombre) {
        this.id_temporada = id_temporada;
        this.nombre = nombre;
        this.listJornadas = new ArrayList<>(); // Inicializamos la lista
        this.listEquipos = new ArrayList<>();  // Inicializamos la lista
    }

    // Getters y Setters
    public int getId_temporada() {
        return id_temporada;
    }

    public void setId_temporada(int id_temporada) {
        this.id_temporada = id_temporada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ArrayList<Equipo> getListEquipos() {
        return listEquipos;
    }

    public void setListEquipos(ArrayList<Equipo> listEquipos) {
        this.listEquipos = listEquipos;
    }

    public ArrayList<Jornada> getListJornadas() {
        return listJornadas;
    }

    public void setListJornadas(ArrayList<Jornada> listJornadas) {
        this.listJornadas = listJornadas;
    }

    // Método para agregar una jornada a la temporada
    public void agregarJornada(Jornada jornada) {
        this.listJornadas.add(jornada);
    }
    
    public boolean isActiva() {
    	if (estado == "Activa") {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    public boolean isFinalizada() {
    	if (estado == "Finalizada") {
    		return false;
    	}
    	else {
    		return true;
    	}
    }

    public void setFinalizada(boolean Finalizada) {
        this.Finalizada = Finalizada;
    }

    // Método para agregar un equipo a la temporada
    public void agregarEquipo(Equipo equipoOriginal) {
        // Crear una copia del equipo original
        Equipo equipoCopia = new Equipo(
            equipoOriginal.getNombre(),
            equipoOriginal.getAnoFundacion(),
            equipoOriginal.getCiudad(),
            new ArrayList<>(), // Lista vacía de jugadores (se llenará con copias)
            equipoOriginal.getPuntos(),
            equipoOriginal.getPartidosJugados(),
            equipoOriginal.getImagen()
        );

        // Clonar cada jugador del equipo original y agregarlo al equipo copia
        for (Jugador jugador : equipoOriginal.getJugadores()) {
            Jugador copiaJugador = jugador.clone(); // Clonar el jugador
            copiaJugador.setEquipo(equipoCopia); // Asignar el equipo copia al jugador clonado
            equipoCopia.getJugadores().add(copiaJugador);
        }

        // Agregar la copia del equipo a la temporada
        this.listEquipos.add(equipoCopia);
    }
    
 // Método para eliminar un equipo de la temporada
    public void eliminarEquipo(Equipo equipo) {
        listEquipos.remove(equipo);
    }

    // Guardar todas las temporadas en un solo archivo .ser
    public static void guardarTemporadas(ArrayList<Temporada> temporadas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("temporadas.ser"))) {
            oos.writeObject(temporadas);
            System.out.println("Temporadas guardadas con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Cargar todas las temporadas desde el archivo .ser
    @SuppressWarnings("unchecked")
    public ArrayList<Temporada> cargarTemporadas() {
        ArrayList<Temporada> temporadas = new ArrayList<>();
        File file = new File("temporadas.ser");
        
        if (!file.exists()) {
        	 // Si el archivo no existe, creamos la temporada con datos predeterminados
            System.out.println("Archivo no encontrado. Se cargarán datos predeterminados.");
            log.add("Archivo no encontrado. Se cargarán datos predeterminados.", 3);
            // Crear datos predeterminados
            temporadas = crearDatosPredeterminados();
            
            for (Temporada temporada : temporadas) {
                if (temporada.getEstado().equals("Finalizada")) {
                    temporada.simularResultadosJornadas();
                }
            }
            
            // Guardar los datos predeterminados
            guardarTemporadas(temporadas);
        } else {
            // Si el archivo existe, cargamos las temporadas desde el archivo
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("temporadas.ser"))) {
                temporadas = (ArrayList<Temporada>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return temporadas;
    }
    
    public Jugador obtenerJugadorDeEquipo(String nombreEquipo, String nombreJugador) {

    	
    	  // Buscar el equipo en la lista de equipos de esta temporada
        for (Equipo equipo : listEquipos) {
            if (equipo.getNombre().equalsIgnoreCase(nombreEquipo)) {
                // Buscar el jugador dentro del equipo
                for (Jugador jugador : equipo.getJugadores()) {
                    if (jugador.getNombre().equalsIgnoreCase(nombreJugador)) {
                        equipo.getJugadores();
                        System.out.println("Jugador " + nombreJugador + this.nombre);
                        return jugador; // Salimos del método una vez eliminado
                    }
                }
                System.out.println("No se encontró al jugador " + nombreJugador + " en el equipo " + nombreEquipo + " en la temporada " + this.nombre);
            }
        }
         System.out.println("No se encontró el equipo " + nombreEquipo + " en la temporada " + this.nombre);
		return null;
	}
    
    
    public void simularResultadosJornadas() {
        Random random = new Random();

        for (Jornada jornada : listJornadas) {
            for (Partido partido : jornada.getPartidos()) {
                // Generar goles aleatorios para el equipo local y visitante
                int golesLocal = random.nextInt(5); // Máximo 4 goles
                int golesVisitante = random.nextInt(5); // Máximo 4 goles

                // Asignar los goles al partido
                partido.setGolesLocal(golesLocal);
                partido.setGolesVisitante(golesVisitante);
                
             // Obtener los equipos del partido
                Equipo equipoLocal = partido.getEquipoLocal();
                Equipo equipoVisitante = partido.getEquipoVisitante();

                // Incrementar el contador de partidos jugados para ambos equipos
                equipoLocal.setPartidosJugados(equipoLocal.getPartidosJugados() + 1);
                equipoVisitante.setPartidosJugados(equipoVisitante.getPartidosJugados() + 1);
            }
        }
    }
    
    public void agregarJugadorAEquipo(String nombreEquipo, Jugador jugador) {
        boolean equipoEncontrado = false;

        // Recorremos la lista de equipos para encontrar el equipo por su nombre
        for (Equipo equipo : listEquipos) {
            if (equipo.getNombre().equals(nombreEquipo)) {
                equipo.agregarJugador(jugador); // Agregamos el jugador al equipo
                break;
            }
        }

        if (!equipoEncontrado) {
            System.out.println("Equipo no encontrado: " + nombreEquipo); // Caso en el que el equipo no existe
        }
    }

    
    public void eliminarJugadorDeEquipo(String nombreEquipo, String nombreJugador) {
        // Buscar el equipo en la lista de equipos de esta temporada
        for (Equipo equipo : listEquipos) {
            if (equipo.getNombre().equalsIgnoreCase(nombreEquipo)) {
                // Buscar el jugador dentro del equipo
                for (Jugador jugador : equipo.getJugadores()) {
                    if (jugador.getNombre().equalsIgnoreCase(nombreJugador)) {
                        equipo.getJugadores().remove(jugador);
                        System.out.println("Jugador " + nombreJugador + " eliminado del equipo " + nombreEquipo + " en la temporada " + this.nombre);
                        return; // Salimos del método una vez eliminado
                    }
                }
                System.out.println("No se encontró al jugador " + nombreJugador + " en el equipo " + nombreEquipo + " en la temporada " + this.nombre);
                return;
            }
        }
        System.out.println("No se encontró el equipo " + nombreEquipo + " en la temporada " + this.nombre);
    }



    public static ArrayList<Temporada> crearDatosPredeterminados() {
        ArrayList<Temporada> temporadas = new ArrayList<>();

        // Crear equipos
        ArrayList<Jugador> jugadores = new ArrayList<>();

        // Cargar la imagen predeterminada
        ImageIcon logoPredeterminado = new ImageIcon("C:\\xampp\\htdocs\\Temporada2_Grupo3_LM\\img\\escudos\\escudo.png");
        // Crear equipos y asignarles la imagen renombrada
        Equipo equipo1 = new Equipo("Real Madrid", "1909", "madrid", jugadores, 0, 0,cambiarNombreImagen(logoPredeterminado, "real_madrid"));
        Equipo equipo2 = new Equipo("Barcelona", "1989", "barcelona", jugadores, 0, 0, cambiarNombreImagen(logoPredeterminado, "barcelona"));
        Equipo equipo3 = new Equipo("Atletico Madrid", "1979", "madrid", jugadores, 0, 0, cambiarNombreImagen(logoPredeterminado, "atletico_madrid"));
        Equipo equipo4 = new Equipo("Valencia", "1969", "valencia", jugadores, 0, 0,cambiarNombreImagen(logoPredeterminado, "valencia"));
        Equipo equipo5 = new Equipo("Betis", "1999", "sevilla", jugadores, 0,0, cambiarNombreImagen(logoPredeterminado, "betis"));
        Equipo equipo6 = new Equipo("Athletic Club", "1959", "bilbao", jugadores, 0, 0,cambiarNombreImagen(logoPredeterminado, "athletic_club"));

        // Crear jugadores y asignarlos a los equipos con posiciones específicas
        for (Equipo equipo : new Equipo[]{equipo1, equipo2, equipo3, equipo4, equipo5, equipo6}) {
        	int jugadorNum = 1; // Inicia en 1 para cada equipo

            // Asignar 4 delanteros
            for (int i = 0; i < 4; i++) {
                String nombreJugador = "Jugador_" + jugadorNum;
                String rutaImagen = "C:/xampp/htdocs/Temporada2_Grupo3_LM/img/" + equipo.getNombre().toLowerCase().replaceAll("\\s+", "_") + "/Jugador_" + jugadorNum + ".png";
                
                // Verificar si existe la imagen del jugador
                if (verificarExistenciaImagen(rutaImagen)) {
                    ImageIcon imagenJugador = new ImageIcon(rutaImagen);
                    Jugador delantero = new Jugador(nombreJugador, 25 + (jugadorNum % 10), "Delantero", equipo, imagenJugador);
                    equipo.getJugadores().add(delantero);
                    System.out.println("Creado: " + nombreJugador + " en " + equipo.getNombre());
                    System.out.println("Ruta: " + nombreJugador + " en " + rutaImagen);
                } else {
                    // Si no existe la imagen, coger la imagen por defecto y copiarla
                    String rutaImagenPredeterminada = "C:/xampp/htdocs/Temporada2_Grupo3_LM/img/jugadores/Jugador_" + jugadorNum + ".png";
                    if (verificarExistenciaImagen(rutaImagenPredeterminada)) {
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
                if (verificarExistenciaImagen(rutaImagen)) {
                    ImageIcon imagenJugador = new ImageIcon(rutaImagen);
                    Jugador defensa = new Jugador(nombreJugador, 25 + (jugadorNum % 10), "Defensa", equipo, imagenJugador);
                    equipo.getJugadores().add(defensa);
                    System.out.println("Creado: " + nombreJugador + " en " + equipo.getNombre());
                    System.out.println("Ruta: " + nombreJugador + " en " + rutaImagen);
                } else {
                    // Si no existe la imagen, coger la imagen por defecto y copiarla
                    String rutaImagenPredeterminada = "C:/xampp/htdocs/Temporada2_Grupo3_LM/img/jugadores/Jugador_" + jugadorNum + ".png";
                    if (verificarExistenciaImagen(rutaImagenPredeterminada)) {
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
                if (verificarExistenciaImagen(rutaImagen)) {
                    ImageIcon imagenJugador = new ImageIcon(rutaImagen);
                    Jugador mediocampista = new Jugador(nombreJugador, 25 + (jugadorNum % 10), "Mediocampista", equipo, imagenJugador);
                    equipo.getJugadores().add(mediocampista);
                    System.out.println("Creado: " + nombreJugador + " en " + equipo.getNombre());
                    System.out.println("Ruta: " + nombreJugador + " en " + rutaImagen);
                } else {
                    // Si no existe la imagen, coger la imagen por defecto y copiarlas
                    String rutaImagenPredeterminada = "C:/xampp/htdocs/Temporada2_Grupo3_LM/img/jugadores/Jugador_" + jugadorNum + ".png";
                    if (verificarExistenciaImagen(rutaImagenPredeterminada)) {
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
                if (verificarExistenciaImagen(rutaImagen)) {
                    ImageIcon imagenJugador = new ImageIcon(rutaImagen);
                    Jugador portero = new Jugador(nombreJugador, 25 + (jugadorNum % 10), "Portero", equipo, imagenJugador);
                    equipo.getJugadores().add(portero);
                    System.out.println("Creado: " + nombreJugador + " en " + equipo.getNombre());
                    System.out.println("Ruta: " + nombreJugador + " en " + rutaImagen);
                } else {
                    // Si no existe la imagen, coger la imagen por defecto y copiarla
                    String rutaImagenPredeterminada = "C:/xampp/htdocs/Temporada2_Grupo3_LM/img/jugadores/Jugador_" + jugadorNum + ".png";
                    if (verificarExistenciaImagen(rutaImagenPredeterminada)) {
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

        // Crear temporadas y agregar equipos
        Temporada temporada2025 = new Temporada(1, "Temporada 2025", "En Curso");
        Temporada temporada2024 = new Temporada(2, "Temporada 2024", "Inactiva");
        Temporada temporada2023 = new Temporada(3, "Temporada 2023", "Finalizada");


        // Agregar equipos a las temporadas
        for (Equipo equipo : new Equipo[]{equipo1, equipo2, equipo3, equipo4, equipo5, equipo6}) {
            temporada2025.agregarEquipo(equipo);
            temporada2024.agregarEquipo(equipo);
            temporada2023.agregarEquipo(equipo);
        }

        
        //  Agregar la generación de jornadas correctamente**
        temporada2025.crearJornadasRobin();
        temporada2024.crearJornadasRobin();
        temporada2023.crearJornadasRobin();


        // Agregar temporadas a la lista
        temporadas.add(temporada2025);
        temporadas.add(temporada2024);
        temporadas.add(temporada2023);

        return temporadas;
    }

    
    @Override
    public String toString() {
        return this.nombre;  // Retorna solo el nombre de la temporada
    }
    
    
    public String mostrarDetalles() {
        return "ID: " + id_temporada +
               ", Nombre: " + nombre +
               ", Estado: " + estado +
               ", Equipos: " + listEquipos.size() +
               ", Jornadas: " + listJornadas.size();
    }



    
    // Método para verificar si la imagen del jugador existe en el sistema de archivos
    public static boolean verificarExistenciaImagen(String rutaImagen) {
        File archivo = new File(rutaImagen);
        return archivo.exists() && archivo.isFile();
    }

    public ArrayList<Jugador> obtenerJugadoresDeEquipo(String equipoNombre) {
        System.out.println("Buscando jugadores para el equipo: " + equipoNombre);

        for (Equipo equipo : getListEquipos()) {
            System.out.println("Equipo en lista: " + equipo.getNombre());
            if (equipo.getNombre().equals(equipoNombre)) {
                System.out.println("Equipo encontrado. Jugadores:");

                for (Jugador jugador : equipo.getJugadores()) {
                    System.out.println(" - " + jugador.getNombre());
                }

                return equipo.getJugadores();
            }
        }

        System.out.println("Equipo no encontrado en la lista.");
        return new ArrayList<>();
    }


    // Método para cambiar el nombre de la imagen, borrar el archivo anterior si existe, y guardarla con el nombre del equipo en su caprta de equipo
    public static ImageIcon cambiarNombreImagen(ImageIcon logoPredeterminado, String nombreEquipo) {
        try {
            // Ruta del archivo original
            File archivoOriginal = new File("C:/xampp/htdocs/Temporada2_Grupo3_LM/img/escudos/escudo.png");

            // Verificar si el archivo original existe
            if (!archivoOriginal.exists()) {
                throw new FileNotFoundException("El archivo original no se encuentra: " + archivoOriginal.getAbsolutePath());
            }

            // Ruta de la carpeta del equipo
            String carpetaEquipo = "C:/xampp/htdocs/Temporada2_Grupo3_LM/img/" + nombreEquipo.replaceAll("\\s+", "_");
            File directorio = new File(carpetaEquipo);

            // Crear la carpeta del equipo si no existe
            if (!directorio.exists()) {
                boolean carpetaCreada = directorio.mkdirs();  // Crea la carpeta si no existe
                if (!carpetaCreada) {
                    throw new IOException("No se pudo crear la carpeta para el equipo: " + carpetaEquipo);
                }
            }

            // Crear el nuevo archivo con el nombre del equipo
            File archivoNuevo = new File(carpetaEquipo + "/" + nombreEquipo + ".png");

            // Si el archivo ya existe, lo eliminamos
            if (archivoNuevo.exists()) {
                archivoNuevo.delete();
            }

            // Copiar el archivo original al nuevo archivo con el nombre adecuado
            Files.copy(archivoOriginal.toPath(), archivoNuevo.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Retornar la imagen con el nuevo nombre
            return new ImageIcon(archivoNuevo.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            return logoPredeterminado;  // Si hay un error, retorna la imagen predeterminada
        }
    }


    // Método para crear las jornadas de forma "Round Robin" (ida y vuelta)
    public void crearJornadasRobin() {
        if (listEquipos.size() < 6) {
            System.out.println("Se necesitan al menos seis equipos para crear las jornadas.");
            return;
        }

        int numEquipos = listEquipos.size();
        int numJornadas = numEquipos - 1;  // Número correcto de jornadas en una liga de ida
        ArrayList<Partido> partidosIda = new ArrayList<>();

        // 🔹 Generar las jornadas de IDA correctamente
        for (int i = 0; i < numJornadas; i++) {
            Jornada jornada = new Jornada(i + 1);
            for (int j = 0; j < numEquipos / 2; j++) {
                Equipo local = listEquipos.get((i + j) % numEquipos);
                Equipo visitante = listEquipos.get((i + numEquipos - j - 1) % numEquipos);

                Partido partidoIda = new Partido(local, visitante);
                jornada.agregarPartido(partidoIda);
                partidosIda.add(partidoIda);
            }
            listJornadas.add(jornada);
        }

        // 🔹 Generar las jornadas de VUELTA correctamente
        for (int i = 0; i < numJornadas; i++) {
            Jornada jornada = new Jornada(numJornadas + i + 1);
            for (int j = 0; j < numEquipos / 2; j++) {
                Partido partidoIda = partidosIda.get(i * (numEquipos / 2) + j);
                Equipo local = partidoIda.getEquipoVisitante();
                Equipo visitante = partidoIda.getEquipoLocal();

                Partido partidoVuelta = new Partido(local, visitante);
                jornada.agregarPartido(partidoVuelta);
            }
            listJornadas.add(jornada);
        }

        System.out.println("Jornadas creadas exitosamente.");
    }


    public static void generarXMLDesdeListaTemporadas(ArrayList<Temporada> temporadas, String nombreArchivo) {
        Xml xml = new Xml();
        xml.generarXMLDesdeListaTemporadas(temporadas, nombreArchivo);
    }	
 
}
