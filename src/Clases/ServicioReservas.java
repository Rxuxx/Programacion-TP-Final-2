package Clases;

import Enumeradores.EEstadoHabitacion;
import Exceptions.HabitacionNoDisponibleException;
import Exceptions.ReservaInvalidaException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServicioReservas {
    private ArrayList<Reserva> reservas;

    public ServicioReservas() {
        this.reservas = new ArrayList<>();
    }

    public boolean estaDisponible(Habitacion habitacion, LocalDate desde, LocalDate hasta) {

        if (habitacion.getEstado() == EEstadoHabitacion.NO_DISPONIBLE) {
            return false;
        }

        for (Reserva r : reservas) {
            boolean mismaHabitacion = r.getHabitacion().getNumero() == habitacion.getNumero();
            boolean sePisan = desde.isBefore(r.getFechaHasta()) && r.getFechaDesde().isBefore(hasta);

            if (mismaHabitacion && sePisan) {
                return false;
            }
        }
        return true;
    }

    public Reserva reservar(Habitacion habitacion, Pasajero pasajero, LocalDate desde, LocalDate hasta) throws ReservaInvalidaException, HabitacionNoDisponibleException {

            Reserva nueva = new Reserva(habitacion, pasajero, desde, hasta);

            if (!estaDisponible(habitacion, desde, hasta)) {
                throw new HabitacionNoDisponibleException("La habitacion " + habitacion.getNumero() + " no está disponible en esas fechas.");
            }
            reservas.add(nueva);
            return nueva;
    }






}
