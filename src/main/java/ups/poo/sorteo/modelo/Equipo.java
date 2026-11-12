package ups.poo.sorteo.modelo;

import java.io.Serializable;

// Representa un equipo de la liga
public class Equipo implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String nombre;
    private final String ciudad;

    public Equipo(String nombre, String ciudad) {
        this.nombre = nombre;
        this.ciudad = ciudad;
    }

    // getters de nombre y ciudad
    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    // Retorna el nombre del equipo junto a su ciudad
    @Override
    public String toString() {
        return nombre + " (" + ciudad + ")";
    }
}