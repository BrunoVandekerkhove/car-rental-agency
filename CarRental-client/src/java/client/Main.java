package client;

import client.CompanyLoader.CrcData;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import rental.CarType;
import rental.Reservation;
import rental.ReservationConstraints;
import session.CarRentalSessionRemote;
import session.ManagerSessionRemote;

public class Main extends AbstractTestManagement<CarRentalSessionRemote, ManagerSessionRemote> {

    public static void main(String[] args) throws Exception {
        new Main("trips").run();
    }

    public Main(String script) throws NamingException {
        super(script);
        registerCompanies();
        this.context = new InitialContext();
    }
    
    private void registerCompanies() throws NamingException {
        ManagerSessionRemote ms = getNewManagerSession("Registration");
        CompanyLoader loader = new CompanyLoader();
        for (String company : Arrays.asList("hertz", "dockx"))
        try {
            CrcData data = loader.loadData(company + ".csv");
            ms.registerCompany(data.name, data.regions, data.cars);
        }
        catch (IOException | NumberFormatException e) {
            System.out.println("Failed to load company named '" + company + "'!");
        }
    }
    
    private final InitialContext context;

    // Session creation
    
    @Override
    protected CarRentalSessionRemote getNewReservationSession(String name) throws NamingException {
        String beanID = "java:global/CarRental/CarRental-ejb/CarRentalSession";
        CarRentalSessionRemote session = (CarRentalSessionRemote)context.lookup(beanID);
        session.setRenterName(name);
        return session;
    }

    @Override
    protected ManagerSessionRemote getNewManagerSession(String name) throws NamingException {
        try {
            String beanID = "java:global/CarRental/CarRental-ejb/CarRentalManagerSession";
            return (ManagerSessionRemote)context.lookup(beanID);
        }
        catch (NamingException e) {
            throw new IllegalArgumentException("The manager session could not be created.");
        }
    }
    
    // Reservation session    

    @Override
    protected String getCheapestCarType(CarRentalSessionRemote session, Date start, Date end, String region) throws Exception {
        return session.getCheapestCarType(start, end, region).toString();
    }
    
    @Override
    protected void getAvailableCarTypes(CarRentalSessionRemote session, Date start, Date end) throws Exception {
        System.out.println("Available car types : ");
        for (CarType type : session.getAvailableCarTypes(start, end))
            System.out.println(type);
    }

    @Override
    protected void createQuote(CarRentalSessionRemote session, String name, Date start, Date end, String carType, String region) throws Exception {
        session.createQuote(name, new ReservationConstraints(start, end, carType, region));
    }

    @Override
    protected List<Reservation> confirmQuotes(CarRentalSessionRemote session, String name) throws Exception {
        return session.confirmQuotes();
    }
    
    // Manager session
    
    @Override
    protected Set<String> getBestClients(ManagerSessionRemote ms) throws Exception {
        return ms.getBestClients();
    }

    @Override
    protected CarType getMostPopularCarTypeIn(ManagerSessionRemote ms, String carRentalCompanyName, int year) throws Exception {
        return ms.getMostPopularCarTypeIn(carRentalCompanyName, year);
    }

    @Override
    protected int getNumberOfReservationsBy(ManagerSessionRemote ms, String clientName) throws Exception {
        return ms.getNumberOfReservations(clientName);
    }

    @Override
    protected int getNumberOfReservationsForCarType(ManagerSessionRemote ms, String carRentalName, String carType) throws Exception {
        return ms.getNumberOfReservations(carType, carType);
    }
    
}