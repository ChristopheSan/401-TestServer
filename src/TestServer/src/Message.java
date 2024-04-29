import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;
import java.util.*;

// This is the Message that is contained within ChatRoom Class
public class Message implements Serializable{
	private String message;
	private String sender;
	private String chatID; //will handle multiple receivers, will contain chatroom ID
	private LocalDateTime timestamp;
	private String formattedTimestamp;
	private String id;

	// Constructor for a brand new Message
	public Message(String message, String sender, String chatID) {
		this.message = message;
		this.sender = sender;
		this.chatID = chatID;
		timestamp = LocalDateTime.now();
		// this format is:
		// 2024-04-27T16:41:49.080818
	}
	
	//Load From File constructor	
	public Message(String message, String sender, String chatid, LocalDateTime timestamp) {
		this.message = message;
		this.sender = sender;
		this.chatID = chatid;
		this.timestamp = timestamp;
	}

    //getters
	public String getMessage() {
		return message;
	}
	
	public String getSender() {
		return sender;
	}
	
	public String getChatID() {
		return chatID;
	}
	
	public String getID() {
		return id;
	}

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

	//setters
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public void setChatID(String chatID){
		this.chatID = chatID;
	}

	public void setTimestamp(LocalDateTime timestamp) {
        this.formattedTimestamp = timestamp.format(DateTimeFormatter.ofPattern("MM/dd HH:mm"));
    } 

	//other methods
	public String toStringForFile() {
		return message + "," + sender + "," + chatID + "," + timestamp;
	}

	public String toString() {
        //User sender = new User();
        //sender.setUserName("Dummy Sender");
		setTimestamp(timestamp);
        //return "[" + formattedTimestamp + "] " + sender.getUsername() + ": " + message;
		return "[" + formattedTimestamp + "] " + sender + ":\n " + message;
    }
}
