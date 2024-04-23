import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Client {
	private static ObjectOutputStream oos = null;
	private static ObjectInputStream ois = null;

	public static void main(String[] args) {
		String input = "";
		Scanner sc = new Scanner(System.in);
		
		try (Socket socket = new Socket("10.0.0.133", 1234)) {
			// once we have connected need to "login"
			if(!Login(socket)) {
				// autosend login on startup
				System.out.println("Login failed. Restart program...");
			}
			
			else { // once we're logged in	
				System.out.println("SUCCESSFUL LOGIN\nInput a text message to send to the server. Type \"logout\" to quit.");
				while (input.compareTo("logout") != 0) {
					input = sc.nextLine().toLowerCase();
					if (input.compareTo("logout") != 0) {
						// Sending messages to the server
						List<Message> messages = new LinkedList<>();
						Message toSend = new Message(Type.TEXT, Status.NOT_SERVICED, input);
						messages.add(toSend);
						sendMessage(socket, messages);
					}
				}
				
				// closing the scanner object
				sc.close();
				String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()));
				System.out.print("Logging out at: " + timeStamp);
				Logout(socket);
				// socket should be closed
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static boolean Login(Socket socket) {
		// Create login message and send it to the server
		// then we need to read the return message from the server
		// if it returns success? then return true
		// else, false.
		
		// Create and oos to send an object through
		try {
			 oos = new ObjectOutputStream(socket.getOutputStream());
			 ois = new ObjectInputStream(socket.getInputStream());
			
			// Create login message to get validated
			// should work
			 List<Message> messages = new LinkedList<>();
			 Message login = new Message(Type.LOGIN);
			 messages.add(login);
			 oos.writeObject(messages);
			 oos.flush();
			
			// should fail
//			Message failLogin = new Message(Type.LOGIN, Status.SUCCESS, "");
//			oos.writeObject(failLogin);
			
			// receive the object back
			List<Message> fromServer = (List<Message>) ois.readObject();
			Message returned = fromServer.get(0); 
			if (returned.status == Status.SUCCESS) // login was successful
				return true;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// else the login failed
		return false;
	}
	public static void sendMessage(Socket socket, List<Message> messages) {
		try {
			Message m = messages.get(0);
			String original = m.getText();
			
			oos.writeObject(messages);
			oos.flush();
			
			// receive the object back
			List<Message> fromServer =(List<Message>)ois.readObject();
			Message returned = fromServer.get(0);
			
			if (returned.getStatus() == Status.UNSUCCESSFUL) {
				System.out.println("There was an unsucessful conversion on the server end");
			}
			else if (returned.getStatus() == Status.SUCCESS) {
				System.out.println("To Server: " + original +
							   "\nFrom Server: " + returned.getText());
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	public static void Logout(Socket socket) {
		Message m = new Message(Type.LOGOUT);
		List<Message> messages = new LinkedList<>();
		messages.add(m);
		
		try {
			oos.writeObject(messages);
			
			List<Message> fromServer = (List<Message>) ois.readObject();
			Message returned = fromServer.get(0); 
			if (returned.status == Status.SUCCESS) // logout was successful
				socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Object sendMessageToServer(LoginMessage request) {
		// TODO Auto-generated method stub
		return null;
	}

}
