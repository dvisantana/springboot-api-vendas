package com.xbrain.api.vendascontrol.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbrain.api.vendascontrol.models.VendedorModel;
import com.xbrain.api.vendascontrol.repositories.ResumoVendedor;
import com.xbrain.api.vendascontrol.repositories.VendedorRepository;

@Service
public class VendedorService {
    
    @Autowired
    private VendedorRepository vendedorRepository;

        // ================ CRUD Padrão ================ //
        public Page<VendedorModel> findAll(Pageable pageable){
            return vendedorRepository.findAll(pageable);
        }
    
        public Optional<VendedorModel> findById(Long id){
            return vendedorRepository.findById(id);
        }
    
        @Transactional
        public VendedorModel save(VendedorModel vendedor){
            return vendedorRepository.save(vendedor);
        }
    
        @Transactional
        public void deleteById(Long id){
            vendedorRepository.deleteById(id);
        }
        // ================ CRUD Padrão ================ //

        public Iterable<ResumoVendedor> resumoVendedores(LocalDate dataIn, LocalDate dataFim){
            return vendedorRepository.resumoVendedores(dataIn.atStartOfDay(),dataFim.atStartOfDay());
        }

}
