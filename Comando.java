package com.restaurante.comando;

import com.restaurante.excepcion.RestauranteException;

/** COMMAND (rol: Command): una acción sobre el pedido que se puede deshacer. */
public interface Comando {

    void ejecutar() throws RestauranteException;

    void deshacer() throws RestauranteException;

    String descripcion();
}
