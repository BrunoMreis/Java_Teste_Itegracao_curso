package br.com.alura.leilao.security;

import br.com.alura.leilao.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class LeilaoUserDetailsTest {

    @Test
    void deveRetornarAuthoritiesComRoleDoUsuario() {
        Usuario usuario = new Usuario();
        usuario.setRole("ROLE_USER");
        LeilaoUserDetails userDetails = new LeilaoUserDetails(usuario);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertEquals(1, authorities.size());
        assertEquals("ROLE_USER", authorities.iterator().next().getAuthority());
    }

    @Test
    void deveRetornarSenhaENomeCorretos() {
        Usuario usuario = new Usuario();
        usuario.setSenha("senha123");
        usuario.setNome("bruno");
        LeilaoUserDetails userDetails = new LeilaoUserDetails(usuario);
        assertEquals("senha123", userDetails.getPassword());
        assertEquals("bruno", userDetails.getUsername());
    }

    @Test
    void deveRetornarUsuarioOriginal() {
        Usuario usuario = new Usuario();
        LeilaoUserDetails userDetails = new LeilaoUserDetails(usuario);
        assertSame(usuario, userDetails.getUsuario());
    }

    @Test
    void deveRetornarTrueParaStatusDaConta() {
        Usuario usuario = new Usuario();
        LeilaoUserDetails userDetails = new LeilaoUserDetails(usuario);
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }
}
