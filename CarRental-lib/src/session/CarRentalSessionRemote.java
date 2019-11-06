package session;

import java.util.Set;
import javax.ejb.Remote;

@Remote
public interface CarRentalSessionRemote {

    void initialize(String name);
    
    Set<String> getAllRentalCompanies();
    
}
