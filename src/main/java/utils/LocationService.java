package utils;

import java.util.ArrayList;
import java.util.List;

import model.LocationSignal;

public class LocationService {

	public static double distance(LocationSignal l1, LocationSignal l2) {
		
		
		double lat1 = l1.getLat();
		double lat2 = l2.getLat();
		double lng1 = l1.getLng();
		double lng2 = l2.getLng();

		final int R = 6371; // Raza pamantului in kilometri 

		Double latDistance = Math.toRadians(lat2 - lat1);
		Double lngDistance = Math.toRadians(lng2 - lng1);
		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lngDistance / 2)
				* Math.sin(lngDistance / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; //conversie in metri

	//	distance = Math.pow(distance, 2);

		return distance;

	}
	
	public List<LocationSignal> signals = new ArrayList<LocationSignal>();

//	public static final double razaKilometri = 6372; // raza pamantului in km

/*	public static double distHaversine(double lat1, double lng1, double lat2,
			double lng2) {

		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLng / 2)
				* Math.sin(dLng / 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.asin(Math.sqrt(a));
		return razaKilometri * c * 1000;
	}
	
	public static double distTotal(List<LocationSignal> signals){
		double totalDist = 0;
		for(int i = 0; i < signals.size() -1 ; i++){
			LocationSignal loc1 = signals.get(i);
			LocationSignal loc2 = signals.get(i+1);
			totalDist += distHaversine(loc1.getLat(), loc1.getLng(), loc2.getLat(), loc2.getLng());
		}
	
		return totalDist * 0.001; // transforma inapoi in kilometri
	}*/

}


