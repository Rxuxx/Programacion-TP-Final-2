package Clases;

import Enumeradores.EEstadoHabitacion;
import Enumeradores.EMotivoNoDisponible;
import Interfaces.IEscritorio;

public class Simple extends Habitacion implements IEscritorio {

    private boolean tieneEscritorio;

    public Simple(int numero, int piso, double precioBase, EEstadoHabitacion estado, EMotivoNoDisponible motivo, boolean tieneEscritorio) {
        super(numero, piso, precioBase, estado, motivo);
        this.tieneEscritorio = tieneEscritorio;
    }

    public void setTieneEscritorio(boolean tieneEscritorio) {
        this.tieneEscritorio = tieneEscritorio;
    }

    @Override
    public String toString() {
        return super.toString() + "Simple{" +
                "tieneEscritorio=" + tieneEscritorio +
                '}';
    }

    @Override
    public double calcularPrecioPorNoche() {
        return this.getPrecioBase();
    }

    @Override
    public int getCapacidad() {
        return 1;
    }

    @Override
    public String getTipo() {
        return "Simple";
    }

    @Override
    public boolean tieneEscritorio() {
        return this.tieneEscritorio;
    }
}
