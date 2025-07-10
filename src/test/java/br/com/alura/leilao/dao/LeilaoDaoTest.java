package br.com.alura.leilao.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.builder.LeilaoBuilder;
import jakarta.persistence.EntityManager;

class LeilaoDaoTest {

	private LeilaoDao leilaoDao;
	private EntityManager em;

	@BeforeEach
	public void beforeEach() {
		this.em = JPAUtil.getEntityManager();

		this.leilaoDao = new LeilaoDao(em);

		this.em.getTransaction().begin();

	}

	@AfterEach
	public void afetEach() {
		em.getTransaction().rollback();
	}

	@Test
	public void deveAtualizarLeilao() {
		Usuario usuario = criarUsuario();
		Leilao leilao = criaLeilao("Notebook","2000", usuario);

		leilao = leilaoDao.salvar(leilao);

		leilao.setNome("Desktop");
		leilao.setValorInicial(new BigDecimal("3000.50"));

		leilao = leilaoDao.salvar(leilao);

		Leilao leilaoSalvo = leilaoDao.buscarPorId(leilao.getId());
		assertEquals("Desktop", leilaoSalvo.getNome());
		assertEquals(new BigDecimal("3000.50"), leilaoSalvo.getValorInicial());

	}

	private Leilao criaLeilao(String nomeLeiloado, String valorLeiloadoInicial, Usuario usuario) {
		return 
				new LeilaoBuilder()
				.comNome(nomeLeiloado)
				.comValorInicial(valorLeiloadoInicial)
				.comData(LocalDate.now())
				.comUsuario(usuario)
				.criar();
	}

	@Test
	public void deveCadastrarUmLeilao() {
		Usuario usuario = criarUsuario();
		Leilao leilao = new Leilao("Notebook", new BigDecimal("2000"), usuario);

		leilao = leilaoDao.salvar(leilao);

		Leilao leilaoSalvo = leilaoDao.buscarPorId(leilao.getId());
		assertNotNull(leilaoSalvo);

	}

	public Usuario criarUsuario() {
		Usuario usuario = new UsuarioBuilder()
				.comNome("Fulano")
				.comEmail("fulano@email.com")
				.comSenha("12345678")
				.criar();
				
				
		em.persist(usuario);

		return usuario;

	}

}
