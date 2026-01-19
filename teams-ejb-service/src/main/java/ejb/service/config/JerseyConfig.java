package ejb.service.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import ejb.service.ejb.TeamsServiceBean;
import jakarta.ws.rs.ApplicationPath;
import jakarta.xml.ws.Endpoint;

@Component
@ApplicationPath("")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        Endpoint.publish(
                "http://172.20.0.12:18090/wsdl",
                new TeamsServiceBean());
    }
}
