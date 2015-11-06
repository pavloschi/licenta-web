package dao.signal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import utils.Const;
import utils.LocationService;
import model.LocationSignal;
import model.Trip;

public class LocationSignalDaoImpl implements LocationSignalDao {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
	}

	@Override
	public void insert(LocationSignal signal) {
		String sql = "INSERT into location_signals(timestamp, carId,lat,lng,speed,code)"
				+ "values(?,?,?,?)";
		jdbcTemplate.update(sql, signal.getTimestamp(), signal.getCarId(),
				signal.getLat(), signal.getLng(), signal.getSpeed(),
				signal.getCode());

	}

	@Override
	public List<LocationSignal> getAllSignals() {
		String sql = "SELECT * from location_signals";
		List<LocationSignal> signals = jdbcTemplate.query(sql,
				new LocationSignalMapper());
		return signals;
	}
	

	@Override
	public List<LocationSignal> getAllSignalsForCar(int carId) {
		String sql = "SELECT * from location_signals where carId = ?";
		List<LocationSignal> signals = jdbcTemplate.query(sql,new Object[]{carId} , new LocationSignalMapper());
		return signals;
	}

	@Override
	public List<Trip> getAllTripsForCar(int carId) {
		String sql = "SELECT * from location_signals where carId = ?";
		List<LocationSignal> allLocations = jdbcTemplate.query(sql,new Object[]{carId} , new LocationSignalMapper());
		System.out.println("All locations: " + allLocations.size());
		ArrayList<Trip> trips = new ArrayList<Trip>();
		Trip trip = new Trip();
		
		for(LocationSignal location:allLocations){
			
			if(location.getCode() == Const.TRIP_START){
				
				if(trip.getLocations().size() > 0){
					Trip tAux = new Trip();
					tAux.setLocations(new ArrayList<LocationSignal>(trip.getLocations()));
					System.out.println("Trip: "+ tAux.getLocations().size());
					trips.add(tAux);
					trip.clearLocations();
				}
			}
			trip.addLocationSignal(location);
		}
		
		if(trip.getLocations().size() > 0){
			trips.add(trip);
		}
		
		for(Trip t: trips){
			double totalDist = 0;
			ArrayList<LocationSignal> locations = t.getLocations();
			if(t.getLocations().size() > 0){
				for(int i = 0; i < locations.size() -1 ; i++){
					totalDist += LocationService.distance(locations.get(i), locations.get(i+1));
				}
			}
			totalDist = Math.round(totalDist*100.0)/100.0;
			t.setDistance(totalDist);
		}
		
		return trips;
		
	}
	
	static class LocationSignalMapper implements RowMapper<LocationSignal> {
		@Override
		public LocationSignal mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			
			LocationSignal signal = new LocationSignal();
			signal.setId(rs.getInt("id"));
			signal.setTimestamp(rs.getLong("timestamp"));
			signal.setCarId(rs.getInt("carId"));
			signal.setLat(rs.getDouble("lat"));
			signal.setLng(rs.getDouble("lng"));
			signal.setSpeed(rs.getInt("speed"));
			signal.setCode(rs.getInt("code"));
			return signal;
			
		}
	}
	
}
