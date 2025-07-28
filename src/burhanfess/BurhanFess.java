package burhanfess;

import burhanfess.displays.AdminDisplay;
import burhanfess.displays.CosmicDisplay;
import burhanfess.displays.UnauthorizedDisplay;
import burhanfess.repositories.UserRepositoryImpl;
import burhanfess.users.Admin;
import burhanfess.users.Cosmic;
import burhanfess.users.User;
import java.util.Scanner;

public class BurhanFess {
    //scanner static untuk digunakan di seluruh aplikasi
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
    UserRepositoryImpl repo = UserRepositoryImpl.getInstance();
    String currentUsername;
    while (true) {
        currentUsername = new UnauthorizedDisplay().runAndGetUsername();
        User u = repo.getUserByUsername(currentUsername);
        if (u instanceof Admin) {
            new AdminDisplay((Admin)u).runAndGetUsername();
        } else {
            new CosmicDisplay((Cosmic)u).runAndGetUsername();
        }
    }
}

}
