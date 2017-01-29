package org.touchinghand;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.touchinghand.admin.registration.service.LoginService;
import org.touchinghand.dao.AdminRepository;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	DataSource dataSource;
	@Autowired
	AdminRepository logRegRepo;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/#/", "/#register/", "/login", "/register", "/**/*.css", 
						"/**/*.js", "/**/**/*.css", "/**/**/*.js",
						"/**/**/**/*.css", "/**/**/**/*.js", 
						"/**/**/**/**/*.css", "/**/**/**/**/*.js", "/**/home.html", "**/login.html",
						"**/**/login.template.html", "**/**/registration.template.html")
				.permitAll().
				regexMatchers("/registration*")
				.permitAll()
				//.anyRequest().authenticated()
				.and()
				.formLogin().loginPage("/login").permitAll()
				.and()
				.logout().permitAll();
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(new LoginService(logRegRepo));
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}

}
