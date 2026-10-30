package com.restaurante.modelo;

/** Persona que entrega los domicilios. */
public class Repartidor {

    private final String nombre;
    private boolean disponible = true;

    public Repartidor(String valorNombre) {
        nombre = valorNombre;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean valor) {
        disponible = valor;
    }

    @Override
    public String toString() {
        return nombre + (disponible ? " (disponible)" : " (en ruta)");
    }
}
