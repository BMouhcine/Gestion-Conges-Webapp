package io.gestionconges.spring.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.hibernate.*;
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
import io.gestionconges.spring.Personnel.Personnel;
import io.gestionconges.spring.conges.HistoriqueConges;
import io.gestionconges.spring.ligneConges.LigneConges;
import io.gestionconges.spring.services.CongesService;
import io.gestionconges.spring.services.LigneCongesService;
import io.gestionconges.spring.services.PersonnelService;
@Controller
public class CongeController {
	private String oldCIN;
	private int oldID;
	@Autowired
	private SessionFactory session;
	@Autowired
	private PersonnelService personnelService;
	@Autowired
	private CongesService congesService;
	@Autowired
	private LigneCongesService ligneCongesService;
	public CongeController() {
		super();
	}
	@RequestMapping("/Historique-Conges")
	public String ListeHistoConges(Model model) {
		List historiqueList = session.getCurrentSession().createSQLQuery("select lc.id as lcid,c.id,cin,nom,prenom,nombre_jours,date_demande,"
				+ "date_debut,date_fin,date_reprise"
				+ " from personnel p, conges c, ligne_conges lc where p.cin=lc.id_personnel AND c.id=lc.id_conges").list();
		model.addAttribute("HistoriqueConges",historiqueList);
		return "HistoriqueConges";
	}

	@RequestMapping(value = "/Historique-Conges/Modifier-Conge", method = RequestMethod.POST)
	@Transactional
	public String ModifierConge(@ModelAttribute HistoriqueConges historiqueConges, BindingResult bindingResult, Model model, @RequestParam(defaultValue = "0:0") String checkPersonnel, @RequestParam String submit, HttpServletResponse httpServletResponse) throws IOException {
		model.addAttribute("ListePersonnel",personnelService.getAll());
		String[] idCIN = checkPersonnel.split("@");
		int idLigneConges = Integer.parseInt(idCIN[0]);
		int id = Integer.parseInt(idCIN[1]);
		oldID = id;
		String CIN = idCIN[2];
		oldCIN = CIN;
		LigneConges foundLigneConges = ligneCongesService.getOne(idLigneConges);
		model.addAttribute(foundLigneConges);
		historiqueConges = congesService.getOne(id);
		Personnel foundPersonnel = personnelService.getOne(CIN);
		if (submit.equals("Supprimer")) {
				congesService.delete(id);
				foundPersonnel.setJours_restants(foundPersonnel.getJours_restants()+Integer.parseInt(historiqueConges.getNombre_jours()));
				if(foundPersonnel.getJours_restants()>44) {
	    			 foundPersonnel.setJours_restants(44);
	    		 }
				personnelService.edit(foundPersonnel);
				httpServletResponse.sendRedirect("/Historique-Conges?CongeSupprime");
		}
		else {
			model.addAttribute("Cin",foundPersonnel.getCIN());
			model.addAttribute("Nom",foundPersonnel.getNom());
			model.addAttribute("Prenom",foundPersonnel.getPrenom());
			model.addAttribute("historiqueConges",historiqueConges);
			return "ModifierConge";
		}
		return null;
	}
	
    @RequestMapping("/Demande-Conge")
    public String DemandeConge(@ModelAttribute HistoriqueConges historiqueConges, BindingResult bindingResult,Model model) {
    	model.addAttribute("historiqueConges", historiqueConges);
    	model.addAttribute("ListePersonnel", personnelService.getAll());
    	return "DemandeConge";
    }

    @RequestMapping(value = "/Demande-Conge", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public void ajouterConge(@ModelAttribute HistoriqueConges historiqueConges, BindingResult bindingResult,HttpServletResponse httpServletResponse,@RequestBody String Demandeur) {
    	try {
    		Pattern pattern = Pattern.compile("Demandeur=(.*?)&");
    		Matcher matcher = pattern.matcher(Demandeur);
    		matcher.find();
    		String cINN = matcher.group(1);
    		int nombreJours = Integer.parseInt(historiqueConges.getNombre_jours());
    		historiqueConges.setNombre_jours(String.valueOf(nombreJours));
    		Personnel foundPersonnel  = personnelService.getOne(cINN);
    		int nombreJoursRestants = foundPersonnel.getJours_restants();
    		if (nombreJours > nombreJoursRestants) {
    			httpServletResponse.sendRedirect("/Demande-Conge?NbrJrsDep");
    		} else {
    			int firstDay = historiqueConges.getDate_debut().getDay();
    			for(int i = 0;i<nombreJours;i++) {
    				if(firstDay == 7) {
    					nombreJours -= 1;
    					firstDay=5;
    				}if(firstDay == 6) {
    					nombreJours -= 1;
    				}
    				firstDay++;
    			}
    			historiqueConges.setNombre_jours(String.valueOf(nombreJours));
    			foundPersonnel.setJours_restants(nombreJoursRestants-nombreJours);
    			LigneConges ligneConges = new LigneConges();
    			ligneConges.setHistoriqueConges(historiqueConges);
    			ligneConges.setPersonnel(foundPersonnel);
    			List<LigneConges> ligneCongesChildren = new ArrayList<LigneConges>();
    			ligneCongesChildren.add(ligneConges);
    			historiqueConges.setLigneConges(ligneCongesChildren);
    			foundPersonnel.setLigneConges(ligneCongesChildren);
    			congesService.add(historiqueConges);
    			personnelService.edit(foundPersonnel);
    			httpServletResponse.sendRedirect("/Historique-Conges?CongeAjoute");
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

	   @RequestMapping(value = "/perform_edit_conge", method = RequestMethod.POST)
	   @Transactional
	   public void PerformEditConge(@ModelAttribute HistoriqueConges historiqueConges, BindingResult bindingResult, HttpServletResponse httpServletResponse) {
		     try {
		    	 
		    	 int NbrJours = Integer.parseInt(congesService.getOne(oldID).getNombre_jours());
		    	 int Diff = Integer.parseInt(historiqueConges.getNombre_jours()) - NbrJours;
		    	 if (Diff != 0) {
		    		 Personnel foundPersonnel = personnelService.getOne(oldCIN);
		    		 foundPersonnel.setJours_restants(foundPersonnel.getJours_restants() - Diff);
		    		 personnelService.add(foundPersonnel);
		    	 }
		    	 congesService.edit(historiqueConges);
		    	 httpServletResponse.sendRedirect("/Historique-Conges?CongeModifie");
		     } catch (IOException e) { 
		    	 // TODO Auto-generated catch block e.printStackTrace();
		     }
	    }
	
	
	
	
	
	
	
}