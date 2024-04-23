// Proposed workflow:
// Server receives message of type Logout
// if .getStatus() == PENDING 
// setStatus(MessageStatus.Success)
// Send LogoutMessage back to the client
//      user is logged out from their client

// Server DISCONNECT CLIENT
// REMOVE FROM ONLINE USERS
// i think that's it...

// I don't think there's any other data we need to send

public class LogoutMessage extends ServerMessage {
    private MessageTypes type;
    private MessageStatus status;

    public LogoutMessage(){
        this.type = MessageTypes.LOGOUT;
        this.status = MessageStatus.PENDING;
    }

    public MessageStatus getStatus(){
        return status;
    }

    public MessageTypes getType(){
        return type;
    }

    public void setStatus(MessageStatus status){
        this.status = status;
    }
}
