package Persistencia;

import Clases.Habitacion;
import Clases.Ocupacion;
import Clases.Pasajero;
import Exceptions.DatosInvalidosException;
import Exceptions.FalloConexionException;
import Exceptions.PasajeroNoEncontradoException;

import java.sql.*;
import java.time.LocalDate;

public class ConexionOcupacion {

    public static void crearTablaOcupaciones() throws FalloConexionException {
        Connection conn = ConexionBDD.conectar();
        try {
            Statement stmt = conn.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS ocupacion ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "numero_habitacion INTEGER NOT NULL, "
                    + "dni_pasajero VARCHAR(20) NOT NULL, "
                    + "fecha_ingreso VARCHAR(10) NOT NULL, "
                    + "fecha_salida VARCHAR(10));";
            stmt.execute(query);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new FalloConexionException();
        }
    }

    public static void insertarOcupacion(Ocupacion o) throws FalloConexionException {
        crearTablaOcupaciones();
        Connection conn = ConexionBDD.conectar();
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO ocupacion VALUES (?, ?, ?)");
            stmt.setInt(1, o.getHabitacion().getNumero());
            stmt.setString(2, o.getPasajero().getDni());
            stmt.setString(3, o.getFechaIngreso().toString());
            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new FalloConexionException();
        }
    }

    public static void registrarSalida(int numeroHabitacion, LocalDate fecha) throws FalloConexionException {
        crearTablaOcupaciones();
        Connection conn = ConexionBDD.conectar();
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE ocupacion SET fecha_salida = ? WHERE numero_habitacion = ? AND fecha_salida IS NULL");
            stmt.setString(1, fecha.toString());
            stmt.setInt(2, numeroHabitacion);
            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new FalloConexionException();
        }
    }

    public static Ocupacion buscarHabitacionesDisponibles(Habitacion h) throws FalloConexionException {
        crearTablaOcupaciones();
        Connection conn = ConexionBDD.conectar();
        String dni = null;
        LocalDate ingreso = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM ocupacion WHERE numero_habitacion = ? AND fecha_salida IS NULL");
            stmt.setInt(1, h.getNumero());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                dni = rs.getString("dni_pasajero");
                ingreso = LocalDate.parse(rs.getString("fecha_ingreso"));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new FalloConexionException();
        }

        if (dni == null) {
            return null;
        }

        try {
            Pasajero p = ConexionPasajeros.buscarPorDni(dni);
            return new Ocupacion(p, h, ingreso);
        } catch (PasajeroNoEncontradoException | DatosInvalidosException e) {
            return null;
        }
    }
}