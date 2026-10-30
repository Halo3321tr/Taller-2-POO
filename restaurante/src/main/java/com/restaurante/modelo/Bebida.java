package com.restaurante.modelo;

/** Producto abstracto del patrón Abstract Factory: familia "Bebida". */
public abstract class Bebida extends Plato {

    protected Bebida(String nombre, double precio, LineaCocina linea, int stock) {
        super(nombre, precio, linea, Categoria.BEBIDA, stock);
    }

    @Override
    public String describir() {
        return "Bebida " + getLinea() + ": " + getNombre();
    }
}
