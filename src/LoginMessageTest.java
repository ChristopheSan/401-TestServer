import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LoginMessageTest {
    private LoginMessage message;

    @Before
    public void setUp() {
        message = new LoginMessage("password", "username");
    }

    @Test
    public void testMessageCreation() {
        assertEquals("username", message.getUsername());
        assertEquals("password", message.getPassword());
        assertFalse("Initial state should be unsuccessful", message.isSuccessful());
    }

    @Test
    public void testSuccess() {
        message.setSuccess(true);
        assertTrue("Login should be successful", message.isSuccessful());
    }
}
