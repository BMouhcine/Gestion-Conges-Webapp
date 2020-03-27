package io.gestionconges.spring.Controllers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.gestionconges.spring.CongesMaladie.CongesMaladie;
import io.gestionconges.spring.Personnel.Personnel;
import io.gestionconges.spring.ligneCongesMaladie.LigneCongesMaladie;
import io.gestionconges.spring.services.CongesMaladieService;
import io.gestionconges.spring.services.PersonnelService;
@Controller
public class CongesMaladieController {
	private String oldCIN;
	private int oldID;
	@Autowired
	private SessionFactory session;
	@Autowired
	private PersonnelService personnelService;
	@Autowired
	private CongesMaladieService congesMaladieService;
	
	
	public CongesMaladieController() {
		super();
	}
	@RequestMapping("/Conges-Maladie")
	public String ListeCongesMaladie(Model model) {
		List congesMaladie = session.getCurrentSession().createSQLQuery("select lc.id as lcid,c.id,cin,nom,prenom,nombre_jours,date_demande,"
				+ "date_debut,date_fin,date_reprise"
				+ " from personnel p, conges_maladie c, ligne_conges_maladie lc where p.cin=lc.id_personnel AND c.id=lc.id_conges_maladie").list();
		model.addAttribute("congesMaladie",congesMaladie);
		return "CongesMaladie";
	}
	@RequestMapping("/Demande-Conge-Maladie")
    public String DemandeConge(@ModelAttribute CongesMaladie congesMaladie, BindingResult bindingResult, Model model) {
    	model.addAttribute("congesMaladie",congesMaladie);
		model.addAttribute("ListePersonnel", personnelService.getAll());
    	return "DemandeCongeMaladie";
    }
	
	@RequestMapping(value = "/Demande-Conge-Maladie", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public void ajouterConge(@ModelAttribute CongesMaladie congesMaladie, BindingResult bindingResult,HttpServletResponse httpServletResponse,@RequestBody String Demandeur) {
    	try {
    		Pattern pattern = Pattern.compile("Demandeur=(.*?)&");
    		Matcher matcher = pattern.matcher(Demandeur);
    		matcher.find();
    		String cINN = matcher.group(1);
    		int nombreJours = Integer.parseInt(congesMaladie.getNombre_jours());
    		Personnel foundPersonnel  = personnelService.getOne(cINN);
    		int firstDay = congesMaladie.getDate_debut().getDay();
    		for(int i = 0;i<nombreJours;i++) {
    			if(firstDay == 7) {
    				nombreJours -= 1;
    			}if(firstDay == 6) {
    				nombreJours -= 1;
    			}
    			firstDay++;
    		}
    			congesMaladie.setNombre_jours(String.valueOf(nombreJours));
    			LigneCongesMaladie ligneCongesMaladie = new LigneCongesMaladie();
    			ligneCongesMaladie.setHistoriqueConges(congesMaladie);
    			ligneCongesMaladie.setPersonnel(foundPersonnel);
    			List<LigneCongesMaladie> ligneCongesMaladieChildren = new ArrayList<LigneCongesMaladie>();
    			ligneCongesMaladieChildren.add(ligneCongesMaladie);
    			congesMaladie.setLigneCongesMaladie(ligneCongesMaladieChildren);
    			foundPersonnel.setLigneCongesMaladies(ligneCongesMaladieChildren);
    			congesMaladieService.add(congesMaladie);
    			personnelService.edit(foundPersonnel);
    			httpServletResponse.sendRedirect("/Conges-Maladie?CongeAjoute");
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
	
	
	@RequestMapping(value = "/Conges-Maladie/Modifier-Conge", method = RequestMethod.POST)
	@Transactional
	public String ModifierConge(@ModelAttribute CongesMaladie congesMaladie, BindingResult bindingResult, Model model, @RequestParam(defaultValue = "0:0") String checkPersonnel, @RequestParam String submit, HttpServletResponse httpServletResponse) throws IOException {
		model.addAttribute("ListePersonnel",personnelService.getAll());
		String[] idCIN = checkPersonnel.split("@");
		int idLigneConges = Integer.parseInt(idCIN[0]);
		int id = Integer.parseInt(idCIN[1]);
		oldID = id;
		String CIN = idCIN[2];
		oldCIN = CIN;
		//LigneConges foundLigneConges = ligneCongesService.getOne(idLigneConges);
		//model.addAttribute(foundLigneConges);
		congesMaladie = congesMaladieService.getOne(id);
		Personnel foundPersonnel = personnelService.getOne(CIN);
		if (submit.equals("Supprimer")) {
				congesMaladieService.delete(id);
				personnelService.edit(foundPersonnel);
				httpServletResponse.sendRedirect("/Conges-Maladie?CongeSupprime");
		}
		else {
			model.addAttribute("Cin",foundPersonnel.getCIN());
			model.addAttribute("Nom",foundPersonnel.getNom());
			model.addAttribute("Prenom",foundPersonnel.getPrenom());
			model.addAttribute("congesMaladie",congesMaladie);
			return "ModifierCongeMaladie";
		}
		return null;
	}
	@RequestMapping(value = "/perform_edit_conge_maladie", method = RequestMethod.POST)
	   @Transactional
	   public void PerformEditConge(@ModelAttribute CongesMaladie congesMaladie, BindingResult bindingResult, HttpServletResponse httpServletResponse) {
		     try {
		    	 Personnel foundPersonnel = personnelService.getOne(oldCIN);
		    	 Date firstDate = congesMaladie.getDate_debut();
		    	 Date lastDate = congesMaladie.getDate_fin();
		    	 int nombreJours = 0;
		    	 while(firstDate.before(lastDate)) {
		    		 if(firstDate.getDay() != 7 && firstDate.getDay() != 6) {
		    			 nombreJours += 1;
		    		 }
		    		 firstDate.setTime(firstDate.getTime()+86400000);
		    	 }
		    	 congesMaladie.setNombre_jours(String.valueOf(nombreJours));
		    	 congesMaladieService.edit(congesMaladie);
		    	 personnelService.edit(foundPersonnel);
		    	 httpServletResponse.sendRedirect("/Conges-Maladie?CongeModifie");
		     } catch (IOException e) { 
		    	 // TODO Auto-generated catch block e.printStackTrace();
		     }
	    }
	
}
