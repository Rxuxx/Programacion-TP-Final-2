package Persistencia;

import Clases.Reserva;
import Exceptions.FalloConexionException;

import java.sql.*;

public class ConexionReserva {

    public static void crearTablaReservas() throws FalloConexionException {
        Connection conn = ConexionBDD.conectar();
        try {
            Statement stmt = conn.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS reserva ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "numero_habitacion INTEGER NOT NULL, "
                    + "dni_pasajero TEXT NOT NULL, "
                    + "fecha_desde TEXT NOT NULL, "
                    + "fecha_hasta TEXT NOT NULL);";
            stmt.execute(query);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new FalloConexionException();
        }
    }

    public static void insertarReserva(Reserva r) throws FalloConexionException {
        crearTablaReservas();
        Connection conn = ConexionBDD.conectar();
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO reserva (numero_habitacion, dni_pasajero, fecha_desde, fecha_hasta) "
                            + "VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, r.getHabitacion().getNumero());
            stmt.setString(2, r.getPasajero().getDni());
            stmt.setString(3, r.getFechaDesde().toString());
            stmt.setString(4, r.getFechaHasta().toString());

            stmt.execute();

            ResultSet claves = stmt.getGeneratedKeys();
            if (claves.next()) {
                r.setId(claves.getInt(1));
            }
            claves.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new FalloConexionException();
        }
    }

}
