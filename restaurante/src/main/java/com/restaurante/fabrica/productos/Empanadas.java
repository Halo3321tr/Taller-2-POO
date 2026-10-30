package com.restaurante.fabrica.productos;

import com.restaurante.modelo.Entrada;
import com.restaurante.modelo.LineaCocina;

/** ConcreteProduct: entrada colombiana. */
public class Empanadas extends Entrada {

    public Empanadas() {
        super("Empanadas", 8000, LineaCocina.COLOMBIANA, 10);
    }
}
