package ups.poo.sorteo.modelo;

import java.io.*;

//Guarda y carga el torneo en un archivo
public class ArchivoTorneo {
    private final String ruta;

    public ArchivoTorneo(String ruta){
        this.ruta = ruta;
    }

    // Escribe el torneo completo en el archivo
    public void guardar(Torneo torneo) throws IOException {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ruta))){
            salida.writeObject(torneo);
        }
    }

    // Reconstruye el torneo a partir del archivo
    public Torneo cargar() throws IOException, ClassNotFoundException {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ruta))) {
            Object objeto = entrada.readObject();
            if (objeto instanceof Torneo torneo) {
                return torneo;
            }
            throw new IOException("el archivo no contiene un torneo");
        }
    }

    public boolean existe() {
        return new File(ruta).exists();
    }

    public String getRuta() {
        return ruta;
    }
}
