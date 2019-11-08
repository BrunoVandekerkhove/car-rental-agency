package session;

import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.Remote;
import rental.CarType;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

@Remote
public interface CarRentalSessionRemote {

    void initialize(String name);
    
    Set<String> getAllRentalCompanies();
    Set<String> getAvailableCarTypes(Date start, Date end);
    
    public void createQuote(ReservationConstraints constraints, String client) throws ReservationException;
    public List<Quote> getCurrentQuotes();
    public List<Reservation> confirmQuotes() throws ReservationException;
    
}
