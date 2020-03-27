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
import io.gestionconges.spring.Division.Division;
import io.gestionconges.spring.services.PersonnelService;

@Controller
public class DivisionController {
	@Autowired
	private SessionFactory session;
	
	@RequestMapping("/Divisions")
	public String ListeDivisions(Model model) {
		List<Division> divisions = (List<Division>) session.getCurrentSession().createSQLQuery("select * from division").getResultList();
		model.addAttribute("divisionsList",divisions);
		return "DivisionsList";
	}
	@RequestMapping("/Ajouter-Division")
	public String AddDivision(@ModelAttribute Division division, BindingResult bindingResult, Model model) {
		return "AjouterDivision";
	}
	
	@RequestMapping(value="/Ajouter-Division", method = RequestMethod.POST)
	@Transactional
	public void AddDivisionPOST(@ModelAttribute Division division, BindingResult bindingResult, Model model, HttpServletResponse httpServletResponse) throws IOException {
		session.getCurrentSession().save(division);
		httpServletResponse.sendRedirect("/Divisions?CongeAjoute");
	}
	
	@RequestMapping(value = "/Divisions/Supprimer-Division", method = RequestMethod.POST)
	@Transactional
	public String ModifierConge(@ModelAttribute Division division, BindingResult bindingResult, Model model, @RequestParam(defaultValue = "0:0") String checkedDivision, @RequestParam String submit, HttpServletResponse httpServletResponse) throws IOException {
		division.setNom_division(checkedDivision);
		session.getCurrentSession().delete(division);
		httpServletResponse.sendRedirect("/Divisions?CongeSupprime");
		return null;
	}
	
	
}
