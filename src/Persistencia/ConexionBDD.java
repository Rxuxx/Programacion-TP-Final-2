package Persistencia;

import Exceptions.FalloConexionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBDD {
    private static final String url = "jdbc:mysql://localhost:3306/hotel";
    private static final String user = "root";
    private static final String password = "root777";
    public static Connection conn;

    public static Connection conectar() throws FalloConexionException {

        try{
            conn = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
        System.out.println("ERROR: " + e.getMessage());
        throw new FalloConexionException();
    }
        return conn;
    }

    public static void cerrarConexion()throws FalloConexionException{
        try{
            conn.close();
        } catch (Exception e) {
            throw new FalloConexionException();
        }
    }




}
