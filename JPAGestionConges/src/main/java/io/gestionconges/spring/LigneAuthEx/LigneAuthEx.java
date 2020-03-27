package io.gestionconges.spring.LigneAuthEx;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import io.gestionconges.spring.AuthEx.AuthEx;
import io.gestionconges.spring.Personnel.Personnel;
@Entity
@Table(name = "ligne_authex")
public class LigneAuthEx implements Serializable {
	@Id
	@Column(name="id")
	private int id;
	@ManyToOne
	@JoinColumn(name="id_authex")
	private AuthEx authEx;
	@ManyToOne
	@JoinColumn(name="id_personnel")
	private Personnel personnel;
	public LigneAuthEx() {
		super();
	}


	public AuthEx getAuthEx() {
		return authEx;
	}


	public void setAuthEx(AuthEx authEx) {
		this.authEx = authEx;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Personnel getPersonnel() {
		return personnel;
	}
	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}
	
}
