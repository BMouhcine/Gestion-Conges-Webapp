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
import io.gestionconges.spring.Grade.Grade;
import io.gestionconges.spring.services.PersonnelService;

@Controller
public class GradeController {
	@Autowired
	private SessionFactory session;
	
	@RequestMapping("/Grades")
	public String ListeGrades(Model model) {
		List<Grade> grades = (List<Grade>) session.getCurrentSession().createSQLQuery("select * from grade").getResultList();
		model.addAttribute("gradesList",grades);
		return "GradesList";
	}
	@RequestMapping("/Ajouter-Grade")
	public String AddGrade(@ModelAttribute Grade grade, BindingResult bindingResult, Model model) {
		return "AjouterGrade";
	}
	
	@RequestMapping(value="/Ajouter-Grade", method = RequestMethod.POST)
	@Transactional
	public void AddGradePOST(@ModelAttribute Grade grade, BindingResult bindingResult, Model model, HttpServletResponse httpServletResponse) throws IOException {
		session.getCurrentSession().save(grade);
		httpServletResponse.sendRedirect("/Grades?CongeAjoute");
	}
	@RequestMapping(value = "/Grades/Supprimer-Grade", method = RequestMethod.POST)
	@Transactional
	public String ModifierGrade(@ModelAttribute Grade grade, BindingResult bindingResult, Model model, @RequestParam(defaultValue = "0:0") String checkedGrade, @RequestParam String submit, HttpServletResponse httpServletResponse) throws IOException {
		grade.setNom_grade(checkedGrade);
		session.getCurrentSession().delete(grade);
		httpServletResponse.sendRedirect("/Grades?CongeSupprime");
		return null;
	}
}
