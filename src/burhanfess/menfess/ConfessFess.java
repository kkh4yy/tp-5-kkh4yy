package burhanfess.menfess;

import burhanfess.users.User;

public class ConfessFess extends Menfess {
    private User receiver;

    public ConfessFess(User user, String content, User receiver) {
        super(user, content);
        this.receiver = receiver;
    }

    public User getReceiver() {
        return receiver;
    }

    @Override
    public String getType() {
        return "Confession";
    }

    @Override
    public String toString() {
        return "[" + getType() + "] oleh anonim\n"
             + "ID: " + id + "\n" + content + "\n"
             + "Dikirim pada " + formatter.format(timestamp);
    }
}