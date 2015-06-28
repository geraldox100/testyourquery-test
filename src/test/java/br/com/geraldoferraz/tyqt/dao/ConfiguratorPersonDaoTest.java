package br.com.geraldoferraz.tyqt.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.geraldoferraz.testyourquery.TestYourQueryRunner;
import br.com.geraldoferraz.testyourquery.annotations.Configurator;
import br.com.geraldoferraz.testyourquery.annotations.Dao;
import br.com.geraldoferraz.testyourquery.config.Configuration;
import br.com.geraldoferraz.testyourquery.config.ConfigurationFactory;
import br.com.geraldoferraz.tyqt.dominio.Person;

@RunWith(TestYourQueryRunner.class)
public class ConfiguratorPersonDaoTest {

	@Configurator
	public static Configuration configuration() {
		return new ConfigurationFactory()
			.searchEntitiesAt("br.com.geraldoferraz")
			.withSessionPerTestMode()
			.shouldShowSQL()
			.build();
	}
	
	@PersistenceContext
	private EntityManager em;

	@Dao
	private PersonDao dao;

	@Before
	public void before() {
		Person c1 = new Person("Geraldo");
		Person c2 = new Person("Michel");
		Person c3 = new Person("Gabriela");
		Person c4 = new Person("Agnelito");

		em.persist(c1);
		em.persist(c2);
		em.persist(c3);
		em.persist(c4);
		
		verifyListSize();
	}

	@Test
	public void searchByANameThatExists() {
		Person geraldo = dao.searchByName("Geraldo");

		assertNotNull(geraldo);
		assertEquals(geraldo.getNome(), "Geraldo");
		
		verifyListSize();
	}

	@Test
	public void searchByPartOfANameThatExists() {
		Person geraldo = dao.searchByName("ich");

		assertNotNull(geraldo);
		assertEquals(geraldo.getNome(), "Michel");
		
		verifyListSize();
	}

	@Test(expected = Exception.class)
	public void searchByANameThatDontExist() {
		verifyListSize();
		dao.searchByName("sbrables");
		
	}
	
	@SuppressWarnings("unchecked")
	private void verifyListSize() {
		List<Person> resultList = em.createQuery("select p from Person p").getResultList();
		assertEquals(4, resultList.size());
	}

}
