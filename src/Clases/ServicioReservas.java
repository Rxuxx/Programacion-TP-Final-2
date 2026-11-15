package Clases;

import Enumeradores.EEstadoHabitacion;
import Exceptions.FalloConexionException;
import Exceptions.HabitacionNoDisponibleException;
import Exceptions.ReservaInvalidaException;
import Persistencia.ConexionReserva;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServicioReservas {
    private ArrayList<Reserva> reservas;

    public ServicioReservas() {
        this.reservas = new ArrayList<>();
    }

    public boolean estaDisponible(Habitacion habitacion, LocalDate desde, LocalDate hasta) {

        if (!habitacion.estaDisponible(desde, hasta)) {
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

    public Reserva reservar(Habitacion habitacion, Pasajero pasajero, LocalDate desde, LocalDate hasta) throws ReservaInvalidaException, HabitacionNoDisponibleException, FalloConexionException {

        Reserva nueva = new Reserva(habitacion, pasajero, desde, hasta);

        if (!estaDisponible(habitacion, desde, hasta)) {
            throw new HabitacionNoDisponibleException("La habitacion " + habitacion.getNumero() + " no está disponible en esas fechas.");
        }

        ConexionReserva.insertarReserva(nueva);
        reservas.add(nueva);
        return nueva;
    }

    public void cargarReservas(List<Reserva> cargadas) {
        reservas.addAll(cargadas);
    }

    public Reserva buscarReservaDisponible(Habitacion habitacion, Pasajero pasajero, LocalDate fecha) {
        for (Reserva r : reservas) {
            boolean mismaHabitacion = r.getHabitacion().getNumero() == habitacion.getNumero();
            boolean mismoPasajero = r.getPasajero().equals(pasajero);
            boolean vigente = !fecha.isBefore(r.getFechaDesde()) && fecha.isBefore(r.getFechaHasta());

            if (mismaHabitacion && mismoPasajero && vigente) {
                return r;
            }
        }
        return null;
    }

}
