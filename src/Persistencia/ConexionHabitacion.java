package Persistencia;

import Clases.Doble;
import Clases.Habitacion;
import Clases.Simple;
import Clases.Suite;
import Enumeradores.EEstadoHabitacion;
import Enumeradores.EMotivoNoDisponible;
import Exceptions.FalloConexionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConexionHabitacion {

    public static void crearTablaHabitaciones() throws FalloConexionException {
        Connection conn = ConexionBDD.conectar();
        try {
            Statement stmt = conn.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS habitacion ("
                    + "numero INTEGER PRIMARY KEY, "
                    + "piso INTEGER NOT NULL, "
                    + "precio_base REAL NOT NULL, "
                    + "estado TEXT NOT NULL, "
                    + "motivo TEXT, "
                    + "tipo TEXT NOT NULL, "
                    + "tiene_escritorio INTEGER, "
                    + "cama_matrimonial INTEGER, "
                    + "tiene_jacuzzi INTEGER, "
                    + "cantidad_ambientes INTEGER);";
            stmt.execute(query);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new FalloConexionException();
        }
    }

    public static void insertarHabitacion(Habitacion h) throws FalloConexionException {
        crearTablaHabitaciones();
        Connection conn = ConexionBDD.conectar();
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO habitacion VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            stmt.setInt(1, h.getNumero());
            stmt.setInt(2, h.getPiso());
            stmt.setDouble(3, h.getPrecioBase());
            stmt.setString(4, h.getEstado().name());
            if (h.getMotivo() != null) {
                stmt.setString(5, h.getMotivo().name());
            } else {
                stmt.setNull(5, Types.VARCHAR);
            }
            stmt.setString(6, h.getTipo());
            stmt.setNull(7, Types.INTEGER);
            stmt.setNull(8, Types.INTEGER);
            stmt.setNull(9, Types.INTEGER);
            stmt.setNull(10, Types.INTEGER);

            if (h instanceof Simple) {
                Simple s = (Simple) h;
                stmt.setInt(7, s.tieneEscritorio() ? 1 : 0);
            }

            if (h instanceof Doble) {
                Doble d = (Doble) h;
                stmt.setInt(8, d.camaMatrimonial() ? 1 : 0);
            }
            if (h instanceof Suite) {
                Suite su = (Suite) h;
                stmt.setInt(9, su.tieneJacuzzi() ? 1 : 0);
                stmt.setInt(10, su.getCantidadAmbientes());
            }

            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new FalloConexionException();
        }
    }

    public static void actualizarEstado(Habitacion h) throws FalloConexionException {
        crearTablaHabitaciones();
        Connection conn = ConexionBDD.conectar();
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE habitacion SET estado = ?, motivo = ? WHERE numero = ?");

            stmt.setString(1, h.getEstado().name());
            if (h.getMotivo() != null) {
                stmt.setString(2, h.getMotivo().name());
            } else {
                stmt.setNull(2, Types.VARCHAR);
            }
            stmt.setInt(3, h.getNumero());

            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new FalloConexionException();
        }
    }

    public static List<Habitacion> obtenerHabitaciones() throws FalloConexionException {
        crearTablaHabitaciones();
        List<Habitacion> lista = new ArrayList<>();
        Connection conn = ConexionBDD.conectar();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM habitacion");

            while (rs.next()) {

                int numero = rs.getInt("numero");
                int piso = rs.getInt("piso");
                double precioBase = rs.getDouble("precio_base");
                EEstadoHabitacion estado = EEstadoHabitacion.valueOf(rs.getString("estado"));

                String motivoTexto = rs.getString("motivo");
                EMotivoNoDisponible motivo = null;
                if (motivoTexto != null) {
                    motivo = EMotivoNoDisponible.valueOf(motivoTexto);
                }

                String tipo = rs.getString("tipo");
                if (tipo.equals("Simple")) {
                    boolean escritorio = rs.getInt("tiene_escritorio") == 1;
                    lista.add(new Simple(numero, piso, precioBase, estado, motivo, escritorio));
                } else if (tipo.equals("Doble")) {
                    boolean cama = rs.getInt("cama_matrimonial") == 1;
                    lista.add(new Doble(numero, piso, precioBase, estado, motivo, cama));
                } else if (tipo.equals("Suite")) {
                    boolean jacuzzi = rs.getInt("tiene_jacuzzi") == 1;
                    int ambientes = rs.getInt("cantidad_ambientes");
                    lista.add(new Suite(numero, piso, precioBase, estado, motivo, ambientes, jacuzzi));
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
