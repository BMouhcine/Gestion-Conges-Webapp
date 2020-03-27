package io.gestionconges.spring.daosImpl;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import io.gestionconges.spring.daos.LigneCongesDao;
import io.gestionconges.spring.ligneConges.LigneConges;
@Repository
public class LigneCongesImpl implements LigneCongesDao{
	
		@Autowired
		private SessionFactory sessionFactory;
		@Override
		public void add(LigneConges ligneConges) {
			sessionFactory.getCurrentSession().saveOrUpdate(ligneConges);

		}

		@Override
		public void edit(LigneConges ligneConges) {
			sessionFactory.getCurrentSession().merge(ligneConges);

		}

		@Override
		public Criteria getAll() {
			return sessionFactory.getCurrentSession().createCriteria(LigneConges.class);
		}

		@Override
		public LigneConges getOne(int id) {
			return (LigneConges) sessionFactory.getCurrentSession().get(LigneConges.class, id);
		}

		@Override
		public void delete(int id) {
			sessionFactory.getCurrentSession().delete(getOne(id));
		}



}
