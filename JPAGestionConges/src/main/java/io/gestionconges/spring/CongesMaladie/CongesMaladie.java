package io.gestionconges.spring.CongesMaladie;

import java.io.Serializable;
import java.sql.Date;
import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import io.gestionconges.spring.ligneConges.*;
import io.gestionconges.spring.ligneCongesMaladie.LigneCongesMaladie;

@Entity
@Table(name = "conges_maladie")
public class CongesMaladie implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="nombre_jours")
	private String nombre_jours;
	@Column(name="date_demande")
	private Date date_demande;
	@Column(name="date_debut")
	private Date date_debut;
	@Column(name="date_fin")
	private Date date_fin;
	@OneToMany(mappedBy = "congesMaladie",cascade = CascadeType.ALL)
	private List<LigneCongesMaladie> ligneCongesMaladie;
	public List<LigneCongesMaladie> getLigneCongesMaladie() {
		return ligneCongesMaladie;
	}


	public void setLigneCongesMaladie(List<LigneCongesMaladie> ligneCongesMaladie) {
		this.ligneCongesMaladie = ligneCongesMaladie;
	}

	@Column(name="date_reprise")
	private Date date_reprise;
	public CongesMaladie() {
		super();
	}


	public CongesMaladie(String nombre_jours, Date dateDemande, Date dateDebut,Date date_fin, Date dateReprise) {
		super();
		this.nombre_jours = nombre_jours;
		this.date_demande = dateDemande;
		this.date_debut = dateDebut;
		this.date_fin = date_fin;
		this.date_reprise = dateReprise;
	}


	public Date getDate_demande() {
		return date_demande;
	}

	public void setDate_demande(Date date_demande) {
		this.date_demande = date_demande;
	}

	public void setLigneConges(List<LigneCongesMaladie> ligneCongesMaladies) {
		this.ligneCongesMaladie = ligneCongesMaladie;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public Date getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(Date date_debut) {
		this.date_debut = date_debut;
	}

	public Date getDate_reprise() {
		return date_reprise;
	}

	public void setDate_reprise(Date date_reprise) {
		this.date_reprise = date_reprise;
	}

	public Date getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(Date date_fin) {
		this.date_fin = date_fin;
	}

	public String getNombre_jours() {
		return nombre_jours;
	}

	public void setNombre_jours(String nombre_jours) {
		this.nombre_jours = nombre_jours;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

