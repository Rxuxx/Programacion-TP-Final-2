package Exceptions;

public class SinMotivoException extends RuntimeException {
    public SinMotivoException(String message) {
        super("Una habitación no disponible necesita un motivo.");
    }
}
