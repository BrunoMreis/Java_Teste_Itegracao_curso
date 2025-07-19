
package br.com.alura.leilao.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LanceTest {

    @Test
    @DisplayName("Deve criar Lance válido com usuário e valor positivo")
    void deveCriarLanceValidoComUsuarioEValor() {
        Usuario usuario = new Usuario(); // Use um mock se necessário
        BigDecimal valor = new BigDecimal("100.00");

        Lance lance = new Lance(usuario, valor);

        assertEquals(usuario, lance.getUsuario());
        assertEquals(valor, lance.getValor());
        assertNotNull(lance.getData());
        assertTrue(lance.getData().isEqual(LocalDate.now()));
    }

    @Test
    @DisplayName("Deve lançar exceção para valor zero ou negativo")
    void deveLancarExcecaoParaValorInvalido() {
        Usuario usuario = new Usuario();
        BigDecimal valorInvalido = new BigDecimal("-10");

        assertThrows(IllegalArgumentException.class, () -> new Lance(usuario, valorInvalido));
    }

    @Test
    @DisplayName("Deve criar Lance com construtor apenas com valor")
    void deveCriarLanceComValor() {
        BigDecimal valor = new BigDecimal("0.50");

        Lance lance = new Lance(valor);

        assertEquals(valor, lance.getValor());
        assertNull(lance.getUsuario());
    }

    @Test
    @DisplayName("Deve permitir modificar os atributos via setters")
    void deveModificarAtributosViaSetters() {
        Lance lance = new Lance(new BigDecimal("1.00"));
        Usuario usuario = new Usuario();
        Leilao leilao = new Leilao();
        LocalDate data = LocalDate.of(2020, 1, 1);
        Long id = 42L;

        lance.setId(id);
        lance.setUsuario(usuario);
        lance.setLeilao(leilao);
        lance.setData(data);

        assertEquals(id, lance.getId());
        assertEquals(usuario, lance.getUsuario());
        assertEquals(leilao, lance.getLeilao());
        assertEquals(data, lance.getData());
    }
}
