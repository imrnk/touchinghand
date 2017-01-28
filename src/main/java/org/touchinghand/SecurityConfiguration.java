package org.touchinghand;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.touchinghand.configuration.RESTAuthenticationEntryPoint;
import org.touchinghand.configuration.RESTAuthenticationSuccessHandler;
import org.touchinghand.configuration.RESTLogoutSuccessHandler;


@Configuration
public class SecurityConfiguration {

	@Bean
	public RESTAuthenticationEntryPoint authenticationEntryPoint() {
		return new RESTAuthenticationEntryPoint();
	}

	@Bean
	public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
		return new SimpleUrlAuthenticationFailureHandler();
	}

	@Bean
	public RESTAuthenticationSuccessHandler authenticationSuccessHandler() {
		return new RESTAuthenticationSuccessHandler();
	}

	/*@Bean
	public CORSFilter corsFilter() {
		return new CORSFilter();
	}*/

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new RESTLogoutSuccessHandler();
	}
}

