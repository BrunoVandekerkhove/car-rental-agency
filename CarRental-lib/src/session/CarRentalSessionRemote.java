package session;

import java.util.Set;
import javax.ejb.Remote;

@Remote
public interface CarRentalSessionRemote {

    void intialize(String name);
    
    Set<String> getAllRentalCompanies();
    
}
