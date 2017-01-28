package org.touchinghand;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude={HibernateJpaAutoConfiguration.class})
public class ApplicationConfig {

  @Bean(name="dataSource")
  @ConfigurationProperties(prefix="mysql.datasource")
  
  public DataSource dataSource() {

    return DataSourceBuilder.create().build();
  }
}
