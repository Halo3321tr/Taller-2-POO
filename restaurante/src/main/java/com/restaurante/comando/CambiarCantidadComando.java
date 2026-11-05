package com.restaurante.comando;

import com.restaurante.modelo.Pedido;
import com.restaurante.modelo.Plato;

/** COMMAND (rol: ConcreteCommand #3): cambia la cantidad de un plato a un valor exacto. */
public class CambiarCantidadComando extends ComandoSobrePlato {

    private final int nuevaCantidad;

    public CambiarCantidadComando(Pedido pedido, Plato plato, int cantidad) {
        super(pedido, plato);
        nuevaCantidad = cantidad;
    }

    @Override
    protected int calcularNuevaCantidad(int cantidadActual) {
        return nuevaCantidad;
    }

    @Override
    public String descripcion() {
        return "Cambiar " + getPlato().getNombre() + " a " + nuevaCantidad + " unidades";
    }
}
