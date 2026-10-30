package com.restaurante.fabrica.productos;

import com.restaurante.modelo.LineaCocina;
import com.restaurante.modelo.PlatoFuerte;

/** ConcreteProduct: plato fuerte italiano. */
public class Lasagna extends PlatoFuerte {

    public Lasagna() {
        super("Lasagna", 35000, LineaCocina.ITALIANA, 10);
    }
}
