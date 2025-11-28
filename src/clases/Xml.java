package clases;

import java.util.ArrayList;
import java.util.Collections;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Xml {
	
	  private ArrayList<String> elementosXML;
	    private ArrayList<String> contenidoXMLFormateado;

	    public Xml() {
	        elementosXML = new ArrayList<>();
	        contenidoXMLFormateado = new ArrayList<>();
	    }
	    
	    public void add(String contenido, boolean esDato, int indentacion) {
	        String indentacionString = "    ".repeat(indentacion);
	        String contenidoFormateado;
	        
	        if (esDato) {
	            contenidoFormateado = indentacionString + contenido;
	        } else {
	            int count = Collections.frequency(elementosXML, contenido);
	            
	            if (count % 2 != 0) {
	                contenidoFormateado = indentacionString + "</" + contenido + ">";
	            } else {
	                contenidoFormateado = indentacionString + "<" + contenido + ">";
	            }
	            elementosXML.add(contenido);
	        }
	        
	        contenidoXMLFormateado.add(contenidoFormateado);
	    }

	    public void file(String archivo) {
	        String nombreArchivo = "C:\\xampp\\htdocs\\Temporada2_Grupo3_LM\\xmlPrueba\\" + archivo + ".xml";
	        File file = new File(nombreArchivo);

	        // Depuración: Verificar si el archivo existe ANTES de intentar eliminarlo
	        if (file.exists()) {
	            System.out.println("🔍 El archivo EXISTE antes de intentar eliminarlo: " + nombreArchivo);
	            if (file.delete()) {
	                System.out.println("✅ Archivo eliminado correctamente: " + nombreArchivo);
	            } else {
	                System.err.println("❌ No se pudo eliminar el archivo.");
	                return;
	            }
	        } else {
	            System.out.println("⚠️ El archivo NO EXISTE antes de crearlo: " + nombreArchivo);
	        }

	        try (FileWriter fichero = new FileWriter(nombreArchivo);
	             BufferedWriter bw = new BufferedWriter(fichero)) {
	            
	            bw.write("<?xml version='1.0' encoding='utf-8'?>");
	            bw.newLine();
	            bw.write("<?xml-model href='temporada.xsd'?>");
	            bw.newLine();

	            for (String linea : contenidoXMLFormateado) {
	                bw.write(linea);
	                bw.newLine();
	            }

	            System.out.println("📁 Archivo XML creado con éxito: " + nombreArchivo);
	        } catch (IOException e) {
	            System.err.println("🚨 Error al crear el archivo: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }


    
    public void generarXMLDesdeListaTemporadas(ArrayList<Temporada> temporadas, String nombreArchivo) {
        this.clear();
        this.add("Temporadas", false, 0); // Etiqueta raíz

        for (Temporada temporada : temporadas) {
            this.add("Temporada", false, 1);

            this.add("Numero", false, 2);
            this.add(temporada.getNombre(), true, 3);
            this.add("Numero", false, 2);

            this.add("Estado", false, 2);
            this.add(temporada.getEstado(), true, 3);
            this.add("Estado", false, 2);

            // 🔹 **Equipos**
            this.add("Equipos", false, 2);
            for (Equipo equipo : temporada.getListEquipos()) {
                this.add("Equipo", false, 3);

                this.add("Nombre", false, 4);
                this.add(equipo.getNombre(), true, 5);
                this.add("Nombre", false, 4);

                this.add("Ciudad", false, 4);
                this.add(equipo.getCiudad(), true, 5);
                this.add("Ciudad", false, 4);

                this.add("ano_fundacion", false, 4);
                this.add(equipo.getAnoFundacion(), true, 5);
                this.add("ano_fundacion", false, 4);

                // 🔹 **Jugadores del equipo**
                this.add("Jugadores", false, 4);
                for (Jugador jugador : equipo.getJugadores()) {
                    this.add("Jugador", false, 5);

                    this.add("Nombre", false, 6);
                    this.add(jugador.getNombre(), true, 7);
                    this.add("Nombre", false, 6);

                    this.add("Edad", false, 6);
                    this.add(String.valueOf(jugador.getEdad()), true, 7);
                    this.add("Edad", false, 6);

                    this.add("Posicion", false, 6);
                    this.add(jugador.getPosicion(), true, 7);
                    this.add("Posicion", false, 6);
                    
                     this.add("Imagen", false, 6);
                     this.add(jugador.getNombre()+".png", true, 7);
                     this.add("Imagen", false, 6);


                    this.add("Jugador", false, 5);
                }
                this.add("Jugadores", false, 4);

                
                this.add("Imagen", false, 6);
                this.add(equipo.getNombre().toLowerCase().replaceAll("\\s+", "_")+".png", true, 7);
                this.add("Imagen", false, 6);
                this.add("Equipo", false, 3);
            }
            this.add("Equipos", false, 2);

            // 🔹 **Jornadas**
            this.add("Jornadas", false, 2);
            for (Jornada jornada : temporada.getListJornadas()) {
                this.add("Jornada", false, 3);

                this.add("Numero", false, 4);
                this.add(String.valueOf(jornada.getNumero()), true, 5);
                this.add("Numero", false, 4);
                
                // 🔹 **Partidos**
                this.add("Partidos", false, 2);
                for (Partido partido : jornada.getPartidos()) {
                    this.add("Partido", false, 3);

                    this.add("EquipoLocal", false, 4);
                    // Aquí se debe agregar el nombre del equipo local
                    this.add(partido.getEquipoLocal().getNombre(), true, 5);
                    this.add("EquipoLocal", false, 4);
                    
                    this.add("EquipoVisitante", false, 4);
                    // Aquí se debe agregar el nombre del equipo visitante
                    this.add(partido.getEquipoVisitante().getNombre(), true, 5);
                    this.add("EquipoVisitante", false, 4);

                    this.add("GolesLocal", false, 4);
                    // Aquí se debe agregar el número de goles del equipo local
                    this.add(String.valueOf(partido.getGolesLocal()), true, 5);
                    this.add("GolesLocal", false, 4);
                    
                    this.add("GolesVisitante", false, 4);
                    // Aquí se debe agregar el número de goles del equipo visitante
                    this.add(String.valueOf(partido.getGolesVisitante()), true, 5);
                    this.add("GolesVisitante", false, 4);

                    this.add("Partido", false, 3);
                }
                this.add("Partidos", false, 2);

                this.add("Jornada", false, 3);
            }
            this.add("Jornadas", false, 2);


            this.add("Temporada", false, 1);
        }

        this.add("Temporadas", false, 0); // Cierre de la raíz
        this.file(nombreArchivo);
    }


    public void clear() {
    	elementosXML.clear();
    	contenidoXMLFormateado.clear();
    }
    
}