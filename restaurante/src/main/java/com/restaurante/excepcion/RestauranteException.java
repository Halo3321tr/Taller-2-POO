package com.restaurante.excepcion;

/**
 * Excepción base del dominio (requisito 2.c): de ella heredan todas las demás.
 */
public class RestauranteException extends Exception {

    public RestauranteException(String mensaje) {
        super(mensaje);
    }
}
