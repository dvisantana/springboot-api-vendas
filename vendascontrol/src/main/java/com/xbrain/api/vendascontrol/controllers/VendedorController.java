package com.xbrain.api.vendascontrol.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xbrain.api.vendascontrol.models.VendaModel;
import com.xbrain.api.vendascontrol.models.VendedorModel;
import com.xbrain.api.vendascontrol.services.VendedorService;

@RestController
@RequestMapping("/vendedor")
public class VendedorController {
    
    @Autowired
    private VendedorService vendedorService;

    // ================ Metodos GET ================ //
    @GetMapping("/listar")
    private ResponseEntity<Page<VendedorModel>> listarTodasVendas(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(vendedorService.findAll(pageable));
    }
    @GetMapping("/listar/{id}")
    private ResponseEntity<Object> listarVendedorPorId(@PathVariable(value = "id") Long id){
        Optional<VendedorModel> vendedorOptional = vendedorService.findById(id);
        if(!vendedorOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendedor não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(vendedorOptional.get());
    }
    // ================ Metodos GET ================ //
    
    // ================ Metodos POST ================ //
    @PostMapping("/cadastrar")
    private ResponseEntity<Object> cadastrarVendedor(@RequestBody VendedorModel vendedor){
        return ResponseEntity.status(HttpStatus.CREATED).body(vendedorService.save(vendedor));
    }
    // ================ Metodos POST ================ //

    // ================ Metodos PUT ================ //
    @PutMapping("/alterar/{id}")
    private ResponseEntity<Object> alterarVendedor(@PathVariable(value = "id") Long id, @RequestBody VendedorModel vendedorDTO){
        Optional<VendedorModel> vendedorOptional = vendedorService.findById(id);
        if(!vendedorOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendedor não encontrado!");
        }

        var vendedor = vendedorOptional.get();
        vendedor.setNome(vendedorDTO.getNome());
        return ResponseEntity.status(HttpStatus.OK).body(vendedorService.save(vendedor));
    }
    // ================ Metodos PUT ================ //

    // =============== Metodos DELETE =============== //
    @DeleteMapping("/deletar/{id}")
    private ResponseEntity<Object> deletarVendedor(@PathVariable(value = "id") Long id){
        Optional<VendedorModel> vendedorOptional = vendedorService.findById(id);
        if(!vendedorOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendedor não encontrado!");
        }
        vendedorService.deleteById(id);        
        return ResponseEntity.status(HttpStatus.OK).body("Vendedor deletado com sucesso!");
    }
    // =============== Metodos DELETE =============== //


}
