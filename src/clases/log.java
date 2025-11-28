package clases;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class log {
    public static void add(String log, int severidad) {
        String formattedDate;
        LocalDateTime d = LocalDateTime.now();
        formattedDate = d.getDayOfMonth() + "/" + d.getMonthValue() + "/" + d.getYear() + " A las " + d.getHour() + ":" + d.getMinute() + ":" + d.getSecond();


        log = formattedDate + ": " + log;
        try (FileWriter fichero = new FileWriter("consoleLog.log", true); // Agregar "true" para crear el archivo si no existe
             BufferedWriter bw = new BufferedWriter(fichero)) {

            if (severidad == 2) {
                log = "ERROR: " + log;
            } else if (severidad == 1) {
                log = "WARNING: " + log;
            } else {
                log = "INFO: " + log;
            }

            System.out.println(log + ".");
            bw.write(log + ".");
            bw.newLine();

        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void clear() {
        try (FileWriter writer = new FileWriter("consoleLog.log")) {
            // Si abres un archivo sin hacer nada, borra todo lo que tiene
        } catch (IOException e) {
            System.err.println("Error al limpiar el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
