package io.gestionconges.spring.daosImpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.gestionconges.spring.AuthEx.AuthEx;
import io.gestionconges.spring.daos.AuthExDao;
@Repository
public class AuthExDaoImpl implements AuthExDao {
	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public void add(AuthEx authEx) {
		sessionFactory.getCurrentSession().saveOrUpdate(authEx);
	}

	@Override
	public void edit(AuthEx authEx) {
		sessionFactory.getCurrentSession().merge(authEx);

	}

	@Override
	public List<AuthEx> getAll() {
		return sessionFactory.getCurrentSession().createSQLQuery("select * from authex").getResultList();
	}

	@Override
	public AuthEx getOne(int id) {
		return (AuthEx) sessionFactory.getCurrentSession().get(AuthEx.class, id);
	}

	@Override
	public void delete(int id) {
		sessionFactory.getCurrentSession().delete(getOne(id));
	}
}
