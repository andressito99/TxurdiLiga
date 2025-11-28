package clases;

import java.io.Serializable;
import java.util.ArrayList;

public class Jornada implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8928583620187840055L;
	private int numero; // Número de la jornada
    private ArrayList<Partido> listPartidos; // Lista de partidos de la jornadas

    // Constructor
    public Jornada(int numero) {
        this.numero = numero;
        this.listPartidos = new ArrayList<>();
    }
    
 // Getters y Setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public ArrayList<Partido> getPartidos() {
        return listPartidos;
    }

    public void setPartidos(ArrayList<Partido> partidos) {
        this.listPartidos = partidos;
    }
    
    
    
 @Override
	public String toString() {
		return "" + numero;
	}

	// Método para agregar un partido a la jornada
    public void agregarPartido(Partido partido) {
    	listPartidos.add(partido);
    }
}