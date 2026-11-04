package com.restaurante.excepcion;

/** Se pide un plato sin unidades suficientes en cocina. */
public class PlatoAgotadoException extends RestauranteException {

    public PlatoAgotadoException(String plato) {
        super("El plato '" + plato + "' no tiene unidades suficientes");
    }
}
