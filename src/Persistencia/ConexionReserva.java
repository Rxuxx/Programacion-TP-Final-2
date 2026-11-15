package Persistencia;

import Clases.Habitacion;
import Clases.Pasajero;
import Clases.Reserva;
import Exceptions.DatosInvalidosException;
import Exceptions.FalloConexionException;
import Exceptions.PasajeroNoEncontradoException;
import Exceptions.ReservaInvalidaException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConexionReserva {

    public static void crearTablaReservas() throws FalloConexionException {
        Connection conn = ConexionBDD.conectar();
        try {
            Statement stmt = conn.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS reserva ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
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

    public static List<Reserva> obtenerReservas(List<Habitacion> habitaciones) throws FalloConexionException {
        crearTablaReservas();
        List<Reserva> lista = new ArrayList<>();
        Connection conn = ConexionBDD.conectar();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM reserva");

            while (rs.next()) {
                int id = rs.getInt("id");
                int numeroHabitacion = rs.getInt("numero_habitacion");
                String dni = rs.getString("dni_pasajero");
                LocalDate desde = LocalDate.parse(rs.getString("fecha_desde"));
                LocalDate hasta = LocalDate.parse(rs.getString("fecha_hasta"));

                // Buscamos la habitacion que recibimos
                Habitacion hab = null;
                for (Habitacion h : habitaciones) {
                    if (h.getNumero() == numeroHabitacion) {
                        hab = h;
                    }
                }

                try {
                    Pasajero pas = ConexionPasajeros.buscarPorDni(dni);
                    Reserva r = new Reserva(hab, pas, desde, hasta);
                    r.setId(id);
                    lista.add(r);
                } catch (PasajeroNoEncontradoException | DatosInvalidosException | ReservaInvalidaException e) {

                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new FalloConexionException();
        }
        return lista;
    }


}
