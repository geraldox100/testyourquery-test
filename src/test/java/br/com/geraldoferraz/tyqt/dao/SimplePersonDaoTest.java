package br.com.geraldoferraz.tyqt.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.geraldoferraz.testyourquery.TestYourQueryRunner;
import br.com.geraldoferraz.testyourquery.annotations.Configurator;
import br.com.geraldoferraz.testyourquery.annotations.Dao;
import br.com.geraldoferraz.testyourquery.config.Configuration;
import br.com.geraldoferraz.testyourquery.config.ConfigurationFactory;
import br.com.geraldoferraz.tyqt.dominio.Person;

@RunWith(TestYourQueryRunner.class)
public class SimplePersonDaoTest {
	
	@Configurator
	public static Configuration configurator(){
		return new ConfigurationFactory()
				.addEntity(Person.class)
				.build();
	}

	@PersistenceContext
	private static EntityManager em;

	@Dao
	private PersonDao dao;

	@BeforeClass
	public static void beforeClass() {
		Person c1 = new Person("Geraldo");
		Person c2 = new Person("Michel");
		Person c3 = new Person("Gabriela");
		Person c4 = new Person("Agnelito");

		em.persist(c1);
		em.persist(c2);
		em.persist(c3);
		em.persist(c4);
		
	}

	@Test
	public void searchByANameThatExists() {
		Person geraldo = dao.searchByName("Geraldo");

		assertNotNull(geraldo);
		assertEquals(geraldo.getNome(), "Geraldo");
	}

	@Test
	public void searchByPartOfANameThatExists() {
		Person geraldo = dao.searchByName("ich");

		assertNotNull(geraldo);
		assertEquals(geraldo.getNome(), "Michel");
	}

	@Test(expected = Exception.class)
	public void searchByANameThatDontExist() {
		dao.searchByName("sbrables");
	}

}
