package io.gestionconges.spring.Division;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.gestionconges.spring.Personnel.Personnel;

@Entity
@Table(name="division")
public class Division {
	@Id
	@Column(name="nom_division")
	private String nom_division;
	@OneToMany(mappedBy = "division",cascade = CascadeType.ALL)
	private List<Personnel> personnelChildren;
	
	public Division() {
		// TODO Auto-generated constructor stub
	}
	
	public Division(String nom_division, List<Personnel> personnelChildren) {
		super();
		this.nom_division = nom_division;
		this.personnelChildren = personnelChildren;
	}
	public List<Personnel> getPersonnelChildren() {
		return personnelChildren;
	}
	public void setPersonnelChildren(List<Personnel> personnelChildren) {
		this.personnelChildren = personnelChildren;
	}
	public String getNom_division() {
		return nom_division;
	}
	public void setNom_division(String nom_division) {
		this.nom_division = nom_division;
	}
	
}
