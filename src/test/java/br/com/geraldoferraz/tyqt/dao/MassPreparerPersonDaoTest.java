package br.com.geraldoferraz.tyqt.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.geraldoferraz.testyourquery.TestYourQueryRunner;
import br.com.geraldoferraz.testyourquery.annotations.Dao;
import br.com.geraldoferraz.testyourquery.annotations.JDBCConnection;
import br.com.geraldoferraz.testyourquery.annotations.MassPreparer;
import br.com.geraldoferraz.tyqt.dominio.Person;

@RunWith(TestYourQueryRunner.class)
public class MassPreparerPersonDaoTest {

	@PersistenceContext
	private static EntityManager em;

	@Dao
	private PersonDao dao;
	
	@JDBCConnection
	private Connection conn;

	@Test
	@MassPreparer(PersonDaoPreparer.class)
	public void searchByANameThatExists() {
		Person geraldo = dao.searchByName("Geraldo");

		assertNotNull(geraldo);
		assertEquals(geraldo.getNome(), "Geraldo");

		verifyListSize();
	}

	@Test
	@MassPreparer(PersonDaoPreparer.class)
	public void searchByPartOfANameThatExists() {
		Person geraldo = dao.searchByName("ich");

		assertNotNull(geraldo);
		assertEquals(geraldo.getNome(), "Michel");

		verifyListSize();
	}

	@Test(expected = Exception.class)
	@MassPreparer(PersonDaoPreparer.class)
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
