package com.restaurante.modelo;

import com.restaurante.excepcion.PlatoAgotadoException;
import java.util.Objects;

/**
 * Abstracción base de cualquier plato (pilar: abstracción).
 * Implementa Comparable<Plato> (requisito 3.a): orden natural por nombre.
 * El stock está encapsulado: solo cambia mediante consumir() y reponer().
 */
public abstract class Plato implements Comparable<Plato> {

    private final String nombre;
    private final double precio;
    private final LineaCocina linea;
    private final Categoria categoria;
    private int stock;

    protected Plato(String valorNombre, double valorPrecio, LineaCocina valorLinea,
                    Categoria valorCategoria, int valorStock) {
        if (valorNombre == null || valorNombre.isBlank()) {
            throw new IllegalArgumentException("El plato necesita un nombre");
        }
        if (valorPrecio <= 0) {
            throw new IllegalArgumentException("El precio debe ser positivo");
        }
        if (valorStock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        nombre = valorNombre;
        precio = valorPrecio;
        linea = valorLinea;
        categoria = valorCategoria;
        stock = valorStock;
    }

    /** Cada categoría describe el plato a su manera (polimorfismo). */
    public abstract String describir();

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public LineaCocina getLinea() {
        return linea;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public int getStock() {
        return stock;
    }

    public boolean estaDisponible() {
        return stock > 0;
    }

    public void consumir(int unidades) throws PlatoAgotadoException {
        if (unidades > stock) {
            throw new PlatoAgotadoException(nombre);
        }
        stock -= unidades;
    }

    public void reponer(int unidades) {
        if (unidades <= 0) {
            throw new IllegalArgumentException("Debe reponer al menos una unidad");
        }
        stock += unidades;
    }

    @Override
    public int compareTo(Plato otro) {
        return nombre.compareToIgnoreCase(otro.nombre);
    }

    @Override
    public boolean equals(Object otro) {
        if (!(otro instanceof Plato)) {
            return false;
        }
        Plato p = (Plato) otro;
        return nombre.equalsIgnoreCase(p.nombre) && linea == p.linea;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre.toLowerCase(), linea);
    }

    @Override
    public String toString() {
        return nombre + " (" + linea + ", $" + precio + ", stock " + stock + ")";
    }
}
