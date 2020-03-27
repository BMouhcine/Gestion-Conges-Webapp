package io.gestionconges.spring.daosImpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.gestionconges.spring.conges.HistoriqueConges;
import io.gestionconges.spring.conges.HistoriqueCongesPK;
import io.gestionconges.spring.daos.CongesDao;
@Repository
public class CongeDaosImpl implements CongesDao {
	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public void add(HistoriqueConges historiqueConges) {
		sessionFactory.getCurrentSession().saveOrUpdate(historiqueConges);

	}

	@Override
	public void edit(HistoriqueConges historiqueConges) {
		sessionFactory.getCurrentSession().merge(historiqueConges);

	}

	@Override
	public List<HistoriqueConges> getAll() {
		return sessionFactory.getCurrentSession().createSQLQuery("select * from conges").getResultList();
	}

	@Override
	public HistoriqueConges getOne(int id) {
		return (HistoriqueConges) sessionFactory.getCurrentSession().get(HistoriqueConges.class, id);
	}

	@Override
	public void delete(int id) {
		sessionFactory.getCurrentSession().delete(getOne(id));
	}

}
