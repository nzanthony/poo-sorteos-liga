package ups.poo.sorteo.modelo;

// Etapas del torneo
public enum Etapa {
    OCTAVOS("Octavos de final", 16),
    CUARTOS("Cuartos de final", 8),
    SEMIFINALES("Semifinales", 4),
    FINAL("Final", 2);

    private final String nombre;
    private final int cantidadEquipos;

    Etapa(String nombre, int cantidadEquipos) {
        this.nombre = nombre;
        this.cantidadEquipos = cantidadEquipos;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getCantidadEquipos() {
        return cantidadEquipos;
    }

    // Devuelve la etapa que sigue o null si es la final
    public Etapa siguiente() {
        int i = ordinal() + 1;
        return i < values().length ? values()[i] : null;
    }

    // Devuelve la etapa que corresponde a una cantidad de equipos, o null si no existe
    public static Etapa paraCantidad(int cantidad) {
        for (Etapa etapa : values()) {
            if (etapa.cantidadEquipos == cantidad) {
                return etapa;
            }
        }
        return null;
    }
}