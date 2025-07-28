package burhanfess.services;

import burhanfess.repositories.UserRepositoryImpl;
import burhanfess.users.Cosmic;
import burhanfess.exceptions.UsernameAlreadyExistsException;
import burhanfess.exceptions.InvalidCredentialsException;

public class UnauthorizedServiceImpl implements UnauthorizedService {
    private final UserRepositoryImpl userRepo = UserRepositoryImpl.getInstance();

    @Override
    public void register(String username, String password) {
        if (userRepo.getUserByUsername(username) != null) {
            throw new UsernameAlreadyExistsException("User dengan username '" + username + "' sudah ada");
        }
        userRepo.addUser(new Cosmic(username, password));
    }

    @Override
    public void login(String username, String password) {
        var user = userRepo.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new InvalidCredentialsException("Username atau password salah");
        }
    }

    @Override
    public void exit() {
        System.out.println("Terima kasih telah menggunakan Burhanfess. Sampai jumpa!");
    }
}