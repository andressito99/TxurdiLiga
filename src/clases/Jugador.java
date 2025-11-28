package clases;

import java.io.Serializable;
import javax.swing.ImageIcon;

public class Jugador implements Serializable, Cloneable {

    private static final long serialVersionUID = -3594324243356090829L;
    private String nombre;          // Nombre del jugador
    private int edad;              // Edad del jugador
    private String posicion;       // Posición (4 delantero, 4 defensa, 4 mediocampista, 3 portero)
    private Equipo equipo;         // El equipo al que pertenece el jugador
    private ImageIcon imagen;      // Imagen del jugador

    // Constructor de la clase Jugador
    public Jugador(String nombre, int edad, String posicion, Equipo equipo, ImageIcon imagen) {
        this.nombre = nombre;
        this.edad = edad;
        this.posicion = posicion;
        this.equipo = equipo;
        this.imagen = imagen;
    }

    // Métodos Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getEquipo() {
        return equipo.getNombre(); // Devuelve el nombre del equipo, no el objeto Equipos
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public String getRutaImagen() {
        return (imagen != null) ? imagen.getDescription() : "sin_imagen.png";
    }
    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }

    // Sobrescribir el método clone() para permitir la clonación de jugadores
    @Override
    public Jugador clone() {
        try {
            // Clonar el objeto Jugador
            Jugador clon = (Jugador) super.clone();
            // Clonar la imagen (ImageIcon no es clonable, por lo que se crea una nueva instancia)
            if (this.imagen != null) {
                clon.imagen = new ImageIcon(this.imagen.getImage());
            }
            return clon;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error al clonar el jugador", e);
        }
    }
}