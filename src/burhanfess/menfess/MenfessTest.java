package burhanfess.menfess;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import burhanfess.users.Cosmic;

class MenfessTest {
    @Test
    void testToStringFormats() {
        var user = new Cosmic("u", "p");
        var m1 = new CurhatFess(user, "hi");
        String s = m1.toString();
        assertTrue(s.contains("[Curhat]"));
        assertTrue(s.contains("hi"));
        assertTrue(s.contains("Dikirim pada"));
    }
}
