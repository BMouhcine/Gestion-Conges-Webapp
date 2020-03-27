package io.gestionconges.spring;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter implements AuthenticationFailureHandler {
 
    
 
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
          .csrf().disable()
          .authorizeRequests()
          .antMatchers("/css/**","/images/**","/js/**").permitAll()
          .antMatchers("/").permitAll()
          .antMatchers("/Liste-Personnel").authenticated()
          .antMatchers("/Demande-Conge","/Liste-Personnel/**","/Authex/**","/Conges-Maladie/**","/Demande-Conge-Maladie/**").fullyAuthenticated()
          .and()
          .formLogin()
          .loginPage("/").permitAll()
          .defaultSuccessUrl("/Liste-Personnel")
          .usernameParameter("username")
          .passwordParameter("password")
          .and()
          .logout()
          .logoutUrl("/perform_logout")
          .deleteCookies("JSESSIONID");
    }
    @Autowired
    private javax.sql.DataSource dataSource;
    @Value("select username,password,enabled from user where username=? and enabled=true")
    private String usersQuery;
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    	auth.jdbcAuthentication().usersByUsernameQuery(usersQuery)//.authoritiesByUsernameQuery(rolesQuery)
    	.dataSource(dataSource)
    	.passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}
}
