package br.com.alura.leilao.dao;

import org.springframework.stereotype.Repository;

import br.com.alura.leilao.model.Usuario;
import jakarta.persistence.EntityManager;

@Repository
public class UsuarioDao {


	private EntityManager em;
	
	public UsuarioDao( EntityManager em) {
		this.em = em;
	}
	

	public Usuario buscarPorUsername(String username) {
		 Usuario usuarioEncontrado = em.createQuery("SELECT u FROM Usuario u WHERE u.nome = :username", Usuario.class)
				.setParameter("username", username)
				.getSingleResult();
		 
		 return usuarioEncontrado;
	}

	public void deletar(Usuario usuario) {
		em.remove(usuario);
	}

}
