package com.xbrain.api.vendascontrol.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xbrain.api.vendascontrol.dtos.VendaDto;
import com.xbrain.api.vendascontrol.models.VendaModel;
import com.xbrain.api.vendascontrol.models.VendedorModel;
import com.xbrain.api.vendascontrol.services.VendaService;
import com.xbrain.api.vendascontrol.services.VendedorService;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;
    @Autowired
    private VendedorService vendedorService;

    // ================ Metodos GET ================ //
    @GetMapping("/listar")
    private ResponseEntity<Page<VendaModel>> listarTodasVendas(@PageableDefault(page = 0, size = 10, sort = "data", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(vendaService.findAll(pageable));
    }
    @GetMapping("/{id}")
    private ResponseEntity<Object> listarVendaPorId(@PathVariable(value = "id") Long id){
        Optional<VendaModel> vendaOptional = vendaService.findById(id);
        if(!vendaOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venda não encontrada!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(vendaOptional.get());
    }
    // ================ Metodos GET ================ //

    // ================ Metodos POST ================ //
    @PostMapping("/cadastrar")
    private ResponseEntity<Object> cadastrarVenda(@RequestBody VendaDto vendaDTO){

        // Validações para tratar possiveis entradas inviaveis
        Optional<VendedorModel> vendedorOptional = vendedorService.findById(vendaDTO.getVendedor());
        if(vendaDTO.getValor() <= 0){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Valor informado inválido.");
        }
        if(!vendedorOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendedor não encontrado!");
        }
        var venda = new VendaModel(vendaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaService.save(venda));
    }
    // ================ Metodos POST ================ //

    // ================ Metodos PUT ================ //
    @PutMapping("/alterar/{id}")
    private ResponseEntity<Object> alterarVenda(@PathVariable(value = "id") Long id, @RequestBody VendaDto vendaDTO){

        // Validações para tratar possiveis entradas inviaveis
        Optional<VendaModel> vendaOptional = vendaService.findById(id);
        Optional<VendedorModel> vendedorOptional = vendedorService.findById(vendaDTO.getVendedor());
        if(!vendaOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venda não encontrada!");
        }        
        if(vendaDTO.getValor() <= 0){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Valor informado inválido.");
        }
        if(!vendedorOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendedor não encontrado!");
        }

        var venda = new VendaModel(vendaDTO);
        venda.setId(vendaOptional.get().getId());
        venda.setData(vendaOptional.get().getData());
        return ResponseEntity.status(HttpStatus.OK).body(vendaService.save(venda));
    }
    // ================ Metodos PUT ================ //

    // =============== Metodos DELETE =============== //
    @DeleteMapping("/deletar/{id}")
    private ResponseEntity<Object> deletarVenda(@PathVariable(value = "id") Long id){
        Optional<VendaModel> vendaOptional = vendaService.findById(id);
        if(!vendaOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venda não encontrada!");
        }
        vendaService.deleteById(id);        
        return ResponseEntity.status(HttpStatus.OK).body("Venda deletada com sucesso!");
    }
    // =============== Metodos DELETE =============== //
}
