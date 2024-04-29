public class NotifyMessage extends ServerMessage {
    private String notificationText; 
    private String username;

    public NotifyMessage(String notificationText) {
        this.setType(MessageTypes.NOTIFY_USER);
        this.setStatus(MessageStatus.PENDING);
        this.notificationText = notificationText;        
    }

    public String getNotificationText() {
        return notificationText;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}