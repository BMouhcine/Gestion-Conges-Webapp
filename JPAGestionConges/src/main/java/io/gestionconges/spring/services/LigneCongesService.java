package io.gestionconges.spring.services;
import org.hibernate.Criteria;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import io.gestionconges.spring.ligneConges.LigneConges;

@Service
public interface LigneCongesService {
		public void add(LigneConges ligneConges);
		public void edit(LigneConges ligneConges);
		public Criteria getAll();
		public LigneConges getOne(int id);
		public void delete(int id);
}
