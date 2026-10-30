package com.restaurante.generico;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/** Clase genérica #1 (requisito 4.b): repositorio en memoria para cualquier entidad. */
public class RepositorioEnMemoria<T, ID> implements Repositorio<T, ID> {

    private final Map<ID, T> almacen = new LinkedHashMap<>();
    private final Function<T, ID> extractorId;

    public RepositorioEnMemoria(Function<T, ID> extractor) {
        extractorId = extractor;
    }

    @Override
    public void guardar(T entidad) {
        almacen.put(extractorId.apply(entidad), entidad);
    }

    @Override
    public Optional<T> buscarPorId(ID id) {
        return Optional.ofNullable(almacen.get(id));
    }

    @Override
    public List<T> listar() {
        return new ArrayList<>(almacen.values());
    }

    @Override
    public boolean eliminar(ID id) {
        return almacen.remove(id) != null;
    }
}
