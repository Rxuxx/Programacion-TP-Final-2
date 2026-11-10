package Clases;

import Enumeradores.EEstadoHabitacion;
import Enumeradores.EPermiso;
import Exceptions.DatosInvalidosException;

import java.time.LocalDate;

public class Recepcionista extends Usuario{
    public Recepcionista(String user, String password) throws DatosInvalidosException {
        super(user, password);
        obtenerPermiso(EPermiso.CARGAR_PASAJEROS);
        obtenerPermiso(EPermiso.GESTIONAR_RESERVAS);
        obtenerPermiso(EPermiso.HACER_CHECKIN);
        obtenerPermiso(EPermiso.HACER_CHECKOUT);
        obtenerPermiso(EPermiso.VER_ESTADO_HABITACIONES);
    }

    public Pasajero cargarPasajero(String nombre, String dni, String origen, String domicilio)throws DatosInvalidosException{
        return new Pasajero(nombre, dni, origen, domicilio);
    }

    public Ocupacion hacerCheckIn(Pasajero p, Habitacion h, LocalDate fecha) throws DatosInvalidosException{
        if (h.getEstado() == EEstadoHabitacion.NO_DISPONIBLE || h.getEstado() == EEstadoHabitacion.OCUPADA){
            throw new DatosInvalidosException("No es posible asinar esa habitacion, no se encunetra a disposicion");
        }
        h.setEstado(EEstadoHabitacion.OCUPADA);
        return new Ocupacion(p,h,fecha);
    }

    public void hacerCheckOut(Ocupacion o, LocalDate fecha){
        o.registrarSalida(fecha);
        o.getHabitacion().setEstado(EEstadoHabitacion.DISPONIBLE);
    }

}
