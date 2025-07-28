package burhanfess.users;

public abstract class User {
    private static int idGenerator = 0;
    protected int id;
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.id = idGenerator++;
        this.username = username;
        this.password = password;
    }

    public abstract String getRole();

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nUsername: " + username + "\nRole: " + getRole();
    }
}