package com.restaurante.servicio;

import com.restaurante.generico.Par;
import com.restaurante.modelo.EstadoPedido;
import com.restaurante.modelo.LineaCocina;
import com.restaurante.modelo.Pedido;
import java.time.Duration;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/** Reportes del administrador, todos calculados con Streams. */
public class ServicioReportes {

    private static final Logger LOG = Logger.getLogger(ServicioReportes.class.getName());

    private final Supplier<List<Pedido>> fuente;

    public ServicioReportes(Supplier<List<Pedido>> fuentePedidos) {
        fuente = fuentePedidos;
    }

    /** Cuánto vendió cada línea de cocina (solo platos, sin tarifa de domicilio). */
    public Map<LineaCocina, Double> ventasPorLinea() {
        LOG.info("Generando reporte de ventas por línea");
        return fuente.get().stream()
                .flatMap(p -> p.getItems().entrySet().stream())
                .collect(Collectors.groupingBy(
                        e -> e.getKey().getLinea(),
                        () -> new EnumMap<LineaCocina, Double>(LineaCocina.class),
                        Collectors.summingDouble(e -> e.getKey().getPrecio() * e.getValue())));
    }

    /** Los platos más pedidos, de mayor a menor cantidad. */
    public List<Par<String, Integer>> platosMasPedidos(int limite) {
        LOG.info("Generando reporte de platos más pedidos");
        return fuente.get().stream()
                .flatMap(p -> p.getItems().entrySet().stream())
                .collect(Collectors.groupingBy(
                        e -> e.getKey().getNombre(),
                        Collectors.summingInt(Map.Entry::getValue)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.<String, Integer>comparingByKey()))
                .limit(limite)
                .map(e -> new Par<>(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    /** Minutos promedio entre crear un pedido y dejarlo listo. */
    public Optional<Double> tiempoPromedioMinutos() {
        LOG.info("Generando reporte de tiempo promedio");
        OptionalDouble promedio = fuente.get().stream()
                .filter(p -> p.getEstado() == EstadoPedido.LISTO
                        || p.getEstado() == EstadoPedido.ENTREGADO)
                .map(Pedido::duracion)
                .flatMap(Optional::stream)
                .mapToLong(Duration::toMillis)
                .average();
        return promedio.isPresent()
                ? Optional.of(promedio.getAsDouble() / 60000.0)
                : Optional.empty();
    }

    /** Todo lo cobrado, incluidas las tarifas de domicilio. */
    public double ingresosTotales() {
        return fuente.get().stream()
                .map(Pedido::total)
                .reduce(0.0, Double::sum);
    }
}
