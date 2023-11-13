import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class OptionsTest {

    private Options options;
    private User userTerminal;
    private User userSelected;

    @BeforeEach
    void setUp() {
        userTerminal = new User("user1", "pass1", true); // Assuming User class has such a constructor
        userSelected = new User("user2", "pass2", false);
        options = new Options(userTerminal, userSelected);
    }

    @Test
    void getBlockedShouldReturnListOfBlockedUsers() {
        ArrayList<String> blockedUsers = Options.getBlocked();
        assertNotNull(blockedUsers);
        // Assertions based on expected content of blocked-usernames.txt
    }
}