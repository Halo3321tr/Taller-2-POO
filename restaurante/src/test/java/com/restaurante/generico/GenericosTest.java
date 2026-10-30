package com.restaurante.generico;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.restaurante.fabrica.FabricaColombiana;
import com.restaurante.fabrica.Menu;
import com.restaurante.modelo.Categoria;
import com.restaurante.modelo.Plato;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenericosTest {

    private RepositorioEnMemoria<Plato, String> repositorio;
    private ColaDeEspera<String> cola;
    private List<Plato> platos;

    @BeforeEach
    void preparar() {
        platos = new ArrayList<>(new Menu(new FabricaColombiana()).getPlatos());
        repositorio = new RepositorioEnMemoria<>(Plato::getNombre);
        platos.forEach(repositorio::guardar);
        cola = new ColaDeEspera<>();
    }

    // ---------- RepositorioEnMemoria ----------

    @Test
    void repositorioBuscaPorId() {
        assertEquals("Natilla", repositorio.buscarPorId("Natilla").get().getNombre());
    }

    @Test
    void repositorioDevuelveOptionalVacioSiNoExiste() {
        assertFalse(repositorio.buscarPorId("Pizza").isPresent());
    }

    @Test
    void repositorioEliminaYLista() {
        assertTrue(repositorio.eliminar("Natilla"));
        assertFalse(repositorio.eliminar("Natilla"));
        assertEquals(3, repositorio.listar().size());
    }

    // ---------- ColaDeEspera ----------

    @Test
    void colaRespetaFifo() {
        cola.encolar("Pedido 1");
        cola.encolar("Pedido 2");
        assertEquals("Pedido 1", cola.desencolar().get());
        assertEquals("Pedido 2", cola.verSiguiente().get());
        assertEquals(1, cola.tamanio());
    }

    @Test
    void colaVaciaDevuelveOptionalVacio() {
        assertTrue(cola.estaVacia());
        assertEquals(Optional.empty(), cola.desencolar());
    }

    @Test
    void colaRechazaNulos() {
        assertThrows(IllegalArgumentException.class, () -> cola.encolar(null));
    }

    // ---------- Par ----------

    @Test
    void parComparaPorValor() {
        Par<String, Integer> a = new Par<>("Tiramisu", 4);
        Par<String, Integer> b = new Par<>("Tiramisu", 4);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertEquals("Tiramisu", a.getPrimero());
        assertEquals(4, a.getSegundo().intValue());
    }

    // ---------- UtilColecciones ----------

    @Test
    void filtrarDejaSoloLosQueCumplen() {
        List<Plato> caros = UtilColecciones.filtrar(platos, p -> p.getPrecio() > 8500);
        assertEquals(2, caros.size());
    }

    @Test
    void transformarConvierteANombres() {
        List<String> nombres = UtilColecciones.transformar(platos, Plato::getNombre);
        assertEquals(Arrays.asList("Empanadas", "Bandeja Paisa", "Limonada de Coco", "Natilla"), nombres);
    }

    @Test
    void agruparPorCategoria() {
        Map<Categoria, List<Plato>> grupos = UtilColecciones.agruparPor(platos, Plato::getCategoria);
        assertEquals(4, grupos.size());
        assertEquals(1, grupos.get(Categoria.POSTRE).size());
    }

    @Test
    void maximoUsaElOrdenNatural() {
        assertEquals("Natilla", UtilColecciones.maximo(platos).get().getNombre());
        assertFalse(UtilColecciones.maximo(new ArrayList<Plato>()).isPresent());
    }

    // ---------- UtilWildcards ----------

    @Test
    void contarAceptaCualquierTipo() {
        assertEquals(4, UtilWildcards.contar(platos));
        assertEquals(2, UtilWildcards.contar(Arrays.asList("a", "b")));
    }

    @Test
    void sumarPreciosLeeSubtipos() {
        assertEquals(56000.0, UtilWildcards.sumarPrecios(platos), 0.001);
    }

    @Test
    void copiarEnAgregaEnColeccionDeSupertipos() {
        Collection<Object> destino = new ArrayList<>();
        UtilWildcards.copiarEn(platos, destino);
        assertEquals(4, destino.size());
    }
}
