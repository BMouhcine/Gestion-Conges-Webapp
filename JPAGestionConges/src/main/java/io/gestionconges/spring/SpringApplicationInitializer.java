package io.gestionconges.spring;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class SpringApplicationInitializer 
extends AbstractAnnotationConfigDispatcherServletInitializer {
 
  protected Class<?>[] getRootConfigClasses() {
      return new Class[] {SecSecurityConfig.class};
  }

@Override
protected Class<?>[] getServletConfigClasses() {
	// TODO Auto-generated method stub
	return null;
}

@Override
protected String[] getServletMappings() {
	// TODO Auto-generated method stub
	return null;
}
}