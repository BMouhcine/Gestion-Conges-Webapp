package io.gestionconges.spring.daos;

import io.gestionconges.spring.User.User;

public interface TopicDao {
	public void add(User user);
	public void edit(User user);
	public java.util.List getAll();
	
}