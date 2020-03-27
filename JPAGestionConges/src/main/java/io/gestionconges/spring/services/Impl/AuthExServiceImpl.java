package io.gestionconges.spring.services.Impl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.gestionconges.spring.AuthEx.AuthEx;
import io.gestionconges.spring.daos.AuthExDao;
import io.gestionconges.spring.services.AuthExService;
@Service
public class AuthExServiceImpl implements AuthExService {
	@Autowired
	private AuthExDao authExDao;
	@Transactional
	public void add(AuthEx authEx) {
		authExDao.add(authEx);
	}

	@Transactional
	public void edit(AuthEx authEx) {
		authExDao.edit(authEx);
	}

	@Transactional
	public List<AuthEx> getAll() {
		return authExDao.getAll();
	}

	@Transactional
	public AuthEx getOne(int id) {
		return authExDao.getOne(id);
	}

	@Transactional
	public void delete(int id) {
		authExDao.delete(id);
	}}
