import java.io.*;
import java.net.*;
import java.util.*;


public class ClientHandler implements Runnable {
	private final Socket client;
	private Server server;
	private boolean connected;
	private boolean loggedIn;
	
	public ClientHandler(Socket socket, Server server) {
		this.client = socket;
		this.server = server;
		connected = true;
		loggedIn = false;
	}
	
	@Override
	public void run() {
		try {
			// to have access to the client's messages
			InputStream input = client.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(input);
			
			// to have the ability to send the client objects
			OutputStream outputStream = client.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			while (connected) {
				
				// check closed via client
				// TODO CREATE A CHECK SO THAT WE DON'T GET EXCEPTIONS WHEN
				// CLIENT CLOSES CONNECTION 
				
				List<ServerMessage> messages = (List<ServerMessage>) ois.readObject();
				ServerMessage m = messages.get(0);
				MessageTypes msgType = m.getType();
				
				// identify what to do
				switch (msgType) {
				case CHAT_MESSAGE:
					if (loggedIn) {}
						// server.handleTextMessage((LoginMessage) m);
					else {
						m.setStatus(MessageStatus.FAILED);
					}
					break;
				case LOGIN:
					server.handleLogin((LoginMessage) m); // this will modify the status after handling
					if (m.getStatus() == MessageStatus.SUCCESS)
						loggedIn = true;
					break;
				case LOGOUT:
					server.handleLogout((LogoutMessage)m);
					if (m.getStatus() == MessageStatus.SUCCESS) {
						connected = false;
						loggedIn = false;
					}
					break;
				default:
					System.out.println("Undefined message type");
				}
				
				messages.removeAll(messages); 	// refresh messages to send back
												// in this case we are only dealing with 1 message at a time
				messages.add(m);
				oos.writeObject(messages); // this should send the object back to the client -
				oos.flush();
				if(loggedIn == false && connected == false) { // do a check if logout was run
					client.close();
				}
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				System.out.println("Closing connection between client");
				client.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			server.decrementCurrentConnections();
		}
		
	}
}
