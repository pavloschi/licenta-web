package model;

import java.util.ArrayList;

public class Trip {
	
	private int tripNr;
	private ArrayList<LocationSignal> locations;
	private double distance;
	
	
	public void addLocationSignal(LocationSignal signal){
		locations.add(signal);
	}
	
	public void clearLocations(){
		locations.clear();
	}
	
	public Trip() {
		super();
		locations = new ArrayList<LocationSignal>();
	}
	
	

	public Trip(ArrayList<LocationSignal> locations) {
		super();
		this.locations = locations;
	}



	public Trip(int tripNr, ArrayList<LocationSignal> locations) {
		super();
		this.tripNr = tripNr;
		this.locations = locations;
	}
	
	public int getTripNr() {
		return tripNr;
	}
	public void setTripNr(int tripNr) {
		this.tripNr = tripNr;
	}
	public ArrayList<LocationSignal> getLocations() {
		return locations;
	}
	public void setLocations(ArrayList<LocationSignal> locations) {
		this.locations = locations;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	
	
	
	
	

}
