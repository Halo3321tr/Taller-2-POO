package com.restaurante.excepcion;

/** Operación no permitida sobre un pedido (vacío, ya confirmado, cantidad inválida...). */
public class PedidoInvalidoException extends RestauranteException {

    public PedidoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
