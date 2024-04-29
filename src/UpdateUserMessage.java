import java.util.Map;

public class UpdateUserMessage extends ServerMessage {
    private String userId;
    private String password;
    private String firstname;
    private String lastname;
    private Map<String, String> updates;

    public UpdateUserMessage(String userId, String password, String firstname, String lastname) {
        this.setType(MessageTypes.UPDATE_USER);
        this.setStatus(MessageStatus.PENDING);
        this.userId = userId;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }

    
}
