package Clases;

import Exceptions.HabitacionNoDisponibleException;
import Exceptions.ReservaInvalidaException;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuRecepcionista {
    private ServicioHabitaciones servicioHabitaciones;
    private ServicioReservas servicioReservas;
    private Scanner scanner = new Scanner(System.in);

    public MenuRecepcionista(ServicioHabitaciones sh, ServicioReservas sr) {
        this.servicioHabitaciones = sh;
        this.servicioReservas = sr;
    }

    public void mostrar() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ RECEPCIONISTA ---");
            System.out.println("1. Listar habitaciones disponibles");
            System.out.println("2. Listar habitaciones ocupadas");
            System.out.println("3. Listar habitaciones no disponibles");
            System.out.println("4. Hacer una reserva");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = Integer.parseInt(scanner.nextLine());;

            switch (opcion) {
                case 1 -> mostrarHabitaciones(servicioHabitaciones.listarDisponibles());
                case 2 -> mostrarHabitaciones(servicioHabitaciones.listarOcupadas());
                case 3 -> mostrarHabitaciones(servicioHabitaciones.listarNoDisponibles());
                case 4 -> hacerReserva();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }

    private void mostrarHabitaciones(List<Habitacion> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay habitaciones en esta categoría.");
            return;
        }
        for (Habitacion h : lista) {
            System.out.println("Habitación " + h.getNumero()
                    + " | " + h.getTipo()
                    + " | Piso " + h.getPiso()
                    + " | $" + h.calcularPrecioPorNoche() + " por noche"
                    + (h.getMotivo() != null ? " | Motivo: " + h.getMotivo() : ""));
        }
    }

    private void hacerReserva() {
        System.out.print("Número de habitación: ");
        int numero = Integer.parseInt(scanner.nextLine());

        Habitacion hab = servicioHabitaciones.buscarPorNumero(numero);
        if (hab == null) {
            System.out.println("No existe esa habitación.");
            return;
        }

        System.out.print("Fecha de entrada (AAAA-MM-DD): ");
        LocalDate desde = LocalDate.parse(scanner.nextLine());

        System.out.print("Fecha de salida (AAAA-MM-DD): ");
        LocalDate hasta = LocalDate.parse(scanner.nextLine());

        Pasajero pasajero = null;

        try {
            servicioReservas.reservar(hab, pasajero, desde, hasta);
            System.out.println("Reserva realizada.");
        } catch (ReservaInvalidaException | HabitacionNoDisponibleException e) {
            System.out.println("No se pudo reservar: " + e.getMessage());
        }
    }

}
