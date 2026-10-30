package com.restaurante.fabrica;

import com.restaurante.fabrica.productos.Aranciata;
import com.restaurante.fabrica.productos.Bruschetta;
import com.restaurante.fabrica.productos.Lasagna;
import com.restaurante.fabrica.productos.Tiramisu;
import com.restaurante.modelo.Bebida;
import com.restaurante.modelo.Entrada;
import com.restaurante.modelo.LineaCocina;
import com.restaurante.modelo.PlatoFuerte;
import com.restaurante.modelo.Postre;

/** ABSTRACT FACTORY (rol: ConcreteFactory #2): familia de la cocina italiana. */
public class FabricaItaliana implements FabricaCocina {

    @Override
    public LineaCocina getLinea() {
        return LineaCocina.ITALIANA;
    }

    @Override
    public Entrada crearEntrada() {
        return new Bruschetta();
    }

    @Override
    public PlatoFuerte crearPlatoFuerte() {
        return new Lasagna();
    }

    @Override
    public Bebida crearBebida() {
        return new Aranciata();
    }

    @Override
    public Postre crearPostre() {
        return new Tiramisu();
    }
}
