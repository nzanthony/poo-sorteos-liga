package ups.poo.sorteo.vista;

import java.util.*;

import ups.poo.sorteo.modelo.Equipo;
import ups.poo.sorteo.modelo.Etapa;
import ups.poo.sorteo.modelo.Partido;

// Maneja entrada y salida 
public class VistaConsola {
    private final Scanner scanner = new Scanner(System.in);

    public void mostrarMenu() {
        System.out.println("\n=== Sorteo LigaPro Ecuador ===");
        System.out.println("1. Ver equipos");
        System.out.println("2. Sortear siguiente etapa");
        System.out.println("3. Simular torneo completo");
        System.out.println("4. Ver resultados");
        System.out.println("0. Salir");
    }

    // Lee un numero entre minimo y maximo y repite hasta recibir uno valido
    public int leerOpcion(int minimo, int maximo){
        while (true) {
            System.out.print("Opcion: ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion >= minimo && opcion <= maximo) {
                    return opcion;
                }
            } catch (NumberFormatException e) {
                // No permite una entrada no numerica
            }
            mostrarError("ingrese un numero entre " + minimo + " y " + maximo);
        }
    }

    public void mostrarEquipos(List<Equipo> equipos) {
        System.out.println("\n=== Equipos participantes ===");
        for (int i = 0; i < equipos.size(); i++) {
            System.out.println((i + 1) + ". " + equipos.get(i));
        }
    }

    public void mostrarPartidos(Etapa etapa, List<Partido> partidos) {
        System.out.println("\n=== " + etapa.getNombre() + " ===");
        for (Partido partido : partidos) {
            String linea = " " + partido;
            if (partido.getGanador() != null) {
                linea += "  -> Ganador: " + partido.getGanador().getNombre();
            }
            System.out.println(linea);
        }
    }

    public void mostrarCampeon(Equipo campeon) {
        System.out.println("\n*** Campeon: " + campeon.getNombre() + " ***");
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String mensaje) {
        System.out.println("Error: " + mensaje);
    }
}
