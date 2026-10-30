package com.restaurante.fabrica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.restaurante.modelo.Categoria;
import com.restaurante.modelo.LineaCocina;
import com.restaurante.modelo.Plato;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Pruebas del patrón Abstract Factory. */
class FabricaCocinaTest {

    private List<FabricaCocina> fabricas;

    @BeforeEach
    void preparar() {
        fabricas = Arrays.asList(new FabricaColombiana(), new FabricaItaliana());
    }

    @Test
    void cadaFabricaCreaUnaFamiliaCoherente() {
        for (FabricaCocina fabrica : fabricas) {
            LineaCocina linea = fabrica.getLinea();
            assertEquals(linea, fabrica.crearEntrada().getLinea());
            assertEquals(linea, fabrica.crearPlatoFuerte().getLinea());
            assertEquals(linea, fabrica.crearBebida().getLinea());
            assertEquals(linea, fabrica.crearPostre().getLinea());
        }
    }

    @Test
    void cadaFabricaCreaCadaCategoria() {
        for (FabricaCocina fabrica : fabricas) {
            assertEquals(Categoria.ENTRADA, fabrica.crearEntrada().getCategoria());
            assertEquals(Categoria.PLATO_FUERTE, fabrica.crearPlatoFuerte().getCategoria());
            assertEquals(Categoria.BEBIDA, fabrica.crearBebida().getCategoria());
            assertEquals(Categoria.POSTRE, fabrica.crearPostre().getCategoria());
        }
    }

    @Test
    void lasFabricasProducenPlatosDistintos() {
        assertFalse(new FabricaColombiana().crearEntrada().getNombre()
                .equals(new FabricaItaliana().crearEntrada().getNombre()));
    }

    @Test
    void menuTieneUnPlatoPorCategoriaDeSuLinea() {
        for (FabricaCocina fabrica : fabricas) {
            Menu menu = new Menu(fabrica);
            assertEquals(4, menu.getPlatos().size());
            assertEquals(fabrica.getLinea(), menu.getLinea());
        }
    }

    @Test
    void describirEsPolimorfico() {
        Menu menu = new Menu(new FabricaItaliana());
        for (Plato plato : menu.getPlatos()) {
            assertTrue(plato.describir().contains(plato.getNombre()));
        }
    }

    @Test
    void menuNoOfreceLosPlatosAgotados() throws Exception {
        Menu menu = new Menu(new FabricaColombiana());
        Plato bandeja = menu.buscarPorNombre("Bandeja Paisa").get();
        bandeja.consumir(10);

        assertEquals(3, menu.disponibles().size());
        assertFalse(menu.disponibles().contains(bandeja));
    }
}
