package clases;

import java.util.ArrayList;
import java.util.List;

public class Gestion {
    // Lista para almacenar los nombres de los usuarioss
    private static List<String> nombresUsuarios = new ArrayList<>();

    // Método para agregar un nombre de usuario a la lista
    public static void agregarUsuario(String nombre) {
        nombresUsuarios.add(nombre);
    }

    // Método para obtener el nombre del último usuario (último que inició sesión)
    public static String obtenerUltimoUsuario() {
        if (!nombresUsuarios.isEmpty()) {
            return nombresUsuarios.get(nombresUsuarios.size() - 1);
        }
        return null;  // Retorna null si no hay usuarios en la lista
    }

    // Método para obtener todos los nombres de los usuarios
    public static List<String> obtenerUsuarios() {
        return nombresUsuarios;
    }
}
