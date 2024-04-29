import java.util.List;

public class CreateChatMessage extends ServerMessage {
    private List<String> participantIds;
    private ChatRoom createdChat; 
    //private String chatName; 

    public CreateChatMessage(List<String> participantIds) {
       this.setType(MessageTypes.CREATE_CHAT);
       this.setStatus(MessageStatus.PENDING);
        this.participantIds = participantIds;
    }

    public List<String> getParticipantIds() {
        return participantIds;
    }

    // public String getChatName() {
    //     return chatName;
    // }

    // If a successful creation on the server then place chat here
    public void setCreatedChat(ChatRoom createdChat) {
        this.createdChat = createdChat;
    } 

    // Client will use this in it's handler method to add the new chat
    // to the currentUser's chat list
    // call update chatroom function
    public ChatRoom getCreatedChat() {
        return createdChat;
    }
}
