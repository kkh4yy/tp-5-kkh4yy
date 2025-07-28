// File: src/test/java/burhanfess/repositories/MenfessRepositoryImplTest.java
package burhanfess.repositories;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import burhanfess.users.Cosmic;
import burhanfess.repositories.MenfessRepositoryImpl;
import burhanfess.menfess.CurhatFess;
import burhanfess.menfess.PromosiFess;
import burhanfess.menfess.ConfessFess;
import burhanfess.users.User;

import java.util.List;

class MenfessRepositoryImplTest {
    private MenfessRepositoryImpl repo;
    private User alice, bob;

    @BeforeEach
    void setUp() {
        repo = MenfessRepositoryImpl.getInstance();
        // clear any existing via reflection or recreate singleton - here assume fresh
        alice = new Cosmic("alice", "pw");
        bob   = new Cosmic("bob", "pw");
    }

    @Test
    void testAddAndListByUser() {
        repo.addMenfess(new CurhatFess(alice, "hello"));
        repo.addMenfess(new PromosiFess(bob, "promo"));
        List<?> aList = repo.getAllMenfessesByUser(alice);
        assertEquals(1, aList.size());
    }

    @Test
    void testHideAndUnhide() {
        var m = new PromosiFess(alice, "xx");
        repo.addMenfess(m);
        assertFalse(m.isHidden());
        repo.hideMenfess(m);
        assertTrue(m.isHidden());
        assertEquals(1, repo.getAllHiddenMenfesses().size());
        repo.unhideMenfess(m);
        assertFalse(m.isHidden());
    }

    @Test
    void testConfessFessReceiverFiltering() {
        var conf = new ConfessFess(alice, "c", bob);
        repo.addMenfess(conf);
        List<?> forBob = repo.getAllMenfessesForUser(bob);
        assertTrue(forBob.contains(conf));
        List<?> forAlice = repo.getAllMenfessesForUser(alice);
        assertFalse(forAlice.contains(conf));
    }
}
