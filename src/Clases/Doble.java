package Clases;

import Enumeradores.EEstadoHabitacion;
import Enumeradores.EMotivoNoDisponible;
import Interfaces.ICamaMatrimoneal;

public class Doble extends Habitacion implements ICamaMatrimoneal {
    public Doble(int numero, int piso, double precioBase, EEstadoHabitacion estado, EMotivoNoDisponible motivo) {
        super(numero, piso, precioBase, estado, motivo);
    }

    @Override
    public boolean camaMatrimonial() {
        return false;
    }
}
