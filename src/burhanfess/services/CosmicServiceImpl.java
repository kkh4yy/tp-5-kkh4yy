package burhanfess.services;

import java.util.List;
import burhanfess.repositories.MenfessRepositoryImpl;
import burhanfess.repositories.UserRepositoryImpl;
import burhanfess.users.User;
import burhanfess.users.Cosmic;
import burhanfess.menfess.*;
import burhanfess.exceptions.*;

public class CosmicServiceImpl implements CosmicService {
    private final Cosmic cosmic;
    private final UserRepositoryImpl userRepo = UserRepositoryImpl.getInstance();
    private final MenfessRepositoryImpl menfessRepo = MenfessRepositoryImpl.getInstance();

    public CosmicServiceImpl(Cosmic cosmic) {
        this.cosmic = cosmic;
    }

    @Override
    public void sendCurhatFess(String content) {
        menfessRepo.addMenfess(new CurhatFess(cosmic, content));
    }

    @Override
    public void sendPromosiFess(String content) {
        menfessRepo.addMenfess(new PromosiFess(cosmic, content));
    }

    @Override
    public void sendConfessFess(String content, String receiverUsername) {
        User recv = userRepo.getUserByUsername(receiverUsername);
        if (recv == null) {
            throw new InvalidCredentialsException("User dengan username '" + receiverUsername + "' tidak ditemukan");
        }
        menfessRepo.addMenfess(new ConfessFess(cosmic, content, recv));
    }

    @Override
    public List<Menfess> getAllUnhiddenMenfesses() {
        return menfessRepo.getAllUnhiddenMenfesses();
    }

    @Override
    public List<Menfess> getAllSentMenfesses() {
        return menfessRepo.getAllMenfessesByUser(cosmic);
    }

    @Override
    public List<Menfess> getAllReceivedMenfesses() {
        return menfessRepo.getAllMenfessesForUser(cosmic);
    }

    @Override
    public void changePassword(String newPassword) {
        if (cosmic.getPassword().equals(newPassword)) {
            throw new PasswordUnchangedException("Password yang dimasukkan tidak boleh sama dengan password sebelumnya");
        }
        userRepo.changePassword(cosmic, newPassword);
    }

    @Override
    public void logout() {
        // no-op
    }
}