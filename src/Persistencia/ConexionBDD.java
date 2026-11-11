package Persistencia;

import Exceptions.FalloConexionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBDD {
    private static final String url = "";
    private static final String user = "";
    private static final String password = "";
    public static Connection conn;

    public static Connection conectar() throws FalloConexionException {

        try{
            conn = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
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
