package br.com.alura.leilao.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import br.com.alura.leilao.dao.UsuarioDao;
import br.com.alura.leilao.dto.NovoLanceDto;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.security.WebSecurityConfig;
import br.com.alura.leilao.service.LanceService;

@WebMvcTest(LanceController.class)
@Import(WebSecurityConfig.class)
class LanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LanceService lanceService;

    @MockitoBean
    private UsuarioDao usuarioDao;

    @Test
    void deveAdicionarLanceComSucesso() throws Exception {
        NovoLanceDto dto = new NovoLanceDto();
        dto.setLeilaoId(1L);
        when(lanceService.getLeilao(1L)).thenReturn(new Leilao());
        when(lanceService.propoeLance(any(NovoLanceDto.class), anyString())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/lances")
                .param("leilaoId", "1")
                .param("valor", "100.0")
                .with(user("usuario")) // substitui .principal(...)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/leiloes/1"))
                .andExpect(flash().attributeExists("message"));
    }

    @Test
    void deveRetornarErroQuandoLanceInvalido() throws Exception {
        NovoLanceDto dto = new NovoLanceDto();
        dto.setLeilaoId(1L);
        when(lanceService.getLeilao(1L)).thenReturn(new Leilao());
        when(lanceService.propoeLance(any(NovoLanceDto.class), anyString())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/lances")
                .param("leilaoId", "1")
                .param("valor", "100.0")
                .with(user("usuario")) // substitui .principal(...)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/leiloes/1"))
                .andExpect(flash().attributeExists("error"));
    }

     @Test
    void deveRetornarParaShowQuandoHaErrosDeValidacao() throws Exception {
        Leilao leilao = new Leilao();
        Usuario usuario = new Usuario();
        usuario.setNome("usuarioTeste");
        leilao.setUsuario(usuario);
        when(lanceService.getLeilao(1L)).thenReturn(leilao);
        // Não mocka propoeLance pois não deve ser chamado

        mockMvc.perform(MockMvcRequestBuilders.post("/lances")
                .param("leilaoId", "1")
                // Não envia o valor, para forçar erro de validação
                .with(user("usuario"))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("lance"))
                .andExpect(model().attributeExists("leilao"));
    }
}
