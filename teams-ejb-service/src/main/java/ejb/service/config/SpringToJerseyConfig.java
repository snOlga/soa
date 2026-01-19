package ejb.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ejb.service.repository.TeamRepository;

@Component
public class SpringToJerseyConfig {
    @Autowired
    public static TeamRepository teamRepository;
}
