package com.restaurante.modelo;

import java.time.LocalDateTime;

/** Pedido a domicilio: tarifa según la zona y un repartidor asignado. */
public class PedidoDomicilio extends Pedido {

    private final Zona zona;
    private final String direccion;
    private final CalculadorTarifa calculador;
    private Repartidor repartidor;

    public PedidoDomicilio(int id, LocalDateTime momento, Zona valorZona,
                           String valorDireccion, CalculadorTarifa valorCalculador) {
        super(id, momento);
        zona = valorZona;
        direccion = valorDireccion;
        calculador = valorCalculador;
    }

    public Zona getZona() {
        return zona;
    }

    public String getDireccion() {
        return direccion;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(Repartidor asignado) {
        repartidor = asignado;
    }

    @Override
    public double costoAdicional() {
        return calculador.tarifaPara(zona);
    }

    @Override
    public String getTipo() {
        return "Domicilio " + zona;
    }
}
