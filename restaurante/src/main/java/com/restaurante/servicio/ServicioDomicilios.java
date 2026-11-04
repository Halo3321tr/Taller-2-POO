package com.restaurante.servicio;

import com.restaurante.excepcion.NoHayRepartidorException;
import com.restaurante.modelo.PedidoDomicilio;
import com.restaurante.modelo.Repartidor;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/** Asigna y libera repartidores. */
public class ServicioDomicilios {

    private static final Logger LOG = Logger.getLogger(ServicioDomicilios.class.getName());

    private final List<Repartidor> repartidores;

    public ServicioDomicilios(List<Repartidor> lista) {
        repartidores = new ArrayList<>(lista);
    }

    public Repartidor asignarRepartidor(PedidoDomicilio pedido) throws NoHayRepartidorException {
        Repartidor elegido = repartidores.stream()
                .filter(Repartidor::isDisponible)
                .findFirst()
                .orElseThrow(() -> {
                    LOG.warning("Domicilio " + pedido.getId() + ": no hay repartidores disponibles");
                    return new NoHayRepartidorException();
                });
        elegido.setDisponible(false);
        pedido.setRepartidor(elegido);
        LOG.info("Repartidor " + elegido.getNombre() + " asignado al domicilio " + pedido.getId());
        return elegido;
    }

    public void liberar(PedidoDomicilio pedido) {
        Repartidor repartidor = pedido.getRepartidor();
        if (repartidor != null) {
            repartidor.setDisponible(true);
            LOG.info("Repartidor " + repartidor.getNombre() + " quedó disponible");
        }
    }

    public long disponibles() {
        return repartidores.stream().filter(Repartidor::isDisponible).count();
    }
}
