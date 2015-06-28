package br.com.geraldoferraz.tyqt.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import br.com.geraldoferraz.tyqt.dominio.Person;

public class PersonDao {

	@PersistenceUnit
	private EntityManager em;
	
	public void save(Person person){
		em.persist(person);
	}
	
	public Person searchByName(String name){
		Query q = em.createQuery("select p from Person p where p.name like :name");
		q.setParameter("name", "%"+name+"%");
		Person retorno = null;
		retorno = (Person) q.getResultList().get(0);
		return retorno;
	}

}
