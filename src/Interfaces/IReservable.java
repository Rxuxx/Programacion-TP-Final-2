package Interfaces;

import java.time.LocalDate;

public interface IReservable {

    boolean estaDisponible(LocalDate desde, LocalDate hasta);

}
