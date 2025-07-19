package br.com.alura.leilao.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.GeradorSenhaService;
import br.com.alura.leilao.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

class UsuarioDaoTest {
	private UsuarioDao usuarioDao;
	private EntityManager em;
	private Usuario user;

	@BeforeEach
	public void beforeEach() {
		em = JPAUtil.getEntityManager();
		this.usuarioDao = new UsuarioDao(em);
		this.user = criarUsuario();
		em.getTransaction().begin();

	}

	@AfterEach
	public void afetEach() {
		em.getTransaction().rollback();
	}

	@Test
	void deveEncontrarUsuarioPeloUsername() {

		assertNotNull(this.user.getNome());
		Usuario usuarioEncontrado = this.usuarioDao.buscarPorUsername(this.user.getNome());
		assertNotNull(usuarioEncontrado);
	}

	@Test
	void naoDeveEncontrarUsuarioPeloUsername() {

		assertThrows(NoResultException.class, () -> this.usuarioDao.buscarPorUsername("sicrano"));

	}

	@Test
	void deveDeletar() {
		Usuario usuario = this.user;
		this.usuarioDao.deletar(usuario);

		assertThrows(NoResultException.class, () -> this.usuarioDao.buscarPorUsername(usuario.getNome()));

	}

	public Usuario criarUsuario() {

		Usuario usuario = new UsuarioBuilder()
				.comNome("fulano")
				.comEmail("fulano@fulando.com.br")
				.comSenha(GeradorSenhaService.geraStringSegura(16))
				.criar();
		em.persist(usuario);

		return usuario;

	}

}
