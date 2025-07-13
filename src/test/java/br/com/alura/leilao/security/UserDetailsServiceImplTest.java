package br.com.alura.leilao.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.alura.leilao.dao.UsuarioDao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.GeradorSenhaService;

class UserDetailsServiceImplTest {

    @Mock
    private UsuarioDao usuarioDao;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private Usuario usuario;
    private String nome;
    private String email;
    private String randoString;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        nome = UUID.randomUUID().toString();
        email = UUID.randomUUID().toString() + "@test.com";
        randoString = GeradorSenhaService.geraStringSegura(16);
        usuario = new Usuario(nome, email, randoString);

    }

    @Test
    void deveRetornarUserDetailsQuandoUsuarioExiste() {
        when(usuarioDao.buscarPorUsername(this.nome)).thenReturn(usuario);

        UserDetails userDetails = userDetailsService.loadUserByUsername(this.nome);

        assertNotNull(userDetails);
        assertEquals(this.nome, userDetails.getUsername());
        assertEquals(this.randoString, userDetails.getPassword());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
        when(usuarioDao.buscarPorUsername("naoexiste")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () ->
                userDetailsService.loadUserByUsername("naoexiste")
        );
    }




}
