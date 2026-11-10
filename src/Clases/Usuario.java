package Clases;

import Enumeradores.EPermiso;
import Exceptions.DatosInvalidosException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Usuario {
    private String user;
    private String password;
    private Set<EPermiso> permisos; /// un set para tener asignado mas de un permiso y que no se repitan

    public Usuario(String user, String password) throws DatosInvalidosException {
        if (user == null || user.isBlank()){
            throw new DatosInvalidosException("Usuario invalido, por favor ingresar un nombre correctamente");
        }

        if (password == null || password.isBlank()){
            throw new DatosInvalidosException("Contraseña invalida, por favor intente de nuevo");
        }
        this.user = user;
        this.password = password;
        this.permisos = new HashSet<>();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void obtenerPermiso(EPermiso p){
        permisos.add(p);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals(user, usuario.user);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(user);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "user='" + user + '\'' +
                ", permisos=" + listarPermisos() +
                '}';
    }

    /// metodo para ver que permisos tiene el usuario
    public String listarPermisos(){
        StringBuilder sb = new StringBuilder();
        for (EPermiso p : permisos){
            sb.append(p.name());
            sb.append("\n");
        }

        return sb.toString();
    }

    /// metodo para corroborar un permiso especifico
    public boolean tienePermiso(EPermiso p) {
        return permisos.contains(p);
    }


    /// metodo para que al iniciar sesion con el usuario entre con la contraseña correcta
    public boolean validarPassword(String intento) {
        return password.equals(intento);
    }

}
