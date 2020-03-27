package io.gestionconges.spring.services;

import org.springframework.stereotype.Service;

import io.gestionconges.spring.conges.HistoriqueConges;
import io.gestionconges.spring.conges.HistoriqueCongesPK;

@Service
public interface CongesService {
	public void add(HistoriqueConges historiqueConges);
	public void edit(HistoriqueConges historiqueConges);
	public java.util.List getAll();
	public HistoriqueConges getOne(int id);
	public void delete(int id);
}
