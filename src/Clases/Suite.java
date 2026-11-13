package Clases;

import Enumeradores.EEstadoHabitacion;
import Enumeradores.EMotivoNoDisponible;
import Interfaces.IJacuzzi;

public class Suite extends Habitacion implements IJacuzzi {
   private int cantidadAmbientes;
   private boolean tieneJacuzzi;

    public Suite(int numero, int piso, double precioBase, EEstadoHabitacion estado, EMotivoNoDisponible motivo, int cantidadAmbientes, boolean tieneJacuzzi) {
        super(numero, piso, precioBase, estado, motivo);
        this.cantidadAmbientes = cantidadAmbientes;
        this.tieneJacuzzi = tieneJacuzzi;
    }

    public int getCantidadAmbientes() {
        return this.cantidadAmbientes;
    }

    public void setCantidadAmbientes(int cantidadAmbientes) {
        this.cantidadAmbientes = cantidadAmbientes;
    }

    public void setTieneJacuzzi(boolean tieneJacuzzi) {
        this.tieneJacuzzi = tieneJacuzzi;
    }

    @Override
    public String
    toString() {
        return super.toString() + "Suite{" +
                "cantidadAmbientes=" + cantidadAmbientes +
                ", tieneJacuzzi=" + tieneJacuzzi +
                '}';
    }

    @Override
    public double calcularPrecioPorNoche() {
        return this.getPrecioBase() * 2.5;
    }

    @Override
    public int getCapacidad() {
        return 4;
    }

    @Override
    public String getTipo() {
        return "Suite";
    }

    @Override
    public boolean tieneJacuzzi() {
        return this.tieneJacuzzi;
    }

}