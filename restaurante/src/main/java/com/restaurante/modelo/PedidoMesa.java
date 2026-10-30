package com.restaurante.modelo;

import java.time.LocalDateTime;

/** Pedido atendido en el salón: no tiene costo adicional. */
public class PedidoMesa extends Pedido {

    private final int numeroMesa;

    public PedidoMesa(int id, LocalDateTime momento, int mesa) {
        super(id, momento);
        numeroMesa = mesa;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    @Override
    public double costoAdicional() {
        return 0;
    }

    @Override
    public String getTipo() {
        return "Mesa " + numeroMesa;
    }
}
