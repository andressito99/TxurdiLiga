package clases;

import java.io.Serializable;

public class Partido implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 772724823964523943L;
	private Equipo equipoLocal; // Equipo local
    private Equipo equipoVisitante; // Equipo visitante
    private int golesLocal; // Goles marcados por el equipo local
    private int golesVisitante; // Goles marcados por el equipo visitante

 // Constructor cuando el partido ya tiene un resultados
    public Partido(Equipo equipoLocal, Equipo equipoVisitante, int golesLocal, int golesVisitante) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
    }

    // Constructor para un partido aún no jugado
    public Partido(Equipo equipoLocal, Equipo equipoVisitante) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.golesLocal = -1; // Valor especial para indicar que el partido no se ha jugado
        this.golesVisitante = -1;
    }
   
    
    
 // Getters y Setters
    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(int golesLocal) {
        this.golesLocal = golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(int golesVisitante) {
        this.golesVisitante = golesVisitante;
    }
    
    public boolean estaJugado() {
        return golesLocal != -1 && golesVisitante != -1; // Comprobamos que ambos goles sean válidos
    }


    
 // Método para obtener el resultado del partido
    public int obtenerResultado() {
        if (!estaJugado()) { // Si el partido no ha sido jugado, no tiene resultado
            return -1; 
        } else if (golesLocal > golesVisitante) {
            return 1;  // Gana el equipo local
        } else if (golesVisitante > golesLocal) {
            return 2;  // Gana el equipo visitante
        } else {
            return 0;  // Empate
        }
    }


    
    public void actualizarPuntos() {
        // Si el partido no ha sido jugado, no hacemos nada
        if (golesLocal == -1 || golesVisitante == -1) {
            return;
        }

        // Si el local gana
        if (golesLocal > golesVisitante) {
            equipoLocal.setPuntos(equipoLocal.getPuntos()+3); // Gana el equipo local
            System.out.println(equipoLocal.getNombre());
        }
        // Si el visitante gana
        else if (golesVisitante > golesLocal) {
            equipoVisitante.setPuntos(equipoVisitante.getPuntos()+3 ); // Gana el equipo visitante
            System.out.println(equipoVisitante.getNombre());
        }
        // Si es empate
        else {
            equipoLocal.setPuntos(equipoLocal.getPuntos() + 1); // Empate, sumamos 1 punto al local
            equipoVisitante.setPuntos(equipoVisitante.getPuntos() + 1); // Empate, sumamos 1 punto al visitante
        }
    }















}