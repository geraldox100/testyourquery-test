package br.com.geraldoferraz.tyqt.dao;

import javax.persistence.EntityManager;

import br.com.geraldoferraz.testyourquery.util.ScriptRunner;
import br.com.geraldoferraz.tyqt.dominio.Person;

public class PersonDaoPreparer implements ScriptRunner {

	public void run(EntityManager em) {
		em.createQuery("delete from Person").executeUpdate();
		Person c1 = new Person("Geraldo");
		Person c2 = new Person("Michel");
		Person c3 = new Person("Gabriela");
		Person c4 = new Person("Agnelito");

		em.persist(c1);
		em.persist(c2);
		em.persist(c3);
		em.persist(c4);

	}

}
