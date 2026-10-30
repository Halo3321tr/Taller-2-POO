package com.restaurante.modelo;

/** Producto abstracto del patrón Abstract Factory: familia "Entrada". */
public abstract class Entrada extends Plato {

    protected Entrada(String nombre, double precio, LineaCocina linea, int stock) {
        super(nombre, precio, linea, Categoria.ENTRADA, stock);
    }

    @Override
    public String describir() {
        return "Entrada " + getLinea() + ": " + getNombre();
    }
}
