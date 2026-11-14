package Exceptions;

public class PasajeroNoEncontradoException extends Exception{
    public PasajeroNoEncontradoException(String s) {
        super("No se encontro el pasajero");
    }
}
