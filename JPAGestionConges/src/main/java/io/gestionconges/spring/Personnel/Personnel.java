package io.gestionconges.spring.Personnel;
import java.io.Serializable;
import io.gestionconges.spring.Service.*;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import io.gestionconges.spring.Budget.Budget;
import io.gestionconges.spring.Division.Division;
import io.gestionconges.spring.Grade.Grade;
import io.gestionconges.spring.LigneAuthEx.LigneAuthEx;
import io.gestionconges.spring.ligneConges.LigneConges;
import io.gestionconges.spring.ligneCongesMaladie.LigneCongesMaladie;

@Entity
public class Personnel implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CIN")
	private String CIN;
	@Column(name="Nom")
	private String Nom;
	@Column(name="Prenom")
	private String Prenom;
	@Column(name="jours_restants")
	private int jours_restants;
	@OneToMany(mappedBy = "personnel",cascade = CascadeType.ALL)
	private List<LigneConges> ligneConges;
	@OneToMany(mappedBy = "personnel")
	private List<LigneCongesMaladie> ligneCongesMaladies;
	@OneToMany(mappedBy = "personnel")
	private List<LigneAuthEx> ligneAuthExs;
	@ManyToOne
	@JoinColumn(name="Division")
	private Division division;
	@ManyToOne
	@JoinColumn(name="Grade")
	private Grade grade;
	@ManyToOne
	@JoinColumn(name="Service")
	private Service service;
	@ManyToOne
	@JoinColumn(name="Budget")
	private Budget budget;
	
	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public List<LigneCongesMaladie> getLigneCongesMaladies() {
		return ligneCongesMaladies;
	}

	public void setLigneCongesMaladies(List<LigneCongesMaladie> ligneCongesMaladies) {
		this.ligneCongesMaladies = ligneCongesMaladies;
	}

	public Personnel() {
		super();
	}
	
	public Personnel(String cIN, String nom, String prenom, String grade,int jours_restants) {
		super();
		CIN = cIN;
		Nom = nom;
		Prenom = prenom;
		this.jours_restants = jours_restants;
	}

	public Service getService() {
		return service;
	}

	public List<LigneAuthEx> getLigneAuthExs() {
		return ligneAuthExs;
	}

	public void setLigneAuthExs(List<LigneAuthEx> ligneAuthExs) {
		this.ligneAuthExs = ligneAuthExs;
	}

	public int getJours_restants() {
		return jours_restants;
	}

	public void setJours_restants(int jours_restants) {
		this.jours_restants = jours_restants;
	}

	public List<LigneConges> getLigneConges() {
		return ligneConges;
	}

	public void setLigneConges(List<LigneConges> ligneConges) {
		this.ligneConges = ligneConges;
	}
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Personnel(String cIN, String nom, String prenom) {
		super();
		CIN = cIN;
		Nom = nom;
		Prenom = prenom;
	}
	public String getCIN() {
		return CIN;
	}
	public void setCIN(String cIN) {
		CIN = cIN;
	}
	public String getNom() {
		return Nom;
	}
	public void setNom(String nom) {
		Nom = nom;
	}
	public String getPrenom() {
		return Prenom;
	}
	public void setPrenom(String prenom) {
		Prenom = prenom;
	}


}
