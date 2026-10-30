package com.restaurante.modelo;

import com.restaurante.excepcion.PedidoInvalidoException;
import com.restaurante.excepcion.PlatoAgotadoException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Receptor del patrón Command: es el objeto sobre el que actúan los comandos.
 * Clase abstracta: mesa y domicilio comparten items, pero cobran distinto (polimorfismo).
 */
public abstract class Pedido {

    private final int id;
    private final LocalDateTime creadoEn;
    private LocalDateTime listoEn;
    private EstadoPedido estado = EstadoPedido.BORRADOR;
    private final Map<Plato, Integer> items = new LinkedHashMap<>();

    protected Pedido(int valorId, LocalDateTime momento) {
        id = valorId;
        creadoEn = momento;
    }

    /** Costo extra además de los platos (0 en mesa, tarifa en domicilio). */
    public abstract double costoAdicional();

    public abstract String getTipo();

    /**
     * Fija la cantidad de un plato (0 lo quita). Es la única puerta de modificación,
     * así todos los comandos validan igual.
     */
    public void fijarCantidad(Plato plato, int cantidad)
            throws PedidoInvalidoException, PlatoAgotadoException {
        if (plato == null) {
            throw new PedidoInvalidoException("Debe indicar un plato");
        }
        if (estado != EstadoPedido.BORRADOR) {
            throw new PedidoInvalidoException("El pedido " + id + " ya no se puede modificar");
        }
        if (cantidad < 0) {
            throw new PedidoInvalidoException("La cantidad no puede ser negativa");
        }
        if (cantidad == 0) {
            items.remove(plato);
            return;
        }
        if (cantidad > plato.getStock()) {
            throw new PlatoAgotadoException(plato.getNombre());
        }
        items.put(plato, cantidad);
    }

    public int cantidadDe(Plato plato) {
        return items.getOrDefault(plato, 0);
    }

    public Map<Plato, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public double subtotal() {
        return items.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrecio() * e.getValue())
                .sum();
    }

    public double total() {
        return subtotal() + costoAdicional();
    }

    /** Tiempo que tardó el pedido desde que se creó hasta que quedó listo. */
    public Optional<Duration> duracion() {
        if (listoEn == null) {
            return Optional.empty();
        }
        return Optional.of(Duration.between(creadoEn, listoEn));
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public LocalDateTime getListoEn() {
        return listoEn;
    }

    public void setListoEn(LocalDateTime momento) {
        listoEn = momento;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido nuevoEstado) {
        estado = nuevoEstado;
    }

    @Override
    public String toString() {
        return getTipo() + " #" + id + " [" + estado + "] total $" + total();
    }
}
