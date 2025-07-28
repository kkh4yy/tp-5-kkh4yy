package burhanfess.displays;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public interface Display {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy");
    Scanner scanner = new Scanner(System.in);

    void showHeader();
    void showMenu();
    String runAndGetUsername();
    void showFooter();

    default void showCurrentDate() {
        System.out.println("Sel, " + formatter.format(LocalDateTime.now()));
    }
}