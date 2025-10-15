package teams.service;

import java.net.http.HttpClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableJpaRepositories(basePackages = { "soa.models.repository", "teams.service.repository" })
@EntityScan(basePackages = { "soa.models.entity", "teams.service.entity" })
@ComponentScan(basePackages = { "soa.models.mapper", "teams.service.mapper" })
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class SpringMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMainApplication.class, args);
	}

}
