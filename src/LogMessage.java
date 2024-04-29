import java.util.List;

public class LogMessage extends ServerMessage {
    private String userId; 			
    private String requestDetails; 	
    private List<ChatRoom> userChats;

    // Constructor for log message
    public LogMessage(String userId) {
        this.setType(MessageTypes.GET_LOGS);
        this.setStatus(MessageStatus.PENDING);
        this.userId = userId;
        //this.requestDetails = requestDetails;
    }

    // Getter and setter for the status of the log request
    // get and set handled by the super class

    // Getter for the user ID
    public String getUserId() {
        return userId;
    }

    // Getter for the request details
    public String getRequestDetails() {
        return requestDetails;
    }

    public void setUserChats(List<ChatRoom> userChats) {
        this.userChats = userChats;
    }

    public List<ChatRoom> getUserChats() {
        return userChats;
    }
}
