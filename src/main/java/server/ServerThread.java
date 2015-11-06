package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import scala.annotation.meta.getter;
import model.LocationSignal;
import model.SocketMessage;

public class ServerThread extends Thread {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	double lat;
	
	private String obj;
	
	private int carId;

	public ServerThread(Socket socket) {
		try {
			
			this.socket = socket;
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void run() {

		try {
			while (true) {
				
				Object obj = in.readObject();
				
				if(obj instanceof SocketMessage){
					SocketMessage message = (SocketMessage) obj;
					if(message.getMessage() == SocketMessage.CAR_CONNECTED){
						setCarId(message.getCarId());
						System.out.println("Car " + carId + " connected!"); 
					}
				}
				
				if(obj instanceof LocationSignal){
					LocationSignal signal = (LocationSignal) obj;
					ServerController.getInstance().onMessage("location","",signal);
					
				}
				
				System.out.println(obj);
				
//				System.out.println("=====");
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public void sendDestination(LocationSignal signal) {
		if(out != null){
			try {
				out.writeObject(signal);
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	

}
