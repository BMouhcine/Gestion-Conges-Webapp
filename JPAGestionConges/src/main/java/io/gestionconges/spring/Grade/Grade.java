package io.gestionconges.spring.Grade;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.gestionconges.spring.Personnel.Personnel;

@Entity
@Table(name="grade")
public class Grade {
	@Id
	@Column(name="nom_grade")
	private String nom_grade;
	@OneToMany(mappedBy = "grade",cascade = CascadeType.ALL)
	private List<Personnel> personnelChildren;
	
	
	
	
	public Grade() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Grade(String nom_grade, List<Personnel> personnelChildren) {
		super();
		this.nom_grade = nom_grade;
		this.personnelChildren = personnelChildren;
	}
	public String getNom_grade() {
		return nom_grade;
	}
	public void setNom_grade(String nom_grade) {
		this.nom_grade = nom_grade;
	}
	public List<Personnel> getPersonnelChildren() {
		return personnelChildren;
	}
	public void setPersonnelChildren(List<Personnel> personnelChildren) {
		this.personnelChildren = personnelChildren;
	}
	
}
