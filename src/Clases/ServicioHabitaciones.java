package Clases;

import Enumeradores.EEstadoHabitacion;

import java.util.ArrayList;
import java.util.List;

public class ServicioHabitaciones {

    private List<Habitacion> habitaciones;

    public ServicioHabitaciones() {
        this.habitaciones = new ArrayList<>();
    }

    public void agregarHabitacion(Habitacion h) {
        habitaciones.add(h);
    }

    public List<Habitacion> listarDisponibles() {
        List<Habitacion> resultado = new ArrayList<>();
        for (Habitacion h : habitaciones) {
            if (h.getEstado() == EEstadoHabitacion.DISPONIBLE) {
                resultado.add(h);
            }
        }
        return resultado;
    }

    public List<Habitacion> listarNoDisponibles(){
        List<Habitacion> resultadoNoDisponible = new ArrayList<>();

        for (Habitacion h : habitaciones) {
            if (h.getEstado() == EEstadoHabitacion.NO_DISPONIBLE) {
                resultadoNoDisponible.add(h);
            }
        }
        return resultadoNoDisponible;
    }

    public List<Habitacion> listarOcupadas(){
        List<Habitacion> resultadoOcupadas = new ArrayList<>();

        for (Habitacion h : habitaciones) {
            if (h.getEstado() == EEstadoHabitacion.OCUPADA) {
                resultadoOcupadas.add(h);
            }
        }
        return resultadoOcupadas;
    }


}
