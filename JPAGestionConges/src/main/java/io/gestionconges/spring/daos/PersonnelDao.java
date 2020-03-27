package io.gestionconges.spring.daos;

import java.util.List;
import io.gestionconges.spring.Personnel.Personnel;

public interface PersonnelDao {
	public void add(Personnel personnel);
	public void edit(Personnel personnel);
	public List<Personnel> getAll();
	public Personnel getOne(String CIN);
	public void delete(String CIN);
	public List getDemandeurCIN();
	public List getDemandeurName();
}