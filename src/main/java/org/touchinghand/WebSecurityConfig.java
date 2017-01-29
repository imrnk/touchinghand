package org.touchinghand;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.touchinghand.admin.registration.service.LoginService;
import org.touchinghand.configuration.cors.CORSFilter;
import org.touchinghand.configuration.csrf.CsrfTokenResponseCookieBindingFilter;
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
	@Resource
	private AuthenticationEntryPoint authenticationEntryPoint;
	@Resource
	private AuthenticationFailureHandler authenticationFailureHandler;
	@Resource
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	@Resource
	private CORSFilter corsFilter;
	@Resource
	private LogoutSuccessHandler logoutSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.OPTIONS, "/*/**").permitAll()
		.antMatchers("/login").permitAll()
		.antMatchers("/register").permitAll()
		.antMatchers("/", "/**/*.css", "/**/**/*,css",
					"/**/*.js", "/**/**/*.js").permitAll()
		.antMatchers("/dashboard", "/dasboard/**", "/logout").authenticated();
		
		// Handlers and entry points
				http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
				http.formLogin().successHandler(authenticationSuccessHandler);
				http.formLogin().failureHandler(authenticationFailureHandler);

				// Logout
				http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);

				// CORS
				http.addFilterBefore(corsFilter, ChannelProcessingFilter.class);

				// CSRF
				http.csrf().requireCsrfProtectionMatcher(
					new AndRequestMatcher(
						// Apply CSRF protection to all paths that do NOT match the ones below
						new NegatedRequestMatcher(new AntPathRequestMatcher("/", HttpMethod.OPTIONS.toString())),
						new NegatedRequestMatcher(new AntPathRequestMatcher("/", HttpMethod.GET.toString())),
						new NegatedRequestMatcher(new AntPathRequestMatcher("/", HttpMethod.POST.toString())),
						new NegatedRequestMatcher(new AntPathRequestMatcher("/", HttpMethod.HEAD.toString())),
						new NegatedRequestMatcher(new AntPathRequestMatcher("/", HttpMethod.TRACE.toString())),
						
						new NegatedRequestMatcher(new AntPathRequestMatcher("/css/**", HttpMethod.GET.toString())),
						new NegatedRequestMatcher(new AntPathRequestMatcher("/js/**", HttpMethod.GET.toString())),
						new NegatedRequestMatcher(new AntPathRequestMatcher("/js/**/**", HttpMethod.GET.toString())),
						// We disable CSRF at login/logout, but only for OPTIONS methods
						new NegatedRequestMatcher(new AntPathRequestMatcher("/login*/**", HttpMethod.OPTIONS.toString())),
						new NegatedRequestMatcher(new AntPathRequestMatcher("/logout*/**", HttpMethod.OPTIONS.toString())),
						
						//Disable CSRF at register for all methods
						new NegatedRequestMatcher(new AntPathRequestMatcher("/register*/**", HttpMethod.OPTIONS.toString())),
						new NegatedRequestMatcher(new AntPathRequestMatcher("/register*/**", HttpMethod.GET.toString())),
						new NegatedRequestMatcher(new AntPathRequestMatcher("/register*/**", HttpMethod.POST.toString())),
						new NegatedRequestMatcher(new AntPathRequestMatcher("/register*/**", HttpMethod.HEAD.toString())),
						new NegatedRequestMatcher(new AntPathRequestMatcher("/register*/**", HttpMethod.TRACE.toString()))
						//TODO: DO we need to disable CSRF at dashboard??
						
					//	new NegatedRequestMatcher(new AntPathRequestMatcher("/dashboard*/**", HttpMethod.GET.toString())),
					//	new NegatedRequestMatcher(new AntPathRequestMatcher("/dashboard*/**", HttpMethod.HEAD.toString())),
					//	new NegatedRequestMatcher(new AntPathRequestMatcher("/dashboard*/**", HttpMethod.OPTIONS.toString())),
					//	new NegatedRequestMatcher(new AntPathRequestMatcher("/dashboard*/**", HttpMethod.TRACE.toString()))
						
					)
				);
				http.addFilterAfter(new CsrfTokenResponseCookieBindingFilter(), CsrfFilter.class); // CSRF tokens handling

		
		//http.authorizeRequests()
		//		.antMatchers("/", "/#/", "/#register/", "/login", "/register", "/**/*.css", 
		//				"/**/*.js", "/**/**/*.css", "/**/**/*.js",
		//				"/**/**/**/*.css", "/**/**/**/*.js", 
		//				"/**/**/**/**/*.css", "/**/**/**/**/*.js", "/**/home.html", "**/login.html",
		//				"**/**/login.template.html", "**/**/registration.template.html")
		//		.permitAll().
		//		regexMatchers("/registration*")
		//		.permitAll()
		//		//.anyRequest().authenticated()
		//		.and()
		//		.formLogin().loginPage("/login").permitAll()
		//		.and()
		//		.logout().permitAll();*/
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
