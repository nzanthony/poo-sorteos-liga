package ups.poo.sorteo.modelo;

import java.io.Serializable;
import java.util.*;

import ups.poo.sorteo.modelo.excepciones.NumeroEquiposInvalidoException;
import ups.poo.sorteo.modelo.excepciones.SorteoInvalidoException;

// Clase serializada: contiene el estado completo del torneo
public class Torneo implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String nombre;
    private final List<Equipo> equipos;
    private final Map<Etapa, List<Partido>> partidosPorEtapa;
    private final Random random;
    private Etapa etapaActual;
    private Equipo campeon;

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
            throw new NumeroEquiposInvalidoException(
                    "Se recibieron " + cantidad + " equipos. La cantidad de equipos debe ser 2, 4, 8 o 16");
        }
    }

    // Sortea los partidos de una etapa a partir de sus participantes
    public List<Partido> sortearEtapa(List<Equipo> participantes)
            throws NumeroEquiposInvalidoException, SorteoInvalidoException {
        validarCantidad(participantes.size());
        if (new HashSet<>(participantes).size() != participantes.size()) {
            throw new SorteoInvalidoException("Hay equipos repetidos en la etapa");
        }
        List<Equipo> mezclados = new ArrayList<>(participantes);
        Collections.shuffle(mezclados, random);
        return emparejar(mezclados, new ArrayList<>());
    }

    // Empareja el primer equipo con un rival al azar y sortea el resto
    private List<Partido> emparejar(List<Equipo> disponibles, List<Partido> partidos) throws SorteoInvalidoException {
        if (disponibles.isEmpty()) { // Caso base: todos los equipos tienen partido
            return partidos;
        }
        if (disponibles.size() == 1) { // Condicion imposible: un equipo sin rival
            throw new SorteoInvalidoException("El equipo " + disponibles.get(0).getNombre() + " quedo sin rival");
        }

        Equipo local = disponibles.get(0);
        int indiceRival = 1 + random.nextInt(disponibles.size() - 1);
        Equipo visitante = disponibles.get(indiceRival);
        partidos.add(new Partido(local, visitante));

        // Se quitan ambos equipos para que no repitan partido en la etapa
        List<Equipo> restantes = new ArrayList<>(disponibles);
        restantes.remove(indiceRival);
        restantes.remove(0);

        // Llamada recursiva con dos equipos menos
        return emparejar(restantes, partidos);
    }

    // Avanza el torneo un paso
    public void avanzarEtapa() throws NumeroEquiposInvalidoException, SorteoInvalidoException {
        if (estaFinalizado()){
            return;
        }
        // Se sortea la etapa inicial con todos los equipos
        if (etapaActual == null) {
            etapaActual = Etapa.paraCantidad(equipos.size());
            partidosPorEtapa.put(etapaActual, sortearEtapa(equipos));
            return;
        }
        List<Equipo> ganadores = jugarEtapa(etapaActual);
        if (etapaActual == Etapa.FINAL){
            campeon = ganadores.get(0);
            return;
        }
        etapaActual = etapaActual.siguiente();
        partidosPorEtapa.put(etapaActual, sortearEtapa(ganadores));
    }

    //Simula el torneo completo recursivamente
    public void simularTorneo() throws NumeroEquiposInvalidoException, SorteoInvalidoException {
        if (estaFinalizado()) {
            return; // Si ya hay campeon lo retorna
        }
        avanzarEtapa();
        simularTorneo();
    }

    // Juega los partidos de una etapa y devuelve los ganadores
    private List<Equipo> jugarEtapa(Etapa etapa) throws SorteoInvalidoException {
        List<Partido> partidos = partidosPorEtapa.get(etapa);
        if (partidos == null || partidos.isEmpty()){
            throw new SorteoInvalidoException("No hay partidos sorteados para " + etapa.getNombre());
        }
        List<Equipo> ganadores = new ArrayList<>();
        for (Partido partido : partidos) {
            partido.definirGanador(random);
            if (partido.getGanador() == null) {
                throw new SorteoInvalidoException("El partido " + partido + " no tiene ganador");
            }
            ganadores.add(partido.getGanador());
        }
        return ganadores;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public List<Equipo> getEquipos() {
        return List.copyOf(equipos);
    }

    public Etapa getEtapaActual() {
        return etapaActual;
    }

    public Equipo getCampeon() {
        return campeon;
    }

    public boolean estaFinalizado() {
        return campeon != null;
    }

    //Devuelve los partidos de una etapa o una lista vacia si aun no se sortea
    public List<Partido> getPartidos(Etapa etapa) {
        List<Partido> partidos = partidosPorEtapa.get(etapa);
        return partidos == null ? List.of() : List.copyOf(partidos);
    }
}