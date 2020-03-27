package io.gestionconges.spring.ligneConges;
import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.gestionconges.spring.Personnel.Personnel;
import io.gestionconges.spring.conges.HistoriqueConges;
@Entity
@Table(name = "ligne_conges")
public class LigneConges implements Serializable {
	@Id
	@Column(name="id")
	private int id;
	@ManyToOne
	@JoinColumn(name="id_conges")
	private HistoriqueConges historiqueConges;
	@ManyToOne
	@JoinColumn(name="id_personnel")
	private Personnel personnel;
	public LigneConges() {
		super();
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public HistoriqueConges getHistoriqueConges() {
		return historiqueConges;
	}
	public void setHistoriqueConges(HistoriqueConges historiqueConges) {
		this.historiqueConges = historiqueConges;
	}
	public Personnel getPersonnel() {
		return personnel;
	}
	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}
	
}
