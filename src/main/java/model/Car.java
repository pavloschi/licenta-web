package model;

public class Car {
	private int id;
	private String plate;
	private String driver;
	
	
	
	
	public Car() {
		super();
	}
	
	public Car(int id, String plate, String driver) {
		super();
		this.id = id;
		this.plate = plate;
		this.driver = driver;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	
	
}
