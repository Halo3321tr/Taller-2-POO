package com.restaurante.comando;

import com.restaurante.excepcion.RestauranteException;
import com.restaurante.modelo.Pedido;
import com.restaurante.modelo.Plato;

/**
 * Base de los comandos concretos. Guarda la cantidad anterior al ejecutar,
 * que es todo lo que hace falta para deshacer. Cada subclase solo decide
 * cuál es la nueva cantidad.
 */
public abstract class ComandoSobrePlato implements Comando {

    private final Pedido pedido;
    private final Plato plato;
    private int cantidadAnterior;

    protected ComandoSobrePlato(Pedido receptor, Plato platoAfectado) {
        pedido = receptor;
        plato = platoAfectado;
    }

    protected abstract int calcularNuevaCantidad(int cantidadActual);

    protected Plato getPlato() {
        return plato;
    }

    @Override
    public void ejecutar() throws RestauranteException {
        cantidadAnterior = pedido.cantidadDe(plato);
        pedido.fijarCantidad(plato, calcularNuevaCantidad(cantidadAnterior));
    }

    @Override
    public void deshacer() throws RestauranteException {
        pedido.fijarCantidad(plato, cantidadAnterior);
    }
}
