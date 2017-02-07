package org.touchinghand;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.touchinghand.configuration.AuthenticationFilter;
import org.touchinghand.configuration.cors.CORSFilter;
import org.touchinghand.configuration.csrf.CsrfTokenResponseCookieBindingFilter;
import org.touchinghand.dao.AdminRepository;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	public Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	DataSource dataSource;
	@Autowired
	AdminRepository adminRepo;
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

	@Bean
	public AuthenticationFilter customUsernamePasswordAuthenticationFilter() throws Exception {
		AuthenticationFilter customUsernamePasswordAuthenticationFilter = new AuthenticationFilter();
		customUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
		return customUsernamePasswordAuthenticationFilter;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	/**/

	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/*/**").permitAll().antMatchers("/login").permitAll()
				.antMatchers("/register").permitAll()
				.antMatchers("/", "/**/*.css", "/**/**/*,css", "/**/*.js", "/**/**/*.js").permitAll()
				.antMatchers("/dashboard", "/dasboard/**", "/logout").authenticated();

		http.userDetailsService(userDetailsService);
		// Handlers and entry points
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
		// login process
		http.formLogin().loginProcessingUrl("/login");
		// success and failure handler
		http.formLogin().successHandler(authenticationSuccessHandler);
		http.formLogin().failureHandler(authenticationFailureHandler);

		// Logout
		http.logout().logoutUrl("/logout").logoutSuccessUrl("/login").logoutSuccessHandler(logoutSuccessHandler);

		// CORS
		http.addFilterBefore(corsFilter, ChannelProcessingFilter.class);
		//Custom authentication filter - have to use if json object is used
		http.addFilterBefore(customUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		// CSRF
		http.csrf()
				.requireCsrfProtectionMatcher(new AndRequestMatcher(
						// Apply CSRF protection to all paths that do NOT match
						// the ones below
						new NegatedRequestMatcher(new AntPathRequestMatcher("/", HttpMethod.OPTIONS.toString())),
						new NegatedRequestMatcher(new AntPathRequestMatcher("/", HttpMethod.GET.toString())),
						new NegatedRequestMatcher(new AntPathRequestMatcher("/", HttpMethod.POST.toString())),
						new NegatedRequestMatcher(new AntPathRequestMatcher("/", HttpMethod.HEAD.toString())),
						new NegatedRequestMatcher(new AntPathRequestMatcher("/", HttpMethod.TRACE.toString())),

						new NegatedRequestMatcher(new AntPathRequestMatcher("/css/**", HttpMethod.GET.toString())),
						new NegatedRequestMatcher(new AntPathRequestMatcher("/js/**", HttpMethod.GET.toString())),
						new NegatedRequestMatcher(new AntPathRequestMatcher("/js/**/**", HttpMethod.GET.toString())),
						// We disable CSRF at login/logout, but only for OPTIONS
						// methods
						new NegatedRequestMatcher(
								new AntPathRequestMatcher("/login*/**", HttpMethod.OPTIONS.toString())),
						new NegatedRequestMatcher(
								new AntPathRequestMatcher("/logout*/**", HttpMethod.OPTIONS.toString())),

						// Disable CSRF at register for OPTIONS
						new NegatedRequestMatcher(
								new AntPathRequestMatcher("/register*/**", HttpMethod.OPTIONS.toString()))));
		http.addFilterAfter(new CsrfTokenResponseCookieBindingFilter(), CsrfFilter.class); // CSRF
																							// tokens
																							// handling

	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authProvider());
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
