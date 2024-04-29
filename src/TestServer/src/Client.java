import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Client {
    private String HOSTIP = "localhost"; // hostName/IP to connection
    private int PORT = 12345; // Port number to connect to on hostName
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket socket;  // flag to determine if login has been authenticated

    // LoginGUI attributes
    private LoginUI loginGUI;
   
    // MainGUI attributes
    private User currentUser;
    private ClientGUI mainGUI;
    private Map<String, String> userlist; // Username, Display name?
    private boolean loggedIn;

    //Message
    private MessagePanel mssgPanel;
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {
        try {
            socket = new Socket(HOSTIP, PORT);
            if (!socket.isConnected()) {
                JOptionPane.showMessageDialog(null, "Server connection issue. Check IP.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }

            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
           // new Thread(new IncomingReader()).start();
            //Thread chatMessageListenerThread = new Thread(new IncomingReader());
            //chatMessageListenerThread.start();
            new Thread(() -> listenForMessages()).start();
            loggedIn = false;
            invokeLoginUI();

            while (!loggedIn) { // wait until we get a valid login
            	// waiting for login message to return
                Thread.sleep(1000);
                System.out.println("waiting to be verified");
            }
            System.out.println("CONNECTED");
            invokeMainGUI();
            while (loggedIn) {
            	// wait for logout
            }
            
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void listenForMessages() {
        while (true) {
            try {
            	// Accept a Message
                List<ServerMessage> fromServer = (List<ServerMessage>) input.readObject();
                for (ServerMessage m : fromServer) { // Route each message that came in from the server
                    //debugging
                    System.out.println("Debug: Message received from server: " + m);
                	switch (m.getType()) {
                        case LOGIN:
                            System.out.println("Debug: LoginMessage received: " + m);
                            new Thread(() -> handleLoginMessage((LoginMessage) m)).start();// Cast to Login Message
                            break;

                        case LOGOUT:
                            new Thread(() -> handleLogoutMessage((LogoutMessage) m)).start();
                            break;
                        case CHAT_MESSAGE:
                            new Thread(()->handleChatMessage((ChatMessage) m)).start();
                            System.out.println("Debug: CHAT_MESSAGE received: " + ((ChatMessage) m).getMessage());
                            break;
                        case GET_LOGS:
                            new Thread(() -> handleGetLogs((LogMessage) m)).start();
                            break;
                        case UPDATE_USER:
                            // i don't think we need to do anything here
                            new Thread(() -> handleUpdateUser((UpdateUserMessage) m)).start();
                            break;
                        case CREATE_CHAT:
                        	new Thread(()->handleCreateChatMessage((CreateChatMessage) m)).start();
                            break;
                        case ADD_USERS_TO_CHAT:
                            new Thread(() -> handleAddUsersToChat((AddUsersToChatMessage) m)).start();
                            break;
                        case NOTIFY_USER:
                            // low priority
                            break;
                        case PIN_CHAT:
                            // save this for later if we have time
                            break; 
                        default:
                            System.out.println("Reached an undefined state");
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void sendMessageToServer(ServerMessage m) { //This method works, it does recognize LoginMessage but nto ChatMessage
        try {
            System.out.println("Message Type to send: " + m.getType());
        	List<ServerMessage> toServer = new ArrayList<>();
        	toServer.add(m);
        	
            output.writeObject(toServer);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Message Handling Methods ----------------------------------------------
    public void handleLoginMessage(LoginMessage msg) {
    	// The user has sent a request for login
        // we need to know if it was successful
        loggedIn = msg.isSuccessful() && socket.isConnected();

        if (loggedIn) {
            currentUser = msg.getUser();
            killLoginGUI();
        } else {  /*  Stay in login loop */
            loginGUI.updateWaitingStatus(loggedIn);
        }            
    }

    public void handleLogoutMessage(LogoutMessage msg) {
        // TODO implement this  
    }

    public void handleUpdateUser(UpdateUserMessage msg) {
        // TODO implement this
    }

    public void handleGetLogsMessage(LogMessage msg) {
        // TODO implement this  
    }

    public void handleAddUsersToChat(AddUsersToChatMessage msg) {
        // TODO implement this  
    }

    public void handleGetLogs(LogMessage msg) {
        // TODO implement this  
    }

    public void handleChatMessage(ChatMessage chatMessage) {
        // Log the received chat message for debugging
        System.out.println("Chat message received: " + chatMessage.getMessage());
        // Broadcast the message to other clients (excluding the sender)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //chatMessage.setTimestamp(LocalDateTime.now());
                //chatMessage.setSender(currentUser);
                Message fromServer = chatMessage.getMessage();
                fromServer.setTimestamp(LocalDateTime.now());
                mainGUI.updateMessagePanel(fromServer.toString()); //This should update the GUi message panel 
                System.out.println("updateMessagePanel in Client");
            }});
    }
    
    public void handleCreateChatMessage(CreateChatMessage createChatMessage) {
    	// System.out.println("Chatroom info received: " + createChatMessage.getParticipantIds() + " " 
    	// 											  + createChatMessage.getChatName());
    	// wip
    }
    
    // Other Methods ****************************************************************
    private Client getThisClient() {
        return this;
    }

    private void killLoginGUI() {
        EventQueue.invokeLater(new Runnable() { 
			public void run() {
				try {
					loginGUI.setVisible(false); // turn off login gui?
                    loginGUI = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		    });
    }

    private void invokeLoginUI() {
        EventQueue.invokeLater(new Runnable() { 
            public void run() {
                try {
                    loginGUI = new LoginUI(getThisClient());
                    loginGUI.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            });
    }

    private void invokeMainGUI() {
        EventQueue.invokeLater(new Runnable() { 
			public void run() {
				try {
                    // TODO remove the test user when we get the server to pass a user on login 
                    // test purposes start
                    // Message msg1 = new Message("M_1", "User_2", "1", LocalDateTime.now());
                    // Message msg2 = new Message("M_2", "User_2", "1", LocalDateTime.now());
                    // ChatRoom chat = new ChatRoom();
                    // chat.addMessage(msg2);
                    // chat.addMessage(msg1);
                    // chat.setChatID("Chat_1");
                    // User testUser = new User();
                    // testUser.addChat(chat);
                    // testUser.setIT(true);
                    // testUser.setUserName("User 1");
                    // mainGUI = new ClientGUI(getThisClient(), testUser);
                    // currentUser = testUser;
                    // end test 
                    List<User> users = generateUsers(7, 10);
                    mainGUI = new ClientGUI(getThisClient(), users.get(0));
                    currentUser =  users.get(0);
                    // uncomment this when server passes a user
					//mainGUI = new ClientGUI(getThisClient(), currentUser); 
					mainGUI.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		    });
    }
    
    // Testing Methodsss
    private void testLoginFromUI(ServerMessage m){
                //test waiting
                LoginMessage msg = (LoginMessage) m;
                // test successful
                loginGUI.updateWaitingStatus(true);
                loggedIn = true; // 
                currentUser = msg.getUser();

                 killLoginGUI();
    } 

    // Method to generate users
    public static List<User> generateUsers(int numUsers, int numChatRooms) {
        List<User> users = new ArrayList<>();
        List<ChatRoom> allMessages = populateChatRooms(numUsers, numChatRooms); // Generate messages for all chat rooms
        
        for (int i = 1; i <= numUsers; i++) {
            // Generate user with a unique username
            User user = new User("User" + i, "FirstName" + i, "LastName" + i, "User" + i, "password" + i, false, new ArrayList<>());
            for (ChatRoom room : allMessages) {
                if (room.isParticipant(user.getUsername())) {
                    user.addChat(room);
                }
            }
            users.add(user); // Add user to the list
        }
        
        return users;
    }

    public static List<ChatRoom> populateChatRooms(int numUsers, int numberOfChatRooms) {
    List<ChatRoom> allMessages = new ArrayList<>();

    // Generate dummy messages for each chat room
    for (int i = 0; i < numberOfChatRooms; i++) {
        // Create a new chat room
        ChatRoom chatRoom = new ChatRoom();
        
        // Randomly select participants for this chat room
        List<String> participants = new ArrayList<>();
        Random random = new Random();
        int numParticipants = 0;
        for (int j = 1; j < numUsers; j++) {
            if (j == 1 || (numParticipants < 2 && random.nextBoolean())) {
                 participants.add("User" + j);
                 numParticipants++;
            }
        }
        chatRoom.setParticipants(participants);

        // Generate random number of messages for each chat room (between 5 and 20)
        int numberOfMessages = new Random().nextInt(16) + 5;
        for (int k = 0; k < numberOfMessages; k++) {
            String sender = participants.get(new Random().nextInt(participants.size()));
            String messageContent = generateRandomMessage();
            Message message = new Message(messageContent, sender, chatRoom.getChatID(), LocalDateTime.now());
            chatRoom.addMessage(message);
            
        }
        allMessages.add(chatRoom);
    }
    return allMessages;
}

    // Method to generate random message content
    private static String generateRandomMessage() {
        String[] messages = {
            "Hello!",
            "Hello, my name is BiG BOi!",
            "How are you?",
            "What's up?",
            "I'm good, thanks!",
            "This is a random message.",
            "Let's meet tomorrow.",
            "Did you watch the game last night?",
            "I'm feeling hungry.",
            "Can you send me the report?",
            "I'll be late today.",
            "AHHHHHHHHHHHHHHHHH im gonna kms",
            "lol Just kidding this is sosososoosososososos much fun :^)",
            "This is another random Message"
        };
        return messages[new Random().nextInt(messages.length)];
    }
}