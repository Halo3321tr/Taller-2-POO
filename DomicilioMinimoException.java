package com.restaurante.excepcion;

/** El valor de los platos de un domicilio no alcanza el mínimo exigido. */
public class DomicilioMinimoException extends RestauranteException {

    public DomicilioMinimoException(double minimo, double actual) {
        super("El domicilio requiere un mínimo de " + minimo + " y el pedido suma " + actual);
    }
}
