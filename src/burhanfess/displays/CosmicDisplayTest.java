package burhanfess.displays;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Scanner;

import burhanfess.users.Cosmic;

class CosmicDisplayTest {
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;
    private Field scannerField;

    @BeforeEach
    void setUp() throws Exception {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Lokasi scanner sama seperti AdminDisplay
        try {
            scannerField = CosmicDisplay.class.getDeclaredField("scanner");
            scannerField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Class<?> clazz = CosmicDisplay.class.getSuperclass();
            scannerField = clazz.getDeclaredField("scanner");
            scannerField.setAccessible(true);
        }
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    private void resetScanner() throws Exception {
        scannerField.set(null, new Scanner(System.in));
    }

    @Test
    void testLogout() throws Exception {
        System.setIn(new ByteArrayInputStream("6\n".getBytes()));
        resetScanner();

        CosmicDisplay display = new CosmicDisplay(new Cosmic("user", "pw"));
        String result = display.runAndGetUsername();

        assertNull(result);
        assertTrue(outContent.toString().contains("logout"));
    }

    @Test
    void testInvalidMenuThenLogout() throws Exception {
        System.setIn(new ByteArrayInputStream("x\n6\n".getBytes()));
        resetScanner();

        CosmicDisplay display = new CosmicDisplay(new Cosmic("user", "pw"));
        String result = display.runAndGetUsername();

        assertNull(result);
        assertTrue(outContent.toString().contains("Pilihan tidak valid"));
    }

    @Test
    void testMenu1Curhat() throws Exception {
        String input = String.join("\n", "1", "isi curhat", "1", "6");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        resetScanner();

        CosmicDisplay display = new CosmicDisplay(new Cosmic("user", "pw"));
        assertNull(display.runAndGetUsername());
        assertTrue(outContent.toString().contains("Menfess berhasil dikirim"));
    }

    @Test
    void testMenu1Promosi() throws Exception {
        String input = String.join("\n", "1", "isi promosi", "2", "6");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        resetScanner();

        CosmicDisplay display = new CosmicDisplay(new Cosmic("user", "pw"));
        assertNull(display.runAndGetUsername());
        assertTrue(outContent.toString().contains("Menfess berhasil dikirim"));
    }

    @Test
    void testMenu1Confession() throws Exception {
        String input = String.join("\n", "1", "isi confession", "3", "targetuser", "6");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        resetScanner();

        CosmicDisplay display = new CosmicDisplay(new Cosmic("user", "pw"));
        assertNull(display.runAndGetUsername());
    }

    @Test
    void testMenu1InvalidTipeMenfess() throws Exception {
        String input = String.join("\n", "1", "isi salah", "x", "dummyRecv", "6");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        resetScanner();

        CosmicDisplay display = new CosmicDisplay(new Cosmic("user", "pw"));
        assertNull(display.runAndGetUsername());
        assertTrue(outContent.toString().contains("Pilihan tidak valid"));
    }

    @Test
    void testMenu2() throws Exception {
        String input = String.join("\n", "2", "6");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        resetScanner();

        CosmicDisplay display = new CosmicDisplay(new Cosmic("user", "pw"));
        assertNull(display.runAndGetUsername());
        assertTrue(outContent.toString().contains("menfess bersifat publik"));
    }

    @Test
    void testMenu3() throws Exception {
        String input = String.join("\n", "3", "6");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        resetScanner();

        CosmicDisplay display = new CosmicDisplay(new Cosmic("user", "pw"));
        assertNull(display.runAndGetUsername());
        assertTrue(outContent.toString().contains("yang kamu kirim"));
    }

    @Test
    void testMenu4() throws Exception {
        String input = String.join("\n", "4", "6");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        resetScanner();

        CosmicDisplay display = new CosmicDisplay(new Cosmic("user", "pw"));
        assertNull(display.runAndGetUsername());
        assertTrue(outContent.toString().contains("yang kamu terima"));
    }

    @Test
    void testMenu5() throws Exception {
        String input = String.join("\n", "5", "newpassword", "6");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        resetScanner();

        CosmicDisplay display = new CosmicDisplay(new Cosmic("user", "pw"));
        assertNull(display.runAndGetUsername());
        assertTrue(outContent.toString().contains("Password berhasil diubah"));
    }

    @Test
    void testMenuInvalidAngkaThenLogout() throws Exception {
        String input = String.join("\n", "9", "6");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        resetScanner();

        CosmicDisplay display = new CosmicDisplay(new Cosmic("user", "pw"));
        assertNull(display.runAndGetUsername());
        assertTrue(outContent.toString().contains("Input tidak valid"));
    }
}
