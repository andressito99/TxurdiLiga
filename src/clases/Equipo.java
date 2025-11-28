package clases;

import java.io.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Equipo implements Serializable {

    private static final long serialVersionUID = 2171754837756528471L;
    private String nombre;
    private String anoFundacion;
    private String ciudad;
    private ArrayList<Jugador> jugadores;
    private int puntos;
    private int partidosJugados;
    private ImageIcon imagen;  // Campo para almacenar la imagen del equipo

    // Constructor s
    public Equipo(String nombre, String anoFundacion, String ciudad, ArrayList<Jugador> jugadores, int puntos, int partidosJugados, ImageIcon imagen) {
        this.nombre = nombre;
        this.anoFundacion = anoFundacion;
        this.ciudad = ciudad;
        this.jugadores = (jugadores != null) ? new ArrayList<>(jugadores) : new ArrayList<>(); // Se crea una copia para evitar referencias compartidas
        this.puntos = puntos;
        this.partidosJugados = partidosJugados;
        this.imagen = imagen;
    }
    
    // Constructor copia
    public Equipo(Equipo otroEquipo) {
        this.nombre = otroEquipo.nombre;
        this.anoFundacion = otroEquipo.anoFundacion;
        this.ciudad = otroEquipo.ciudad;
        this.jugadores = new ArrayList<>(otroEquipo.jugadores); // Se crea una nueva lista con los mismos jugadores
        this.puntos = otroEquipo.puntos;
        this.puntos = otroEquipo.partidosJugados;
        this.imagen = otroEquipo.imagen;
    }

    // Constructor solo con el nombre
    public Equipo(String nombre) {
        this.nombre = nombre;
        this.jugadores = new ArrayList<>();  // Se inicializa la lista vacía
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnoFundacion() {
        return anoFundacion;
    }

    public void setAnoFundacion(String anoFundacion) {
        this.anoFundacion = anoFundacion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = (jugadores != null) ? new ArrayList<>(jugadores) : new ArrayList<>();
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }

    public ImageIcon getLogo() {
        return imagen;
    }

    public void setLogo(ImageIcon imagen) {
        this.imagen = imagen;
    }

    // Métodos para agregar o eliminar jugadores
    public void agregarJugador(Jugador jugador) {
        if (jugadores == null) {
            jugadores = new ArrayList<>();
        }
        if (!jugadores.contains(jugador)) {  // Evita duplicados
            jugadores.add(jugador);
        }
    }

    public void eliminarJugador(Jugador jugador) {
        if (jugadores != null) {
            jugadores.remove(jugador);
        }
    }

	public int getPartidosJugados() {
		return partidosJugados;
	}

	public void setPartidosJugados(int partidosJugados) {
		this.partidosJugados = partidosJugados;
	}
}
