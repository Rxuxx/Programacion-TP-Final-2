import Clases.*;

public class Main {
    public static void main(String[] args) throws Exception {

    Repositorio<Usuario> usuariosLista = new Repositorio<>();
    Repositorio<Pasajero> pasajerosLista = new Repositorio<>();

    try{
        Administrador admin = new Administrador("admin", "1234");
        usuariosLista.agregar(admin);

        Pasajero pasajero1 = new Pasajero("Juan", "12345678", "Mar del Plata", "Calle 1");
        Pasajero pasajero2 = new Pasajero("Ana", "87654321", "Balcarce", "Calle 2");

        pasajerosLista.agregar(pasajero1);
        pasajerosLista.agregar(pasajero2);

    } catch (Exception e) {
        throw new RuntimeException(e);
    }

    Menu menu = new Menu(usuariosLista, pasajerosLista);
    menu.iniciar();

    }
}