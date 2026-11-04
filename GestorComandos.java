package com.restaurante.comando;

import com.restaurante.excepcion.RestauranteException;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * COMMAND (rol: Invoker): ejecuta comandos y guarda el historial para deshacer.
 * Un comando que falla al ejecutarse no entra al historial.
 */
public class GestorComandos {

    private final Deque<Comando> historial = new ArrayDeque<>();

    public void ejecutar(Comando comando) throws RestauranteException {
        comando.ejecutar();
        historial.push(comando);
    }

    /** Deshace el último cambio; devuelve false si no había nada que deshacer. */
    public boolean deshacerUltimo() throws RestauranteException {
        Comando ultimo = historial.peek();
        if (ultimo == null) {
            return false;
        }
        ultimo.deshacer();
        historial.pop();
        return true;
    }

    public int tamanioHistorial() {
        return historial.size();
    }
}
