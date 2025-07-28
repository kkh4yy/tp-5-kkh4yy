package burhanfess.displays;

import burhanfess.services.UnauthorizedService;
import burhanfess.services.UnauthorizedServiceImpl;
import java.util.Scanner;

public class UnauthorizedDisplay implements Display {
    private final UnauthorizedService svc = new UnauthorizedServiceImpl();
    //scanner
    public static Scanner scanner = new Scanner(System.in);

    @Override
    public void showHeader() {
        System.out.println("Selamat datang di Burhanfess");
        showCurrentDate();
        System.out.println("--------------------------------------------------------------------------------");
    }

    @Override
    public void showMenu() {
        System.out.println("Silakan pilih salah satu opsi berikut.");
        System.out.println("1. Registrasi");
        System.out.println("2. Login");
        System.out.println("3. Keluar");
        System.out.print("Masukkan pilihan: ");
    }

    @Override
    public String runAndGetUsername() {
        while (true) {
            showHeader();
            showMenu();
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1 -> {
                        System.out.println("Silakan masukkan username dan password untuk registrasi:");
                        System.out.print("Username: "); String u = scanner.nextLine();
                        System.out.print("Password: "); String p = scanner.nextLine();
                        svc.register(u, p);
                        System.out.println("Registrasi berhasil! Silakan login.");
                    }
                    case 2 -> {
                        System.out.println("Silakan masukkan username dan password untuk login:");
                        System.out.print("Username: "); String u2 = scanner.nextLine();
                        System.out.print("Password: "); String p2 = scanner.nextLine();
                        svc.login(u2, p2);
                        System.out.println("Login berhasil! Selamat datang, " + u2 + "!");
                        System.out.println("--------------------------------------------------------------------------------");
                        return u2;
                    }
                    case 3 -> {
                        svc.exit();
                        System.out.println("--------------------------------------------------------------------------------");
                        showFooter();
                        System.exit(0);
                    }
                    default -> System.out.println("Input tidak valid. Silakan coba lagi.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Silakan masukkan angka yang sesuai.");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void showFooter() {
        System.out.println("BurhanFess - 2025");
        System.out.println("Created by Burhan");
    }
}