package com.restaurante.generico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/** Clase utilitaria con métodos genéricos estáticos (requisito 4.c). */
public final class UtilColecciones {

    private UtilColecciones() {
        // clase utilitaria: no se instancia
    }

    /** Filtra una lista con un Predicate. */
    public static <T> List<T> filtrar(List<T> lista, Predicate<T> condicion) {
        List<T> resultado = new ArrayList<>();
        for (T elemento : lista) {
            if (condicion.test(elemento)) {
                resultado.add(elemento);
            }
        }
        return resultado;
    }

    /** Transforma una lista de T en una lista de R con una Function. */
    public static <T, R> List<R> transformar(List<T> lista, Function<T, R> funcion) {
        List<R> resultado = new ArrayList<>();
        for (T elemento : lista) {
            resultado.add(funcion.apply(elemento));
        }
        return resultado;
    }

    /** Agrupa elementos por una clave (por ejemplo, platos por categoría). */
    public static <T, K> Map<K, List<T>> agruparPor(List<T> lista, Function<T, K> clave) {
        Map<K, List<T>> grupos = new HashMap<>();
        for (T elemento : lista) {
            grupos.computeIfAbsent(clave.apply(elemento), k -> new ArrayList<>()).add(elemento);
        }
        return grupos;
    }

    /** Máximo según el orden natural; Optional vacío si la lista está vacía. */
    public static <T extends Comparable<? super T>> Optional<T> maximo(List<T> lista) {
        T mayor = null;
        for (T elemento : lista) {
            if (mayor == null || elemento.compareTo(mayor) > 0) {
                mayor = elemento;
            }
        }
        return Optional.ofNullable(mayor);
    }
}
