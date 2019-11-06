package client;

import java.util.Date;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import rental.CarType;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import session.CarRentalManagerSessionRemote;
import session.CarRentalSessionRemote;

public class Main extends AbstractTestAgency<CarRentalSessionRemote, CarRentalManagerSessionRemote> {
    
    public static void main(String[] args) throws Exception {
        Main main = new Main("simpleTrips");
        main.run();
    }

    public Main(String script) throws NamingException {
        super(script);
        this.context = new InitialContext();
    }
    
    private final InitialContext context;

    @Override
    protected CarRentalSessionRemote getNewReservationSession(String name) throws Exception {
        String beanID = "java:global/CarRental/CarRental-ejb/CarRentalSession";
        CarRentalSessionRemote session = (CarRentalSessionRemote)context.lookup(beanID);
        session.initialize(name);
        return session;
    }

    @Override
    protected CarRentalManagerSessionRemote getNewManagerSession(String name) throws Exception {
        String beanID = "java:global/CarRental/CarRental-ejb/CarRentalManagerSession";
        CarRentalManagerSessionRemote session = (CarRentalManagerSessionRemote)context.lookup(beanID);
        session.initialize(name);
        return session;    
    }

    @Override
    protected void getAvailableCarTypes(CarRentalSessionRemote session, Date start, Date end) throws Exception {
        System.out.println("Available car types :");
        for (String type : session.getAvailableCarTypes(start, end))
            System.out.println(type);
    }

    @Override
    protected void createQuote(CarRentalSessionRemote session, String name, Date start, Date end, String carType, String region) throws Exception {
        ReservationConstraints constraints = new ReservationConstraints(start, end, carType, region);
        session.createQuote(constraints, name);
	System.out.println("Quote created for client named '" + name + "'.");
    }

    @Override
    protected List<Reservation> confirmQuotes(CarRentalSessionRemote session, String name) throws Exception {
        System.out.println("Confirming quotes :");
        for (Quote quote : session.getCurrentQuotes())
            System.out.println(quote);
        return session.confirmQuotes();
    }

    @Override
    protected int getNumberOfReservationsBy(CarRentalManagerSessionRemote ms, String clientName) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected int getNumberOfReservationsForCarType(CarRentalManagerSessionRemote ms, String carRentalName, String carType) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
