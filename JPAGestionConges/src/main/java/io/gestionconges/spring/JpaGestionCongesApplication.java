package io.gestionconges.spring;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;


@SpringBootApplication
@ComponentScan
public class JpaGestionCongesApplication extends SpringBootServletInitializer {
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JpaGestionCongesApplication.class);
    }
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
	   LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	   sessionFactory.setDataSource(restDataSource());
	   sessionFactory.setPackagesToScan(
	       new String[] {"io.gestionconges.spring"}
	   );
	   sessionFactory.setHibernateProperties(hibernateProperties());
	   return sessionFactory;
	}


	@Bean
	public DataSource restDataSource() {
	    BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	    dataSource.setUrl("jdbc:mysql://localhost:3306/MyDB");
	    dataSource.setUsername("root");
	    dataSource.setPassword("password");
	    return dataSource;
	}
	Properties hibernateProperties() {
	    return new Properties() {
	  
			private static final long serialVersionUID = 1L;

			{
	            setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
	        }
	    };
	}

	
	public static void main(String[] args) {
		//ApplicationContext context = new ClassPathXmlApplicationContext("/ApplicationContext.xml");
		SpringApplication.run(JpaGestionCongesApplication.class, args);
	}
}