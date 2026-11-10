package Clases;

import java.time.LocalDate;

public class Ocupacion {
    private Pasajero pasajero;
    private Habitacion habitacion;
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;

    public Ocupacion(Pasajero pasajero, Habitacion habitacion, LocalDate fechaIngreso) {
        this.pasajero = pasajero;
        this.habitacion = habitacion;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = null;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void registrarSalida(LocalDate fecha) {
        this.fechaSalida = fecha;
    }


    @Override
    public String toString() {
        return "Ocupacion{" +
                "pasajero=" + pasajero.getNombre() +
                ", habitacion=" + habitacion.getNumero() +
                ", ingreso=" + fechaIngreso +
                ", salida=" + fechaSalida +
                '}';
    }
}