package io.gestionconges.spring.daos;
import java.util.List;
import io.gestionconges.spring.AuthEx.AuthEx;


public interface AuthExDao {
	public void add(AuthEx authEx);
	public void edit(AuthEx authEx);
	public List<AuthEx> getAll();
	public AuthEx getOne(int id);
	public void delete(int id);
}
