package ups.poo.sorteo.controlador;

import java.util.List;

import ups.poo.sorteo.modelo.*;
import ups.poo.sorteo.modelo.excepciones.*;
import ups.poo.sorteo.vista.VistaConsola;

public class ControladorTorneo {
    private final VistaConsola vista;
    private final List<Equipo> equiposIniciales;
    private Torneo torneo;

    public ControladorTorneo(VistaConsola vista, List<Equipo> equiposIniciales) {
        this.vista = vista;
        this.equiposIniciales = equiposIniciales;

    }

    public void iniciar() {
        if (!crearTorneo()){
            return;
        }
        int opcion;
        do {
            vista.mostrarMenu();
            opcion = vista.leerOpcion(0, 2);
            switch (opcion) {
                case 1 -> vista.mostrarEquipos(torneo.getEquipos());
                case 2 -> sortearOctavos();
                case 0 -> vista.mostrarMensaje("Hasta luego");
            }
        }while (opcion != 0);
    }

    // Crea el torneo con los equipos iniciales
    private boolean crearTorneo() {
        try {
            torneo = new Torneo("LigaPro Ecuador 2026", equiposIniciales);
            return true;
        } catch (NumeroEquiposInvalidoException e){
            vista.mostrarError(e.getMessage());
            return false; // Retorna false si la cantidad no es valida
        }
    }

    private void sortearOctavos() {
        try {
            List<Partido> partidos = torneo.sortearEtapa(torneo.getEquipos());
            vista.mostrarPartidos(Etapa.OCTAVOS, partidos);
        } catch(NumeroEquiposInvalidoException | SorteoInvalidoException e) {
            vista.mostrarError(e.getMessage());
        }
    }
}