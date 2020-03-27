package io.gestionconges.spring.Controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import io.gestionconges.spring.services.PersonnelService;
import io.gestionconges.spring.services.TopicService;

@Controller

public class GeneralController {
	public static String CINStatic;
	public GeneralController() {
		super();
	}
	@Autowired
	private org.hibernate.SessionFactory session;
	@Autowired
	public TopicService topicService;
	@Autowired
	public PersonnelService personnelService;

	@RequestMapping("/accueil")
	public String Acc(ModelMap model,HttpServletRequest httpServletRequest) {
		model.addAttribute("usernameSaved", httpServletRequest.getUserPrincipal().getName());
		return "accueil";
	}
	@RequestMapping("/AlreadyExistsError")
	public String alreadyExistsError() {
		return "AlreadyExistsError";
	}
}