package session;

import java.util.Map;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import rental.CarRentalCompany;
import rental.RentalStore;

@Stateless
@RolesAllowed({"Manager"})
public class CarRentalManagerSession extends Session implements CarRentalManagerSessionRemote {
    
    @Override
    public int getNbOfReservationsForCarType(String carRentalName, String carType) {
        return RentalStore.getRental(carRentalName).getReservationsForCarType(carType);
    }
    
    @Override
    public int getNbOfReservationsByRenter(String clientName) {
        int total = 0;
        for (CarRentalCompany company : RentalStore.getRentals().values())
            total += company.getNbReservationsBy(clientName);
        return total;
    }
    
}
