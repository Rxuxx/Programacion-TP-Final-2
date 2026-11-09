package Clases;

import Enumeradores.EEstadoHabitacion;
import Enumeradores.EMotivoNoDisponible;

public class Habitacion {

    private int numero;
    private int piso;
    private double precioBase;
    private EEstadoHabitacion estado;
    private EMotivoNoDisponible motivo;

    public Habitacion(int numero, int piso, double precioBase, EEstadoHabitacion estado, EMotivoNoDisponible motivo) {

        this.numero = numero;
        this.piso = piso;
        this.precioBase = precioBase;
        this.estado = estado;
        this.motivo = motivo;
    }

    public int getNumero() {

        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public EEstadoHabitacion getEstado() {
        return estado;
    }

    public void setEstado(EEstadoHabitacion estado) {
        this.estado = estado;
    }

    public EMotivoNoDisponible getMotivo() {
        return motivo;
    }

    public void setMotivo(EMotivoNoDisponible motivo) {
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "numero=" + numero +
                ", piso=" + piso +
                ", precioBase=" + precioBase +
                ", estado=" + estado +
                ", motivo=" + motivo +
                '}';
    }
}
