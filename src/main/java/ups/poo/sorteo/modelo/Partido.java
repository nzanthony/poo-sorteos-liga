package ups.poo.sorteo.modelo;

import java.io.Serializable;
import java.util.Random;

// Representa un partido entre un equipo local y un visitante
public class Partido implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Equipo local;
    private final Equipo visitante;
    private Equipo ganador; // Se asigna despues de jugar el partido

    public Partido(Equipo local, Equipo visitante) {
        this.local = local;
        this.visitante = visitante;
    }

    public Equipo getLocal() {
        return local;
    }

    public Equipo getVisitante() {
        return visitante;
    }

    // Retorna null hasta que se llame a definirGanador()
    public Equipo getGanador() {
        return ganador;
    }

    // Elige al ganador al azar
    public void definirGanador(Random random) {
        ganador = random.nextBoolean() ? local : visitante;
    }

    // Retorna el partido a disputarse
    @Override
    public String toString() {
        return local.getNombre() + " vs " + visitante.getNombre();
    }
}