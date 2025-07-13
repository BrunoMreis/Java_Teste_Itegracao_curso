package br.com.alura.leilao.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.alura.leilao.dao.LeilaoDao;
import br.com.alura.leilao.dao.UsuarioDao;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

@WebMvcTest(LeilaoController.class)
class LeilaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LeilaoDao leilaoDao;

    @MockBean
    private UsuarioDao usuarioDao;

    @Test
    void deveListarLeiloes() throws Exception {
        when(leilaoDao.buscarTodos()).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get("/leiloes").with(user("usuario")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("leiloes"))
                .andExpect(model().attributeExists("pricipalUsuarioLogado"));
    }

    @Test
    void deveMostrarFormularioDeNovoLeilao() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/leiloes/new").with(user("usuario")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attributeExists("leilao"));
    }

    @Test
    void deveMostrarFormularioDeEdicao() throws Exception {
        Leilao leilao = new Leilao();
        leilao.setDataAbertura(LocalDate.now()); 
        when(leilaoDao.buscarPorId(1L)).thenReturn(leilao);
        mockMvc.perform(MockMvcRequestBuilders.get("/leiloes/1/form").with(user("usuario")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attributeExists("leilao"));
    }

    @Test
    void deveMostrarDetalhesDoLeilao() throws Exception {
        Leilao leilao = new Leilao();
        Usuario usuario = new Usuario();
        usuario.setNome("usuarioTeste");
        leilao.setUsuario(usuario);
        when(leilaoDao.buscarPorId(1L)).thenReturn(leilao);
        mockMvc.perform(MockMvcRequestBuilders.get("/leiloes/1").with(user("usuario")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attributeExists("leilao"))
                .andExpect(model().attributeExists("lance"));
    }

    @Test
    void deveRetornarParaFormQuandoHaErrosDeValidacao() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/leiloes")
                .with(user("usuario"))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("leilao"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    void deveSalvarLeilaoComSucesso() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome("usuarioTeste");
        when(usuarioDao.buscarPorUsername(anyString())).thenReturn(usuario);
        when(leilaoDao.salvar(any(Leilao.class))).thenReturn(new Leilao());
        mockMvc.perform(MockMvcRequestBuilders.post("/leiloes")
                .param("nome", "Leilao Teste")
                .param("valorInicial", "100.0")
                .param("dataAbertura", "09/07/2025")
                .with(user("usuarioTeste"))
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/leiloes"))
                .andExpect(flash().attributeExists("message"));
    }
}
