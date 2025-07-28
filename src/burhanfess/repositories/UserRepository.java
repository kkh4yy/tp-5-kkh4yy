package burhanfess.repositories;

import java.util.List;
import burhanfess.users.User;

public interface UserRepository {
    List<User> getAllUsers();
    User getUserByUsername(String username);
    void addUser(User user);
    void changePassword(User user, String newPassword);
}