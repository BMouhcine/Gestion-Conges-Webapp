package io.gestionconges.spring.Controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import io.gestionconges.spring.User.User;
import io.gestionconges.spring.services.PersonnelService;
import io.gestionconges.spring.services.TopicService;
import javassist.expr.NewArray;

@Controller
public class UserController {
	private String OLDUSERNAME;
	public UserController() {
		super();
	}
	@Autowired
	private org.hibernate.SessionFactory session;
	@Autowired
	public TopicService topicService;
	@Autowired
	public PersonnelService personnelService;
//------------------------------ LOGIN CONTROL --------------------------------------
	@RequestMapping(value="/",method = RequestMethod.GET) 
	public ModelAndView Login(Model model) { 
		model.addAttribute(new User());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
//------------------------------ END LOGIN CONTROL --------------------------------------
//------------------------------ ACCOUNT EDIT PARAM --------------------------------------
	@RequestMapping("/Parametres-Compte")
	public String ParamCompte(@ModelAttribute User user,BindingResult bindingResult,Model model,HttpServletRequest httpServletRequest) {
		NativeQuery<User> userPrincipalQuery = session.getCurrentSession().createSQLQuery("select * from user where username='"+httpServletRequest.getUserPrincipal().getName()+"'");
		User usr = userPrincipalQuery.addEntity(User.class).getSingleResult();
		model.addAttribute("user",usr);
		OLDUSERNAME = httpServletRequest.getUserPrincipal().getName();
		return "ParametreCompte";
	}

	@RequestMapping(value = "/perform_edit_account")
	@Transactional
	public void EditAccount(@ModelAttribute User user, BindingResult bindingResult, HttpServletRequest httpServletRequest,Model model, @RequestParam String oldPassword, HttpServletResponse httpServletResponse) throws IOException, ServletException {
		NativeQuery<String>  passwordNativeQuery = session.getCurrentSession().createSQLQuery("select password from user where id="+user.getId());
		String passwordFound = passwordNativeQuery.getSingleResult().toString();
		if(new BCryptPasswordEncoder().matches(oldPassword, passwordFound)) {
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			user.setEnabled(true);
			topicService.add(user);
			session.getCurrentSession().createSQLQuery("update authorities set username='"+user.getUsername()+"' where username='"+OLDUSERNAME+"'").executeUpdate();
			httpServletResponse.sendRedirect("/accueil?AccountCredentialsUpdated");
		}else {
			httpServletResponse.sendRedirect("/Parametres-Compte?wrongOldPassword");
		}
		
	}
//------------------------------ END ACCOUNT EDIT PARAM --------------------------------------
}