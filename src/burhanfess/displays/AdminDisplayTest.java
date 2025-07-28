package burhanfess.displays;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;
import java.lang.reflect.Field;
import java.util.Scanner;

import burhanfess.users.Admin;

class AdminDisplayTest {
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    // Reflective access to shared scanner if it's static
    private Field scannerField;

    @BeforeEach
    void setUp() throws Exception {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Setup reflective scanner reset if needed
        try {
            scannerField = AdminDisplay.class.getDeclaredField("scanner");
            scannerField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            // Might be defined in a superclass or elsewhere
            Class<?> clazz = AdminDisplay.class.getSuperclass();
            scannerField = clazz.getDeclaredField("scanner");
            scannerField.setAccessible(true);
        }
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    /** Reset scanner in AdminDisplay to use current System.in */
    private void resetScanner() throws Exception {
        Scanner scanner = new Scanner(System.in);
        scannerField.set(null, scanner); // static field assumed
    }

    @Test
    void testLogoutImmediately() throws Exception {
        System.setIn(new ByteArrayInputStream("6\n".getBytes()));
        resetScanner();

        AdminDisplay display = new AdminDisplay(new Admin("admin", "pw"));
        String result = display.runAndGetUsername();

        assertNull(result);
        String output = outContent.toString();
        assertTrue(output.contains("Halo, Admin admin!"));
        assertTrue(output.contains("Silakan pilih salah satu opsi berikut"));
        assertTrue(output.contains("Kamu telah berhasil logout."));
        assertTrue(output.contains("BurhanFess - 2025"));
    }

    @Test
    void testInvalidOptionThenLogout() throws Exception {
        System.setIn(new ByteArrayInputStream("x\n6\n".getBytes()));
        resetScanner();

        AdminDisplay display = new AdminDisplay(new Admin("admin", "pw"));
        String result = display.runAndGetUsername();

        assertNull(result);
        String output = outContent.toString();
        assertTrue(output.contains("Input tidak valid"));
        assertTrue(output.contains("Kamu telah berhasil logout."));
    }

    @Test
    void testMenuLihatPenggunaById() throws Exception {
        System.setIn(new ByteArrayInputStream("1\n1\n6\n".getBytes()));
        resetScanner();
        AdminDisplay display = new AdminDisplay(new Admin("admin", "pw"));
        String result = display.runAndGetUsername();
        assertNull(result);
        String out = outContent.toString();
        assertTrue(out.contains("Daftar pengguna diurutkan berdasarkan ID:"));
    }

    @Test
    void testMenuLihatPenggunaByUsername() throws Exception {
        System.setIn(new ByteArrayInputStream("1\n2\n6\n".getBytes()));
        resetScanner();
        AdminDisplay display = new AdminDisplay(new Admin("admin", "pw"));
        String result = display.runAndGetUsername();
        assertNull(result);
        String out = outContent.toString();
        assertTrue(out.contains("Daftar pengguna diurutkan berdasarkan username:"));
    }

    @Test
    void testMenuTambahAdmin() throws Exception {
        System.setIn(new ByteArrayInputStream("2\nnewadmin\nnewpass\n6\n".getBytes()));
        resetScanner();
        AdminDisplay display = new AdminDisplay(new Admin("admin", "pw"));
        String result = display.runAndGetUsername();
        assertNull(result);
        assertTrue(outContent.toString().contains("Admin 'newadmin' berhasil ditambahkan"));
    }

    @Test
    void testMenuResetPassword() throws Exception {
        System.setIn(new ByteArrayInputStream("3\nuserx\nnewpassx\n6\n".getBytes()));
        resetScanner();
        AdminDisplay display = new AdminDisplay(new Admin("admin", "pw"));
        String result = display.runAndGetUsername();
        assertNull(result);
    }

    @Test
    void testMenuHideMenfess() throws Exception {
        System.setIn(new ByteArrayInputStream("4\n0\n6\n".getBytes()));
        resetScanner();
        AdminDisplay display = new AdminDisplay(new Admin("admin", "pw"));
        String result = display.runAndGetUsername();
        assertNull(result);
    }

    @Test
    void testMenuUnhideMenfess() throws Exception {
        System.setIn(new ByteArrayInputStream("5\n0\n6\n".getBytes()));
        resetScanner();
        AdminDisplay display = new AdminDisplay(new Admin("admin", "pw"));
        String result = display.runAndGetUsername();
        assertNull(result);
    }

    @Test
    void testMenuAngkaInvalidThenLogout() throws Exception {
        System.setIn(new ByteArrayInputStream("9\n6\n".getBytes())); // 9 tidak valid
        resetScanner();
        AdminDisplay display = new AdminDisplay(new Admin("admin", "pw"));
        String result = display.runAndGetUsername();
        assertNull(result);
    }

    @Test
    void testMenuExceptionFromService() throws Exception {
        // Di sini kita mock input reset password tapi anggap adminService akan throw exception
        // Untuk membuat svc.resetPassword lempar exception, bisa gunakan test double/mocking (e.g., Mockito)
        // Tapi jika belum pakai framework mocking, sementara kita test dengan input jelek yang bikin NumberFormatException
        System.setIn(new ByteArrayInputStream("4\nnotanumber\n6\n".getBytes()));
        resetScanner();
        AdminDisplay display = new AdminDisplay(new Admin("admin", "pw"));
        String result = display.runAndGetUsername();
        assertNull(result);
    }

}
