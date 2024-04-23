import java.io.Serializable;

// All message types will extend this class 
public class ServerMessage implements Serializable{
    private MessageTypes type; // type of message
    private MessageStatus status;

    // public ServerMessage(MessageTypes type, MessageStatus status) {
    //     this.type = type;
    //     this.status = status;
    // }

    public void setType(MessageTypes type) {
        this.type = type;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public MessageTypes getType() {
        return type;
    }

    public MessageStatus getStatus() {
        return status;
    }
}
