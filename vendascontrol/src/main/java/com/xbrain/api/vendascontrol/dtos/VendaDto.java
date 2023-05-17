package com.xbrain.api.vendascontrol.dtos;

public class VendaDto {

    private double valor;
    private long vendedor;

    //Getters & Setters - Valor
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    //Getters & Setters - Vendedor
    public long getVendedor() {
        return vendedor;
    }
    public void setVendedor(long vendedor) {
        this.vendedor = vendedor;
    }

}
