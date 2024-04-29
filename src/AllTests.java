import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({LoginMessageTest.class,
    LogoutMessageTest.class,
    ChatMessageTest.class,
    CreateChatMessageTest.class,
    UpdateUserMessageTest.class,
    AddUsersToChatMessageTest.class,
    NotifyMessageTest.class,
    PinChatMessageTest.class,})
public class AllTests {

}
