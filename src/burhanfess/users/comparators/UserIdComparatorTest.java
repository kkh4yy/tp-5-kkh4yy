// File: src/test/java/burhanfess/users/comparators/UserIdComparatorTest.java
package burhanfess.users.comparators;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import burhanfess.users.User;
import burhanfess.users.Admin;
import burhanfess.users.Cosmic;

class UserIdComparatorTest {
    @Test
    void testCompare() {
        User u1 = new Admin("x", "p");
        User u2 = new Cosmic("y", "p");
        assertTrue(new UserIdComparator().compare(u1, u2) < 0);
        assertEquals(0, new UserIdComparator().compare(u1, u1));
    }
}
