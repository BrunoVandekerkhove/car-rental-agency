package session;

import java.util.Set;
import javax.ejb.Remote;

@Remote
public interface CarRentalManagerSessionRemote {

    void intialize(String name);
    
}
