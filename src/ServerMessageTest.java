import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServerMessageTest {
    private ServerMessage serverMessage;

    @BeforeEach
    void setUp() {
        serverMessage = new ServerMessage(MessageTypes.LOGIN, MessageStatus.SUCCESS);
    }

    @Test
    void getTypeShouldReturnLogin() {
        assertEquals(MessageTypes.LOGIN, serverMessage.getType());
    }

    @Test
    void getStatusShouldReturnSuccess() {
        assertEquals(MessageStatus.SUCCESS, serverMessage.getStatus());
    }

}
