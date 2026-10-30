package com.restaurante.fabrica;

import com.restaurante.generico.UtilColecciones;
import com.restaurante.modelo.LineaCocina;
import com.restaurante.modelo.Plato;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * ABSTRACT FACTORY (rol: Client).
 * Arma el menú pidiendo los platos a una FabricaCocina; no conoce las clases concretas.
 */
public class Menu {

    private final LineaCocina linea;
    private final List<Plato> platos = new ArrayList<>();

    public Menu(FabricaCocina fabrica) {
        linea = fabrica.getLinea();
        platos.add(fabrica.crearEntrada());
        platos.add(fabrica.crearPlatoFuerte());
        platos.add(fabrica.crearBebida());
        platos.add(fabrica.crearPostre());
    }

    public LineaCocina getLinea() {
        return linea;
    }

    public List<Plato> getPlatos() {
        return Collections.unmodifiableList(platos);
    }

    /** Búsqueda con Optional (requisito 7.d). */
    public Optional<Plato> buscarPorNombre(String nombre) {
        return platos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }

    /** Solo los platos que aún tienen unidades: un plato agotado no puede pedirse. */
    public List<Plato> disponibles() {
        return UtilColecciones.filtrar(platos, Plato::estaDisponible);
    }

    /** Orden natural (Comparable) con Collections.sort (requisito 3.c). */
    public List<Plato> ordenadoNatural() {
        List<Plato> copia = new ArrayList<>(platos);
        Collections.sort(copia);
        return copia;
    }

    /** Orden por cualquier criterio con List.sort (requisito 3.c). */
    public List<Plato> ordenadoPor(Comparator<? super Plato> criterio) {
        List<Plato> copia = new ArrayList<>(platos);
        copia.sort(criterio);
        return copia;
    }
}
