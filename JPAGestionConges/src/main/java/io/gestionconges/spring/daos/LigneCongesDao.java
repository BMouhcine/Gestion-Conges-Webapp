package io.gestionconges.spring.daos;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.query.Query;

import io.gestionconges.spring.ligneConges.LigneConges;
public interface LigneCongesDao {

		public void add(LigneConges ligneConges);
		public void edit(LigneConges ligneConges);
		public Criteria getAll();
		public LigneConges getOne(int id);
		public void delete(int id);
}