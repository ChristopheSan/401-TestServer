
public enum MessageTypes {
	LOGIN,
	LOGOUT,
	CHAT_MESSAGE, 		// sending chat message
	GET_LOGS, 			// IT user requesting user logs
	UPDATE_USER, 		// IT user editing a user
	CREATE_CHAT, 		// User creating a new chat-room
	ADD_USERS_TO_CHAT,	// Adding a user to the chat
	NOTIFY_USER,		// Send notification(new msgs) to the recipient
	PIN_CHAT,			// When a user pins a chat, Server must keep a record
	UNDEFINED			// Default type
}

// public class LoginMessage implements serializable {
//	string username;
//	string password;
//   
//	MessageTypes Type = Login;
//}

// public class CreateChatMessage implements serializable {
//	string[] names;
//	//	MessageTypes Type;
//}

// public class ChatMessage implements serializable {
//	Message msg;
//	MessageTypes Type;
//}