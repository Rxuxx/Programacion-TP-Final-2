package Clases;

import Enumeradores.EEstadoHabitacion;
import Enumeradores.EMotivoNoDisponible;
import Interfaces.IEscritorio;

public class Simple extends Habitacion implements IEscritorio {

    public Simple(int numero, int piso, double precioBase, EEstadoHabitacion estado, EMotivoNoDisponible motivo) {
        super(numero, piso, precioBase, estado, motivo);
    }

    @Override
    public boolean tieneEscritorio() {
        return false;
    }
}
