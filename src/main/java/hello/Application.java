package hello;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.catalina.Server;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;




import server.ServerController;
import server.ServerThread;
import dao.signal.LocationSignalDao;

@SpringBootApplication
public class Application {
	private static ServerSocket ss;
	 
	 public static ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("spring_config.xml");
	 
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring_config.xml");

/*		LocationSignalDao signalDao = (LocationSignalDao) context
				.getBean("signalDAO");*/
		
		
		try {
			ss = new ServerSocket(4321);
			System.out.println("Awaiting connections");
			while (true) {
				ServerThread thread = new ServerThread(ss.accept());/*.start();*/
				thread.start();
				ServerController.getInstance().addThread(thread);
			}
		} catch (IOException ex) {
			Logger.getLogger(Application.class.getName())
					.log(Level.SEVERE, null, ex);
		}
	}
}
