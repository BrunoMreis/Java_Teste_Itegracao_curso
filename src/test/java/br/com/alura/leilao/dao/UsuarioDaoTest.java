package br.com.alura.leilao.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

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

		Assert.assertNotNull(this.user.getNome());
	}

	@Test
	void naoDeveEncontrarUsuarioPeloUsername() {

		Assert.assertThrows(NoResultException.class, () -> this.usuarioDao.buscarPorUsername("sicrano"));

	}

	@Test
	void deveDeletar() {
		Usuario usuario = this.user;
		this.usuarioDao.deletar(usuario);

		Assert.assertThrows(NoResultException.class, () -> this.usuarioDao.buscarPorUsername(usuario.getNome()));

	}

	public Usuario criarUsuario() {

		Usuario usuario = new UsuarioBuilder()
				.comNome("fulano")
				.comEmail("fulano@fulando.com.br")
				.comSenha("12345678")
				.criar();
		em.persist(usuario);

		return usuario;

	}

}
