package burhanfess.repositories;

import java.util.List;
import burhanfess.users.User;
import burhanfess.menfess.Menfess;

public interface MenfessRepository {
    List<Menfess> getAllMenfessesByUser(User user);
    List<Menfess> getAllHiddenMenfesses();
    List<Menfess> getAllUnhiddenMenfesses();
    void addMenfess(Menfess menfess);
    void hideMenfess(Menfess menfess);
    void unhideMenfess(Menfess menfess);
    List<Menfess> getAllMenfessesForUser(User user);
}