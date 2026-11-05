package com.restaurante.comando;

import com.restaurante.modelo.Pedido;
import com.restaurante.modelo.Plato;

/** COMMAND (rol: ConcreteCommand #2): quita un plato por completo. */
public class QuitarPlatoComando extends ComandoSobrePlato {

    public QuitarPlatoComando(Pedido pedido, Plato plato) {
        super(pedido, plato);
    }

    @Override
    protected int calcularNuevaCantidad(int cantidadActual) {
        return 0;
    }

    @Override
    public String descripcion() {
        return "Quitar " + getPlato().getNombre();
    }
}
