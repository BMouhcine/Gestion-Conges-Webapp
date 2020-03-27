package io.gestionconges.spring.daos;

import java.util.List;

import io.gestionconges.spring.CongesMaladie.CongesMaladie;

public interface CongesMaladieDao {
	public void add(CongesMaladie CongesMaladie);
	public void edit(CongesMaladie CongesMaladie);
	public List<CongesMaladie> getAll();
	public CongesMaladie getOne(int id);
	public void delete(int id);
}