package server;

import java.awt.CardLayout;
import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.signal.LocationSignalDao;
import model.LocationSignal;

public class ServerController {
	
	private static  ServerController singleton;
	
	
	private UiClient uiClient;
	
	private ArrayList<ServerThread> threads = new ArrayList<ServerThread>();
	
	
	ApplicationContext context ;
	LocationSignalDao signalDao ;
	
	private ServerController(){
		
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring_config.xml");
		LocationSignalDao signalDao = (LocationSignalDao) context
				.getBean("signalDAO");
		
	}
	
	public static synchronized ServerController getInstance(){
		if(singleton == null) singleton = new ServerController();
		return singleton;
	}
	
	public void onMessage(String messageType, String message, LocationSignal signal){
		
		if(uiClient!= null){
			uiClient.sendMessage(messageType, message,signal);
//			signalDao.insert(signal);
		}
		
	}

	public UiClient getUiClient() {
		return uiClient;
	}

	public void setUiClient(UiClient uiClient) {
		this.uiClient = uiClient;
	}
	
	public void addThread(ServerThread thread){
		threads.add(thread);
	}
	
	public boolean sendDestination(int carId, LocationSignal signal){
			
		for(ServerThread thread:threads){
			if(thread.getCarId() == carId){
				thread.sendDestination(signal);
				return true;
			}
		}
		return false;
	}

	public ArrayList<ServerThread> getThreads() {
		return threads;
	}

	public void setThreads(ArrayList<ServerThread> threads) {
		this.threads = threads;
	}
	
	
	
	
	
	
	
}
