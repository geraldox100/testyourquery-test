package br.com.geraldoferraz.tyqt.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.geraldoferraz.testyourquery.config.EntityManagerProvider;

public class MyProvider implements EntityManagerProvider {

	private static final String persistenceUnit = "nutrition-2";

	public EntityManagerFactory getEntityManagerFactory() {
		return Persistence.createEntityManagerFactory(persistenceUnit);
	}

	public String getPersistenceUnit() {
		return persistenceUnit;
	}


}
