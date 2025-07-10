package br.com.alura.leilao.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(IndexController.class)
@AutoConfigureMockMvc(addFilters = false)
class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test   
    void deveRedirecionarParaLeiloes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/leiloes"));
    }
}
