package io.gestionconges.spring.Controllers;

import java.io.IOException;

import io.gestionconges.spring.Budget.Budget;
import io.gestionconges.spring.Division.*;
import io.gestionconges.spring.Grade.Grade;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;

import io.gestionconges.spring.Personnel.Personnel;
import io.gestionconges.spring.Service.Service;
import io.gestionconges.spring.services.PersonnelService;
import io.gestionconges.spring.services.TopicService;
@Controller
public class PersonnelController {
	public PersonnelController() {
		super();
	}
	// ------------------------------------- VARS ------------------------------------
	public static String oldCIN="";
	@Autowired
	private org.hibernate.SessionFactory session;
	@Autowired
	public TopicService topicService;
	@Autowired
	public PersonnelService personnelService;
	//------------------------------------- END VARS ---------------------------------
	@RequestMapping("/Liste-Personnel")
	public String ListePersonnel(Model model) {
		model.addAttribute("FetchList",personnelService.getAll());
		return "ListePersonnel";
	}
	
	
	//------------------------------------- SAVEORUPDATE ----------------------------------
	@RequestMapping("/Liste-Personnel/Ajouter-Personnel")
	public String AjouterPersonnel(@ModelAttribute Personnel personnel, BindingResult bindingResult, Model model) {
		model.addAttribute(personnel);
		model.addAttribute("divisions",session.getCurrentSession().createSQLQuery("select * from division").getResultList());
		model.addAttribute("grades",session.getCurrentSession().createSQLQuery("select * from grade").getResultList());
		model.addAttribute("services",session.getCurrentSession().createSQLQuery("select * from service").getResultList());
		model.addAttribute("budgets",session.getCurrentSession().createSQLQuery("select * from budget").getResultList());
		return "AjouterPersonnel";
	}
	
	
	@RequestMapping(value="/perform_add", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public void PerformAjouterPersonnel(@ModelAttribute Personnel personnel, BindingResult bindingResult, Model model,@RequestParam String gradeSelected,@RequestParam String serviceSelected,@RequestParam String budgetSelected,@RequestParam(defaultValue = "null") String Delete,@RequestParam String divisionSelected, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
		try {
			
			if(personnelService.getOne(personnel.getCIN())!=null) {
				String elementAlreadyExists =personnel.getCIN()+":"+personnel.getNom()+":"+personnel.getPrenom(); 
				//httpServletResponse.sendRedirect("/AlreadyExistsError");
				httpServletRequest.setAttribute("elementAlreadyExistsCIN",elementAlreadyExists.split(":")[0]);
				httpServletRequest.setAttribute("elementAlreadyExistsNom",elementAlreadyExists.split(":")[1]);
				httpServletRequest.setAttribute("elementAlreadyExistsPrenom",elementAlreadyExists.split(":")[2]);
				httpServletRequest.getRequestDispatcher("AlreadyExistsError").forward(httpServletRequest, httpServletResponse);
			}else {
				Division selectedDivisionObject = new Division();
				Grade selectedGradeObject = new Grade();
				Service selectedServiceObject = new Service();
				Budget selectedBudgetObject = new Budget();
				selectedBudgetObject.setNom_budget(budgetSelected);
				selectedServiceObject.setNom_service(serviceSelected);
				selectedGradeObject.setNom_grade(gradeSelected);
				selectedDivisionObject.setNom_division(divisionSelected);
				personnel.setDivision(selectedDivisionObject);
				personnel.setGrade(selectedGradeObject);
				personnel.setService(selectedServiceObject);
				personnel.setBudget(selectedBudgetObject);
				personnelService.add(personnel);
				httpServletResponse.sendRedirect("/Liste-Personnel?PersonnelAjoute");
			}
		}catch (IOException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/perform_edit", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public void PerformEditPersonnel(@ModelAttribute Personnel personnel, BindingResult bindingResult, Model model,@RequestParam(defaultValue = "null") String Delete, @RequestParam String divisionSelected,@RequestParam String gradeSelected,@RequestParam String serviceSelected,@RequestParam String budgetSelected, HttpServletResponse httpServletResponse) {
		try {
			Division selectedDivisionObject = new Division();
			Grade selectedGradeObject = new Grade();
			Service selectedServiceObject = new Service();
			Budget selectedBudgetObject = new Budget();
			selectedBudgetObject.setNom_budget(budgetSelected);
			selectedServiceObject.setNom_service(serviceSelected);
			selectedGradeObject.setNom_grade(gradeSelected);
			selectedDivisionObject.setNom_division(divisionSelected);
			personnel.setDivision(selectedDivisionObject);
			personnel.setGrade(selectedGradeObject);
			personnel.setService(selectedServiceObject);
			personnel.setBudget(selectedBudgetObject);
			personnelService.add(personnel);
			httpServletResponse.sendRedirect("/Liste-Personnel?PersonnelModifie");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/Liste-Personnel/Modifier-Personnel",method = RequestMethod.POST)
	@ResponseBody
	public void POSTModifierPersonnel(@ModelAttribute Personnel personnel, BindingResult bindingResult, Model model,@RequestParam(defaultValue = "0:0") String checkPersonnel,HttpServletResponse httpServletResponse,@RequestParam String submit) {
		Personnel PFOUND = personnelService.getOne(checkPersonnel);
		String Nom= PFOUND.getNom();
		String Prenom= PFOUND.getPrenom();
		String CIN = PFOUND.getCIN();
		String Grade = PFOUND.getGrade().getNom_grade();
		String Division = PFOUND.getDivision().getNom_division();
		String Service = PFOUND.getService().getNom_service();
		String Budget = PFOUND.getBudget().getNom_budget();
		int jours_restants = PFOUND.getJours_restants();
		oldCIN=CIN;
		try {
			if(submit.equals("Modifier")) {
				httpServletResponse.sendRedirect("Modifier-Personnel/"+CIN+"/"+Nom+"/"+Prenom+"/"+Grade+"/"+Division+"/"+Service+"/"+Budget+"/"+jours_restants);
			}else {
				httpServletResponse.sendRedirect("Supprimer-Personnel/"+CIN+"/"+Nom+"/"+Prenom+"/"+Grade+"/"+Division+"/"+Service+"/"+Budget+"/"+jours_restants);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/Liste-Personnel/Modifier-Personnel/{CIN}/{Nom}/{Prenom}/{Grade}/{Division}/{Service}/{Budget}/{jours_restants}")
	public String ModifierPersonnel(@ModelAttribute Personnel personnel, BindingResult bindingResult, Model model,@PathVariable String CIN, @PathVariable String Nom, @PathVariable String Prenom,@PathVariable String Grade, @PathVariable String Division, @PathVariable String Service, @PathVariable String Budget, @PathVariable int jours_restants) { 
		model.addAttribute("CIN",CIN);
		model.addAttribute("Nom",Nom);
		model.addAttribute("Prenom",Prenom);
		model.addAttribute("Grade",Grade);
		model.addAttribute("Division",Division);
		model.addAttribute("Service",Service);
		model.addAttribute("Budget",Budget);
		model.addAttribute("jours_restants",jours_restants);
		model.addAttribute("divisions",session.getCurrentSession().createSQLQuery("select * from division").getResultList());
		model.addAttribute("grades",session.getCurrentSession().createSQLQuery("select * from grade").getResultList());
		model.addAttribute("services",session.getCurrentSession().createSQLQuery("select * from service").getResultList());
		model.addAttribute("budgets",session.getCurrentSession().createSQLQuery("select * from budget").getResultList());
		return "ModifierPersonnel";
	}
	
	//------------------------------------- END SAVEORUPDATE ----------------------------------
	//------------------------------------- DELETE ---------------------------------------------
	@RequestMapping(value="/Liste-Personnel/Supprimer-Personnel/{CIN}/{Nom}/{Prenom}/{Grade}/{Division}/{Service}/{Budget}/{jours_restants}",method = RequestMethod.GET)
	public String GETSupprimerPersonnel(@ModelAttribute Personnel personnel, BindingResult bindingResult, Model model,@PathVariable String CIN, @PathVariable String Nom, @PathVariable String Prenom,@PathVariable String Grade, @PathVariable String Division, @PathVariable String Service, @PathVariable String Budget, @PathVariable int jours_restants) {
		model.addAttribute("CIN",CIN);
		model.addAttribute("Nom",Nom);
		model.addAttribute("Prenom",Prenom);
		model.addAttribute("Service",Grade);
		model.addAttribute("Division",Division);
		model.addAttribute("Service",Service);
		model.addAttribute("Budget",Budget);
		model.addAttribute("jours_restants",jours_restants);
		return "SupprimerPersonnel";
	}
	@RequestMapping(value="/perform_delete", method = RequestMethod.POST)
	public String PerformDeletePersonnel(@ModelAttribute Personnel personnel, BindingResult bindingResult, Model model) {
		personnelService.delete(personnel.getCIN());
		return "redirect:/Liste-Personnel?PersonnelSupprime";
	}
	@RequestMapping("Liste-Personnel/Supprimer-Personnel")
	@ResponseBody
	public void SupprimerPersonnel(@ModelAttribute @RequestParam Personnel personnel, BindingResult bindingResult, Model model,HttpServletResponse httpServletResponse){ 
		try {
			httpServletResponse.sendRedirect("Supprimer-Personnel/"+personnel.getCIN()+"/"+personnel.getNom()+"/"+personnel.getPrenom()+"/"+personnel.getGrade()+"/"+personnel.getDivision()+"/"+personnel.getService()+"/"+personnel.getBudget()+"/"+personnel.getJours_restants());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//------------------------------------- END DELETE ---------------------------------------------
}
