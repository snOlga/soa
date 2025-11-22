package ejb.service.ejb.i;

import javax.ejb.Remote;

@Remote
public interface HelloStatelessWorld {
    String getHelloWorld();
}