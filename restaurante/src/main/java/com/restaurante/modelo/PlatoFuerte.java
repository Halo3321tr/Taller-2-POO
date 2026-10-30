package com.restaurante.modelo;

/** Producto abstracto del patrón Abstract Factory: familia "Plato fuerte". */
public abstract class PlatoFuerte extends Plato {

    protected PlatoFuerte(String nombre, double precio, LineaCocina linea, int stock) {
        super(nombre, precio, linea, Categoria.PLATO_FUERTE, stock);
    }

    @Override
    public String describir() {
        return "Plato fuerte " + getLinea() + ": " + getNombre();
    }
}
