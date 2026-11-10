package Clases;

import Exceptions.DatosInvalidosException;

import java.util.Objects;

public class Pasajero {
    /// atributos

    private String nombre;
    private String dni;
    private String origen;
    private String domicilio;

    /// constructor
    public Pasajero(String nombre, String dni, String origen, String domicilio) throws DatosInvalidosException {
        if (nombre == null || nombre.isBlank()){
            throw new DatosInvalidosException("Error, ingrese el nombre correctamente");
        }

        if (dni == null || dni.isBlank()){
            throw new DatosInvalidosException("Error, ingrese el DNI correctamente");
        }

        /// isBlank() se utiliza para que se ingresen caraceter obligatoriamene y no dejar solo espacios en blanco

        this.nombre = nombre;
        this.dni = dni;
        this.origen = origen;
        this.domicilio = domicilio;
    }

    /// getters y setters

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

    /// equals y hashcode de DNI

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
                + "Domicilio de origen: " + domicilio + "\n" +
                "===============================\n";
    }

}
