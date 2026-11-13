package ups.poo.sorteo.app;

import java.util.List;

import ups.poo.sorteo.modelo.Equipo;
import ups.poo.sorteo.controlador.ControladorTorneo;
import ups.poo.sorteo.vista.VistaConsola;

public class Main {
    public static void main(String[] args) {
        VistaConsola vista = new VistaConsola();
        ControladorTorneo controlador = new ControladorTorneo(vista, crearEquipos());
        controlador.iniciar();
    }

    // Equipos de la LigaPro
    private static List<Equipo> crearEquipos() {
        return List.of(
                new Equipo("Aucas", "Quito"),
                new Equipo("Barcelona SC", "Guayaquil"),
                new Equipo("Delfin", "Manta"),
                new Equipo("Deportivo Cuenca", "Cuenca"),
                new Equipo("Emelec", "Guayaquil"),
                new Equipo("Guayaquil City", "Guayaquil"),
                new Equipo("Independiente del Valle", "Sangolqui"),
                new Equipo("LDU Quito", "Quito"),
                new Equipo("Leones", "Atuntaqui"),
                new Equipo("Libertad", "Loja"),
                new Equipo("Macara", "Ambato"),
                new Equipo("Manta FC", "Manta"),
                new Equipo("Mushuc Runa", "Ambato"),
                new Equipo("Orense", "Machala"),
                new Equipo("Tecnico Universitario", "Ambato"),
                new Equipo("Universidad Catolica", "Quito")
            );
    }
}