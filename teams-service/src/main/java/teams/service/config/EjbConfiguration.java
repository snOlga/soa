package teams.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ejb.service.service.i.TeamsService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

@Configuration
public class EjbConfiguration {

    @Bean
    public TeamsService teamsService() throws NamingException {
        Properties jndiProps = new Properties();

        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProps.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        jndiProps.put(Context.SECURITY_PRINCIPAL, "user");
        jndiProps.put(Context.SECURITY_CREDENTIALS, "user");

        Context context = new InitialContext(jndiProps);
        String jndiName = "ejb:/ejb-0.0.2-SNAPSHOT/TeamsService!ejb.service.service.i.TeamsService";
        return (TeamsService) context.lookup(jndiName);
    }
}