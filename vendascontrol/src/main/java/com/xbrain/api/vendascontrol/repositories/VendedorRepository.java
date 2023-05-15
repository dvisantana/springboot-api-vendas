package com.xbrain.api.vendascontrol.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xbrain.api.vendascontrol.models.VendedorModel;

@Repository
public interface VendedorRepository extends JpaRepository<VendedorModel,Long>{

    @Query(
        "SELECT Vendedor.nome as nomeVendedor, COALESCE(COUNT(Vendas.id), 0) as totalVendas, ROUND(COALESCE(AVG(Vendas.valor), 0),2) as media FROM VendedorModel Vendedor LEFT JOIN VendaModel Vendas ON Vendedor.id = Vendas.vendedor AND data >= :data1 AND data <= :data2 GROUP BY nome"
        )
    Iterable<ResumoVendedor> resumoVendedores(LocalDateTime data1, LocalDateTime data2);

}
