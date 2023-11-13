import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testConstructorWithThreeArguments() {
        User user = new User("username", "password", true); // true for student
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertTrue(user.getUserType());
    }

    @Test
    void testConstructorWithTwoArguments() {
        User user = new User("username", "password");
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        // UserType might be uninitialized, assert based on expected default behavior
    }

    @Test
    void testConstructorWithUsernameAndUserType() {
        User user = new User("username", true); // true for student
        assertEquals("username", user.getUsername());
        assertTrue(user.getUserType());
        // Password might be uninitialized, assert based on expected default behavior
    }
}