package Clases;

import Exceptions.DatosInvalidosException;

public class Recepcionista extends Usuario{
    public Recepcionista(String user, String password) throws DatosInvalidosException {
        super(user, password);
    }
}
