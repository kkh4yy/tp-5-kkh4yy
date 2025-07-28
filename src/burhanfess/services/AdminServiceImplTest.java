// File: src/test/java/burhanfess/services/AdminServiceImplTest.java
package burhanfess.services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import burhanfess.users.Admin;
import burhanfess.repositories.UserRepositoryImpl;
import burhanfess.repositories.MenfessRepositoryImpl;
import burhanfess.exceptions.*;

import java.lang.reflect.Field;
import java.util.Comparator;

class AdminServiceImplTest {
    private AdminService svc;

    @BeforeEach
    void setUp() throws Exception {
        // Reset singletons for clean state
        Field userInst = UserRepositoryImpl.class.getDeclaredField("instance");
        userInst.setAccessible(true);
        userInst.set(null, null);

        Field menfessInst = MenfessRepositoryImpl.class.getDeclaredField("instance");
        menfessInst.setAccessible(true);
        menfessInst.set(null, null);

        // Reinitialize
        UserRepositoryImpl.getInstance().addUser(new Admin("a1", "pw"));
        svc = new AdminServiceImpl();
    }

    @Test
    void testGetAllUsersSortedById() {
        UserRepositoryImpl.getInstance().addUser(new Admin("b", "p"));
        var list = svc.getAllUsers(Comparator.comparingInt(u -> u.getId()));
        assertTrue(list.get(0).getId() < list.get(1).getId());
    }

    @Test
    void testGetAllUsersSortedByUsername() {
        UserRepositoryImpl.getInstance().addUser(new Admin("alpha", "p"));
        var list = svc.getAllUsers(Comparator.comparing(u -> u.getUsername()));
        assertEquals("a1", list.get(0).getUsername());
    }

    @Test
    void testAddAdminSuccessAndDuplicate() {
        svc.addAdmin("newAdmin", "pw");
        assertNotNull(UserRepositoryImpl.getInstance().getUserByUsername("newAdmin"));
        assertThrows(UsernameAlreadyExistsException.class,
            () -> svc.addAdmin("newAdmin", "pw2"));
    }

    @Test
    void testResetPasswordSuccessAndErrors() {
        svc.resetPassword("a1", "newpw");
        assertEquals("newpw", UserRepositoryImpl.getInstance().getUserByUsername("a1").getPassword());
        assertThrows(PasswordUnchangedException.class,
            () -> svc.resetPassword("a1", "newpw"));
        assertThrows(InvalidCredentialsException.class,
            () -> svc.resetPassword("unknown", "pw"));
    }

    @Test
    void testHideUnhideMenfessNotFound() {
        assertThrows(MenfessNotFoundException.class,
            () -> svc.hideMenfess(999));
        assertThrows(MenfessNotFoundException.class,
            () -> svc.unhideMenfess(999));
    }
}