package io.gestionconges.spring.User;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;


@Entity
public class User {
	@Id
	@Column(name="id")
	private int id;
	@Column(name = "username")
	private String username;
	@Column(name="password")
	private String password;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@Column(name="enabled")
	private boolean enabled;
	public User() {
		super();
	}
	public User(Boolean bool, String username, String password) {
		super();
		this.username = username;
		this.password=password;
		this.enabled=bool;
	}
	
	
	

	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
