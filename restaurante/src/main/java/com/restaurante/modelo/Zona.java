package com.restaurante.modelo;

/** Zonas de domicilio; cada una con su tarifa. */
public enum Zona {
    CENTRO(3000),
    NORTE(5000),
    SUR(6000),
    ORIENTE(8000);

    private final double tarifa;

    Zona(double valorTarifa) {
        tarifa = valorTarifa;
    }

    public double getTarifa() {
        return tarifa;
    }
}
