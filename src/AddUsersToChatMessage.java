import java.util.List;

public class AddUsersToChatMessage extends ServerMessage {
    private String chatId; 
    private List<String> newParticipantIds;
    private String username;

    public AddUsersToChatMessage(String username, String chatId, List<String> newParticipantIds) {
        this.username = username;
    	this.chatId = chatId;
        this.newParticipantIds = newParticipantIds;
    }

    public String getChatId() {
        return chatId;
    }

    public List<String> getNewParticipantIds() {
        return newParticipantIds;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}