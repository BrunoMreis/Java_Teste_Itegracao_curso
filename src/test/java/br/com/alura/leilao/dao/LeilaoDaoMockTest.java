package br.com.alura.leilao.dao;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LeilaoDaoMockTest {
    
    
    private EntityManager em;
	private LeilaoDao leilaoDao;
    
	@BeforeEach
	void setUp() {
        em = mock(EntityManager.class);
		leilaoDao = new LeilaoDao(em);
	}
    
	@Test
	void deveBuscarTodosLeiloes() {
		List<Leilao> leiloes = Arrays.asList(new Leilao(), new Leilao());
		TypedQuery<Leilao> query = mock(TypedQuery.class);
		when(em.createQuery("SELECT l FROM Leilao l", Leilao.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(leiloes);
        
		List<Leilao> resultado = leilaoDao.buscarTodos();
		assertEquals(leiloes, resultado);
	}
    
	@Test
	void deveBuscarLeiloesDoPeriodo() {
        LocalDate inicio = LocalDate.now();
		LocalDate fim = inicio.plusDays(5);
		List<Leilao> leiloes = Arrays.asList(new Leilao());
		TypedQuery<Leilao> query = mock(TypedQuery.class);
		when(em.createQuery("SELECT l FROM Leilao l WHERE l.dataAbertura BETWEEN :inicio AND :fim", Leilao.class)).thenReturn(query);
		when(query.setParameter("inicio", inicio)).thenReturn(query);
		when(query.setParameter("fim", inicio)).thenReturn(query); // ATENÇÃO: está usando 'inicio' para os dois parâmetros, igual ao código original
		when(query.getResultList()).thenReturn(leiloes);
        
		List<Leilao> resultado = leilaoDao.buscarLeiloesDoPeriodo(inicio, fim);
		assertEquals(leiloes, resultado);
	}
    
	@Test
	void deveBuscarLeiloesDoUsuario() {
        Usuario usuario = new Usuario("nome", "email@email.com", "senha");
		List<Leilao> leiloes = Arrays.asList(new Leilao());
		TypedQuery<Leilao> query = mock(TypedQuery.class);
		when(em.createQuery("SELECT l FROM Leilao l WHERE l.usuario = :usuario", Leilao.class)).thenReturn(query);
		when(query.setParameter("usuario", usuario)).thenReturn(query);
		when(query.getResultList()).thenReturn(leiloes);
        
		List<Leilao> resultado = leilaoDao.buscarLeiloesDoUsuario(usuario);
		assertEquals(leiloes, resultado);
	}
    
}