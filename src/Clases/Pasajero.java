package Clases;

import java.util.Objects;

public class Pasajero {
    private String nombre;
    private String dni;
    private String origen;
    private String domicilio;

    public Pasajero(String nombre, String dni, String origen, String domicilio) {
        this.nombre = nombre;
        this.dni = dni;
        this.origen = origen;
        this.domicilio = domicilio;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pasajero pasajero)) return false;
        return Objects.equals(dni, pasajero.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }

    @Override
    public String toString() {
        return "Pasajero{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", origen='" + origen + '\'' +
                ", domicilio='" + domicilio + '\'' +
                '}';
    }

    public String generarInforme() {
        return "===== INFORME DE PASAJERO =====\n"
                + "Nombre: " + nombre + "\n"
                + "DNI: " + dni + "\n"
                + "Origen: " + origen + "\n"
                + "Domicilio de origen: " + domicilio;
    }

}
