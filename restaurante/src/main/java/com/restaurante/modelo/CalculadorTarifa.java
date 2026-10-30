package com.restaurante.modelo;

/**
 * Interfaz funcional propia (requisito 7.b).
 * Permite cambiar la forma de calcular la tarifa de un domicilio con una lambda.
 */
@FunctionalInterface
public interface CalculadorTarifa {

    double tarifaPara(Zona zona);
}
