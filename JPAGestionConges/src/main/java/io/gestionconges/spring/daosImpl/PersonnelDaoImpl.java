package io.gestionconges.spring.daosImpl;

import org.apache.naming.java.javaURLContextFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.gestionconges.spring.Personnel.Personnel;
import io.gestionconges.spring.daos.PersonnelDao;
import net.sf.ehcache.CacheOperationOutcomes.GetAllOutcome;
@Repository
public class PersonnelDaoImpl implements PersonnelDao {

	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public void add(Personnel personnel) {
		sessionFactory.getCurrentSession().saveOrUpdate(personnel);
	}

	@Override
	public void edit(Personnel personnel) {
		sessionFactory.getCurrentSession().update(personnel);
	}
	
	@Override 
	  public java.util.List getAll() {
		java.util.List q = sessionFactory.getCurrentSession().createSQLQuery("select cin,nom,prenom,grade,division,service,jours_restants from Personnel").getResultList();
		  return q;
	  }
	@Override
	 public java.util.List getDemandeurCIN(){
		java.util.List q = sessionFactory.getCurrentSession().createSQLQuery("select CIN from Personnel").getResultList();
		return q;
	}
	 public java.util.List getDemandeurName(){
			java.util.List q = sessionFactory.getCurrentSession().createSQLQuery("select Nom,Prenom from Personnel").getResultList();
			return q;
		}
	@Override
	public Personnel getOne(String CIN) {
		return (Personnel) sessionFactory.getCurrentSession().get(Personnel.class, CIN);
	}
	@Override
	public void delete(String CIN) {
		sessionFactory.getCurrentSession().delete(getOne(CIN));
	}

}
