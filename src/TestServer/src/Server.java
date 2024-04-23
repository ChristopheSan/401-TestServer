import java.io.*;
import java.util.Date;
import java.net.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Server {
	private ServerSocket server = null;
	private int currentConnections;
	
	public Server() {
		currentConnections = 0;
		
		try {
			server = new ServerSocket(1234);
			server.setReuseAddress(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(server.getInetAddress().getHostAddress().toCharArray());
		
		//loadServer();
	}
	public void loadServer() {
		
		try {


		while(true) {
			System.out.println("Awaiting connection...");
			// Accept the connection from client
			
			Socket client = server.accept();
			
			System.out.println("New Client connected "
					+ client.getInetAddress().getHostAddress());
			currentConnections++;
			System.out.println("Current Connections: " + currentConnections);
			
			// we can create a client handler or process the connect now
			
			//With ClientHandler
			ClientHandler newThread = new ClientHandler(client, this);
			new Thread(newThread).start();
			
			
		} // wait for new connections
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void handleLogin(LoginMessage msg) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()));
		
		if (msg.getStatus() == MessageStatus.PENDING) {
			msg.setStatus(MessageStatus.SUCCESS);
			msg.setSuccess(true);
			System.out.println("Successful Login: " + timeStamp);
		}
		else {
			msg.setStatus(MessageStatus.FAILED);
			System.out.println("Unsuccessful Login: " + timeStamp);
		}
		
	}
	public void handleTextMessage(Message msg) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()));
		System.out.println("Message received at " + timeStamp);
		if (msg.getStatus() == Status.NOT_SERVICED) {
			msg.setStatus(Status.SUCCESS);
			//msg.setText(toSpongeCase(msg.getText())); // convert to spongecase lol
			msg.setText(msg.getText().toUpperCase());
		}
		else {
			msg.setStatus(Status.UNSUCCESSFUL);
			// dont edit text
		}
		
	}
	public void handleLogout(LogoutMessage msg) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()));
		System.out.println("Logout Message received at " + timeStamp);
		if (msg.getStatus() == MessageStatus.PENDING) {
			msg.setStatus(MessageStatus.SUCCESS);
		}
		else {
			msg.setStatus(MessageStatus.FAILED);
		}
		
	}
	private String toSpongeCase(String str) {
		String ret = "";
		
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if(i % 2 == 0 && Character.isLetter(c)) {
				ret = ret + Character.toUpperCase(c);
			}
			else if (i % 2 == 1 && Character.isLetter(c)) {
				ret = ret + Character.toLowerCase(c);
			}
			else
				ret = ret + c;
				
		}
		return ret;
	}
	public void decrementCurrentConnections(){
		currentConnections--;
	}
}
