package burhanfess.repositories;

import java.util.*;
import burhanfess.users.User;
import burhanfess.menfess.Menfess;

public class MenfessRepositoryImpl implements MenfessRepository {
    private static MenfessRepositoryImpl instance;
    private List<Menfess> menfesses;

    private MenfessRepositoryImpl() {
        menfesses = new ArrayList<>();
    }

    public static MenfessRepositoryImpl getInstance() {
        if (instance == null) instance = new MenfessRepositoryImpl();
        return instance;
    }

    @Override
    public List<Menfess> getAllMenfessesByUser(User user) {
        return menfesses.stream()
            .filter(m -> m.getUser().equals(user))
            .toList();
    }

    @Override
    public List<Menfess> getAllMenfessesForUser(User user) {
        return menfesses.stream()
            .filter(m -> {
                if (m instanceof burhanfess.menfess.ConfessFess) {
                    burhanfess.menfess.ConfessFess c = (burhanfess.menfess.ConfessFess) m;
                    return c.getReceiver().equals(user);
                }
                return true;
            })
            .filter(m -> !m.isHidden())
            .toList();
    }

    @Override
    public List<Menfess> getAllHiddenMenfesses() {
        return menfesses.stream()
            .filter(Menfess::isHidden)
            .toList();
    }

    @Override
    public List<Menfess> getAllUnhiddenMenfesses() {
        return menfesses.stream()
            .filter(m -> !m.isHidden())
            .toList();
    }

    @Override
    public void addMenfess(Menfess menfess) {
        menfesses.add(menfess);
    }

    @Override
    public void hideMenfess(Menfess menfess) {
        menfess.hide();
    }

    @Override
    public void unhideMenfess(Menfess menfess) {
        menfess.unhide();
    }
}