package burhanfess.displays;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Scanner;

class UnauthorizedDisplayTest {
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;
    private Field scannerField;

    @BeforeEach
    void setUp() throws Exception {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Lokasi scanner sama seperti CosmicDisplayTest
        try {
            scannerField = UnauthorizedDisplay.class.getDeclaredField("scanner");
            scannerField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Class<?> clazz = UnauthorizedDisplay.class.getSuperclass();
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
    void testLoginSuccess() throws Exception {
        String input = String.join("\n", "2", "admin", "admin");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        resetScanner();

        UnauthorizedDisplay disp = new UnauthorizedDisplay();
        String username = disp.runAndGetUsername();

        assertEquals("admin", username);
        String out = outContent.toString();
        assertTrue(out.contains("Selamat datang di Burhanfess"));
        assertTrue(out.contains("Login berhasil! Selamat datang, admin!"));
    }

    @Test
    void testRegisterThenLogin() throws Exception {
        String input = String.join("\n", "1", "newuser", "newpass", "2", "newuser", "newpass");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        resetScanner();

        UnauthorizedDisplay disp = new UnauthorizedDisplay();
        String username = disp.runAndGetUsername();

        assertEquals("newuser", username);
        String out = outContent.toString();
        assertTrue(out.contains("Registrasi berhasil!"));
        assertTrue(out.contains("Login berhasil! Selamat datang, newuser!"));
    }

    @Test
    void testInvalidMenuThenLogin() throws Exception {
        String input = String.join("\n", "x", "2", "admin", "admin");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        resetScanner();

        UnauthorizedDisplay disp = new UnauthorizedDisplay();
        String username = disp.runAndGetUsername();

        assertEquals("admin", username);
        assertTrue(outContent.toString().contains("Input tidak valid"));
        assertTrue(outContent.toString().contains("Login berhasil! Selamat datang, admin!"));
    }

    @Test
    void testInvalidNumberMenuThenLogin() throws Exception {
        String input = String.join("\n", "7", "2", "admin", "admin");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        resetScanner();

        UnauthorizedDisplay disp = new UnauthorizedDisplay();
        String username = disp.runAndGetUsername();

        assertEquals("admin", username);
        assertTrue(outContent.toString().contains("Input tidak valid. Silakan coba lagi."));
    }
}
