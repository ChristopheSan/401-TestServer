import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

class AddUsersToChatMessageTest {
    
    private AddUsersToChatMessage addUsersToChatMessage;
    private String chatId;
    private List<String> newParticipantIds;
    private String username;

    @BeforeEach
    void setUp() {
        chatId = "chat";
        username = "user";
        newParticipantIds = Arrays.asList("user1", "user2");
        addUsersToChatMessage = new AddUsersToChatMessage(username, chatId, newParticipantIds);
    }

    @Test
    void testMessageCreation() {
        assertEquals(chatId, addUsersToChatMessage.getChatId(), "Chat ID should match the one set in constructor");
        assertEquals(newParticipantIds, addUsersToChatMessage.getNewParticipantIds(), "Participant IDs should match the ones set in constructor");
        assertEquals(username, addUsersToChatMessage.getUsername(), "Username should match the one set in constructor");
    }

    @Test
    void testMessageParticipantsNotEmpty() {
        assertFalse(addUsersToChatMessage.getNewParticipantIds().isEmpty(), "New participant IDs should not be empty");
    }

    @Test
    void testMessageParticipantsSize() {
        assertEquals(2, addUsersToChatMessage.getNewParticipantIds().size(), "There should be two new participants");
    }

}
