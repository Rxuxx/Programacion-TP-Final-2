package Persistencia;

import Exceptions.FalloConexionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBDD {
    private static final String url = "jdbc:mysql://172.16.1.64:3306/midb";
    private static final String user = "bdd1";
    private static final String password = "bdd1";
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
