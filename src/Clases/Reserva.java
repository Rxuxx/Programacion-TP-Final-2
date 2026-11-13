package Clases;

import Exceptions.ReservaInvalidaException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reserva {

    private int id;
    private Habitacion habitacion;
    private Pasajero pasajero;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;

    public Reserva(Habitacion habitacion, Pasajero pasajero, LocalDate fechaDesde, LocalDate fechaHasta) throws ReservaInvalidaException {
        if (habitacion == null || pasajero == null) {
            throw new ReservaInvalidaException("La reserva necesita una habitación y un pasajero.");
        }
        if (fechaDesde == null || fechaHasta == null) {
            throw new ReservaInvalidaException("Las fechas son obligatorias.");
        }
        if (!fechaHasta.isAfter(fechaDesde)) {
            throw new ReservaInvalidaException("La fecha de salida debe ser posterior a la de entrada.");
        }
        this.habitacion = habitacion;
        this.pasajero = pasajero;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Reserva reserva)) return false;
        return id == reserva.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
