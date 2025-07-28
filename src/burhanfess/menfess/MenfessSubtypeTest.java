// File: src/test/java/burhanfess/menfess/MenfessSubtypeTest.java
package burhanfess.menfess;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import burhanfess.users.Cosmic;

class MenfessSubtypeTest {
    @Test
    void testCurhatFessTypeAndToString() {
        Cosmic user = new Cosmic("alice", "pw");
        CurhatFess cf = new CurhatFess(user, "This is a test curhat.");
        assertEquals("Curhat", cf.getType(), "getType() should return 'Curhat'");
        String s = cf.toString();
        assertTrue(s.contains("[Curhat]"), "toString() should contain '[Curhat]'");
        assertTrue(s.contains("This is a test curhat."), "toString() should contain the content");
        assertTrue(s.contains("alice"), "toString() should show the username as author");
        assertTrue(s.contains("Dikirim pada"), "toString() should include timestamp prefix");
    }

    @Test
    void testPromosiFessTypeAndToString() {
        Cosmic user = new Cosmic("bob", "pw");
        PromosiFess pf = new PromosiFess(user, "Buy now!");
        assertEquals("Promosi", pf.getType(), "getType() should return 'Promosi'");
        String s = pf.toString();
        assertTrue(s.contains("[Promosi]"), "toString() should contain '[Promosi]'");
        assertTrue(s.contains("Buy now!"), "toString() should contain the content");
        assertTrue(s.contains("bob"), "toString() should show the username as author");
    }

    @Test
    void testConfessFessTypeAndToString() {
        Cosmic sender = new Cosmic("charlie", "pw");
        Cosmic receiver = new Cosmic("dave", "pw");
        ConfessFess cf = new ConfessFess(sender, "Secret message", receiver);
        assertEquals("Confession", cf.getType(), "getType() should return 'Confession'");
        String s = cf.toString();
        // Should hide sender name and show 'anonim'
        assertTrue(s.contains("[Confession] oleh anonim"), "toString() should show anonymous sender");
        assertTrue(s.contains("Secret message"), "toString() should contain the content");
        assertTrue(s.contains("Dikirim pada"), "toString() should include timestamp prefix");
        // Receiver not printed in toString(), but stored internally
        assertEquals(receiver, cf.getReceiver(), "getReceiver() should return the correct User");
    }

    @Test
    void testHideAndUnhide() {
        Cosmic user = new Cosmic("eve", "pw");
        CurhatFess m = new CurhatFess(user, "testing hide");
        assertFalse(m.isHidden(), "Initially not hidden");
        m.hide();
        assertTrue(m.isHidden(), "After hide(), isHidden should be true");
        m.unhide();
        assertFalse(m.isHidden(), "After unhide(), isHidden should be false");
    }

    @Test
    void testIdIncrementAcrossInstances() {
        // IDs should be unique and incrementing
        int startId = new CurhatFess(new Cosmic("u1","pw"), "a").getId();
        int nextId = new CurhatFess(new Cosmic("u2","pw"), "b").getId();
        assertEquals(startId + 1, nextId, "Second instance ID should be first ID + 1");
    }
}
