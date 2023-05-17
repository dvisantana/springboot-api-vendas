package com.xbrain.api.vendascontrol.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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

import com.xbrain.api.vendascontrol.dtos.VendedorDto;
import com.xbrain.api.vendascontrol.models.VendaModel;
import com.xbrain.api.vendascontrol.models.VendedorModel;
import com.xbrain.api.vendascontrol.repositories.VendedorRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class VendedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VendedorRepository vendedorRepository;



    // =============== Testes para resumo de vendededores ===============
    @Test
    public void listarResumoVendedor_sucesso() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
        .get("/vendedores/resumo?dataInicio=2023-01-01&dataFim=2023-12-31")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(vendedorRepository.findAll().size())));

        // Se utilizar paginação:
        // .andExpect(MockMvcResultMatchers.jsonPath("$['totalElements']", is(vendedorRepository.findAll().size())));
    }
   
    @Test
    public void listarResumoVendedor_DataInicioInferiorDataFim() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
        .get("/vendedores/resumo?dataInicio=2023-01-01&dataFim=2022-01-01")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotAcceptable());
    }
    
    @Test
    public void listarResumoVendedor_ParametrosNaoInformados() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
        .get("/vendedores/resumo")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotAcceptable());
    }
    
    @Test
    public void listarResumoVendedor_ParametrosVazios() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
        .get("/vendedores/resumo?dataInicio=&dataFim=")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotAcceptable());
    }
    
    @Test
    public void listarResumoVendedor_ParametrosInvalidos() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
        .get("/vendedores/resumo?dataInicio=01/01/2023&dataFim=31/12/2023")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotAcceptable());
    }
    // =============== Testes para resumo de vendededores ===============



    // ============== Testes para listagem de vendededores ==============
    @Test
    public void listarTodosVendedor_sucesso() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
        .get("/vendedores/listar")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$['totalElements']", is(vendedorRepository.findAll().size())));
    }
    
    @Test
    public void listarUmVendedor_sucesso() throws Exception{

        VendedorModel newVendedor = new VendedorModel();
        newVendedor.setNome("Victor Beghini");
        vendedorRepository.save(newVendedor);
        long idVendedor = vendedorRepository.findAll().get(0).getId();

        mockMvc.perform(MockMvcRequestBuilders
        .get("/vendedores/"+idVendedor)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", notNullValue()))
        .andExpect(jsonPath("$.nome", is("Victor Beghini")));

        vendedorRepository.deleteById(idVendedor);
    }

    @Test
    public void listarUmVendedor_naoEncontrado() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
        .get("/vendedores/0")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }
    // ============== Testes para listagem de vendededores ==============



    // ============== Testes para cadastro de vendededores ==============    
    @Test
    public void cadastrarVendedor_sucesso() throws Exception{
        JSONObject data = new JSONObject();
        data.put("nome", "Davi Santana");
        
        mockMvc.perform(post("/vendedores/cadastrar")
                .content(String.valueOf(data))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        List<VendedorModel> vendedorList = vendedorRepository.findAll();
        Assertions.assertEquals("Davi Santana", vendedorList.get(0).getNome());    
        vendedorRepository.deleteById(vendedorList.get(0).getId());   
    }
    
    @Test
    public void cadastrarVendedor_NomeNull() throws Exception{
        JSONObject data = new JSONObject();
        data.put("nome", "");
        
        mockMvc.perform(post("/vendedores/cadastrar")
                .content(String.valueOf(data))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }
    
    @Test
    public void cadastrarVendedor_NomeInferior2Caracteres() throws Exception{
        JSONObject data = new JSONObject();
        data.put("nome", "z");
        
        mockMvc.perform(post("/vendedores/cadastrar")
                .content(String.valueOf(data))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }
    // ============== Testes para cadastro de vendededores ==============    



    // ================ Testes para deletar vendededores ================
    @Test
    public void DeletarVendedor_sucesso() throws Exception{

        VendedorModel newVendedor = new VendedorModel();
        newVendedor.setNome("Victor Beghini");
        vendedorRepository.save(newVendedor);
        long id = vendedorRepository.findAll().get(0).getId();

        mockMvc.perform(MockMvcRequestBuilders
        .delete("/vendedores/deletar/"+id)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }
   
    @Test
    public void DeletarVendedor_VendedorNaoEncontrado() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
        .delete("/vendedores/deletar/0")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }
    // ================ Testes para deletar vendededores ================



    // ================ Testes para alterar vendededores ================
    @Test
    public void AlterarVendedor_sucesso() throws Exception{
        VendedorModel newVendedor = new VendedorModel();
        newVendedor.setNome("Dvi Sntana");
        vendedorRepository.save(newVendedor);
        long id = vendedorRepository.findAll().get(0).getId();
        
        JSONObject data = new JSONObject();
        data.put("nome", "Davi Santana");
        
        mockMvc.perform(put("/vendedores/alterar/"+id)
                .content(String.valueOf(data))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<VendedorModel> vendedorList = vendedorRepository.findAll();
        Assertions.assertEquals("Davi Santana", vendedorList.get(0).getNome());    
        vendedorRepository.deleteById(id);   
    }
    
    @Test
    public void AlterarVendedor_VendedorNaoEncontrado() throws Exception{
        VendedorModel newVendedor = new VendedorModel();
        newVendedor.setNome("Dvi Sntana");
        vendedorRepository.save(newVendedor);
        long id = vendedorRepository.findAll().get(0).getId();
        
        JSONObject data = new JSONObject();
        data.put("nome", "Davi Santana");
        
        mockMvc.perform(put("/vendedores/alterar/0")
                .content(String.valueOf(data))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); 
        vendedorRepository.deleteById(id);   
    }
    
    @Test
    public void AlterarVendedor_NomeInvalido() throws Exception{
        VendedorModel newVendedor = new VendedorModel();
        newVendedor.setNome("Dvi Sntana");
        vendedorRepository.save(newVendedor);
        long id = vendedorRepository.findAll().get(0).getId();
        
        JSONObject data = new JSONObject();
        data.put("nome", "");
        
        mockMvc.perform(put("/vendedores/alterar/"+id)
                .content(String.valueOf(data))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
        vendedorRepository.deleteById(id);   
    }
    // ================ Testes para alterar vendededores ================
}