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
            opcion = vista.leerOpcion(0, 4);
            switch (opcion) {
                case 1 -> vista.mostrarEquipos(torneo.getEquipos());
                case 2 -> sortearSiguienteEtapa();
                case 3 -> simularTorneo();
                case 4 -> verResultados();
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

    private void sortearSiguienteEtapa() {
        if (torneo.estaFinalizado()) {
            vista.mostrarMensaje("El torneo ya termino");
            vista.mostrarCampeon(torneo.getCampeon());
            return;
        }
        try {
            Etapa etapaJugada = torneo.getEtapaActual();
            torneo.avanzarEtapa();
            if (etapaJugada != null) {
                vista.mostrarPartidos(etapaJugada, torneo.getPartidos(etapaJugada));
            }
            if (torneo.estaFinalizado()) {
                vista.mostrarCampeon(torneo.getCampeon());
            } else {
                vista.mostrarPartidos(torneo.getEtapaActual(), torneo.getPartidos(torneo.getEtapaActual()));
            }
        } catch (NumeroEquiposInvalidoException | SorteoInvalidoException e) {
            vista.mostrarError(e.getMessage());
        }
    }

    private void simularTorneo() {
        try {
            torneo.simularTorneo();
            verResultados();
        } catch (NumeroEquiposInvalidoException | SorteoInvalidoException e){
            vista.mostrarError(e.getMessage());
        }
    }

    // Muestra todas las etapas sorteadas hata el momento
    private void verResultados() {
        if (torneo.getEtapaActual() == null) {
            vista.mostrarMensaje("Aun no se ha sorteado ninguna etapa");
            return;
        }
        for (Etapa etapa : Etapa.values()){
            List<Partido> partidos = torneo.getPartidos(etapa);
            if (!partidos.isEmpty()) {
                vista.mostrarPartidos(etapa, partidos);
            }
        }
        if (torneo.estaFinalizado()) {
            vista.mostrarCampeon(torneo.getCampeon());
        }
    }
}