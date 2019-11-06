package client;

import java.util.Date;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import rental.Reservation;
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
        CarRentalSessionRemote session = (CarRentalSessionRemote)context.lookup(CarRentalSessionRemote.class.getName());
        session.initialize(name);
        return session;
    }

    @Override
    protected CarRentalManagerSessionRemote getNewManagerSession(String name) throws Exception {
        CarRentalManagerSessionRemote session = (CarRentalManagerSessionRemote)context.lookup(CarRentalManagerSessionRemote.class.getName());
        session.initialize(name);
        return session;    
    }

    @Override
    protected void getAvailableCarTypes(CarRentalSessionRemote session, Date start, Date end) throws Exception {
        System.out.println("found rental companies: "+session.getAllRentalCompanies());
    }

    @Override
    protected void createQuote(CarRentalSessionRemote session, String name, Date start, Date end, String carType, String region) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<Reservation> confirmQuotes(CarRentalSessionRemote session, String name) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
