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
import io.gestionconges.spring.Budget.*;
import io.gestionconges.spring.services.PersonnelService;

@Controller
public class BudgetController {
	@Autowired
	private SessionFactory session;
	
	@RequestMapping("/Budgets")
	public String ListeGrades(Model model) {
		List<Budget> budgets = (List<Budget>) session.getCurrentSession().createSQLQuery("select * from budget").getResultList();
		model.addAttribute("budgetsList",budgets);
		return "BudgetsList";
	}
	@RequestMapping("/Ajouter-Budget")
	public String AddService(@ModelAttribute Budget budget, BindingResult bindingResult, Model model) {
		return "AjouterBudget";
	}
	
	@RequestMapping(value="/Ajouter-Budget", method = RequestMethod.POST)
	@Transactional
	public void AddServicePOST(@ModelAttribute Budget budget, BindingResult bindingResult, Model model, HttpServletResponse httpServletResponse) throws IOException {
		session.getCurrentSession().save(budget);
		httpServletResponse.sendRedirect("/Budgets?CongeAjoute");
	}
	@RequestMapping(value = "/Budgets/Supprimer-Budget", method = RequestMethod.POST)
	@Transactional
	public String ModifierService(@ModelAttribute Budget budget, BindingResult bindingResult, Model model, @RequestParam(defaultValue = "0:0") String checkedBudget, @RequestParam String submit, HttpServletResponse httpServletResponse) throws IOException {
		budget.setNom_budget(checkedBudget);
		session.getCurrentSession().delete(budget);
		httpServletResponse.sendRedirect("/Budgets?CongeSupprime");
		return null;
	}
}
