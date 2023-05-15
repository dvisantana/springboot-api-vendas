package com.xbrain.api.vendascontrol.models;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.cglib.core.Local;

public class VendaDto {

    private double valor;
    private long vendedor;

    //Getters & Setters
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }

    public long getVendedor() {
        return vendedor;
    }
    public void setVendedor(long vendedor) {
        this.vendedor = vendedor;
    }

}
