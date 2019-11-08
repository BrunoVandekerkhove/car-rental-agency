package session;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateful;
import rental.CarRentalCompany;
import rental.CarType;
import rental.Quote;
import rental.RentalStore;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

@Stateful
public class CarRentalSession extends Session implements CarRentalSessionRemote {

    /* Info */
    
    @Override
    public Set<String> getAllRentalCompanies() {
        return new HashSet<String>(RentalStore.getRentals().keySet());
    }

    @Override
    public Set<String> getAvailableCarTypes(Date start, Date end) {
        Set<String> types = new HashSet<String>();
        for (CarRentalCompany company : RentalStore.getRentals().values())
            for (CarType type : company.getAvailableCarTypes(start, end))
                types.add(type.getName());
        return types;
    }
    
    /* Quotes */
    
    @Override
    public void createQuote(ReservationConstraints constraints, String client) throws ReservationException {
        for (CarRentalCompany company : RentalStore.getRentals().values()) {
            try {
                quotes.add(company.createQuote(constraints, client));
                return;
            } catch (ReservationException ignored) {} // A particular company wasn't able to create a quote
        }
        throw new ReservationException("Couldn't create a quote for client named " + client + ".");
    }

    @Override
    public List<Quote> getCurrentQuotes() {
        return quotes;
    }

    @Override
    public List<Reservation> confirmQuotes() throws ReservationException {
        List<Reservation> reservations = new ArrayList<Reservation>();
        try {
            for (Quote quote : quotes)
                reservations.add(RentalStore.getRental(quote.getRentalCompany()).confirmQuote(quote));
        }
        catch (ReservationException e) {
            for (Reservation reservation : reservations)
                RentalStore.getRental(reservation.getRentalCompany()).cancelReservation(reservation);
            throw new ReservationException("Couldn't confirm quotes.");
        }
        quotes.clear();
        return reservations;
    }
    
    private final List<Quote> quotes = new ArrayList<Quote>();
    
}
