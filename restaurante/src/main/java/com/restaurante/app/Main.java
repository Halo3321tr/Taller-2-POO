package com.restaurante.app;

import com.restaurante.comando.AgregarPlatoComando;
import com.restaurante.comando.CambiarCantidadComando;
import com.restaurante.comando.Comando;
import com.restaurante.comando.GestorComandos;
import com.restaurante.comando.QuitarPlatoComando;
import com.restaurante.excepcion.RestauranteException;
import com.restaurante.fabrica.FabricaColombiana;
import com.restaurante.fabrica.FabricaItaliana;
import com.restaurante.fabrica.Menu;
import com.restaurante.modelo.LineaCocina;
import com.restaurante.modelo.Pedido;
import com.restaurante.modelo.Plato;
import com.restaurante.modelo.Repartidor;
import com.restaurante.modelo.Zona;
import com.restaurante.ordenamiento.Comparadores;
import com.restaurante.servicio.ServicioCocina;
import com.restaurante.servicio.ServicioDomicilios;
import com.restaurante.servicio.ServicioPedidos;
import com.restaurante.servicio.ServicioReportes;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Aplicación de consola. Aquí System.out.println se usa solo para el menú y la
 * presentación; los errores y eventos se registran con Logger.
 */
public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    private final Scanner entrada = new Scanner(System.in);
    private final Map<LineaCocina, Menu> menus = new EnumMap<>(LineaCocina.class);
    private final ServicioPedidos pedidos;
    private final ServicioCocina cocina;
    private final ServicioReportes reportes;
    private Pedido actual;
    private GestorComandos gestor = new GestorComandos();

    public Main() {
        Supplier<LocalDateTime> reloj = LocalDateTime::now;
        menus.put(LineaCocina.COLOMBIANA, new Menu(new FabricaColombiana()));
        menus.put(LineaCocina.ITALIANA, new Menu(new FabricaItaliana()));
        cocina = new ServicioCocina(reloj, p -> LOG.info("Aviso: pedido " + p.getId() + " listo"));
        ServicioDomicilios domicilios = new ServicioDomicilios(
                Arrays.asList(new Repartidor("Carlos"), new Repartidor("Marta")));
        pedidos = new ServicioPedidos(cocina, domicilios, reloj);
        reportes = new ServicioReportes(pedidos::listar);
    }

    public static void main(String[] args) {
        new Main().ejecutar();
    }

    private void ejecutar() {
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            opcion = leerEntero("Opción: ");
            switch (opcion) {
                case 1: verMenu(); break;
                case 2: nuevoPedidoMesa(); break;
                case 3: nuevoDomicilio(); break;
                case 4: agregarPlato(); break;
                case 5: quitarPlato(); break;
                case 6: cambiarCantidad(); break;
                case 7: deshacer(); break;
                case 8: verPedidoActual(); break;
                case 9: confirmar(); break;
                case 10: prepararSiguiente(); break;
                case 11: marcarListo(); break;
                case 12: entregar(); break;
                case 13: verReportes(); break;
                case 0: System.out.println("Hasta pronto."); break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("===== RESTAURANTE =====");
        System.out.println(" 1. Ver menú de una línea");
        System.out.println(" 2. Nuevo pedido en mesa");
        System.out.println(" 3. Nuevo domicilio");
        System.out.println(" 4. Agregar plato al pedido");
        System.out.println(" 5. Quitar plato del pedido");
        System.out.println(" 6. Cambiar cantidad de un plato");
        System.out.println(" 7. Deshacer último cambio");
        System.out.println(" 8. Ver pedido actual");
        System.out.println(" 9. Confirmar pedido (envía a cocina)");
        System.out.println("10. Cocina: preparar siguiente");
        System.out.println("11. Cocina: marcar pedido listo");
        System.out.println("12. Entregar pedido");
        System.out.println("13. Reportes");
        System.out.println(" 0. Salir");
    }

    private void verMenu() {
        Menu menu = elegirMenu();
        if (menu == null) {
            return;
        }
        for (Plato p : menu.ordenadoPor(Comparadores.POR_LINEA_Y_PRECIO_INVERTIDO)) {
            System.out.println(" - " + p.describir() + " | $" + p.getPrecio() + " | stock " + p.getStock());
        }
    }

    private void nuevoPedidoMesa() {
        int mesa = leerEntero("Número de mesa: ");
        actual = pedidos.crearPedidoMesa(mesa);
        gestor = new GestorComandos();
        System.out.println("Pedido creado: " + actual);
    }

    private void nuevoDomicilio() {
        Zona[] zonas = Zona.values();
        for (int i = 0; i < zonas.length; i++) {
            System.out.println((i + 1) + ". " + zonas[i] + " ($" + zonas[i].getTarifa() + ")");
        }
        int indice = leerEntero("Zona: ");
        if (indice < 1 || indice > zonas.length) {
            System.out.println("Zona no válida.");
            return;
        }
        System.out.print("Dirección: ");
        String direccion = entrada.hasNextLine() ? entrada.nextLine() : "";
        actual = pedidos.crearPedidoDomicilio(zonas[indice - 1], direccion);
        gestor = new GestorComandos();
        System.out.println("Domicilio creado: " + actual);
    }

    private void agregarPlato() {
        if (!hayPedido()) {
            return;
        }
        Optional<Plato> plato = elegirPlato();
        int cantidad = leerEntero("Cantidad: ");
        if (plato.isPresent() && cantidad > 0) {
            aplicar(new AgregarPlatoComando(actual, plato.get(), cantidad));
        } else {
            System.out.println("Plato o cantidad no válidos.");
        }
    }

    private void quitarPlato() {
        if (!hayPedido()) {
            return;
        }
        Optional<Plato> plato = elegirPlato();
        if (plato.isPresent()) {
            aplicar(new QuitarPlatoComando(actual, plato.get()));
        } else {
            System.out.println("Plato no válido.");
        }
    }

    private void cambiarCantidad() {
        if (!hayPedido()) {
            return;
        }
        Optional<Plato> plato = elegirPlato();
        int cantidad = leerEntero("Nueva cantidad: ");
        if (plato.isPresent() && cantidad >= 0) {
            aplicar(new CambiarCantidadComando(actual, plato.get(), cantidad));
        } else {
            System.out.println("Plato o cantidad no válidos.");
        }
    }

    private void deshacer() {
        try {
            if (gestor.deshacerUltimo()) {
                System.out.println("Último cambio deshecho.");
            } else {
                System.out.println("No hay cambios para deshacer.");
            }
        } catch (RestauranteException e) {
            LOG.log(Level.WARNING, "No se pudo deshacer: " + e.getMessage());
            System.out.println("No se pudo deshacer el cambio.");
        }
    }

    private void verPedidoActual() {
        if (!hayPedido()) {
            return;
        }
        System.out.println(actual);
        actual.getItems().forEach((plato, cantidad) ->
                System.out.println(" - " + cantidad + " x " + plato.getNombre()));
    }

    private void confirmar() {
        if (!hayPedido()) {
            return;
        }
        try {
            pedidos.confirmar(actual);
            System.out.println("Pedido " + actual.getId() + " enviado a cocina.");
            actual = null;
        } catch (RestauranteException e) {
            System.out.println("No se pudo confirmar el pedido (revise el registro).");
        }
    }

    private void prepararSiguiente() {
        Optional<Pedido> siguiente = cocina.prepararSiguiente();
        if (siguiente.isPresent()) {
            System.out.println("En preparación: " + siguiente.get());
        } else {
            System.out.println("No hay pedidos en cola.");
        }
    }

    private void marcarListo() {
        Optional<Pedido> pedido = pedidos.buscarPedido(leerEntero("Id del pedido: "));
        if (!pedido.isPresent()) {
            System.out.println("Pedido no encontrado.");
            return;
        }
        try {
            cocina.marcarListo(pedido.get());
            System.out.println("Pedido " + pedido.get().getId() + " listo.");
        } catch (RestauranteException e) {
            System.out.println("No se pudo marcar el pedido como listo (revise el registro).");
        }
    }

    private void entregar() {
        Optional<Pedido> pedido = pedidos.buscarPedido(leerEntero("Id del pedido: "));
        if (!pedido.isPresent()) {
            System.out.println("Pedido no encontrado.");
            return;
        }
        try {
            pedidos.entregar(pedido.get());
            System.out.println("Pedido " + pedido.get().getId() + " entregado.");
        } catch (RestauranteException e) {
            System.out.println("No se pudo entregar el pedido (revise el registro).");
        }
    }

    private void verReportes() {
        System.out.println("--- Ventas por línea de cocina ---");
        reportes.ventasPorLinea().forEach((linea, valor) ->
                System.out.println(linea + ": $" + valor));
        System.out.println("--- Platos más pedidos ---");
        reportes.platosMasPedidos(3).forEach(par ->
                System.out.println(par.getPrimero() + ": " + par.getSegundo()));
        System.out.println("--- Tiempo promedio por pedido ---");
        Optional<Double> promedio = reportes.tiempoPromedioMinutos();
        System.out.println(promedio.isPresent()
                ? String.format("%.2f minutos", promedio.get())
                : "Aún no hay pedidos terminados.");
        System.out.println("Ingresos totales: $" + reportes.ingresosTotales());
    }

    // ---------- utilidades de la consola ----------

    private void aplicar(Comando comando) {
        try {
            gestor.ejecutar(comando);
            System.out.println("Hecho: " + comando.descripcion());
        } catch (RestauranteException e) {
            LOG.log(Level.WARNING, "Comando rechazado (" + comando.descripcion() + "): " + e.getMessage());
            System.out.println("No se pudo realizar la operación.");
        }
    }

    private boolean hayPedido() {
        if (actual == null) {
            System.out.println("Primero cree un pedido (opción 2 o 3).");
            return false;
        }
        return true;
    }

    private Menu elegirMenu() {
        System.out.println("1. Colombiana  2. Italiana");
        int opcion = leerEntero("Línea: ");
        LineaCocina[] lineas = LineaCocina.values();
        if (opcion < 1 || opcion > lineas.length) {
            System.out.println("Línea no válida.");
            return null;
        }
        return menus.get(lineas[opcion - 1]);
    }

    private Optional<Plato> elegirPlato() {
        Menu menu = elegirMenu();
        if (menu == null) {
            return Optional.empty();
        }
        List<Plato> lista = menu.ordenadoNatural();
        for (int i = 0; i < lista.size(); i++) {
            Plato p = lista.get(i);
            System.out.println((i + 1) + ". " + p.getNombre() + " ($" + p.getPrecio()
                    + ", stock " + p.getStock() + ")");
        }
        int indice = leerEntero("Plato #: ");
        if (indice < 1 || indice > lista.size()) {
            return Optional.empty();
        }
        return Optional.of(lista.get(indice - 1));
    }

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        if (!entrada.hasNextLine()) {
            return 0;
        }
        String texto = entrada.nextLine().trim();
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            LOG.log(Level.WARNING, "Entrada no numérica: '" + texto + "'");
            return -1;
        }
    }
}
