package com.restaurante.comando;

import com.restaurante.modelo.Pedido;
import com.restaurante.modelo.Plato;

/** COMMAND (rol: ConcreteCommand #1): agrega unidades de un plato. */
public class AgregarPlatoComando extends ComandoSobrePlato {

    private final int cantidad;

    public AgregarPlatoComando(Pedido pedido, Plato plato, int unidades) {
        super(pedido, plato);
        if (unidades <= 0) {
            throw new IllegalArgumentException("Debe agregar al menos una unidad");
        }
        cantidad = unidades;
    }

    @Override
    protected int calcularNuevaCantidad(int cantidadActual) {
        return cantidadActual + cantidad;
    }

    @Override
    public String descripcion() {
        return "Agregar " + cantidad + " x " + getPlato().getNombre();
    }
}
