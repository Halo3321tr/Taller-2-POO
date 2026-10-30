package com.restaurante.generico;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

/**
 * Clase genérica #2 (requisito 4.b): cola FIFO.
 * La cocina la usa para preparar los pedidos estrictamente en orden de llegada.
 */
public class ColaDeEspera<T> {

    private final Deque<T> elementos = new ArrayDeque<>();

    public void encolar(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("No se puede encolar un elemento nulo");
        }
        elementos.addLast(elemento);
    }

    public Optional<T> desencolar() {
        return Optional.ofNullable(elementos.pollFirst());
    }

    public Optional<T> verSiguiente() {
        return Optional.ofNullable(elementos.peekFirst());
    }

    public int tamanio() {
        return elementos.size();
    }

    public boolean estaVacia() {
        return elementos.isEmpty();
    }
}
