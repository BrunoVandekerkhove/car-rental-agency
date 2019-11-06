package session;

import java.util.Set;
import javax.ejb.Remote;

@Remote
public interface CarRentalManagerSessionRemote {

    void initialize(String name);
    
}
