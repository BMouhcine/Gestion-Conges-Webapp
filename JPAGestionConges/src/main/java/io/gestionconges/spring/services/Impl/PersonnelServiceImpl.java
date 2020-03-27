package io.gestionconges.spring.services.Impl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.gestionconges.spring.Personnel.Personnel;
import io.gestionconges.spring.daos.PersonnelDao;
import io.gestionconges.spring.services.PersonnelService;
@Service
public class PersonnelServiceImpl implements PersonnelService {
	@Autowired
	private PersonnelDao personnelDao;
	@Transactional
	public void add(Personnel personnel) {
		personnelDao.add(personnel);
	}

	@Transactional
	public void edit(Personnel personnel) {
		personnelDao.edit(personnel);

	}

	@Transactional
	public List<Personnel> getAll() {
		return personnelDao.getAll();
		
	}

	@Transactional
	public Personnel getOne(String CIN) {
		return personnelDao.getOne(CIN); 
	}
	@Transactional
	public void delete(String CIN) {
		personnelDao.delete(CIN);
	}
	@Transactional
	public List<Personnel> getDemandeurCIN() {
		return personnelDao.getDemandeurCIN();
	}
	@Transactional
	public List<Personnel> getDemandeurName() {
		return personnelDao.getDemandeurName();
	}
	

}
