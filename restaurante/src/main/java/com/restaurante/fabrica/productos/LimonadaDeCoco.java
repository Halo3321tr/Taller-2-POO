package com.restaurante.fabrica.productos;

import com.restaurante.modelo.Bebida;
import com.restaurante.modelo.LineaCocina;

/** ConcreteProduct: bebida colombiana. */
public class LimonadaDeCoco extends Bebida {

    public LimonadaDeCoco() {
        super("Limonada de Coco", 9000, LineaCocina.COLOMBIANA, 10);
    }
}
