package io.gestionconges.spring.Budget;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.gestionconges.spring.Personnel.Personnel;

@Entity
@Table(name="budget")
public class Budget {
	@Id
	@Column(name="nom_budget")
	private String nom_budget;
	@OneToMany(mappedBy = "budget",cascade = CascadeType.ALL)
	private List<Personnel> personnelChildren;
	public Budget() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getNom_budget() {
		return nom_budget;
	}
	public void setNom_budget(String nom_budget) {
		this.nom_budget = nom_budget;
	}
	public List<Personnel> getPersonnelChildren() {
		return personnelChildren;
	}
	public void setPersonnelChildren(List<Personnel> personnelChildren) {
		this.personnelChildren = personnelChildren;
	}
	
}
