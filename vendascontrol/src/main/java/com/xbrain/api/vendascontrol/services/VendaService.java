package com.xbrain.api.vendascontrol.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbrain.api.vendascontrol.dtos.VendaDto;
import com.xbrain.api.vendascontrol.models.VendaModel;
import com.xbrain.api.vendascontrol.repositories.VendaRepository;

@Service
public class VendaService {
    
    @Autowired
    private VendaRepository vendaRepository;

    // ================ CRUD Padrão ================ //
    public Page<VendaModel> findAll(Pageable pageable){
        return vendaRepository.findAll(pageable);
    }

    public Optional<VendaModel> findById(Long id){
        return vendaRepository.findById(id);
    }

    @Transactional
    public VendaModel save(VendaModel venda){
        return vendaRepository.save(venda);
    }

    @Transactional
    public void deleteById(Long id){
        vendaRepository.deleteById(id);
    }
    // ================ CRUD Padrão ================ //
}
