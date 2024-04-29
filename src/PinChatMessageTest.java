import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PinChatMessageTest {
    private PinChatMessage pinChatMessage;

    @BeforeEach
    void setUp() {
        pinChatMessage = new PinChatMessage("chatId");
    }

    @Test
    void getChatIdShouldReturnCorrectChatId() {
        assertEquals("chatId", pinChatMessage.getChatId());
    }

}
