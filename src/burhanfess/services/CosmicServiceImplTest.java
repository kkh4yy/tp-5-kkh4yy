// File: src/test/java/burhanfess/services/CosmicServiceImplTest.java
package burhanfess.services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import burhanfess.users.Cosmic;
import burhanfess.users.Admin;
import burhanfess.repositories.MenfessRepositoryImpl;
import burhanfess.exceptions.InvalidCredentialsException;
import burhanfess.exceptions.PasswordUnchangedException;
import burhanfess.menfess.Menfess;

import java.lang.reflect.Field;
import java.util.List;

class CosmicServiceImplTest {
    private CosmicService svc;
    private Cosmic alice;
    private Admin bob;

    @BeforeEach
    void setUp() throws Exception {
        // Reset repositories for clean state
        Field menInst = MenfessRepositoryImpl.class.getDeclaredField("instance");
        menInst.setAccessible(true);
        menInst.set(null, null);

        // Create new repo and service
        alice = new Cosmic("alice", "pw");
        bob = new Admin("bob", "pw");

        svc = new CosmicServiceImpl(alice);

        assertEquals("Cosmic", alice.getRole());
        assertEquals("Admin", bob.getRole());
        
    }

    @Test
    void testSendCurhatAndPromosi() {
        svc.sendCurhatFess("hey");
        svc.sendPromosiFess("sale");
        List<Menfess> list = svc.getAllUnhiddenMenfesses();
        assertEquals(2, list.size());
        assertTrue(list.stream().anyMatch(m -> m.getType().equals("Curhat")));
        assertTrue(list.stream().anyMatch(m -> m.getType().equals("Promosi")));
    }

    @Test
    void testSendConfessInvalidReceiver() {
        assertThrows(InvalidCredentialsException.class,
            () -> svc.sendConfessFess("hi", "noUser"));
    }

    @Test
    void testSentAndReceivedMenfess() {
        svc.sendCurhatFess("c1");
        svc.sendPromosiFess("p1");
        assertEquals(2, svc.getAllUnhiddenMenfesses().size());
        assertEquals(2, svc.getAllSentMenfesses().size());
        assertEquals(2, svc.getAllReceivedMenfesses().size());
    }

    @Test
    void testChangePasswordErrors() {
        assertThrows(PasswordUnchangedException.class,
            () -> svc.changePassword("pw"));
        assertDoesNotThrow(() -> svc.changePassword("newpw"));
    }

    @Test
    void testLogoutNoOp() {
        assertDoesNotThrow(() -> svc.logout());
    }
}