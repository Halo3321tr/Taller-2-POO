package com.restaurante.fabrica;

import com.restaurante.modelo.Bebida;
import com.restaurante.modelo.Entrada;
import com.restaurante.modelo.LineaCocina;
import com.restaurante.modelo.PlatoFuerte;
import com.restaurante.modelo.Postre;

/**
 * ABSTRACT FACTORY (rol: AbstractFactory).
 * Declara cómo crear una familia coherente de platos sin nombrar clases concretas.
 */
public interface FabricaCocina {

    LineaCocina getLinea();

    Entrada crearEntrada();

    PlatoFuerte crearPlatoFuerte();

    Bebida crearBebida();

    Postre crearPostre();
}
