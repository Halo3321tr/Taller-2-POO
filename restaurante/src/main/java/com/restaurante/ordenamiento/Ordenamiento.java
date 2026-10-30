package com.restaurante.ordenamiento;

import java.util.List;

/** Ordenamiento hecho a mano (requisito 3.d): inserción, genérico. */
public final class Ordenamiento {

    private Ordenamiento() {
    }

    /**
     * Ordena la lista de menor a mayor usando el orden natural.
     * Cada elemento se inserta en su posición dentro de la parte ya ordenada.
     */
    public static <T extends Comparable<? super T>> void ordenar(List<T> lista) {
        for (int i = 1; i < lista.size(); i++) {
            T clave = lista.get(i);
            int j = i - 1;
            while (j >= 0 && lista.get(j).compareTo(clave) > 0) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, clave);
        }
    }
}
