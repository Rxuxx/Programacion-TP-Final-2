package Clases;

import Enumeradores.EEstadoHabitacion;
import Enumeradores.EPermiso;
import Exceptions.*;
import Persistencia.ConexionHabitacion;
import Persistencia.ConexionPasajeros;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private Scanner sc = new Scanner(System.in);
    private Repositorio<Usuario> usuarios;
    private Repositorio<Pasajero> pasajero;
    private ServicioHabitaciones servicioHabitaciones;
    private ServicioReservas servicioReservas;

    public Menu(Repositorio<Usuario> usuarios, Repositorio<Pasajero> pasajero,
                ServicioHabitaciones servicioHabitaciones, ServicioReservas servicioReservas) {
        this.usuarios = usuarios;
        this.pasajero = pasajero;
        this.servicioHabitaciones = servicioHabitaciones;
        this.servicioReservas = servicioReservas;
    }


    public void iniciar() throws Exception {
        Usuario usuario = login();
        if (usuario == null) {
            System.out.println("Demasiados intentos fallidos. Programa cerrado.");
            return;
        }

        if (usuario instanceof Administrador) {
            menuAdmin((Administrador) usuario);
        } else {
            menuRecepcionista();
        }
    }

    private Usuario login() {
        int intentos = 0;
        while (intentos < 3) {
            System.out.print("Usuario: ");
            String nombre = sc.nextLine();
            System.out.print("Contraseña: ");
            String clave = sc.nextLine();

            for (Usuario u : usuarios.getLista()) {
                if (u.getUser().equals(nombre) && u.validarPassword(clave)) {
                    System.out.println("Bienvenido " + nombre);
                    return u;
                }
            }
            System.out.println("Usuario o contraseña incorrectos");
            intentos++;
        }
        return null;
    }

    private void menuAdmin(Administrador admin) throws Exception {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENU ADMINISTRADOR ---");
            System.out.println("1. Hacer backup");
            System.out.println("2. Crear usuario");
            System.out.println("3. Asignar permisos");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    admin.hacerBackup(pasajero.getLista(), "backup_pasajeros.txt");
                    break;
                case 2:
                     crearUsuario();
                    break;
                case 3:
                    asignarPermiso();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }

    private void menuRecepcionista() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENU RECEPCIONISTA ---");
            System.out.println("1. Registrar pasajero");
            System.out.println("2. Hacer reserva");
            System.out.println("3. Check-out");
            System.out.println("4. Listar habitaciones disponibles");
            System.out.println("5. Listar habitaciones ocupadas");
            System.out.println("6. Listar habitaciones no disponibles");
            System.out.println("7. Hacer una reserva");
            System.out.println("8. Agregar habitación");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    System.out.println("");
                    break;
                case 2:
                    System.out.println("");
                    break;
                case 3:
                    System.out.println("");
                    break;
                case 4 :
                    mostrarHabitaciones(servicioHabitaciones.listarDisponibles());
                    break;
                case 5: mostrarHabitaciones(servicioHabitaciones.listarOcupadas());
                    break;
                case 6: mostrarHabitaciones(servicioHabitaciones.listarNoDisponibles());
                    break;
                case 7: hacerReserva();
                    break;
                case 8: agregarHabitacion();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }

    }

    private void crearUsuario() {
        System.out.print("Nombre de usuario: ");
        String user = sc.nextLine();
        System.out.print("Contraseña: ");
        String clave = sc.nextLine();

        try {
            Recepcionista nuevo = new Recepcionista(user, clave);
            usuarios.agregar(nuevo);
            System.out.println("Usuario creado correctamente");
        } catch (DatosInvalidosException e) {
            System.out.println(e.getMessage());
        }
    }

    private void asignarPermiso() {
        System.out.print("Indique a que usuario le va a asignar el permiso: ");
        String user = sc.nextLine();

        Usuario encontrado = null;
        for (Usuario u : usuarios.getLista()) {
            if (u.getUser().equals(user)) {
                encontrado = u;
            }
        }

        if (encontrado == null) {
            System.out.println("No existe ese usuario");
            return;
        }

        System.out.println("Permisos disponibles:");
        for (EPermiso p : EPermiso.values()) {
            System.out.println("- " + p);
        }
        System.out.print("Permiso: ");
        String texto = sc.nextLine();

        try {
            EPermiso permiso = EPermiso.valueOf(texto.toUpperCase());
            encontrado.obtenerPermiso(permiso);
            System.out.println("Permiso asignado");
        } catch (IllegalArgumentException e) {
            System.out.println("Ese permiso no existe");
        }
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
        int numero = Integer.parseInt(sc.nextLine());

        Habitacion hab = servicioHabitaciones.buscarPorNumero(numero);
        if (hab == null) {
            System.out.println("No existe esa habitación.");
            return;
        }

        System.out.print("Fecha de entrada (AAAA-MM-DD): ");
        LocalDate desde = LocalDate.parse(sc.nextLine());

        System.out.print("Fecha de salida (AAAA-MM-DD): ");
        LocalDate hasta = LocalDate.parse(sc.nextLine());

        System.out.print("DNI del pasajero: ");
        String dni = sc.nextLine();

        try {
            Pasajero pasajero = ConexionPasajeros.buscarPorDni(dni);
            servicioReservas.reservar(hab, pasajero, desde, hasta);
            System.out.println("Reserva realizada.");
        } catch (PasajeroNoEncontradoException | DatosInvalidosException e) {
            System.out.println(e.getMessage());
        } catch (ReservaInvalidaException | HabitacionNoDisponibleException e) {
            System.out.println("No se pudo reservar: " + e.getMessage());
        } catch (FalloConexionException e) {
            System.out.println("No se pudo guardar la reserva en la base.");
        }
    }

    private void agregarHabitacion() {
        System.out.print("Número de habitación: ");
        int numero = Integer.parseInt(sc.nextLine());

        if (servicioHabitaciones.buscarPorNumero(numero) != null) {
            System.out.println("Ya existe una habitación con ese número.");
            return;
        }

        System.out.print("Piso: ");
        int piso = Integer.parseInt(sc.nextLine());

        System.out.print("Precio base: ");
        double precioBase = Double.parseDouble(sc.nextLine());

        System.out.print("Tipo (1 = Simple, 2 = Doble, 3 = Suite): ");
        int tipo = Integer.parseInt(sc.nextLine());

        Habitacion nueva = null;
        if (tipo == 1) {
            System.out.print("¿Tiene escritorio? (s/n): ");
            boolean escritorio = sc.nextLine().equalsIgnoreCase("s");
            nueva = new Simple(numero, piso, precioBase, EEstadoHabitacion.DISPONIBLE, null, escritorio);
        }

        if (nueva == null) {
            System.out.println("Tipo inválido.");
            return;
        }

        try {
            ConexionHabitacion.insertarHabitacion(nueva);
            servicioHabitaciones.agregarHabitacion(nueva);
            System.out.println("Habitación agregada.");
        } catch (FalloConexionException e) {
            System.out.println("No se pudo guardar la habitación en la base.");
        }
    }


}