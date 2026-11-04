package com.restaurante.servicio;

import com.restaurante.excepcion.DomicilioMinimoException;
import com.restaurante.excepcion.PedidoInvalidoException;
import com.restaurante.excepcion.PlatoAgotadoException;
import com.restaurante.excepcion.RestauranteException;
import com.restaurante.generico.Repositorio;
import com.restaurante.generico.RepositorioEnMemoria;
import com.restaurante.modelo.EstadoPedido;
import com.restaurante.modelo.Pedido;
import com.restaurante.modelo.PedidoDomicilio;
import com.restaurante.modelo.PedidoMesa;
import com.restaurante.modelo.Plato;
import com.restaurante.modelo.Zona;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Crea, confirma y entrega pedidos aplicando las reglas del restaurante. */
public class ServicioPedidos {

    public static final double MINIMO_DOMICILIO = 25000;

    private static final Logger LOG = Logger.getLogger(ServicioPedidos.class.getName());

    private final Repositorio<Pedido, Integer> repositorio = new RepositorioEnMemoria<>(Pedido::getId);
    private final AtomicInteger consecutivo = new AtomicInteger(1);
    private final ServicioCocina cocina;
    private final ServicioDomicilios domicilios;
    private final Supplier<LocalDateTime> reloj;

    public ServicioPedidos(ServicioCocina servicioCocina, ServicioDomicilios servicioDomicilios,
                           Supplier<LocalDateTime> relojSistema) {
        cocina = servicioCocina;
        domicilios = servicioDomicilios;
        reloj = relojSistema;
    }

    public PedidoMesa crearPedidoMesa(int mesa) {
        PedidoMesa pedido = new PedidoMesa(consecutivo.getAndIncrement(), reloj.get(), mesa);
        LOG.info("Nuevo pedido " + pedido.getId() + " para la mesa " + mesa);
        return pedido;
    }

    public PedidoDomicilio crearPedidoDomicilio(Zona zona, String direccion) {
        PedidoDomicilio pedido = new PedidoDomicilio(
                consecutivo.getAndIncrement(), reloj.get(), zona, direccion, Zona::getTarifa);
        LOG.info("Nuevo domicilio " + pedido.getId() + " hacia zona " + zona);
        return pedido;
    }

    /**
     * Valida todo antes de tocar nada: si algo falla, no se consume stock ni se asigna repartidor.
     */
    public void confirmar(Pedido pedido) throws RestauranteException {
        try {
            validar(pedido);
            if (pedido instanceof PedidoDomicilio) {
                domicilios.asignarRepartidor((PedidoDomicilio) pedido);
            }
            for (Map.Entry<Plato, Integer> item : pedido.getItems().entrySet()) {
                item.getKey().consumir(item.getValue());
            }
            repositorio.guardar(pedido);
            cocina.recibir(pedido);
            LOG.info("Pedido " + pedido.getId() + " confirmado por $" + pedido.total());
        } catch (RestauranteException e) {
            LOG.log(Level.WARNING, "Pedido " + pedido.getId() + " rechazado: " + e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            LOG.log(Level.SEVERE, "Error inesperado confirmando el pedido " + pedido.getId(), e);
            throw e;
        } finally {
            LOG.fine("Fin del intento de confirmar el pedido " + pedido.getId());
        }
    }

    public void entregar(Pedido pedido) throws PedidoInvalidoException {
        if (pedido.getEstado() != EstadoPedido.LISTO) {
            LOG.warning("Pedido " + pedido.getId() + " no está listo para entregar");
            throw new PedidoInvalidoException("El pedido " + pedido.getId() + " no está listo");
        }
        pedido.setEstado(EstadoPedido.ENTREGADO);
        if (pedido instanceof PedidoDomicilio) {
            domicilios.liberar((PedidoDomicilio) pedido);
        }
        LOG.info("Pedido " + pedido.getId() + " entregado");
    }

    public Optional<Pedido> buscarPedido(int id) {
        return repositorio.buscarPorId(id);
    }

    public List<Pedido> listar() {
        return repositorio.listar();
    }

    private void validar(Pedido pedido) throws RestauranteException {
        if (pedido.getEstado() != EstadoPedido.BORRADOR) {
            throw new PedidoInvalidoException("El pedido " + pedido.getId() + " ya fue confirmado");
        }
        if (pedido.getItems().isEmpty()) {
            throw new PedidoInvalidoException("El pedido " + pedido.getId() + " no tiene platos");
        }
        for (Map.Entry<Plato, Integer> item : pedido.getItems().entrySet()) {
            if (item.getValue() > item.getKey().getStock()) {
                throw new PlatoAgotadoException(item.getKey().getNombre());
            }
        }
        if (pedido instanceof PedidoDomicilio && pedido.subtotal() < MINIMO_DOMICILIO) {
            throw new DomicilioMinimoException(MINIMO_DOMICILIO, pedido.subtotal());
        }
    }
}
