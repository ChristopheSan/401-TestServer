import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class Server {
    private static final int PORT = 12345;
    private  Map<String, ObjectOutputStream> clients = new HashMap<>();
    private Map<String, String[]> credentials = new HashMap<>();
    
 
    /*
    user buildUser(string username){
    	String[] vals = credentials.get(username)
    	file the user file
    	get a list<string> chatids
    	for (String file : chatids)
    	build chat
    	add to userChatList

    	user.setChats = userChatList
    */

    public void start() {
    	
    	populateCredentials();
    	//buildChatRoom();
        User testuser = buildUser("testuser1");
    	System.out.println("Testuser Firstname: " + testuser.getFirstName());
        System.out.println("Testuser Lastname: " + testuser.getLastName());
        System.out.println("Testuser Username: " + testuser.getUsername());
        System.out.println("Testuser password: " + testuser.getPassword());
        for (ChatRoom chatroom : testuser.getChats()) {
            System.out.println("ChatID " + chatroom.getChatID() + "\n");
            for (Message m : chatroom.getMessages()) {
                System.out.println(m.toString());
            }
        }

    	// // Example file path for a chat room
        // String chatRoomFilePath = "dummyChatroom.csv";  

        // // Build a chat room using the specified file path
        // ChatRoom chatRoom = buildChatRoom(chatRoomFilePath);
        // if (chatRoom != null) {
        //     System.out.println("Chat room built successfully: " + chatRoom.getChatID());
        //     // You might want to add this chat room to a list or map that tracks all chat rooms
        // } else {
        //     System.out.println("Failed to build chat room from file: " + chatRoomFilePath);
        // }
        
        // Open the server to accept connections
    	try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                new Thread(new ClientHandler(clientSocket, this)).start();
            }
        }
        catch (IOException e) { 
            e.printStackTrace();
        }
    }

    public synchronized void addClient(String username, ObjectOutputStream out) {
        clients.put(username, out);
    }
    
    public synchronized void removeClient(String username) {
        clients.remove(username);
    }
    public synchronized void sendMessageToClient(String username, ServerMessage message) {
        ObjectOutputStream out = clients.get(username);
        if (out != null) {
            try {
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // chat messages?
    public synchronized void broadcastMessage(ServerMessage message) {

        for (ObjectOutputStream out : clients.values()) {
            try  {
                List<ServerMessage> messages = new ArrayList<>(); 
                messages.add(message);
                out.writeObject(messages);
                System.out.println("broadcasted message to client.") ;
            } catch (IOException e) {
                System.out.println("Error broadcasting message to client: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    
    public void handleLogin(LoginMessage msg, ObjectOutputStream outputStream) {	  	
        
        if (msg.getStatus() == MessageStatus.PENDING) {
        	if (validateCredentials(msg.getUsername(), msg.getPassword()))
        	{
        		msg.setStatus(MessageStatus.SUCCESS);
        		msg.setSuccess(true);
                addClient(msg.getUsername(), outputStream);
                
        		
        	} else 
        	{
        		msg.setStatus(MessageStatus.FAILED);
        	}
    		
    	}//
       

        // Send the response back to the client
        // sendMessageToClient(msg);
    	
        /*
    	  if (validateCredentials(message.getUsername(), message.getPassword())) {
              message.setSuccess(true);
              message.setCurrentUser(new User());  // where I need build user
              addClient(message.getUsername(), clients.get(message.getUsername()));
          } else {
              message.setSuccess(false);
          }
          sendMessageToClient(message.getUsername(), message);

          */
    }

    public boolean validateCredentials(String username, String password) {
        if (credentials.containsKey(username)) {
            String[] userDetails = credentials.get(username);
            return userDetails[2].equals(password); // Password is at index 2
        }
        return false;
    }


    
   

    public void handleLogout(LogoutMessage message) {
    	if (message.getStatus() == MessageStatus.PENDING) {
            removeClient(message.getUsername());
            message.setStatus(MessageStatus.SUCCESS);
            sendMessageToClient(message.getUsername(), message);
            System.out.println("User logged out and connection closed: " + message.getUsername());
        }
        
    }

    public void handleChatMessage(ChatMessage message) {
        broadcastMessage(message);
    }

    public void handleUpdateUser(UpdateUserMessage message) {
    	if (message.getStatus() == MessageStatus.PENDING) {
            removeClient(message.getUserId());
            message.setStatus(MessageStatus.SUCCESS);
            sendMessageToClient(message.getUserId(), message);
            System.out.println("User logged out and connection closed: " + message.getUserId());
        }
    }

    public void handleCreateChat(CreateChatMessage message) {
    	// Get participant IDs from the message
        List<String> participantIds = message.getParticipantIds();
        
        // Validate participant list
        if (participantIds == null || participantIds.isEmpty()) {
            message.setStatus(MessageStatus.FAILED);
            //sendMessageToClient(message.getUsername(), message); 		// need to fix sendMessageToClient
            return;
        }

        ChatRoom newChat = new ChatRoom(participantIds);
        
        message.setCreatedChat(newChat);
        message.setStatus(MessageStatus.SUCCESS);
        
    }
    
    public void handlePinChat(PinChatMessage message) {

    }

    public void handleNotifyUser(NotifyMessage message) {
        
    }
    
    public void handleAddUsersToChat(AddUsersToChatMessage message) {
    
    }

    
    public void populateCredentials() {
        try (BufferedReader br = new BufferedReader(new FileReader("credentialsData.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] cred = line.split(",");
                // do wanna add is_IT to this file?
                // Assuming the file format is: username,firstname,lastname,password
                if (cred.length >= 4) {
                    String username = cred[2]; // username is the third item
                    String[] userDetails = {cred[0], cred[1], cred[3]}; // last name, first name, password
                    credentials.put(username, userDetails);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading credentials file: " + e.getMessage());
        }
    }

    
    
    public User buildUser(String username) {
        // Retrieve user details from credentials map
        String[] userDetails = credentials.get(username);
        if (userDetails == null) {
            return null; // If user does not exist in credentials
        }

        //  chat rooms associated with this user
        //List<ChatRoom> userChats = getUserChats(username); // This method needs to be properly implemented to fetch actual data
        List<String> chatIDsToBuild = new ArrayList<>();
        File file = new File(username+".txt"); // username is the file path for userchat room IDs
        
        // Open up the file with the same username
    	if (!file.exists()) {
            System.err.println("File not found: " + username + ".txt");
            return null;
        }
        Scanner reader;
        try {
            reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine(); // each line a chatID we need to go build
                chatIDsToBuild.add(line); 
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // With the list we created from userfile - go find the chats
        List<ChatRoom> userChats = new ArrayList<>();
        for (String chatFilePath : chatIDsToBuild) {
            ChatRoom chatToAdd = buildChatRoom(chatFilePath+".csv");
            if (chatToAdd != null) // buildChatRoom success
                userChats.add(chatToAdd);
            else // buildChatRoom returned null
                System.out.println(chatFilePath + "was not found and did not build correctly.");
        }

        // Creating a new User object with detailed constructor
        User user = new User(username, userDetails[1], userDetails[0], 
                            username, userDetails[2], false, userChats); 
        return user;
    }

    
    
    private List<ChatRoom> getUserChats(String username) {
        // Dummy data for chat rooms
        List<ChatRoom> chats = new ArrayList<>();
        List<String> participants = Arrays.asList("user1", "user2", username); // Dummy participants, including the user

        // Dummy messages, utilizing the Message constructor correctly
        Message message1 = new Message("Hello from user1!", "user1", "chat123");
        Message message2 = new Message("Hello from user2!", "user2", "chat456");

        List<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);

        // Creating chat rooms with these messages
        ChatRoom chat1 = new ChatRoom(participants, messages, UUID.randomUUID().toString(), "chat1file.txt");
        ChatRoom chat2 = new ChatRoom(participants, messages, UUID.randomUUID().toString(), "chat2file.txt");

        chats.add(chat1);
        chats.add(chat2);

        return chats; // Returns the list of chat rooms for the user
    }  
    
    public ChatRoom buildChatRoom(String filePath) // (String populate )this "populate" should be able to populate the file
    {
    	File file = new File(filePath);
    	if (!file.exists()) {
            System.err.println("File not found: " + filePath);
            return null;
        }
    	
        String path = filePath;

        Scanner reader;
		try {
			reader = new Scanner(new File(path), StandardCharsets.UTF_8);
		
        String[] tokens;
        String line;

        // Line 1 is chatID
        // Line 2 is participants
        // Line 3 and bellow are messages
        
        String chatID;
        List<String> participants = new ArrayList<>();

        // Get ID
        line = reader.nextLine();
        tokens = line.split(",");
        // tokens[0] is the id
        chatID = tokens[0];

        // Get Participants
        line = reader.nextLine();
        tokens = line.split(",");

        for (String s : tokens)
            participants.add(s);

        // Get Chat Messages
        List<Message> messages = new ArrayList<>();
        // Loop until the end of the file to build messages
        while (reader.hasNextLine()) {
            line = reader.nextLine();
            tokens = line.split(",");
            // tokens[0] = text
            // tokens[1] = sender
            // tokens[2] = chatid
            // tokens[3] = date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(tokens[3], formatter);
            //Date date = (Date) dateFormat.parse(tokens[3]);

            Message newMsg = new Message(tokens[0], tokens[1],tokens[2],dateTime);
            messages.add(newMsg);
        }
        reader.close();
        // Build ChatRoom Object
        
        ChatRoom newChatRoom = new ChatRoom(participants, messages, chatID, filePath);
        
       // ChatRoom newChatRoom = new ChatRoom(participants, messages, chatID, chatID);
        
        // Print for testing purposes
        
        // int cnt = 0;
        // for (Message msg : newChatRoom.getMessages()) {
        //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");
        //     System.out.println(cnt++ + ": " + msg.getTimestamp().format(formatter) + " " + msg.getMessage());
        // }
        
        return newChatRoom;
        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return null;	
    }        

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
        
    //     server.populateCredentials(); // credentials are populated first

    //  // Test the creation of a chat room from a file
    //     String testFilePath = "chatRoom1.txt";  // Adjust the path as necessary
    //     ChatRoom testChatRoom = server.buildChatRoom(testFilePath);
    //     if (testChatRoom != null) {
    //         System.out.println("Chat room successfully created: " + testChatRoom.getChatID());
    //         for (Message msg : testChatRoom.getMessages()) {
    //             System.out.println(msg);
    //         }
    //     } else {
    //         System.out.println("Failed to create chat room from file.");
    //     }
       
       /* 
        User newUser = server.buildUser("chatRoom1.txt"); 
        if (newUser != null) {
            System.out.println("User: " + newUser.getUsername() + ", " + newUser.getFirstName() + " " + newUser.getLastName());
            for (ChatRoom chat : newUser.getChats()) {
                System.out.println("Chat ID: " + chat.getChatID());
                for (Message message : chat.getMesssages()) {
                    System.out.println("Message: " + message.toString());
                }
            }
        } else {
            System.out.println("User not found or no chats available.");
        }
        
       
        */
        
    }
}