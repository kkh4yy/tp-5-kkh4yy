package burhanfess.services;

public interface UnauthorizedService {
    void register(String username, String password);
    void login(String username, String password);
    void exit();
}
