package session;

import javax.ejb.Remote;

@Remote
public interface CarRentalManagerSessionRemote {

    void initialize(String name);
    
    public int getNbOfReservationsForCarType(String carRentalName, String carType);
    public int getNbOfReservationsByRenter(String clientName);
    
}
