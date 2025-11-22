package teams.service.config;

import ejb.service.ejb.i.HelloStatelessWorld;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

@Configuration
public class EjbConfiguration {

    @Bean
    public HelloStatelessWorld helloStatelessWorld() throws NamingException {
        Properties jndiProps = new Properties();

        // 1. Setup JNDI properties for WildFly
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProps.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        jndiProps.put(Context.SECURITY_PRINCIPAL, "user");
        jndiProps.put(Context.SECURITY_CREDENTIALS, "user");

        // 2. Create Context
        Context context = new InitialContext(jndiProps);

        // 3. Define the JNDI Lookup String
        // Format:
        // ejb:/<app-name>/<module-name>/<distinct-name>/<bean-name>!<view-classname>
        // Based on your EJB pom finalName:
        final String appName = "";
        final String moduleName = "ejb-0.0.1-SNAPSHOT";
        final String distinctName = "";
        final String beanName = "HelloStatelessWorld"; // Defined in @Stateless(name=...)
        final String viewClassName = HelloStatelessWorld.class.getName();

        String jndiName = "ejb:/ejb-0.0.1-SNAPSHOT/HelloStatelessWorld!ejb.service.ejb.i.HelloStatelessWorld";

        // 4. Perform Lookup
        return (HelloStatelessWorld) context.lookup(jndiName);
    }
}