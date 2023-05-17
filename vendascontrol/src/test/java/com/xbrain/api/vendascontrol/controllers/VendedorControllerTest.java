package com.xbrain.api.vendascontrol.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.json.JSONObject;
import org.junit.Before;
import org.springframework.data.domain.Page;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.xbrain.api.vendascontrol.models.VendaModel;
import com.xbrain.api.vendascontrol.models.VendedorDto;
import com.xbrain.api.vendascontrol.models.VendedorModel;
import com.xbrain.api.vendascontrol.repositories.VendedorRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class VendedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VendedorRepository vendedorRepository;

    // @Mock
    // private VendedorRepository vendedorRepository;
    // @InjectMocks
    // private VendedorController vendaController;

    // @Before
    // public void setUp(){
    //     MockitoAnnotations.openMocks(this);
    //     this.mockMvc = MockMvcBuilders.standaloneSetup(vendaController).build();
    // }

    @Test
    public void listarTodosVendedores_sucesso() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
        .get("/vendedores/listar")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$['totalElements']", is(vendedorRepository.findAll().size())));
    }
    
    @Test
    public void listarUmVendedor_sucesso() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
        .get("/vendedores/341")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", notNullValue()))
        .andExpect(jsonPath("$.nome", is("Joao")));
    }

    @Test
    public void listarUmVendedor_naoEncontrado() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
        .get("/vendedores/76")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    public void cadastrarVendedor_sucesso() throws Exception{
        JSONObject data = new JSONObject();
        data.put("nome", "Jacinto");
        
        mockMvc.perform(post("/vendedores/cadastrar")
                .content(String.valueOf(data))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        List<VendedorModel> vendas = vendedorRepository.findAll();
        Assertions.assertEquals("Jacinto", vendas.get(0).getNome());
    }

    
}