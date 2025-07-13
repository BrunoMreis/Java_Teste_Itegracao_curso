package br.com.alura.leilao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.alura.leilao.dao.LanceDao;
import br.com.alura.leilao.dao.LeilaoDao;
import br.com.alura.leilao.dao.UsuarioDao;
import br.com.alura.leilao.dto.NovoLanceDto;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.GeradorSenhaService;

class LanceServiceTest {

    @Mock
    private LanceDao lanceDao;
    @Mock
    private UsuarioDao usuarioDao;
    @Mock
    private LeilaoDao leilaoDao;

    @InjectMocks
    private LanceService lanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveAceitarLanceValido() {
        NovoLanceDto dto = mock(NovoLanceDto.class);
        Usuario usuario = new Usuario("usuarioTeste", "email@email.com", GeradorSenhaService.geraStringSegura(16));
        Leilao leilao = mock(Leilao.class);
        Lance lance = mock(Lance.class);

        when(usuarioDao.buscarPorUsername("usuarioTeste")).thenReturn(usuario);
        when(dto.toLance(usuario)).thenReturn(lance);
        when(dto.getLeilaoId()).thenReturn(1L);
        when(leilaoDao.buscarPorId(1L)).thenReturn(leilao);
        when(leilao.propoe(lance)).thenReturn(true);

        boolean resultado = lanceService.propoeLance(dto, "usuarioTeste");

        assertTrue(resultado);
        verify(lanceDao).salvar(lance);
    }

    @Test
    void deveRejeitarLanceInvalido() {
        NovoLanceDto dto = mock(NovoLanceDto.class);
        Usuario usuario = new Usuario("usuarioTeste", "email@email.com", GeradorSenhaService.geraStringSegura(16));
        Leilao leilao = mock(Leilao.class);
        Lance lance = mock(Lance.class);

        when(usuarioDao.buscarPorUsername("usuarioTeste")).thenReturn(usuario);
        when(dto.toLance(usuario)).thenReturn(lance);
        when(dto.getLeilaoId()).thenReturn(1L);
        when(leilaoDao.buscarPorId(1L)).thenReturn(leilao);
        when(leilao.propoe(lance)).thenReturn(false);

        boolean resultado = lanceService.propoeLance(dto, "usuarioTeste");

        assertFalse(resultado);
        verify(lanceDao, never()).salvar(any());
    }

    @Test
    void deveBuscarLeilaoPorId() {
        Leilao leilao = mock(Leilao.class);
        when(leilaoDao.buscarPorId(10L)).thenReturn(leilao);

        Leilao resultado = lanceService.getLeilao(10L);

        assertEquals(leilao, resultado);
        verify(leilaoDao).buscarPorId(10L);
    }
}
