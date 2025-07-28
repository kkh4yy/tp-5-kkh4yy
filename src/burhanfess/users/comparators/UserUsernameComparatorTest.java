// File: src/test/java/burhanfess/users/comparators/UserUsernameComparatorTest.java
package burhanfess.users.comparators;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import burhanfess.users.User;
import burhanfess.users.Admin;

class UserUsernameComparatorTest {
    @Test
    void testCompare() {
        User a = new Admin("aaa", "p");
        User b = new Admin("bbb", "p");
        assertTrue(new UserUsernameComparator().compare(a, b) < 0);
        assertEquals(0, new UserUsernameComparator().compare(a, a));
    }
}
