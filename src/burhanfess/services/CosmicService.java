package burhanfess.services;

import java.util.List;
import burhanfess.menfess.Menfess;

public interface CosmicService {
    void sendCurhatFess(String content);
    void sendPromosiFess(String content);
    void sendConfessFess(String content, String receiverUsername);
    List<Menfess> getAllUnhiddenMenfesses();
    List<Menfess> getAllSentMenfesses();
    List<Menfess> getAllReceivedMenfesses();
    void changePassword(String newPassword);
    void logout();
}
