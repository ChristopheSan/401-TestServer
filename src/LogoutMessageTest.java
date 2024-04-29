import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LogoutMessageTest {

    private LogoutMessage logoutMessage;

    @Before
    public void setUp() {
        logoutMessage = new LogoutMessage("testUser");
    }

    @Test
    public void testMessageCreation() {
        assertNotNull("LogoutMessage should be created", logoutMessage);
        assertEquals("Username should match", "testUser", logoutMessage.getUsername());
        assertEquals("Message type should be LOGOUT", MessageTypes.LOGOUT, logoutMessage.getType());
        assertEquals("Initial status should be PENDING", MessageStatus.PENDING, logoutMessage.getStatus());
    }

    @Test
    public void testStatusUpdate() {
        logoutMessage.setStatus(MessageStatus.SUCCESS);
        assertEquals("Status should be updated to SUCCESS", MessageStatus.SUCCESS, logoutMessage.getStatus());
    }
}
