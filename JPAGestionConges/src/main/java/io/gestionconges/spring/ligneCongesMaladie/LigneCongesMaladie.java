package io.gestionconges.spring.ligneCongesMaladie;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.gestionconges.spring.CongesMaladie.CongesMaladie;
import io.gestionconges.spring.Personnel.Personnel;
@Entity
@Table(name = "ligne_conges_maladie")
public class LigneCongesMaladie implements Serializable {
	@Id
	@Column(name="id")
	private int id;
	@ManyToOne
	@JoinColumn(name="id_conges_maladie")
	private CongesMaladie congesMaladie;
	@ManyToOne
	@JoinColumn(name="id_personnel")
	private Personnel personnel;
	public LigneCongesMaladie() {
		super();
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CongesMaladie getHistoriqueConges() {
		return congesMaladie;
	}
	public void setHistoriqueConges(CongesMaladie congesMaladie) {
		this.congesMaladie = congesMaladie;
	}
	public Personnel getPersonnel() {
		return personnel;
	}
	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}
	
}
