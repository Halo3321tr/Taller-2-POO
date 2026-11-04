package com.restaurante.servicio;

import com.restaurante.excepcion.PedidoInvalidoException;
import com.restaurante.generico.ColaDeEspera;
import com.restaurante.modelo.EstadoPedido;
import com.restaurante.modelo.Pedido;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * Cocina: prepara los pedidos estrictamente en el orden en que llegaron (cola FIFO).
 * El reloj llega como Supplier (se puede fijar en las pruebas) y el aviso de
 * "pedido listo" como Consumer.
 */
public class ServicioCocina {

    private static final Logger LOG = Logger.getLogger(ServicioCocina.class.getName());

    private final ColaDeEspera<Pedido> cola = new ColaDeEspera<>();
    private final Supplier<LocalDateTime> reloj;
    private final Consumer<Pedido> alTerminar;

    public ServicioCocina(Supplier<LocalDateTime> relojSistema, Consumer<Pedido> notificador) {
        reloj = relojSistema;
        alTerminar = notificador;
    }

    public void recibir(Pedido pedido) {
        pedido.setEstado(EstadoPedido.EN_COLA);
        cola.encolar(pedido);
        LOG.info("Pedido " + pedido.getId() + " en cola de cocina (posición " + cola.tamanio() + ")");
    }

    /** Toma el pedido que lleva más tiempo esperando. */
    public Optional<Pedido> prepararSiguiente() {
        Optional<Pedido> siguiente = cola.desencolar();
        siguiente.ifPresent(p -> {
            p.setEstado(EstadoPedido.EN_PREPARACION);
            LOG.info("Cocina empieza el pedido " + p.getId());
        });
        return siguiente;
    }

    public void marcarListo(Pedido pedido) throws PedidoInvalidoException {
        if (pedido.getEstado() != EstadoPedido.EN_PREPARACION) {
            LOG.warning("Pedido " + pedido.getId() + " no está en preparación");
            throw new PedidoInvalidoException("El pedido " + pedido.getId() + " no está en preparación");
        }
        pedido.setListoEn(reloj.get());
        pedido.setEstado(EstadoPedido.LISTO);
        LOG.info("Pedido " + pedido.getId() + " listo");
        alTerminar.accept(pedido);
    }

    public int pendientes() {
        return cola.tamanio();
    }
}
