package io.gestionconges.spring.Service;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.gestionconges.spring.Personnel.Personnel;

@Entity
@Table(name="service")
public class Service {
	@Id
	@Column(name="nom_service")
	private String nom_service;
	@OneToMany(mappedBy = "service",cascade = CascadeType.ALL)
	private List<Personnel> personnelChildren;
	
	
	public Service() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Service(String nom_service, List<Personnel> personnelChildren) {
		super();
		this.nom_service = nom_service;
		this.personnelChildren = personnelChildren;
	}
	public String getNom_service() {
		return nom_service;
	}
	public void setNom_service(String serviceSelected) {
		this.nom_service = serviceSelected;
	}
	public List<Personnel> getPersonnelChildren() {
		return personnelChildren;
	}
	public void setPersonnelChildren(List<Personnel> personnelChildren) {
		this.personnelChildren = personnelChildren;
	}
	
}
