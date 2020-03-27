package io.gestionconges.spring.services;

import org.springframework.stereotype.Service;
import io.gestionconges.spring.User.User;
@Service
public interface TopicService {
	public void add(User user);
	public void edit(User user);
	public java.util.List getAll();
}
