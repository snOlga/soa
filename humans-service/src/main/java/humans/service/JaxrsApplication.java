package humans.service;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import humans.service.config.JerseyConfig;

@EnableJpaRepositories(basePackages = "soa.models.repository")
@EntityScan(basePackages = "soa.models.entity")
@ComponentScan(basePackages = "soa.mapper")
@EnableAutoConfiguration
@SpringBootApplication
public class JaxrsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JaxrsApplication.class, args);
    }

    @Bean
    public ResourceConfig resourceConfig() {
        return new JerseyConfig();
    }

}
