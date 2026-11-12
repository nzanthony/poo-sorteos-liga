package ups.poo.sorteo.modelo;

import java.io.Serializable;
import java.util.*;

import ups.poo.sorteo.modelo.excepciones.NumeroEquiposInvalidoException;

// Clase serializada: contiene el estado completo del torneo
public class Torneo implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String nombre;
    private final List<Equipo> equipos;
    private final Map<Etapa, List<Partido>> partidosPorEtapa;
    private final Random random;
    
    public Torneo(String nombre, List<Equipo> equipos) throws NumeroEquiposInvalidoException {
        validarCantidad(equipos.size());
        this.nombre = nombre;
        this.equipos = new ArrayList<>(equipos);
        this.partidosPorEtapa = new EnumMap<>(Etapa.class);
        this.random = new Random();
    }

    // Valida que la cantidad de equipos sea potencia de 2 entre 2 y 16
    private void validarCantidad(int cantidad) throws NumeroEquiposInvalidoException {
        boolean potenciaDeDos = cantidad >= 2 && (cantidad & (cantidad - 1)) == 0;
        if (!potenciaDeDos || Etapa.paraCantidad(cantidad) == null) {
            throw new NumeroEquiposInvalidoException("Se recibieron "+ cantidad +" equipos. La cantidad de equipos debe ser 2, 4, 8 o 16");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public List<Equipo> getEquipos() {
        return List.copyOf(equipos);
    }
}