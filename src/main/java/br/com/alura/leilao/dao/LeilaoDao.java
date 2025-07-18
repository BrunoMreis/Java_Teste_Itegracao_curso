package br.com.alura.leilao.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import jakarta.persistence.EntityManager;

@Repository
public class LeilaoDao {

	private EntityManager em;

	
	public LeilaoDao(EntityManager em) {
		this.em = em;
	}

	public Leilao salvar(Leilao leilao) {
		return em.merge(leilao);
	}

	public Leilao buscarPorId(Long id) {
		return em.find(Leilao.class, id);
	}

	public List<Leilao> buscarTodos() {
		return em.createQuery("SELECT l FROM Leilao l", Leilao.class).getResultList();
	}

	public List<Leilao> buscarLeiloesDoPeriodo(LocalDate inicio, LocalDate fim) {
		return em.createQuery("SELECT l FROM Leilao l WHERE l.dataAbertura BETWEEN :inicio AND :fim", Leilao.class)
				.setParameter("inicio", inicio).setParameter("fim", inicio).getResultList();
	}

	public List<Leilao> buscarLeiloesDoUsuario(Usuario usuario) {
		return em.createQuery("SELECT l FROM Leilao l WHERE l.usuario = :usuario", Leilao.class)
				.setParameter("usuario", usuario).getResultList();
	}

}
