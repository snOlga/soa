package ejb.service.ejb;

import ejb.service.ejb.i.HelloStatelessWorld;
import javax.ejb.Stateless;

@Stateless(name = "HelloStatelessWorld")
public class HelloStatelessWorldBean implements HelloStatelessWorld {

    public String getHelloWorld() {
        return "Hello Stateless World!";
    }
}

