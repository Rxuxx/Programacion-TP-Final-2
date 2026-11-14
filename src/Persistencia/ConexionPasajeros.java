package Persistencia;

import Clases.Pasajero;
import Exceptions.DatosInvalidosException;
import Exceptions.FalloConexionException;
import Exceptions.PasajeroNoEncontradoException;

import java.sql.*;

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

    public static Pasajero buscarPorDni(String dni)
            throws FalloConexionException, PasajeroNoEncontradoException, DatosInvalidosException {
        crearTablaPasajeros();
        Connection conn = ConexionBDD.conectar();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM pasajeros WHERE dni = ?");
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();

            Pasajero p = null;
            if (rs.next()) {
                p = new Pasajero(rs.getString("nombre"), rs.getString("dni"),
                        rs.getString("origen"), rs.getString("domicilio"));
            }
            rs.close();
            stmt.close();
            conn.close();

            if (p == null) {
                throw new PasajeroNoEncontradoException("No existe un pasajero con DNI " + dni);
            }
            return p;
        } catch (SQLException e) {
            throw new FalloConexionException();
        }
    }
}
