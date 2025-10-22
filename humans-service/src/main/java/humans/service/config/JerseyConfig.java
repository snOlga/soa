package humans.service.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import humans.service.filter.CorsFilter;
import humans.service.resourse.HumanResouce;
import jakarta.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(HumanResouce.class);
        register(CorsFilter.class);
    }
}
