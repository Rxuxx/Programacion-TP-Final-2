package Clases;

import Enumeradores.EEstadoHabitacion;
import Enumeradores.EMotivoNoDisponible;
import Interfaces.IJacuzzi;

public class Suite extends Habitacion implements IJacuzzi {
   private int cantidadAmbientes;

    public Suite(int numero, int piso, double precioBase, EEstadoHabitacion estado, EMotivoNoDisponible motivo, int cantidadAmbientes) {
        super(numero, piso, precioBase, estado, motivo);
        this.cantidadAmbientes = cantidadAmbientes;
    }

    public int getCantidadAmbientes() {
        return cantidadAmbientes;
    }

    public void setCantidadAmbientes(int cantidadAmbientes) {
        this.cantidadAmbientes = cantidadAmbientes;
    }

    @Override
    public String toString() {
        return super.toString() + "Suite{" +
                "cantidadAmbientes=" + cantidadAmbientes +
                '}';
    }

    @Override
    public boolean tieneJacuzzi() {
        return false;
    }
}
