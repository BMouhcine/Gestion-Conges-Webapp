package io.gestionconges.spring.daosImpl;


import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import io.gestionconges.spring.daos.CongesDao;
import io.gestionconges.spring.daos.CongesMaladieDao;
import io.gestionconges.spring.CongesMaladie.*;
@Repository
public class CongesMaladieDaoImpl implements CongesMaladieDao {
	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public void add(CongesMaladie congesMaladie) {
		sessionFactory.getCurrentSession().saveOrUpdate(congesMaladie);
	}

	@Override
	public void edit(CongesMaladie congesMaladie) {
		sessionFactory.getCurrentSession().merge(congesMaladie);

	}

	@Override
	public List<CongesMaladie> getAll() {
		return sessionFactory.getCurrentSession().createSQLQuery("select * from conges_maladie").getResultList();
	}

	@Override
	public CongesMaladie getOne(int id) {
		return (CongesMaladie) sessionFactory.getCurrentSession().get(CongesMaladie.class, id);
	}

	@Override
	public void delete(int id) {
		sessionFactory.getCurrentSession().delete(getOne(id));
	}

}
