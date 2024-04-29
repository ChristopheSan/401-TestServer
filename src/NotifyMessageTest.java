import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotifyMessageTest {
    private NotifyMessage notifyMessage;

    @BeforeEach
    void setUp() {
        notifyMessage = new NotifyMessage("Notification Text");
    }

    @Test
    void getNotificationTextShouldReturnCorrectText() {
        assertEquals("Notification Text", notifyMessage.getNotificationText());
    }

}
