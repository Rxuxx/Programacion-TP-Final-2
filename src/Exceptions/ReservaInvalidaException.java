package Exceptions;

public class ReservaInvalidaException extends RuntimeException {
    public ReservaInvalidaException(String message) {
        super("Las fechas de las reservas no coinciden");
    }
}
