package teams.service.config;

import ejb.service.ejb.i.TeamsService;
import teams.service.client.MuleEsbTeamsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class EjbConfiguration {

    @Bean
    @Primary
    public TeamsService teamsService(MuleEsbTeamsService muleEsbTeamsService) {
        return muleEsbTeamsService;
    }
}