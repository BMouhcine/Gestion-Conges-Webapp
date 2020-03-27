package io.gestionconges.spring.services;

import org.springframework.stereotype.Service;

import io.gestionconges.spring.CongesMaladie.CongesMaladie;
@Service
public interface CongesMaladieService {
	public void add(CongesMaladie congesMaladie);
	public void edit(CongesMaladie congesMaladie);
	public java.util.List<CongesMaladie> getAll();
	public CongesMaladie getOne(int id);
	public void delete(int id);
}
