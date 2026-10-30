package com.restaurante.ordenamiento;

import com.restaurante.modelo.Plato;
import java.util.Comparator;

/** Criterios de ordenamiento #2 (lambda) y #3 (compuesto) del requisito 3.b. */
public final class Comparadores {

    private Comparadores() {
    }

    /** Criterio como lambda: menos stock primero. */
    public static final Comparator<Plato> POR_STOCK =
            (a, b) -> Integer.compare(a.getStock(), b.getStock());

    /** Criterio compuesto: por línea, luego por precio, y todo invertido. */
    public static final Comparator<Plato> POR_LINEA_Y_PRECIO_INVERTIDO =
            Comparator.comparing(Plato::getLinea)
                    .thenComparing(Plato::getPrecio)
                    .reversed();
}
