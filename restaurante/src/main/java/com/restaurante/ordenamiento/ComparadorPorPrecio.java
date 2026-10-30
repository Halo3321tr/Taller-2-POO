package com.restaurante.ordenamiento;

import com.restaurante.modelo.Plato;
import java.util.Comparator;

/** Criterio de ordenamiento #1 (requisito 3.b): como clase, de menor a mayor precio. */
public class ComparadorPorPrecio implements Comparator<Plato> {

    @Override
    public int compare(Plato a, Plato b) {
        return Double.compare(a.getPrecio(), b.getPrecio());
    }
}
