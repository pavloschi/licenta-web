package server;

import java.io.PrintWriter;

import org.json.JSONObject;

import model.LocationSignal;

public class UiClient {
	
	PrintWriter out;
	
	public UiClient(PrintWriter out) {
		super();
		this.out = out;
	}



	public PrintWriter getOut() {
		return out;
	}



	public void setOut(PrintWriter out) {
		this.out = out;
	}



	public void sendMessage(String messageType, String message,LocationSignal location) {
		
		if(out!=null){
			
			JSONObject json = new JSONObject(location);
			System.out.println(json.toString());
			out.print("event: "+ "location" +"\n");
			
			out.print("data: " + json.toString() + "\n\n");
			
			out.flush();
		
		}
	}
	
}
