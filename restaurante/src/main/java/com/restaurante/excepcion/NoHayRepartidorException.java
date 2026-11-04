package com.restaurante.excepcion;

/** No hay ningún repartidor disponible para asignar al domicilio. */
public class NoHayRepartidorException extends RestauranteException {

    public NoHayRepartidorException() {
        super("No hay repartidores disponibles en este momento");
    }
}
