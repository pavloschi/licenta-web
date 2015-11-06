package dao.signal;

import java.util.List;

import model.LocationSignal;
import model.Trip;

public interface LocationSignalDao {
	
	public void insert(LocationSignal signal);

	public List<LocationSignal> getAllSignals();
	
	public List<LocationSignal> getAllSignalsForCar(int carId);
	
	public List<Trip> getAllTripsForCar(int carId);
	
}
