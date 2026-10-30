package com.restaurante.fabrica;

import com.restaurante.fabrica.productos.BandejaPaisa;
import com.restaurante.fabrica.productos.Empanadas;
import com.restaurante.fabrica.productos.LimonadaDeCoco;
import com.restaurante.fabrica.productos.Natilla;
import com.restaurante.modelo.Bebida;
import com.restaurante.modelo.Entrada;
import com.restaurante.modelo.LineaCocina;
import com.restaurante.modelo.PlatoFuerte;
import com.restaurante.modelo.Postre;

/** ABSTRACT FACTORY (rol: ConcreteFactory #1): familia de la cocina colombiana. */
public class FabricaColombiana implements FabricaCocina {

    @Override
    public LineaCocina getLinea() {
        return LineaCocina.COLOMBIANA;
    }

    @Override
    public Entrada crearEntrada() {
        return new Empanadas();
    }

    @Override
    public PlatoFuerte crearPlatoFuerte() {
        return new BandejaPaisa();
    }

    @Override
    public Bebida crearBebida() {
        return new LimonadaDeCoco();
    }

    @Override
    public Postre crearPostre() {
        return new Natilla();
    }
}
