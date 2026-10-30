package com.restaurante.generico;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz genérica propia (requisito 4.a).
 *
 * @param <T>  tipo de la entidad
 * @param <ID> tipo del identificador
 */
public interface Repositorio<T, ID> {

    void guardar(T entidad);

    Optional<T> buscarPorId(ID id);

    List<T> listar();

    boolean eliminar(ID id);
}
