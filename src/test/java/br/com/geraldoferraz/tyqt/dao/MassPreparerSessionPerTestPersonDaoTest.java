package br.com.geraldoferraz.tyqt.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.geraldoferraz.testyourquery.TestYourQueryRunner;
import br.com.geraldoferraz.testyourquery.annotations.Configurator;
import br.com.geraldoferraz.testyourquery.annotations.Dao;
import br.com.geraldoferraz.testyourquery.annotations.MassPreparer;
import br.com.geraldoferraz.testyourquery.config.Configuration;
import br.com.geraldoferraz.testyourquery.config.ConfigurationFactory;
import br.com.geraldoferraz.tyqt.dominio.Person;

@RunWith(TestYourQueryRunner.class)
public class MassPreparerSessionPerTestPersonDaoTest {
	
	@Configurator
	public static Configuration configuration() {
		return new ConfigurationFactory()
			.searchEntitiesAt("br.com.geraldoferraz")
			.withSessionPerTestMode()
			.shouldShowSQL()
			.build();
	}

	@PersistenceContext
	private static EntityManager em;

	@Dao
	private PersonDao dao;

	@Test
	@MassPreparer(PersonDaoPreparer2.class)
	public void searchByANameThatExists() {
		Person geraldo = dao.searchByName("Geraldo");

		assertNotNull(geraldo);
		assertEquals(geraldo.getNome(), "Geraldo");

		verifyListSize();
	}

	@Test
	@MassPreparer(PersonDaoPreparer2.class)
	public void searchByPartOfANameThatExists() {
		Person geraldo = dao.searchByName("ich");

		assertNotNull(geraldo);
		assertEquals(geraldo.getNome(), "Michel");

		verifyListSize();
	}

	@Test(expected = Exception.class)
	@MassPreparer(PersonDaoPreparer2.class)
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
