package burhanfess.displays;

import burhanfess.menfess.Menfess;
import burhanfess.services.CosmicService;
import burhanfess.services.CosmicServiceImpl;
import burhanfess.users.Cosmic;
import java.util.List;
import java.util.Scanner;

public class CosmicDisplay implements Display {
    private final CosmicService svc;
    private final String username;
    //scanner
    public static Scanner scanner = new Scanner(System.in);

    public CosmicDisplay(Cosmic cosmic) {
        this.svc = new CosmicServiceImpl(cosmic);
        this.username = cosmic.getUsername();
    }

    @Override
    public void showHeader() {
        System.out.println("Halo, Cosmic " + username + "!");
        showCurrentDate();
        System.out.println("--------------------------------------------------------------------------------");
    }

    @Override
    public void showMenu() {
        System.out.println("Silakan pilih salah satu opsi berikut");
        System.out.println("1. Mengirim satu menfess");
        System.out.println("2. Melihat menfess publik");
        System.out.println("3. Melihat menfess yang kamu kirim");
        System.out.println("4. Melihat menfess yang kamu terima");
        System.out.println("5. Ubah password");
        System.out.println("6. Logout");
        System.out.print("Masukkan pilihan: ");
    }

    @Override
    public String runAndGetUsername() {
        while (true) {
            showHeader();
            showMenu();
            String in = scanner.nextLine();
            try {
                int choice = Integer.parseInt(in);
                switch (choice) {
                    case 1 -> {
                        System.out.println("Masukkan isi menfess yang ingin kamu kirim:");
                        String content = scanner.nextLine();
                        System.out.println("Silakan pilih tipe menfess yang ingin dikirim.");
                        System.out.println("1. Curhat");
                        System.out.println("2. Promosi");
                        System.out.println("3. Confession");
                        System.out.print("Masukkan tipe menfess: ");
                        String t = scanner.nextLine();
                        switch (t) {
                            case "1" -> svc.sendCurhatFess(content);
                            case "2" -> svc.sendPromosiFess(content);
                            case "3" -> {
                                System.out.print("Masukkan username yang ingin kamu kirimkan menfess: ");
                                String recv = scanner.nextLine();
                                svc.sendConfessFess(content, recv);
                            }
                            default -> throw new RuntimeException("Pilihan tidak valid. Silakan coba lagi.");
                        }
                        System.out.println("Menfess berhasil dikirim.");
                    }
                    case 2 -> {
                        List<Menfess> list = svc.getAllUnhiddenMenfesses();
                        System.out.println("Daftar menfess bersifat publik:");
                        list.forEach(m -> System.out.println(m + ""));
                    }
                    case 3 -> {
                        List<Menfess> list = svc.getAllSentMenfesses();
                        System.out.println("Daftar menfess yang kamu kirim:");
                        list.forEach(m -> System.out.println(m + ""));
                    }
                    case 4 -> {
                        List<Menfess> list = svc.getAllReceivedMenfesses();
                        System.out.println("Daftar menfess yang kamu terima:");
                        list.forEach(m -> System.out.println(m + ""));
                    }
                    case 5 -> {
                        System.out.print("Masukkan password baru: ");
                        String np = scanner.nextLine();
                        svc.changePassword(np);
                        System.out.println("Password berhasil diubah.");
                    }
                    case 6 -> {
                        System.out.println("Kamu telah berhasil logout.");
                        System.out.println("--------------------------------------------------------------------------------");
                        showFooter();
                        return null;
                    }
                    default -> System.out.println("Input tidak valid. Silakan coba lagi.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
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
