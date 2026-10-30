package com.restaurante.generico;

import java.util.Objects;

/** Clase genérica #3 (requisito 4.b): par inmutable, usado en los reportes. */
public final class Par<A, B> {

    private final A primero;
    private final B segundo;

    public Par(A valorA, B valorB) {
        primero = valorA;
        segundo = valorB;
    }

    public A getPrimero() {
        return primero;
    }

    public B getSegundo() {
        return segundo;
    }

    @Override
    public boolean equals(Object otro) {
        if (!(otro instanceof Par<?, ?>)) {
            return false;
        }
        Par<?, ?> p = (Par<?, ?>) otro;
        return Objects.equals(primero, p.primero) && Objects.equals(segundo, p.segundo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primero, segundo);
    }

    @Override
    public String toString() {
        return "(" + primero + ", " + segundo + ")";
    }
}
