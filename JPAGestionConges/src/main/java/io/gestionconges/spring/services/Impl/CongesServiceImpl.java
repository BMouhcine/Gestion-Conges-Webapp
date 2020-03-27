package io.gestionconges.spring.services.Impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.gestionconges.spring.conges.HistoriqueConges;
import io.gestionconges.spring.conges.HistoriqueCongesPK;
import io.gestionconges.spring.daos.CongesDao;
import io.gestionconges.spring.services.CongesService;
@Service
public class CongesServiceImpl implements CongesService {
	@Autowired
	private CongesDao congesDao;
	@Transactional
	public void add(HistoriqueConges historiqueConges) {
		congesDao.add(historiqueConges);
	}

	@Transactional
	public void edit(HistoriqueConges historiqueConges) {
		congesDao.edit(historiqueConges);
	}

	@Transactional
	public List<HistoriqueConges> getAll() {
		return congesDao.getAll();
	}

	@Transactional
	public HistoriqueConges getOne(int id) {
		return congesDao.getOne(id);
	}

	@Transactional
	public void delete(int id) {
		congesDao.delete(id);
	}
}
