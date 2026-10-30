package com.restaurante.fabrica.productos;

import com.restaurante.modelo.LineaCocina;
import com.restaurante.modelo.PlatoFuerte;

/** ConcreteProduct: plato fuerte colombiano. */
public class BandejaPaisa extends PlatoFuerte {

    public BandejaPaisa() {
        super("Bandeja Paisa", 32000, LineaCocina.COLOMBIANA, 10);
    }
}
