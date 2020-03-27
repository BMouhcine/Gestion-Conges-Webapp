package io.gestionconges.spring.services.Impl;

import io.gestionconges.spring.services.LigneCongesService;
import java.util.List;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.gestionconges.spring.daos.LigneCongesDao;
import io.gestionconges.spring.ligneConges.LigneConges;
@Service
public class LigneCongesServiceImpl implements LigneCongesService{
	@Autowired
	private LigneCongesDao ligneCongesDao;
	@Transactional
	public void add(LigneConges ligneConges) {
		ligneCongesDao.add(ligneConges);
	}
	@Transactional
	public void edit(LigneConges ligneConges) {
		ligneCongesDao.edit(ligneConges);
	}
	@Transactional
	public Criteria getAll() {
		return ligneCongesDao.getAll();
	}
	@Transactional
	public LigneConges getOne(int id) {
		return ligneCongesDao.getOne(id);
	}
	@Transactional
	public void delete(int id) {
		ligneCongesDao.delete(id);
	}
}
