package io.gestionconges.spring.services.Impl;


import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.gestionconges.spring.User.User;
import io.gestionconges.spring.daos.TopicDao;
import io.gestionconges.spring.services.TopicService;
@Service
public class TopicServiceImpl implements TopicService {
	@Autowired
	private TopicDao topicDao;
	@Transactional
	public void add(User user) {
		topicDao.add(user);
	}
	@Transactional
	public void edit(User user) {
		topicDao.edit(user);
	}
	@Transactional
	public java.util.List getAll() {
		return topicDao.getAll();
		
	}

}
