package Clases;

import Enums.EPermiso;
import Exceptions.DatosInvalidosException;

public class Administrador extends Usuario{

    public Administrador(String user, String password) throws DatosInvalidosException {
        super(user, password);
        obtenerPermiso(EPermiso.HACER_BACKUP);
        obtenerPermiso(EPermiso.CREAR_USUARIO);
        obtenerPermiso(EPermiso.CARGAR_PASAJEROS);
        obtenerPermiso(EPermiso.GESTIONAR_RESERVAS);
        obtenerPermiso(EPermiso.HACER_CHECKIN);
        obtenerPermiso(EPermiso.HACER_CHECKOUT);
        obtenerPermiso(EPermiso.VER_ESTADO_HABITACIONES);
    }

    public void asignarPermiso(Usuario u, EPermiso permiso){
        u.obtenerPermiso(permiso);
    }

    public Usuario crearUsuario(String user, String password) throws DatosInvalidosException {
            Recepcionista nuevo = new Recepcionista(user, password);
            return nuevo;
    }



}
