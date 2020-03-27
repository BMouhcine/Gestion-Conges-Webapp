package io.gestionconges.spring.services.Impl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.gestionconges.spring.CongesMaladie.CongesMaladie;
import io.gestionconges.spring.daos.CongesMaladieDao;
import io.gestionconges.spring.services.CongesMaladieService;
@Service
public class CongesMaladieServiceImpl implements CongesMaladieService {
	@Autowired
	private CongesMaladieDao congesMaladieDao;
	@Transactional
	public void add(CongesMaladie congesMaladie) {
		congesMaladieDao.add(congesMaladie);
	}

	@Transactional
	public void edit(CongesMaladie congesMaladie) {
		congesMaladieDao.edit(congesMaladie);
	}

	@Transactional
	public List<CongesMaladie> getAll() {
		return congesMaladieDao.getAll();
	}

	@Transactional
	public CongesMaladie getOne(int id) {
		return congesMaladieDao.getOne(id);
	}

	@Transactional
	public void delete(int id) {
		congesMaladieDao.delete(id);
	}

}
