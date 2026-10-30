package com.restaurante.generico;

import com.restaurante.modelo.Plato;
import java.util.Collection;
import java.util.List;

/** Métodos con comodines (requisito 5). */
public final class UtilWildcards {

    private UtilWildcards() {
    }

    /** 5.a) Solo cuenta: no importa el tipo de los elementos (?). */
    public static int contar(Collection<?> coleccion) {
        int total = 0;
        for (Object ignorado : coleccion) {
            total++;
        }
        return total;
    }

    /** 5.b) Productor (? extends T): lee platos de cualquier subtipo y suma su valor. */
    public static double sumarPrecios(Collection<? extends Plato> platos) {
        double suma = 0;
        for (Plato plato : platos) {
            suma += plato.getPrecio();
        }
        return suma;
    }

    /** 5.c) Consumidor (? super T): agrega elementos en una colección de supertipos. */
    public static <T> void copiarEn(List<? extends T> origen, Collection<? super T> destino) {
        for (T elemento : origen) {
            destino.add(elemento);
        }
    }
}
