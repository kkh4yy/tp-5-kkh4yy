// File: src/test/java/burhanfess/repositories/UserRepositoryImplTest.java
package burhanfess.repositories;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import burhanfess.users.User;
import burhanfess.users.Admin;

import java.util.List;

class UserRepositoryImplTest {
    private UserRepositoryImpl repo;

    @BeforeEach
    void setUp() {
        // fresh singleton for each test
        // (hack: use reflection to reset instance if needed, or rely on unique usernames)
        repo = UserRepositoryImpl.getInstance();
    }

    @Test
    void testAddAndGetUser() {
        User u = new Admin("xuser", "pass");
        repo.addUser(u);
        User fetched = repo.getUserByUsername("xuser");
        assertNotNull(fetched);
        assertEquals("xuser", fetched.getUsername());
    }

    @Test
    void testChangePassword() {
        User u = new Admin("puser", "old");
        repo.addUser(u);
        repo.changePassword(u, "new");
        assertEquals("new", repo.getUserByUsername("puser").getPassword());
    }

    @Test
    void testGetAllUsers() {
        List<User> all = repo.getAllUsers();
        assertTrue(all.size() >= 1, "Harus ada minimal satu Admin default");
    }

    @Test
    void testGetNonExisting() {
        assertNull(repo.getUserByUsername("no_such_user"));
    }
}
