import Clases.*;
import Exceptions.FalloConexionException;
import Persistencia.ConexionHabitacion;
import Persistencia.ConexionReserva;

public class Main {
    public static void main(String[] args) throws Exception {

    Repositorio<Usuario> usuariosLista = new Repositorio<>();
    Repositorio<Pasajero> pasajerosLista = new Repositorio<>();

    try{
        Administrador admin1 = new Administrador("admin", "1234");
        Recepcionista rec1 = new Recepcionista("nicoRec", "123");
        usuariosLista.agregar(admin1);
        usuariosLista.agregar(rec1);
        Pasajero pasajero1 = new Pasajero("Juan", "12345678", "Mar del Plata", "Calle 1");
        Pasajero pasajero2 = new Pasajero("Ana", "87654321", "Balcarce", "Calle 2");

        pasajerosLista.agregar(pasajero1);
        pasajerosLista.agregar(pasajero2);

    } catch (Exception e) {
        throw new RuntimeException(e);
    }

        ServicioHabitaciones sh = new ServicioHabitaciones();
        ServicioReservas sr = new ServicioReservas();

        try {
            for (Habitacion h : ConexionHabitacion.obtenerHabitaciones()) {
                sh.agregarHabitacion(h);
            }
        } catch (FalloConexionException e) {
            System.out.println("No se pudieron cargar las habitaciones de la base.");
        }

        // Cargamos la base antes de que inicie el programa

        try {
            sr.cargarReservas(ConexionReserva.obtenerReservas(sh.getHabitaciones()));
        } catch (FalloConexionException e) {
            System.out.println("No se pudieron cargar las reservas de la base.");
        }

    Menu menu = new Menu(usuariosLista, pasajerosLista, sh, sr);
    menu.iniciar();

    }
}