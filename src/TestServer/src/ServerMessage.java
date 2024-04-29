import java.io.Serializable;

// All message types will extend this class 
public class ServerMessage implements Serializable{
    private MessageTypes type; // type of message
    private MessageStatus status;
    //private String message;

/*     public ServerMessage() {
        type = MessageTypes.UNDEFINED;
        status = MessageStatus.UNDEFINED;
    } */
    
    public void setType(MessageTypes type) {
        this.type = type;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    // public void setMessage(String message) { 
    //     this.message = message;
    // }

    public MessageTypes getType() {
        return type;
    }

    public MessageStatus getStatus() {
        return status;
    }

    // public String getMessage() {
    //     return message;
    // }
}