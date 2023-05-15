package com.xbrain.api.vendascontrol.models;

import java.time.LocalDateTime;
import java.time.ZoneId;

// import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_vendas")
@Getter
@Setter
public class VendaModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private double valor;

    @Column(nullable = false)
    private LocalDateTime data;

    @OneToOne
    @JoinColumn(name = "id_vendedor", referencedColumnName = "id")
    private VendedorModel vendedor;

    public VendaModel(){}

    public VendaModel(VendaDto vendaCreateModel){
        this.valor = vendaCreateModel.getValor();
        this.data = LocalDateTime.now(ZoneId.of("UTC"));
        this.vendedor = new VendedorModel();
        this.vendedor.setId(vendaCreateModel.getVendedor());
    }
    

}
