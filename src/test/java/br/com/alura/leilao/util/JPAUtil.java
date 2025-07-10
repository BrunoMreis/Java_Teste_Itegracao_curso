package br.com.alura.leilao.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
	
	private static EntityManagerFactory factory = 
			Persistence.createEntityManagerFactory("tests");
	
	public static EntityManager getEntityManager() {
		
		return factory.createEntityManager();
	}

}
