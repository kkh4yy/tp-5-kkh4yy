package burhanfess.services;

import java.util.List;
import java.util.Comparator;
import burhanfess.users.User;
import burhanfess.menfess.Menfess;

public interface AdminService {
    List<User> getAllUsers(Comparator<User> comparator);
    void addAdmin(String username, String password);
    void resetPassword(String username, String newPassword);
    List<Menfess> getAllHiddenMenfesses();
    List<Menfess> getAllUnhiddenMenfesses();
    void hideMenfess(int menfessId);
    void unhideMenfess(int menfessId);
    void logout();
}