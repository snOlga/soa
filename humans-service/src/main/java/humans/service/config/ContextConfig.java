package humans.service.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import jakarta.annotation.Resource;
import soa.models.mapper.CarMapper;
import soa.models.mapper.CoordinatesMapper;
import soa.models.mapper.HumanMapper;

@Resource
public class ContextConfig {

    private static final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
            SpringJpaConfig.class);

    public ContextConfig() {
        try {
            context.getBean(CoordinatesMapper.class);
        } catch (Exception e) {
            context.registerBean(CoordinatesMapper.class);
        }
        try {
            context.getBean(CarMapper.class);
        } catch (Exception e) {
            context.registerBean(CarMapper.class);
        }
        try {
            context.getBean(HumanMapper.class);
        } catch (Exception e) {
            context.registerBean(HumanMapper.class);
        }
    }

    public AnnotationConfigApplicationContext getSpringContext() {
        return context;
    }
}
