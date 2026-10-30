package com.restaurante.modelo;

/** Producto abstracto del patrón Abstract Factory: familia "Postre". */
public abstract class Postre extends Plato {

    protected Postre(String nombre, double precio, LineaCocina linea, int stock) {
        super(nombre, precio, linea, Categoria.POSTRE, stock);
    }

    @Override
    public String describir() {
        return "Postre " + getLinea() + ": " + getNombre();
    }
}
