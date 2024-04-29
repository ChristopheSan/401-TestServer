import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter; 

public class App {
    public static void main(String[] args) throws Exception {
        String path = "database\\chats\\dummyChatroom.csv";

        LocalDateTime time = LocalDateTime.now();

        System.out.println(time);

        Scanner reader = new Scanner(new File(path), StandardCharsets.UTF_8);
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
        ChatRoom newChatRoom = new ChatRoom(participants, messages, chatID, chatID);

        int cnt = 0;
        System.out.print("Participants: ");
        for (String users : newChatRoom.getParticipants()) {
            System.out.print(users + ", ");
        }
        System.out.println("");

        for (Message msg : newChatRoom.getMesssages()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");
            System.out.println(cnt++ + ": " + msg.getTimestamp().format(formatter) + " " + msg.getMessage());
        }

        // List<User> createdUsers = generateUsers(10, 10);

        // for (User u : createdUsers) {
        //     System.out.println(u.getFirstName());
        // }
    }

    public static List<User> generateUsers(int numUsers, int numChatRooms) {
        List<User> users = new ArrayList<>();
        List<ChatRoom> allMessages = populateChatRooms(numUsers, numChatRooms); // Generate messages for all chat rooms
        
        for (int i = 1; i <= numUsers; i++) {
            // Generate user with a unique username
            User user = new User("User" + i, "FirstName" + i, "LastName" + i, "username" + i, "password" + i, false, new ArrayList<>());
            
            // Filter messages for this user
            //List<Message> userMessages = new ArrayList<>();
            for (ChatRoom room : allMessages) {
                    user.addChat(room);

            }
            //user.addChat(null)(userMessages); // Set messages for the user
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
        for (int j = 0; j < numUsers; j++) {
            if (random.nextBoolean()) { // Randomly decide if the user participates in this chat room
                participants.add("User" + j);
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
            allMessages.add(chatRoom);
        }
    }
    return allMessages;
}

    // Method to generate random message content
    private static String generateRandomMessage() {
        String[] messages = {
            "Hello!",
            "How are you?",
            "What's up?",
            "I'm good, thanks!",
            "This is a random message.",
            "Let's meet tomorrow.",
            "Did you watch the game last night?",
            "I'm feeling hungry.",
            "Can you send me the report?",
            "I'll be late today."
        };
        return messages[new Random().nextInt(messages.length)];
    }
    

}
