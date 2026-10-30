package com.restaurante.fabrica.productos;

import com.restaurante.modelo.Bebida;
import com.restaurante.modelo.LineaCocina;

/** ConcreteProduct: bebida italiana. */
public class Aranciata extends Bebida {

    public Aranciata() {
        super("Aranciata", 8000, LineaCocina.ITALIANA, 10);
    }
}
