package br.com.geraldoferraz.tyqt.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.geraldoferraz.testyourquery.config.EntityManagerProvider;

public class MyProvider implements EntityManagerProvider {

	public EntityManagerFactory getEntityManagerFactory() {
		return Persistence.createEntityManagerFactory("nutrition-2");
	}

}
