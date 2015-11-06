package dao.car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;



import model.Car;
import model.LocationSignal;


public class CarDaoImpl implements CarDao{
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
	}
	

	@Override
	public List<Car> getAllCars() {
		String sql = "SELECT * from cars";
		List<Car> cars = jdbcTemplate.query(sql,new CarMapper());
		return cars;
	}

	@Override
	public Car getCarForId(int carId) {
		String sql = "SELECT * from cars where carId = ? limit 1";
		List<Car> cars = jdbcTemplate.query(sql,new Object[]{carId} , new CarMapper());
		if(cars.size() > 0){
			return cars.get(0);
		}
		return null;
	}
	
	
	
	static class CarMapper implements RowMapper<Car> {
		@Override
		public Car mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			
			Car car = new Car();
			car.setId(rs.getInt("carId"));
			car.setPlate(rs.getString("nr_plate"));
			car.setDriver(rs.getString("id_driver"));
			return car;
			
		}
	}

}
