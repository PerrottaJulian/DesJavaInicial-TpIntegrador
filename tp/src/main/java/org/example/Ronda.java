package org.example;

import java.util.ArrayList;

public class Ronda {
    public Ronda(int numero, String nombre){
        this.numero = numero;
        this.nombre = nombre;
    }
    private int numero;
    private String nombre;
    public ArrayList<Partido> partidos = new ArrayList<Partido>();


    public int getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }
}
