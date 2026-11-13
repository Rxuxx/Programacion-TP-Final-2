package Exceptions;

public class HabitacionNoDisponibleException extends RuntimeException {
    public HabitacionNoDisponibleException(String message) {
        super("La habitacion no esta disponible");
    }
}
