package Clases;

import Enumeradores.EEstadoHabitacion;
import Enumeradores.EMotivoNoDisponible;
import Interfaces.ICamaMatrimonial;

public class Doble extends Habitacion implements ICamaMatrimonial {

    private boolean camaMatrimonial;

    public Doble(int numero, int piso, double precioBase, EEstadoHabitacion estado, EMotivoNoDisponible motivo, boolean camaMatrimonial) {
        super(numero, piso, precioBase, estado, motivo);
        this.camaMatrimonial = camaMatrimonial;
    }

    public void setCamaMatrimonial(boolean camaMatrimonial) {
        this.camaMatrimonial = camaMatrimonial;
    }

    @Override
    public String toString() {
        return super.toString() + "Doble{" +
                "camaMatrimonial=" + camaMatrimonial +
                '}';
    }

    @Override
    public double calcularPrecioPorNoche() {
        return this.getPrecioBase() * 1.5;
    }

    @Override
    public int getCapacidad() {
        return 2;
    }

    @Override
    public String getTipo() {
        return "Doble";
    }

    @Override
    public boolean camaMatrimonial() {
        return this.camaMatrimonial;
    }
}
