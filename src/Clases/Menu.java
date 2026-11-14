package Clases;

import java.util.Scanner;

public class Menu {
    private Scanner sc = new Scanner(System.in);
    private Repositorio<Usuario> usuarios;

    public Menu(Repositorio<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void iniciar() {
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

    private void menuAdmin(Administrador admin) {
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
                    System.out.println("Backup: en construccion");
                    break;
                case 2:
                    System.out.println("Crear usuario: en construccion");
                    break;
                case 3:
                    System.out.println("Asignar permisos: en construccion");
                    break;
                case 0:
                    System.out.println("Hasta luego");
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
                    System.out.println("Registrar pasajero: en construccion");
                    break;
                case 2:
                    System.out.println("Hacer reserva: en construccion");
                    break;
                case 3:
                    System.out.println("Check-out: en construccion");
                    break;
                case 0:
                    System.out.println("Hasta luego");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }
}