package burhanfess.services;

import java.util.List;
import java.util.Comparator;
import burhanfess.repositories.UserRepositoryImpl;
import burhanfess.repositories.MenfessRepositoryImpl;
import burhanfess.users.User;
import burhanfess.users.Admin;
import burhanfess.menfess.Menfess;
import burhanfess.exceptions.*;

public class AdminServiceImpl implements AdminService {
    private final UserRepositoryImpl userRepo = UserRepositoryImpl.getInstance();
    private final MenfessRepositoryImpl menfessRepo = MenfessRepositoryImpl.getInstance();

    @Override
    public List<User> getAllUsers(Comparator<User> comparator) {
        List<User> list = userRepo.getAllUsers();
        list.sort(comparator);
        return list;
    }

    @Override
    public void addAdmin(String username, String password) {
        if (userRepo.getUserByUsername(username) != null) {
            throw new UsernameAlreadyExistsException("User dengan username '" + username + "' sudah ada");
        }
        userRepo.addUser(new Admin(username, password));
    }

    @Override
    public void resetPassword(String username, String newPassword) {
        User u = userRepo.getUserByUsername(username);
        if (u == null) {
            throw new InvalidCredentialsException("User dengan username '" + username + "' tidak ditemukan");
        }
        if (u.getPassword().equals(newPassword)) {
            throw new PasswordUnchangedException("Password yang dimasukkan tidak boleh sama dengan password sebelumnya");
        }
        userRepo.changePassword(u, newPassword);
    }

    @Override
    public List<Menfess> getAllHiddenMenfesses() {
        return menfessRepo.getAllHiddenMenfesses();
    }

    @Override
    public List<Menfess> getAllUnhiddenMenfesses() {
        return menfessRepo.getAllUnhiddenMenfesses();
    }

    @Override
    public void hideMenfess(int menfessId) {
        Menfess m = menfessRepo.getAllUnhiddenMenfesses().stream()
            .filter(x -> x.getId() == menfessId)
            .findFirst()
            .orElseThrow(() -> new MenfessNotFoundException("Menfess dengan ID " + menfessId + " tidak ditemukan"));
        menfessRepo.hideMenfess(m);
    }

    @Override
    public void unhideMenfess(int menfessId) {
        Menfess m = menfessRepo.getAllHiddenMenfesses().stream()
            .filter(x -> x.getId() == menfessId)
            .findFirst()
            .orElseThrow(() -> new MenfessNotFoundException("Menfess dengan ID " + menfessId + " tidak ditemukan"));
        menfessRepo.unhideMenfess(m);
    }

    @Override
    public void logout() {
        // no-op
    }
}