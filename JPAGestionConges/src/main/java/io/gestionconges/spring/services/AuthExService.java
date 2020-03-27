package io.gestionconges.spring.services;

import org.springframework.stereotype.Service;
import io.gestionconges.spring.AuthEx.AuthEx;

@Service
public interface AuthExService {
	public void add(AuthEx authEx);
	public void edit(AuthEx authEx);
	public java.util.List<AuthEx> getAll();
	public AuthEx getOne(int id);
	public void delete(int id);
}
