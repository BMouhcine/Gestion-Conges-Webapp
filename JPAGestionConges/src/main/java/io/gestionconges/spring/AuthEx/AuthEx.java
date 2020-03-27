package io.gestionconges.spring.AuthEx;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import io.gestionconges.spring.LigneAuthEx.LigneAuthEx;

@Entity
@Table(name = "authex")
public class AuthEx implements Serializable {
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
	@OneToMany(mappedBy = "authEx",cascade = CascadeType.ALL)
	private List<LigneAuthEx> authExs;

	@Column(name="date_reprise")
	private Date date_reprise;
	@Column(name="motif")
	private String motif;
	public AuthEx() {
		super();
	}


	public AuthEx(String nombre_jours, Date dateDemande, Date dateDebut,Date date_fin, Date dateReprise) {
		super();
		this.nombre_jours = nombre_jours;
		this.date_demande = dateDemande;
		this.date_debut = dateDebut;
		this.date_fin = date_fin;
		this.date_reprise = dateReprise;
	}


	public List<LigneAuthEx> getAuthExs() {
		return authExs;
	}


	public void setAuthExs(List<LigneAuthEx> authExs) {
		this.authExs = authExs;
	}


	public String getMotif() {
		return motif;
	}


	public void setMotif(String motif) {
		this.motif = motif;
	}


	public Date getDate_demande() {
		return date_demande;
	}

	public void setDate_demande(Date date_demande) {
		this.date_demande = date_demande;
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

