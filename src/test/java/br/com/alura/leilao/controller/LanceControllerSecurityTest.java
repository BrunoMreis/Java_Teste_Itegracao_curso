package br.com.alura.leilao.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.alura.leilao.dto.NovoLanceDto;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.service.LanceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(LanceController.class)
class LanceControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LanceService lanceService;

    @Test
    void deveNegarPostSemCsrf() throws Exception {
        when(lanceService.getLeilao(1L)).thenReturn(new Leilao());
        when(lanceService.propoeLance(any(NovoLanceDto.class), anyString())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/lances")
                .param("leilaoId", "1")
                .param("valor", "100.0")
                .with(user("usuario")))
                .andExpect(status().isForbidden()); // 403 esperado sem CSRF
    }

    @Test
    void deveNegarPostSemAutenticacao() throws Exception {
        when(lanceService.getLeilao(1L)).thenReturn(new Leilao());
        when(lanceService.propoeLance(any(NovoLanceDto.class), anyString())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/lances")
                .param("leilaoId", "1")
                .param("valor", "100.0")
                .with(csrf()))
                .andExpect(status().isUnauthorized()); // 401 esperado sem autenticação
               
    }

    @Test
    void deveNegarGetNaoPermitido() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lances"))
                .andExpect(status().is4xxClientError()); // 403 ou 405
    }
}
