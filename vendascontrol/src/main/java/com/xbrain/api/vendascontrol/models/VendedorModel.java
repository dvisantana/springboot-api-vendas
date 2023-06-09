package com.xbrain.api.vendascontrol.models;

import com.xbrain.api.vendascontrol.dtos.VendedorDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_vendedores")
@Getter
@Setter
public class VendedorModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    public VendedorModel(){}

    public VendedorModel(VendedorDto vendedorDto){
        this.nome = vendedorDto.getNome();
    }

}
