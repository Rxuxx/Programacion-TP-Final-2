package Clases;

import  Enumeradores.EPermiso;
import Exceptions.DatosInvalidosException;

public class Administrador extends Usuario{

    public Administrador(String user, String password) throws DatosInvalidosException {
        super(user, password);
        obtenerPermiso(EPermiso.HACER_BACKUP);
        obtenerPermiso(EPermiso.CREAR_USUARIO);
    }


    /// metodo para que el administrador le asigne un permiso a cualquier usuario
    public void asignarPermiso(Usuario u, EPermiso permiso) {
        u.obtenerPermiso(permiso);
    }

    public Usuario crearUsuario(String user, String password) throws DatosInvalidosException {
            Recepcionista nuevo = new Recepcionista(user, password);
            return nuevo;
    }



}
