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
import org.hibernate.query.NativeQuery;
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

import io.gestionconges.spring.AuthEx.AuthEx;
import io.gestionconges.spring.CongesMaladie.CongesMaladie;
import io.gestionconges.spring.LigneAuthEx.LigneAuthEx;
import io.gestionconges.spring.Personnel.Personnel;
import io.gestionconges.spring.ligneCongesMaladie.LigneCongesMaladie;
import io.gestionconges.spring.services.AuthExService;
import io.gestionconges.spring.services.PersonnelService;

@Controller
public class AuthExController {
	private String oldCIN;
	private int oldID;
	@Autowired
	private SessionFactory session;
	@Autowired
	private PersonnelService personnelService;
	@Autowired
	private AuthExService authExService;
	public AuthExController() {
		super();
	}
	@RequestMapping("/Authex")
	public String ListeCongesMaladie(Model model) {
		List authex = session.getCurrentSession().createSQLQuery("select lc.id as lcid,c.id,cin,nom,prenom,nombre_jours,date_demande,"
				+ "date_debut,date_fin,date_reprise, motif"
				+ " from personnel p, authex c, ligne_authex lc where p.cin=lc.id_personnel AND c.id=lc.id_authex").list();
		model.addAttribute("authex",authex);
		return "ListeAuthex";
	}
	@RequestMapping("/Demande-Authex")
    public String DemandeConge(@ModelAttribute AuthEx authEx, BindingResult bindingResult, Model model) {
    	model.addAttribute("authex",authEx);
		model.addAttribute("ListePersonnel", personnelService.getAll());
    	return "DemandeAuthex";
    }
	
	@RequestMapping(value = "/Demande-Authex", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public void ajouterConge(@ModelAttribute AuthEx authEx, BindingResult bindingResult,HttpServletResponse httpServletResponse,@RequestBody String Demandeur) {
    	try {
    		Pattern pattern = Pattern.compile("Demandeur=(.*?)&");
    		Matcher matcher = pattern.matcher(Demandeur);
    		matcher.find();
    		String cINN = matcher.group(1);
    		int nombreJours = Integer.parseInt(authEx.getNombre_jours());
    		Personnel foundPersonnel  = personnelService.getOne(cINN);
    		int firstDay = authEx.getDate_debut().getDay();
    		for(int i = 0;i<nombreJours;i++) {
    			if(firstDay == 7) {
    				nombreJours -= 1;
    				firstDay=5;
    			}if(firstDay == 6) {
    				nombreJours -= 1;
    			}
    			firstDay++;
    		}
    			NativeQuery<Number> joursConsommesNativeQuery = session.getCurrentSession().createSQLQuery("SELECT IF(count((select sum(nombre_jours) from authex a, ligne_authex la where la.id_authex=a.id AND la.id_personnel='"+cINN+"'))=0,0,(select sum(nombre_jours) from authex a, ligne_authex la where la.id_authex=a.id AND la.id_personnel='"+cINN+"'))");
    			int joursConsommes = joursConsommesNativeQuery.getSingleResult().intValue();
    			if(joursConsommes+nombreJours<=10) {
    				authEx.setNombre_jours(String.valueOf(nombreJours));
        			LigneAuthEx ligneAuthEx = new LigneAuthEx();
        			ligneAuthEx.setAuthEx(authEx);
        			ligneAuthEx.setPersonnel(foundPersonnel);
        			List<LigneAuthEx> ligneAuthExChildren = new ArrayList<LigneAuthEx>();
        			ligneAuthExChildren.add(ligneAuthEx);
        			authEx.setAuthExs(ligneAuthExChildren);
        			foundPersonnel.setLigneAuthExs(ligneAuthExChildren);
        			authExService.add(authEx);
        			personnelService.edit(foundPersonnel);
        			httpServletResponse.sendRedirect("/Authex?CongeAjoute");
    			}else {
    				httpServletResponse.sendRedirect("/Demande-Authex?NbrJrsDep");
				}
    			
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
	
	@RequestMapping(value = "/Authex/Modifier-Authex", method = RequestMethod.POST)
	@Transactional
	public String ModifierConge(@ModelAttribute AuthEx authEx, BindingResult bindingResult, Model model, @RequestParam(defaultValue = "0:0") String checkPersonnel, @RequestParam String submit, HttpServletResponse httpServletResponse) throws IOException {
		model.addAttribute("ListePersonnel",personnelService.getAll());
		String[] idCIN = checkPersonnel.split("@");
		int idLigneConges = Integer.parseInt(idCIN[0]);
		int id = Integer.parseInt(idCIN[1]);
		oldID = id;
		String CIN = idCIN[2];
		oldCIN = CIN;
		authEx = authExService.getOne(id);
		Personnel foundPersonnel = personnelService.getOne(CIN);
		if (submit.equals("Supprimer")) {
			authExService.delete(id);
			personnelService.edit(foundPersonnel);
			httpServletResponse.sendRedirect("/Authex?CongeSupprime");
		}
		else {
			model.addAttribute("Cin",foundPersonnel.getCIN());
			model.addAttribute("Nom",foundPersonnel.getNom());
			model.addAttribute("Prenom",foundPersonnel.getPrenom());
			model.addAttribute("authex",authEx);
			return "ModifierAuthex";
		}
		return null;
	}
	
	@RequestMapping(value = "/perform_edit_authex", method = RequestMethod.POST)
	   @Transactional
	   public void PerformEditConge(@ModelAttribute AuthEx authEx, BindingResult bindingResult, HttpServletResponse httpServletResponse) {
		     try {
		    	 Personnel foundPersonnel = personnelService.getOne(oldCIN);
		    	 Date firstDate = authEx.getDate_debut();
		    	 Date lastDate = authEx.getDate_fin();
		    	 int nombreJours = 0;
		    	 while(firstDate.before(lastDate)) {
		    		 if(firstDate.getDay() != 7 && firstDate.getDay() != 6) {
		    			 nombreJours += 1;
		    		 }
		    		 firstDate.setTime(firstDate.getTime()+86400000);
		    	 }
		    	 authEx.setNombre_jours(String.valueOf(nombreJours));
		    	 authExService.edit(authEx);
		    	 personnelService.edit(foundPersonnel);
		    	 httpServletResponse.sendRedirect("/Authex?CongeModifie");
		     } catch (IOException e) { 
		    	 // TODO Auto-generated catch block e.printStackTrace();
		     }
	    }
	
	
}
