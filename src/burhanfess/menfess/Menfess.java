package burhanfess.menfess;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import burhanfess.users.User;

public abstract class Menfess {
    private static int idGenerator = 0;
    protected int id;
    protected LocalDateTime timestamp;
    protected String content;
    protected boolean isHidden;
    protected User user;
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public Menfess(User user, String content) {
        this.id = idGenerator++;
        this.user = user;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.isHidden = false;
    }

    public User getUser() {
        return user;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public int getId() {
        return id;
    }

    public void hide() {
        isHidden = true;
    }

    public void unhide() {
        isHidden = false;
    }

    public abstract String getType();

    @Override
    public String toString() {
        String author = this instanceof ConfessFess ? "anonim" : user.getUsername();
        return "[" + getType() + "] oleh " + author + "\n"
             + "ID: " + id + "\n" + content + "\n"
             + "Dikirim pada " + formatter.format(timestamp);
    }
}