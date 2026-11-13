package Exceptions;

import java.sql.SQLException;

public class FalloConexionException extends Exception {
    public FalloConexionException() {
        super("Se produjo un error con la conexion");
    }
}
