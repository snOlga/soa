package humans.service.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import humans.service.service.HumanService;

public class SpringJerseyBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bindAsContract(HumanService.class);
    }

}