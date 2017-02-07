package org.touchinghand;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
		registry.addResourceHandler("classpath:/resources/static/css/**")
				.addResourceLocations("classpath:/resources/static/css/");
		registry.addResourceHandler("classpath:/resources/js/**").addResourceLocations("classpath:/resources/js/");
		registry.addResourceHandler("classpath:/resources/static/**")
				.addResourceLocations("classpath:/resources/static/");
		registry.addResourceHandler("classpath:/resources/static/templates/**")
				.addResourceLocations("classpath:/resources/static/templates/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		super.addViewControllers(registry);
		registry.addViewController("/").setViewName("forward:/login");
		registry.addViewController("login.html");
		registry.addViewController("/login");
		registry.addViewController("/dashboard");
		//registry.addViewController("/dashboard").setViewName("dashboard");
		registry.addViewController("dashboard.html");
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("static/");
		resolver.setSuffix(".html");
		registry.viewResolver(resolver);
	}
}
