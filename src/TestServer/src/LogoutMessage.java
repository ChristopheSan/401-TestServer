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
    private String username;

    public LogoutMessage(String username){
    	this.type = MessageTypes.LOGOUT;
    	this.setUsername(username);
    	
    }


    public MessageTypes getType(){
        return type;
    }


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}
}
