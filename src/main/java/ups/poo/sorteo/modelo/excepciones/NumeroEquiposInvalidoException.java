package ups.poo.sorteo.modelo.excepciones;

// Se lanza cuando la cantidad de equipos no es potencia de 2
public class NumeroEquiposInvalidoException extends Exception{
    private static final long serialVersionUID = 1L;

    public NumeroEquiposInvalidoException(String mensaje) {
        super(mensaje);
    }
}