package burhanfess.exceptions;

public class PasswordUnchangedException extends RuntimeException {
    public PasswordUnchangedException(String message) {
        super(message);
    }
}