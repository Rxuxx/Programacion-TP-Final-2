package Clases;

import java.util.ArrayList;

public class Repositorio <T>{
    private ArrayList<T> lista;

    public Repositorio() {
        this.lista = new ArrayList<>();
    }

    public ArrayList<T> getLista() {
        return lista;
    }

    public void agregar(T elemento) {
        lista.add(elemento);
    }

    public boolean eliminar(T elemento) {
        return lista.remove(elemento);
    }

    public T buscar(T elemento){
        if (lista.contains(elemento)){
            return elemento;
        }
        return null;
    }

    public String listar(){
        StringBuilder sb = new StringBuilder();
        for (T e : lista){
            sb.append(e.toString());
            sb.append('\n');
        }
        return sb.toString();
    }
}
