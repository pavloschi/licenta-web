package hello;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.opensaml.xml.encryption.P;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import server.ServerController;
import server.ServerThread;
import server.UiClient;
import model.Car;
import model.LocationSignal;
import model.Trip;
import dao.car.CarDao;
import dao.signal.LocationSignalDao;

@RestController
public class UiController {

	LocationSignalDao locationDao = (LocationSignalDao) Application.context
			.getBean("signalDAO");
	
	CarDao carDao = (CarDao) Application.context.getBean("carDAO");

	private static final String template = "Hello, %s!";

	@RequestMapping("/signals")
	public ResponseEntity<List<LocationSignal>> getSignals() {
		
		return new ResponseEntity<List<LocationSignal>>(
				locationDao.getAllSignals(), HttpStatus.OK);
	}
	
	
	
	//GET TRIPS FOR CAR
	@RequestMapping(value = "/trips/{carId}", method = RequestMethod.GET)
	public ResponseEntity<List<Trip>> getTrips(@PathVariable Integer carId) {
		List<Trip> trips = locationDao.getAllTripsForCar(carId);
		System.out.println("Trip size:" + trips.size());
		for (Trip t : trips) {
			System.out.println(t.getLocations().size());
		}
		if (trips.size() > 0) {
			return new ResponseEntity<List<Trip>>(trips, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Trip>>(trips,
					HttpStatus.NO_CONTENT);
		}

	}
	
	@RequestMapping(value = "/trips/{carId}/{tripId}", method = RequestMethod.GET)
	public ResponseEntity<Trip> getTripForId(@PathVariable Integer carId, @PathVariable Integer tripId) {
		List<Trip> trips = locationDao.getAllTripsForCar(carId);
		System.out.println("Trip size:" + trips.size());
		for (Trip t : trips) {
			System.out.println(t.getLocations().size());
		}
		if (trips.size() > 0) {
			return new ResponseEntity<Trip>(trips.get(tripId), HttpStatus.OK);
		} else {
			return new ResponseEntity<Trip>(HttpStatus.NO_CONTENT);
		}

	}
	
	

	// EVENTSOURCE
	@RequestMapping(value = "/subscribe", method = RequestMethod.GET)
	public void sseResponse(HttpServletResponse response) throws Exception {
		response.setContentType("text/event-stream");
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setStatus(200);
		final PrintWriter out = response.getWriter();
		final UiClient uiCLient = new UiClient(out);
		ServerController.getInstance().setUiClient(uiCLient);
		for (int i = 0; i < 900; i++) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// SEND DESTINATION TO CAR
	@RequestMapping(value = "/destination/{carId}/{lat}/{lng}", method = RequestMethod.POST)
	public ResponseEntity<String> sendDestination(@PathVariable Integer carId,
			@PathVariable Double lat, @PathVariable Double lng) {
		LocationSignal signal = new LocationSignal();
		signal.setLat(lat);
		signal.setLng(lng);
		if (ServerController.getInstance().sendDestination(carId, signal)) {
			return new ResponseEntity<String>("sent", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Not sent", HttpStatus.NO_CONTENT);
		}

	}
	
	//GET CARS
	@RequestMapping("/cars")
	public ResponseEntity<List<Car>> getCars() {
		List<Car> cars = carDao.getAllCars();
		if(cars.size()>0){
			return new ResponseEntity<List<Car>>(
					carDao.getAllCars(), HttpStatus.OK);
		}
		return new ResponseEntity<List<Car>>(HttpStatus.NO_CONTENT);
	}
	
	//GET CONNECTED CARS
	@RequestMapping("/cars/connected")
	public ResponseEntity<List<Car>> getConnectedCars() {
		ArrayList<Car> cars = new ArrayList<Car>();
		ArrayList<ServerThread> threads = ServerController.getInstance().getThreads();
		for(ServerThread thread : threads){
			Car car = carDao.getCarForId(thread.getCarId());
			cars.add(car);
		}
		return new ResponseEntity<List<Car>>(
				cars, HttpStatus.OK);
	}

}
