import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class CreateChatMessageTest {
    private CreateChatMessage createChatMessage;
    private List<String> participantIds;

    @BeforeEach
    void setUp() {
        participantIds = Arrays.asList("user1", "user2");
        createChatMessage = new CreateChatMessage(participantIds);
    }

    @Test
    void getParticipantIdsShouldReturnCorrectParticipants() {
        assertEquals(participantIds, createChatMessage.getParticipantIds());
    }

}
