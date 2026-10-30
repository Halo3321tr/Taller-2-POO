package com.restaurante.fabrica.productos;

import com.restaurante.modelo.LineaCocina;
import com.restaurante.modelo.Postre;

/** ConcreteProduct: postre colombiano. */
public class Natilla extends Postre {

    public Natilla() {
        super("Natilla", 7000, LineaCocina.COLOMBIANA, 10);
    }
}
