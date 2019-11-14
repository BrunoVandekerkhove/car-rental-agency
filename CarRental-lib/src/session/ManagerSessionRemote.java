package session;

import java.util.List;
import java.util.Set;
import javax.ejb.Remote;
import rental.Car;
import rental.CarType;

@Remote
public interface ManagerSessionRemote {
        
    public void registerCompany(String name, List<String> regions, List<Car> cars);
    
    public Set<CarType> getCarTypes(String company);
    public Set<Integer> getCarIds(String company,String type);
    
    public int getNumberOfReservations(String company, String type, int carId);
    public int getNumberOfReservations(String company, String type);
      
}