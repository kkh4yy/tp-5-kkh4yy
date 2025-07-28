package burhanfess.menfess;

import burhanfess.users.User;

public class CurhatFess extends Menfess {
    public CurhatFess(User user, String content) {
        super(user, content);
    }

    @Override
    public String getType() {
        return "Curhat";
    }
}
