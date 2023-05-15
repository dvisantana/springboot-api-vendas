package com.xbrain.api.vendascontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xbrain.api.vendascontrol.models.VendedorModel;

@Repository
public interface VendedorRepository extends JpaRepository<VendedorModel,Long>{}
