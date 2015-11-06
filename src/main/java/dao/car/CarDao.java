package dao.car;

import java.util.List;

import model.Car;
import model.LocationSignal;

public interface CarDao {

	public List<Car> getAllCars();

	public Car getCarForId(int carId);

}
