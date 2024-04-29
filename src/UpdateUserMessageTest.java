import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UpdateUserMessageTest {
    private UpdateUserMessage updateUserMessage;

    @BeforeEach
    void setUp() {
        updateUserMessage = new UpdateUserMessage("userID");
    }

    @Test
    void getUserIdShouldReturnCorrectId() {
        assertEquals("userID", updateUserMessage.getUserId());
    }

}
