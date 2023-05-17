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

    @Test
    public void cadastrarVendas_sucesso() throws Exception{
        JSONObject data = new JSONObject();
        data.put("vendedor", "341");
        data.put("valor", "45.90");
        
        mockMvc.perform(post("/vendas/cadastrar")
                .content(String.valueOf(data))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        List<VendaModel> vendas = vendaRepository.findAll();
        Assertions.assertEquals(45.9, vendas.get(0).getValor());
        Assertions.assertEquals(341, vendas.get(0).getVendedor().getId());
    }

    
}
