package Clases;

import Enumeradores.EEstadoHabitacion;
import Enumeradores.EMotivoNoDisponible;
import Enumeradores.EPermiso;
import Exceptions.*;
import Persistencia.ConexionHabitacion;
import Persistencia.ConexionOcupacion;
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
            menuRecepcionista((Recepcionista) usuario);
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

    private void menuRecepcionista(Recepcionista rec) {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENU RECEPCIONISTA ---");
            System.out.println("1. Registrar pasajero");
            System.out.println("2. Hacer reserva");
            System.out.println("3. Check-in");
            System.out.println("4. Check-out");
            System.out.println("5. Listar habitaciones disponibles");
            System.out.println("6. Listar habitaciones ocupadas");
            System.out.println("7. Listar habitaciones no disponibles");
            System.out.println("8. Agregar habitación");
            System.out.println("9. Marcar habitación como no disponible");
            System.out.println("10. Marcar habitación como disponible");
            System.out.println("11. Consultar disponibilidad por período");
            System.out.println("12. Informe de pasajero");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    registrarPasajero();
                    break;
                case 2:
                    hacerReserva();
                    break;
                case 3:
                    hacerCheckIn(rec);
                    break;
                case 4:
                    hacerCheckOut(rec);
                    break;
                case 5:
                    mostrarHabitaciones(servicioHabitaciones.listarDisponibles());
                    break;
                case 6: mostrarOcupadas();
                    break;
                case 7: mostrarHabitaciones(servicioHabitaciones.listarNoDisponibles());
                    break;
                case 8: agregarHabitacion();
                    break;
                case 9: marcarNoDisponible();
                    break;
                case 10: marcarDisponible();
                    break;
                case 11: consultarDisponibilidad();
                    break;
                case 12: verInformePasajero();
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
            Pasajero pas = ConexionPasajeros.buscarPorDni(dni);
            servicioReservas.reservar(hab, pas, desde, hasta);
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
        } else if (tipo == 2) {
            System.out.print("¿Tiene cama matrimonial? (s/n): ");
            boolean cama = sc.nextLine().equalsIgnoreCase("s");
            nueva = new Doble(numero, piso, precioBase, EEstadoHabitacion.DISPONIBLE, null, cama);
        }
        else if (tipo == 3) {
            System.out.print("¿Tiene jacuzzi? (s/n): ");
            boolean jacuzzi = sc.nextLine().equalsIgnoreCase("s");
            System.out.print("Cantidad de ambientes: ");
            int ambientes = Integer.parseInt(sc.nextLine());
            nueva = new Suite(numero, piso, precioBase, EEstadoHabitacion.DISPONIBLE, null, ambientes, jacuzzi);
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

    private void registrarPasajero() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("DNI: ");
        String dni = sc.nextLine();
        System.out.print("Origen: ");
        String origen = sc.nextLine();
        System.out.print("Domicilio de origen: ");
        String domicilio = sc.nextLine();

        try {
            Pasajero nuevo = new Pasajero(nombre, dni, origen, domicilio);

            try {
                ConexionPasajeros.buscarPorDni(dni);
                System.out.println("Ya existe un pasajero con ese DNI.");
                return;
            } catch (PasajeroNoEncontradoException e) {
            }

            ConexionPasajeros.insertarPasajero(nuevo);
            pasajero.agregar(nuevo);
            System.out.println("Pasajero registrado.");
        } catch (DatosInvalidosException e) {
            System.out.println(e.getMessage());
        } catch (FalloConexionException e) {
            System.out.println("No se pudo guardar el pasajero en la base.");
        }
    }

    private void hacerCheckIn(Recepcionista rec) {
        System.out.print("Número de habitación: ");
        int numero = Integer.parseInt(sc.nextLine());

        Habitacion hab = servicioHabitaciones.buscarPorNumero(numero);
        if (hab == null) {
            System.out.println("No existe esa habitación.");
            return;
        }

        System.out.print("DNI del pasajero: ");
        String dni = sc.nextLine();

        try {
            Pasajero pas = ConexionPasajeros.buscarPorDni(dni);
            LocalDate hoy = LocalDate.now();

            Reserva reserva = servicioReservas.buscarReservaDisponible(hab, pas, hoy);
            if (reserva == null && !servicioReservas.estaDisponible(hab, hoy, hoy.plusDays(1))) {
                System.out.println("La habitación no está disponible hoy y el pasajero no tiene reserva.");
                return;
            }

            Ocupacion ocupacion = rec.hacerCheckIn(pas, hab, hoy);
            ConexionHabitacion.actualizarEstado(hab);
            ConexionOcupacion.insertarOcupacion(ocupacion);
            System.out.println("Check-in realizado.");
        } catch (PasajeroNoEncontradoException | DatosInvalidosException e) {
            System.out.println(e.getMessage());
        } catch (FalloConexionException e) {
        e.printStackTrace();
        System.out.println("No se pudo guardar el check-in en la base.");
    }
    }

    private void hacerCheckOut(Recepcionista rec) {
        System.out.print("Número de habitación: ");
        int numero = Integer.parseInt(sc.nextLine());

        Habitacion hab = servicioHabitaciones.buscarPorNumero(numero);
        if (hab == null) {
            System.out.println("No existe esa habitación.");
            return;
        }

        try {
            Ocupacion ocupacion = ConexionOcupacion.buscarHabitacionesDisponibles(hab);
            if (ocupacion == null) {
                System.out.println("Esa habitación no tiene una ocupación en curso.");
                return;
            }

            LocalDate hoy = LocalDate.now();
            rec.hacerCheckOut(ocupacion, hoy);
            ConexionHabitacion.actualizarEstado(hab);
            ConexionOcupacion.registrarSalida(numero, hoy);
            System.out.println("Check-out realizado. Pasajero: " + ocupacion.getPasajero().getNombre());
        } catch (FalloConexionException e) {
            System.out.println("No se pudo guardar el check-out en la base.");
        }
    }

    private void mostrarOcupadas() {
        List<Habitacion> ocupadas = servicioHabitaciones.listarOcupadas();
        if (ocupadas.isEmpty()) {
            System.out.println("No hay habitaciones ocupadas.");
            return;
        }

        for (Habitacion h : ocupadas) {
            System.out.println("Habitación " + h.getNumero() + " | " + h.getTipo()
                    + " | Piso " + h.getPiso());
            try {
                Ocupacion o = ConexionOcupacion.buscarHabitacionesDisponibles(h);
                if (o != null) {
                    Pasajero p = o.getPasajero();
                    System.out.println("   Ocupante: " + p.getNombre()
                            + " | DNI " + p.getDni()
                            + " | Origen: " + p.getOrigen()
                            + " | Domicilio: " + p.getDomicilio()
                            + " | Ingreso: " + o.getFechaIngreso());
                } else {
                    System.out.println("   (sin ocupación registrada)");
                }
            } catch (FalloConexionException e) {
                System.out.println("   No se pudo leer el ocupante de la base.");
            }
        }
    }

    private void marcarNoDisponible() {
        System.out.print("Número de habitación: ");
        int numero = Integer.parseInt(sc.nextLine());

        Habitacion hab = servicioHabitaciones.buscarPorNumero(numero);
        if (hab == null) {
            System.out.println("No existe esa habitación.");
            return;
        }
        if (hab.getEstado() == EEstadoHabitacion.OCUPADA) {
            System.out.println("La habitación está ocupada: primero hay que hacer el check-out.");
            return;
        }

        System.out.println("Motivos disponibles:");
        for (EMotivoNoDisponible m : EMotivoNoDisponible.values()) {
            System.out.println("- " + m);
        }
        System.out.print("Motivo: ");
        String texto = sc.nextLine();

        try {
            EMotivoNoDisponible motivo = EMotivoNoDisponible.valueOf(texto.toUpperCase());
            hab.marcarNoDisponible(motivo);
            ConexionHabitacion.actualizarEstado(hab);
            System.out.println("Habitación marcada como no disponible.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ese motivo no existe.");
        } catch (SinMotivoException e) {
            System.out.println(e.getMessage());
        } catch (FalloConexionException e) {
            System.out.println("No se pudo guardar el cambio en la base.");
        }
    }

    private void marcarDisponible() {
        System.out.print("Número de habitación: ");
        int numero = Integer.parseInt(sc.nextLine());

        Habitacion hab = servicioHabitaciones.buscarPorNumero(numero);
        if (hab == null) {
            System.out.println("No existe esa habitación.");
            return;
        }
        if (hab.getEstado() != EEstadoHabitacion.NO_DISPONIBLE) {
            System.out.println("Esa habitación no figura como no disponible.");
            return;
        }

        try {
            hab.marcarDisponible();
            ConexionHabitacion.actualizarEstado(hab);
            System.out.println("Habitación disponible nuevamente.");
        } catch (FalloConexionException e) {
            System.out.println("No se pudo guardar el cambio en la base.");
        }
    }

    private void consultarDisponibilidad() {
        System.out.print("Número de habitación: ");
        int numero = Integer.parseInt(sc.nextLine());

        Habitacion hab = servicioHabitaciones.buscarPorNumero(numero);
        if (hab == null) {
            System.out.println("No existe esa habitación.");
            return;
        }

        System.out.print("Fecha desde (AAAA-MM-DD): ");
        LocalDate desde = LocalDate.parse(sc.nextLine());
        System.out.print("Fecha hasta (AAAA-MM-DD): ");
        LocalDate hasta = LocalDate.parse(sc.nextLine());

        if (!hasta.isAfter(desde)) {
            System.out.println("La fecha hasta debe ser posterior a la fecha desde.");
            return;
        }

        if (hab.getEstado() == EEstadoHabitacion.NO_DISPONIBLE) {
            System.out.println("No se puede: la habitación no está disponible (motivo: " + hab.getMotivo() + ").");
        } else if (hab.getEstado() == EEstadoHabitacion.OCUPADA && !desde.isAfter(LocalDate.now())) {
            System.out.println("No se puede: la habitación está ocupada actualmente.");
        } else if (!servicioReservas.estaDisponible(hab, desde, hasta)) {
            System.out.println("No se puede: ya hay una reserva que se superpone con ese período.");
        } else {
            System.out.println("Sí, la habitación " + numero + " se puede ocupar en ese período.");
        }
    }

    private void verInformePasajero() {
        System.out.print("DNI del pasajero: ");
        String dni = sc.nextLine();

        try {
            Pasajero p = ConexionPasajeros.buscarPorDni(dni);
            System.out.println(p.generarInforme());
        } catch (PasajeroNoEncontradoException | DatosInvalidosException e) {
            System.out.println(e.getMessage());
        } catch (FalloConexionException e) {
            System.out.println("No se pudo leer el pasajero de la base.");
        }
    }

}