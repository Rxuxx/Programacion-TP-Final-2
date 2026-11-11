package Persistencia;

import Clases.Pasajero;
import Exceptions.FalloConexionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionPasajeros {

    public static void crearTablaPasajeros()throws FalloConexionException {
        Connection conn = ConexionBDD.conectar();
        try {
            Statement stmt = conn.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS pasajeros ("
                    + "dni VARCHAR(20) NOT NULL, "
                    + "nombre VARCHAR(100) NOT NULL, "
                    + "origen VARCHAR(100), "
                    + "domicilio VARCHAR(150), "
                    + "PRIMARY KEY (dni));";
            stmt.execute(query);
            stmt.close();
            conn.close();
            ConexionBDD.cerrarConexion();
        } catch (SQLException e) {
            throw new FalloConexionException();
        }
    }

    public static void insertarPasajero(Pasajero p) throws FalloConexionException{
        crearTablaPasajeros();
        Connection conn = ConexionBDD.conectar();
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO pasajeros VALUES (?, ?, ?, ?)");
            stmt.setString(1, p.getDni());
            stmt.setString(2, p.getNombre());
            stmt.setString(3, p.getOrigen());
            stmt.setString(4, p.getDomicilio());

            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new FalloConexionException();
        }
    }

}
