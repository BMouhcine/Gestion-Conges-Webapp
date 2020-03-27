package io.gestionconges.spring.conges;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Id;



@Embeddable
public class HistoriqueCongesPK implements Serializable {
	public int id;
	public String CIN;
	public HistoriqueCongesPK() {
		super();
	}
	public HistoriqueCongesPK(int id, String cIN) {
		super();
		this.id = id;
		CIN = cIN;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCIN() {
		return CIN;
	}
	public void setCIN(String cIN) {
		CIN = cIN;
	}

}
