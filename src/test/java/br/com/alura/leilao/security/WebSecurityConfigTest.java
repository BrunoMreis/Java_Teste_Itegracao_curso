package br.com.alura.leilao.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.LocaleResolver;


class WebSecurityConfigTest {

    private final WebSecurityConfig config = new WebSecurityConfig();

    @Test
    void deveCriarBeansPrincipais() {
        // Testa se os beans são criados corretamente
        UserDetailsService uds = config.userDetailsService();
        assertNotNull(uds);
        assertTrue(uds instanceof UserDetailsServiceImpl);

        BCryptPasswordEncoder encoder = config.passwordEncoder();
        assertNotNull(encoder);
        assertTrue(encoder.matches("senha", encoder.encode("senha")));

        DaoAuthenticationProvider provider = config.authenticationProvider();
        assertNotNull(provider);
    }

    @Test
    void deveCriarLocaleResolver() {
        LocaleResolver resolver = config.localeResolver();
        assertNotNull(resolver);
        assertEquals("pt", resolver.resolveLocale(new MockHttpServletRequest()).getLanguage());
    }

        @Test
    void deveConfigurarSecurityFilterChainCorretamente() throws Exception {
        // Arrange
        HttpSecurity http = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);
        WebSecurityConfig config = new WebSecurityConfig();

        // Act
        SecurityFilterChain chain = config.securityFilterChain(http);

        // Assert
        assertNotNull(chain);

    }

}
