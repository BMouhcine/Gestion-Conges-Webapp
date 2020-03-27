package io.gestionconges.spring.daos;

import java.util.List;
import io.gestionconges.spring.conges.HistoriqueConges;
import io.gestionconges.spring.conges.HistoriqueCongesPK;

public interface CongesDao {
	public void add(HistoriqueConges historiqueConges);
	public void edit(HistoriqueConges historiqueConges);
	public List<HistoriqueConges> getAll();
	public HistoriqueConges getOne(int id);
	public void delete(int id);
}