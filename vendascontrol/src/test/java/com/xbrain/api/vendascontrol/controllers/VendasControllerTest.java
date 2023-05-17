package com.xbrain.api.vendascontrol.controllers;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.json.JSONObject;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.xbrain.api.vendascontrol.dtos.VendaDto;
import com.xbrain.api.vendascontrol.models.VendaModel;
import com.xbrain.api.vendascontrol.models.VendedorModel;
import com.xbrain.api.vendascontrol.repositories.VendaRepository;
import com.xbrain.api.vendascontrol.repositories.VendedorRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class VendasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private VendedorRepository vendedorRepository;



    // ============== Testes para cadastro de vendas (novas vendas) ==============
    @Test
    public void cadastrarVenda_sucesso() throws Exception{
        VendedorModel newVendedor = new VendedorModel();
        newVendedor.setNome("Victor Beghini");
        vendedorRepository.save(newVendedor);
        long idVendedor = vendedorRepository.findAll().get(0).getId();

        JSONObject data = new JSONObject();
        data.put("vendedor", idVendedor);
        data.put("valor", "45.90");
        
        mockMvc.perform(post("/vendas/cadastrar")
                .content(String.valueOf(data))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        List<VendaModel> vendas = vendaRepository.findAll();
        Assertions.assertEquals(45.9, vendas.get(0).getValor());
        Assertions.assertEquals(idVendedor, vendas.get(0).getVendedor().getId());

        vendaRepository.deleteById(vendas.get(0).getId());
        vendedorRepository.deleteById(idVendedor);
    }

    @Test
    public void cadastrarVenda_VendedorNaoEncontrado() throws Exception{
        JSONObject data = new JSONObject();
        data.put("vendedor", "999");
        data.put("valor", "45.90");
        
        mockMvc.perform(post("/vendas/cadastrar")
                .content(String.valueOf(data))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void cadastrarVenda_ValorInvalido() throws Exception{
        VendedorModel newVendedor = new VendedorModel();
        newVendedor.setNome("Victor Beghini");
        vendedorRepository.save(newVendedor);
        long idVendedor = vendedorRepository.findAll().get(0).getId();

        JSONObject data = new JSONObject();
        data.put("vendedor", idVendedor);
        data.put("valor", "-45.90");
        
        mockMvc.perform(post("/vendas/cadastrar")
                .content(String.valueOf(data))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
        
        vendedorRepository.deleteById(idVendedor);
    }
    // ============== Testes para cadastro de vendas (novas vendas) ==============



    // ===================== Testes para listagem de vendas =====================
    @Test
    public void listarTodasVenda_sucesso() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
        .get("/vendas/listar")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$['totalElements']", is(vendaRepository.findAll().size())));
    }
    
    @Test
    public void listarUmaVenda_sucesso() throws Exception{

        VendedorModel newVendedor = new VendedorModel();
        newVendedor.setNome("Victor Beghini");
        vendedorRepository.save(newVendedor);
        long idVendedor = vendedorRepository.findAll().get(0).getId();

        VendaDto vendaDto = new VendaDto();
        vendaDto.setValor(10.90);
        vendaDto.setVendedor(idVendedor);
        VendaModel newVenda = new VendaModel(vendaDto);
        vendaRepository.save(newVenda);
        long idVenda = vendaRepository.findAll().get(0).getId();

        mockMvc.perform(MockMvcRequestBuilders
        .get("/vendas/"+idVenda)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", notNullValue()))
        .andExpect(jsonPath("$.valor", is(10.90)));

        vendaRepository.deleteById(idVenda);
        vendedorRepository.deleteById(idVendedor);
    }

    @Test
    public void listarUmaVenda_naoEncontrada() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
        .get("/vendas/0")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }
    // ===================== Testes para listagem de vendas =====================



    // ======================= Testes para deletar vendas =======================
    @Test    
    public void DeletarVenda_sucesso() throws Exception{
        VendedorModel newVendedor = new VendedorModel();
        newVendedor.setNome("Victor Beghini");
        vendedorRepository.save(newVendedor);
        long idVendedor = vendedorRepository.findAll().get(0).getId();

        VendaDto vendaDto = new VendaDto();
        vendaDto.setValor(10.90);
        vendaDto.setVendedor(idVendedor);
        VendaModel newVenda = new VendaModel(vendaDto);
        vendaRepository.save(newVenda);
        long idVenda = vendaRepository.findAll().get(0).getId();

        mockMvc.perform(MockMvcRequestBuilders
        .delete("/vendas/deletar/"+idVenda)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

        vendaRepository.deleteById(idVenda);
        vendedorRepository.deleteById(idVendedor);
    }
    
    @Test
    public void DeletarVenda_VendaNaoEncontrada() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
        .delete("/vendas/deletar/0")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }
    // ======================= Testes para deletar vendas =======================



    // ======================= Testes para alterar vendas =======================
    @Test
    public void AlterarVenda_sucesso() throws Exception{
        VendedorModel newVendedor = new VendedorModel();
        newVendedor.setNome("Victor Beghini");
        vendedorRepository.save(newVendedor);
        long idVendedor = vendedorRepository.findAll().get(0).getId();

        VendaDto vendaDto = new VendaDto();
        vendaDto.setValor(10.90);
        vendaDto.setVendedor(idVendedor);
        VendaModel newVenda = new VendaModel(vendaDto);
        vendaRepository.save(newVenda);
        long idVenda = vendaRepository.findAll().get(0).getId();
        
        JSONObject data = new JSONObject();
        data.put("valor", 15.90);
        data.put("vendedor", idVendedor);
        
        mockMvc.perform(put("/vendas/alterar/"+idVenda)
                .content(String.valueOf(data))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<VendaModel> vendaList = vendaRepository.findAll();
        Assertions.assertEquals(15.90, vendaList.get(0).getValor());    
        
        vendaRepository.deleteById(idVenda);
        vendedorRepository.deleteById(idVendedor);   
    }
    
    @Test
    public void AlterarVenda_VendaNaoEncontrada() throws Exception{
        VendedorModel newVendedor = new VendedorModel();
        newVendedor.setNome("Victor Beghini");
        vendedorRepository.save(newVendedor);
        long idVendedor = vendedorRepository.findAll().get(0).getId();

        VendaDto vendaDto = new VendaDto();
        vendaDto.setValor(10.90);
        vendaDto.setVendedor(idVendedor);
        VendaModel newVenda = new VendaModel(vendaDto);
        vendaRepository.save(newVenda);
        long idVenda = vendaRepository.findAll().get(0).getId();
        
        JSONObject data = new JSONObject();
        data.put("valor", 15.90);
        data.put("vendedor", idVendedor);
        
        mockMvc.perform(put("/vendas/alterar/0")
                .content(String.valueOf(data))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); 

        vendaRepository.deleteById(idVenda);
        vendedorRepository.deleteById(idVendedor); 
    }
    
    @Test
    public void AlterarVenda_ValorInvalido() throws Exception{
        VendedorModel newVendedor = new VendedorModel();
        newVendedor.setNome("Victor Beghini");
        vendedorRepository.save(newVendedor);
        long idVendedor = vendedorRepository.findAll().get(0).getId();

        VendaDto vendaDto = new VendaDto();
        vendaDto.setValor(10.90);
        vendaDto.setVendedor(idVendedor);
        VendaModel newVenda = new VendaModel(vendaDto);
        vendaRepository.save(newVenda);
        long idVenda = vendaRepository.findAll().get(0).getId();
        
        JSONObject data = new JSONObject();
        data.put("valor", -15.90);
        data.put("vendedor", idVendedor);
        
        mockMvc.perform(put("/vendas/alterar/"+idVenda)
                .content(String.valueOf(data))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
                
        vendaRepository.deleteById(idVenda);
        vendedorRepository.deleteById(idVendedor);   
    }

    @Test
    public void AlterarVenda_VendedorNaoEncontrado() throws Exception{
        VendedorModel newVendedor = new VendedorModel();
        newVendedor.setNome("Victor Beghini");
        vendedorRepository.save(newVendedor);
        long idVendedor = vendedorRepository.findAll().get(0).getId();

        VendaDto vendaDto = new VendaDto();
        vendaDto.setValor(10.90);
        vendaDto.setVendedor(idVendedor);
        VendaModel newVenda = new VendaModel(vendaDto);
        vendaRepository.save(newVenda);
        long idVenda = vendaRepository.findAll().get(0).getId();
        
        JSONObject data = new JSONObject();
        data.put("valor", 15.90);
        data.put("vendedor", "999");
        
        mockMvc.perform(put("/vendas/alterar/"+idVenda)
                .content(String.valueOf(data))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
                
        vendaRepository.deleteById(idVenda);
        vendedorRepository.deleteById(idVendedor);   
    }
    // ======================= Testes para alterar vendas =======================
}
