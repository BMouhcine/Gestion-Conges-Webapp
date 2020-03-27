package io.gestionconges.spring.services;

import java.util.List;

import org.springframework.stereotype.Service;

import io.gestionconges.spring.Personnel.Personnel;

public interface PersonnelService {
	public void add(Personnel personnel);
	public void edit(Personnel personnel);
	public java.util.List getAll();
	public Personnel getOne(String CIN);
	public void delete(String CIN);
	public List getDemandeurCIN();
	public List getDemandeurName();
}
