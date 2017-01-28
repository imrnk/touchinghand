package org.touchinghand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * The Starting point of the App
s * @author hadoopuser
 *
 */

@SpringBootApplication
public class TouchinghandApplication extends WebMvcConfigurerAdapter {

  public static void main(String[] args) {
    SpringApplication.run(TouchinghandApplication.class, args);
  }

  /*@Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/", "/");
  }*/

}
