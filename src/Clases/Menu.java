package Clases;

import Enumeradores.EPermiso;
import Exceptions.DatosInvalidosException;

import java.util.Scanner;

public class Menu {
    private Scanner sc = new Scanner(System.in);
    private Repositorio<Usuario> usuarios;
    private Repositorio<Pasajero> pasajero;

    public Menu(Repositorio<Usuario> usuarios, Repositorio<Pasajero> pasajero) {
        this.usuarios = usuarios;
        this.pasajero = pasajero;
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

}