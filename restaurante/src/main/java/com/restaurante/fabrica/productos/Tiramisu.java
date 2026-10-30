package com.restaurante.fabrica.productos;

import com.restaurante.modelo.LineaCocina;
import com.restaurante.modelo.Postre;

/** ConcreteProduct: postre italiano. */
public class Tiramisu extends Postre {

    public Tiramisu() {
        super("Tiramisu", 14000, LineaCocina.ITALIANA, 10);
    }
}
