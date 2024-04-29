import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogMessageTest {
    private LogMessage logMessage;

    @BeforeEach
    void setUp() {
        logMessage = new LogMessage("userID");
    }

    @Test
    void getUserIdShouldReturnCorrectId() {
        assertEquals("userID", logMessage.getUserId());
    }

}
