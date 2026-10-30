package com.restaurante.fabrica.productos;

import com.restaurante.modelo.Entrada;
import com.restaurante.modelo.LineaCocina;

/** ConcreteProduct: entrada italiana. */
public class Bruschetta extends Entrada {

    public Bruschetta() {
        super("Bruschetta", 12000, LineaCocina.ITALIANA, 10);
    }
}
