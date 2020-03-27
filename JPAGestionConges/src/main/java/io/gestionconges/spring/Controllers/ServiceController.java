package io.gestionconges.spring.Controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import io.gestionconges.spring.Service.Service;
import io.gestionconges.spring.services.PersonnelService;

@Controller
public class ServiceController {
	@Autowired
	private SessionFactory session;
	public ServiceController() {
		// TODO Auto-generated constructor stub
	}
	@RequestMapping("/Services")
	public String ListeGrades(Model model) {
		List<Service> services = (List<Service>) session.getCurrentSession().createSQLQuery("select * from service").getResultList();
		model.addAttribute("servicesList",services);
		return "ServicesList";
	}
	@RequestMapping("/Ajouter-Service")
	public String AddService(@ModelAttribute Service service, BindingResult bindingResult, Model model) {
		return "AjouterService";
	}
	
	@RequestMapping(value="/Ajouter-Service", method = RequestMethod.POST)
	@Transactional
	public void AddServicePOST(@ModelAttribute Service service, BindingResult bindingResult, Model model, HttpServletResponse httpServletResponse) throws IOException {
		session.getCurrentSession().save(service);
		httpServletResponse.sendRedirect("/Services?CongeAjoute");
	}
	@RequestMapping(value = "/Services/Supprimer-Service", method = RequestMethod.POST)
	@Transactional
	public String ModifierService(@ModelAttribute Service service, BindingResult bindingResult, Model model, @RequestParam(defaultValue = "0:0") String checkedService, @RequestParam String submit, HttpServletResponse httpServletResponse) throws IOException {
		service.setNom_service(checkedService);
		session.getCurrentSession().delete(service);
		httpServletResponse.sendRedirect("/Services?CongeSupprime");
		return null;
	}
}
