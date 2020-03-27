package io.gestionconges.spring.daosImpl;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import io.gestionconges.spring.User.User;
import io.gestionconges.spring.daos.TopicDao;

@Repository
public class TopicDaoImpl implements TopicDao{
	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public void add(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}

	@Override
	public void edit(User user) {
		sessionFactory.getCurrentSession().update(user);
	}
	
	@Override 
	  public java.util.List getAll() {
		   java.util.List q = sessionFactory.getCurrentSession().createSQLQuery("select * from user").getResultList();
		   
		  return q;
	  }

	 

}
